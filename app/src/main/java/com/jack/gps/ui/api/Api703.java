package com.jack.gps.ui.api;

public class Api703 extends Api {

    public static final String SUPPORT_VERSION_NAME = "7.0.3";

    Api703() {
        this.storage_MsgInfoStorage_class = "com.tencent.mm.storage.bj";
        this.storage_MsgInfoStorage_insert_method = "c";
        this.storage_MsgInfo_class = "com.tencent.mm.storage.bi";
    }

    @Override
    public String getSupportVersionName() {
        return SUPPORT_VERSION_NAME;
    }
}
