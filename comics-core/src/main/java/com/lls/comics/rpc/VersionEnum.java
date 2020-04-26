package com.lls.comics.rpc;

/************************************
 * VersionEnum
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public enum VersionEnum {
    VERSION_1((byte) 1, 16);

    private byte version;
    private int headerLength;

    VersionEnum(byte version, int headerLength) {
        this.version = version;
        this.headerLength = headerLength;
    }

    public byte getVersion() {
        return version;
    }

    public int getHeaderLength() {
        return headerLength;
    }
}
