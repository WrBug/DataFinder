package com.wrbug.datafinder.server.vo;

import com.google.gson.annotations.SerializedName;
import com.wrbug.datafinder.server.type.IconType;

public class FilePreviewInfo {
    @SerializedName("type")
    private IconType type;
    @SerializedName("raw")
    private String raw;
    @SerializedName("formatData")
    private Object formatData;

    public IconType getType() {
        return type;
    }

    public void setType(IconType type) {
        this.type = type;
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
