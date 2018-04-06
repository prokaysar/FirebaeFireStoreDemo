package com.prokaysar.firebaefirestoredemo;

public class DataModel extends PostId{

    String header,desc;

    public DataModel() {
    }

    public DataModel(String header, String desc) {
        this.header = header;
        this.desc = desc;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
