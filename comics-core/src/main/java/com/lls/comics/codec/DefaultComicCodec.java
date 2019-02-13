package com.lls.comics.codec;

import com.lls.comics.common.ComicsConstants;
import com.lls.comics.common.URL;
import com.lls.comics.common.URLParamType;
import com.lls.comics.core.extension.ExtensionLoader;
import com.lls.comics.exception.ComicsException;
import com.lls.comics.rpc.DefaultRequest;
import com.lls.comics.rpc.DefaultResponse;
import com.lls.comics.rpc.Request;
import com.lls.comics.rpc.Response;
import com.lls.comics.serializer.Serializer;
import com.lls.comics.util.ByteUtils;
import com.lls.comics.util.ReflectUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/************************************
 * DefaultComicCodec
 * @author liliangshan
 * @date 2019-01-31
 ************************************/
public class DefaultComicCodec extends AbstractCodec {

    private static final int HEADER_LENGTH = 16;
    private static final short MAGIC = (short) 0xF0F0;
    private static final byte MASK = 0x07;

    @Override
    protected byte[] encodeRequest(URL url, Request request) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutput output = createObjectOutput(outputStream);
        output.writeUTF(request.getInterfaceName());
        output.writeUTF(request.getMethodName());
        output.writeUTF(request.getArgumentDesc());
        Serializer serializer = getSerializer(url);
        if (request.getArguments() != null && request.getArguments().length > 0) {
            for (Object object : request.getArguments()) {
                serialize(output, object, serializer);
            }
        }
        if (request.getAttachments() == null || request.getAttachments().isEmpty()) {
            output.writeInt(0);
        } else {
            output.writeInt(request.getAttachments().size());
            for (Map.Entry<String, String> entry : request.getAttachments().entrySet()) {
                output.writeUTF(entry.getKey());
                output.writeUTF(entry.getValue());
            }
        }

        output.flush();
        byte[] body = outputStream.toByteArray();
        byte flag = ComicsConstants.FLAG_REQUEST;
        output.close();
        return encode(body, flag, request.getRequestId());
    }

    /**
     * 数据协议：
     *
     * <pre>
     *
     * header:  16个字节
     *
     * 0-15 bit 	:  magic
     * 16-23 bit	:  flag , 其中： 21-22 bit: event 可支持4种event，比如normal, exception等,  23 bit : 0 is request , 1 is response
     * 24-87 bit 	:  request id
     * 88-119 bit 	:  body content length
     * 120-127      :  reserve content
     * </pre>
     *
     * @param body      body
     * @param flag      flag
     * @param requestId requestId
     * @return bytes
     * @throws IOException e
     */
    private byte[] encode(byte[] body, byte flag, long requestId) throws IOException {
        byte[] header = new byte[HEADER_LENGTH];
        int offset = 0;

        // 0~15bit: magic
        ByteUtils.short2bytes(MAGIC, header, offset);
        offset += 2;

        // 16~23bit: flag
        header[offset++] = flag;

        // 24~87bit: requestId
        ByteUtils.long2bytes(requestId, header, offset);
        offset += 8;

        // 88~119bit
        ByteUtils.int2bytes(body.length, header, offset);

        byte[] data = new byte[header.length + body.length];
        System.arraycopy(header, 0, data, 0, header.length);
        System.arraycopy(body, 0, data, header.length, body.length);
        return data;
    }

    @Override
    protected byte[] encodeResponse(URL url, Response response) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutput output = createObjectOutput(outputStream);
        output.writeLong(response.getCreatedTimeMills());
        byte flag;
        Serializer serializer = getSerializer(url);

        if (response.getException() != null) {
            output.writeUTF(response.getException().getClass().getName());
            serialize(output, response.getValue(), serializer);
            flag = ComicsConstants.FLAG_RESPONSE_EXCEPTION;
        } else if (response.getValue() == null) {
            flag = ComicsConstants.FLAG_RESPONSE_VOID;
        } else {
            output.writeUTF(response.getValue().getClass().getName());
            serialize(output, response.getValue(), serializer);
            flag = ComicsConstants.FLAG_RESPONSE;
        }

        output.flush();
        byte[] body = outputStream.toByteArray();
        output.close();
        return encode(body, flag, response.getRequestId());
    }

    @Override
    protected Object decodeRequest(byte[] body, long requestId, URL url) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        ObjectInput input = createObjectInput(inputStream);
        String interfaceName = input.readUTF();
        String methodName = input.readUTF();
        String argumentDesc = input.readUTF();

        DefaultRequest request = new DefaultRequest();
        request.setInterfaceName(interfaceName);
        request.setMethodName(methodName);
        request.setArgumentDesc(argumentDesc);
        request.setRequestId(requestId);
        Serializer serializer = getSerializer(url);
        request.setArguments(this.decodeRequestArguments(input, argumentDesc, serializer));
        request.setAttachments(this.decodeRequestAttachments(input));

        input.close();
        return request;
    }

    @Override
    protected Object decodeResponse(byte[] body, byte dataType, long requestId, URL url) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        ObjectInput input = createObjectInput(inputStream);
        long createdTimeMills = input.readLong();

        DefaultResponse response = new DefaultResponse();
        response.setCreatedTimeMills(createdTimeMills);
        response.setRequestId(requestId);

        if (dataType == ComicsConstants.FLAG_RESPONSE_VOID) {
            return response;
        }

        String className = input.readUTF();
        Class<?> clz = ReflectUtils.forName(className);
        Serializer serializer = getSerializer(url);
        Object result = deserialize((byte[]) input.readObject(), clz, serializer);
        if (dataType == ComicsConstants.FLAG_RESPONSE) {
            response.setValue(result);
        } else if (dataType == ComicsConstants.FLAG_RESPONSE_EXCEPTION) {
            response.setException((Exception) result);
        } else {
            throw new ComicsException("decode error: response dataType not support ");
        }

        response.setRequestId(requestId);
        input.close();
        return response;
    }

    @Override
    protected void beforeDecodeValidation(byte[] body) {
        if (body.length <= HEADER_LENGTH) {
            throw new ComicsException("decode error, due to bytes format.");
        }
        short type = ByteUtils.bytes2short(body, 0);
        if (type != MAGIC) {
            throw new ComicsException("decode error, due to magic value");
        }
        int bodyLength = ByteUtils.bytes2int(body, 11);
        if (body.length != (HEADER_LENGTH + bodyLength)) {
            throw new ComicsException("decode error, due to content length.");
        }
    }

    @Override
    protected byte[] getBody(byte[] body) {
        int bodyLength = ByteUtils.bytes2int(body, 11);
        byte[] bytes = new byte[bodyLength];
        System.arraycopy(body, HEADER_LENGTH, bytes, 0, bodyLength);
        return bytes;
    }

    @Override
    protected byte getDataType(byte[] body) {
        byte flag = body[3];
        return (byte) (flag & MASK);
    }

    @Override
    protected long getRequestId(byte[] body) {
        return ByteUtils.bytes2long(body, 4);
    }

    @Override
    protected Serializer getSerializer(URL url) {
        String serializerName = url.getArgument(URLParamType.SERIALIZER.getName(), URLParamType.SERIALIZER.getValue());
        return ExtensionLoader.getExtensionLoader(Serializer.class).getExtension(serializerName);
    }

    private Object[] decodeRequestArguments(ObjectInput input, String argumentDesc, Serializer serializer) throws IOException,
        ClassNotFoundException {
        if (argumentDesc == null || argumentDesc.isEmpty()) {
            return null;
        }

        Class<?>[] classTypes = ReflectUtils.forNames(argumentDesc);
        Object[] arguments = new Object[classTypes.length];
        for (int i = 0; i < classTypes.length; i++) {
            arguments[i] = deserialize((byte[]) input.readObject(), classTypes[i], serializer);
        }
        return arguments;
    }

    private Map<String, String> decodeRequestAttachments(ObjectInput input) throws IOException, ClassNotFoundException {
        int size = input.readInt();
        if (size <= 0) {
            return null;
        }

        Map<String, String> attachments = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            attachments.put(input.readUTF(), input.readUTF());
        }
        return attachments;
    }

}
