package com.wrbug.datafinder.server.vo;

import com.google.gson.annotations.SerializedName;
import com.wrbug.datafinder.server.type.FileType;
import com.wrbug.datafinder.server.type.IconType;

import java.io.File;

public class HomeInfoVo {
    @SerializedName("name")
    private String name;
    @SerializedName("iconType")
    private IconType iconType;
    @SerializedName("path")
    private String path;
    @SerializedName("fileType")
    private FileType fileType;
    @SerializedName("readable")
    private boolean readable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IconType getIconType() {
        return iconType;
    }

    public void setIconType(IconType iconType) {
        this.iconType = iconType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        setPath(new File(path));
    }

    public void setPath(File path) {
        this.path = path.getAbsolutePath();
        readable = path.exists() && path.canRead();
        fileType = path.isFile() ? FileType.FILE : FileType.DIRECTORY;
    }

    public boolean isReadable() {
        return readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
