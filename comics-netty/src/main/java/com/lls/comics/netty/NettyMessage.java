package com.lls.comics.netty;

/************************************
 * NettyMessage
 * @author liliangshan
 * @date 2019-02-14
 ************************************/
public class NettyMessage {
    private boolean requested;
    private long requestId;
    private byte[] data;

    public NettyMessage(boolean requested, long requestId, byte[] data) {
        this.requested = requested;
        this.requestId = requestId;
        this.data = data;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
