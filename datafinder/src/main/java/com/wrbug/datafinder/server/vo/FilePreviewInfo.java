package com.wrbug.datafinder.server.vo;

import com.google.gson.annotations.SerializedName;
import com.wrbug.datafinder.server.type.MimeType;

public class FilePreviewInfo {
    @SerializedName("mimeType")
    private MimeType mimeType;
    @SerializedName("raw")
    private String raw;
    @SerializedName("formatData")
    private Object formatData;


    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public Object getFormatData() {
        return formatData;
    }

    public void setFormatData(Object formatData) {
        this.formatData = formatData;
    }
}
