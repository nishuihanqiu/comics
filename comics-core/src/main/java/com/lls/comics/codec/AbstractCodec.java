package com.lls.comics.codec;

import com.lls.comics.common.ComicsConstants;
import com.lls.comics.common.URL;
import com.lls.comics.exception.ComicsException;
import com.lls.comics.rpc.Request;
import com.lls.comics.rpc.Response;
import com.lls.comics.serializer.Serializer;

import java.io.*;

/************************************
 * AbstractCodec
 * @author liliangshan
 * @date 2019-01-31
 ************************************/
public abstract class AbstractCodec implements Codec {

    @Override
    public byte[] encode(URL url, Object message) throws IOException {
        try {
            if (message instanceof Request) {
                return encodeRequest(url, (Request) message);
            }
            if (message instanceof Response) {
                return encodeResponse(url, (Response) message);
            }
        } catch (Exception e) {
            if (e instanceof IOException) {
                throw e;
            }
            throw new ComicsException(this.getClass().getSimpleName() + "encode message error", e);
        }

        throw new ComicsException(this.getClass().getSimpleName() + "encoded message must be an instance of request or response.");
    }

    @Override
    public Object decode(URL url, byte messageType, byte[] bytes) throws IOException {
        beforeDecodeValidation(bytes);
        byte dataType = getDataType(bytes);
        boolean isResponse = dataType != ComicsConstants.FLAG_REQUEST;
        byte[] body = getBody(bytes);
        long requestId = getRequestId(bytes);
        try {
            if (isResponse) {
                return decodeResponse(body, dataType, requestId, url);
            }
            return decodeRequest(body, requestId, url);
        } catch (ClassNotFoundException e) {
            throw new ComicsException(this.getClass().getSimpleName() + "decode " + (isResponse ? "response" : "request") +
                " error: class not found.", e);
        } catch (Exception e) {
            if (e instanceof IOException) {
                throw e;
            }
            if (e instanceof ComicsException) {
                throw e;
            }
            throw new ComicsException(this.getClass().getSimpleName() + "decode " + (isResponse ? "response" : "request") +
                " error:" + e.getMessage(), e);
        }
    }

    protected void serialize(ObjectOutput output, Object message, Serializer serializer) throws IOException {
        if (message == null) {
            output.writeObject(null);
            return;
        }
        output.writeObject(serializer.serialize(message));
    }

    protected Object deserialize(byte[] values, Class<?> clazz, Serializer serializer) throws IOException {
        if (values == null) {
            return null;
        }
        return serializer.deserialize(values, clazz);
    }

    protected ObjectOutput createObjectOutput(OutputStream stream) {
        try {
            return new ObjectOutputStream(stream);
        } catch (IOException e) {
            throw new ComicsException(this.getClass().getSimpleName() + "create objectOutput error", e);
        }
    }

    protected ObjectInput createObjectInput(InputStream stream) {
        try {
            return new ObjectInputStream(stream);
        } catch (IOException e) {
            throw new ComicsException(this.getClass().getSimpleName() + "create objectInput error", e);
        }
    }

    protected abstract byte[] encodeRequest(URL url, Request request) throws IOException;

    protected abstract byte[] encodeResponse(URL url, Response response) throws IOException;

    protected abstract Object decodeRequest(byte[] body, long requestId, URL url) throws IOException, ClassNotFoundException;

    protected abstract Object decodeResponse(byte[] body, byte dataType, long requestId, URL url) throws IOException,
        ClassNotFoundException;

    protected abstract void beforeDecodeValidation(byte[] body);

    protected abstract byte[] getBody(byte[] body);

    protected abstract byte getDataType(byte[] body);

    protected abstract long getRequestId(byte[] body);

    protected abstract Serializer getSerializer(URL url);


}
