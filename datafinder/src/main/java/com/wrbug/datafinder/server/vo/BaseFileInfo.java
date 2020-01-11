package com.wrbug.datafinder.server.vo;

import com.google.gson.annotations.SerializedName;
import com.wrbug.datafinder.server.type.FileType;
import com.wrbug.datafinder.server.type.IconType;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * class: BaseFileInfo
 * author: wrbug
 * date: 2019-12-30
 * description：
 */
public abstract class BaseFileInfo implements IFileInfo {
    @SerializedName("name")
    protected String name;
    protected transient File file;
    @SerializedName("path")
    protected String path;
    @SerializedName("type")
    protected IconType type;
    @SerializedName("fileType")
    protected FileType fileType;

    public BaseFileInfo(File path) {
        this.file = path;
        this.name = path.getName();
        this.path = file.getAbsolutePath();
        type = IconType.get(path);
        fileType = path.isFile() ? FileType.FILE : FileType.DIRECTORY;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @NotNull
    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public IconType getType() {
        return type;
    }

    public void setType(IconType type) {
        this.type = type;
    }
}
