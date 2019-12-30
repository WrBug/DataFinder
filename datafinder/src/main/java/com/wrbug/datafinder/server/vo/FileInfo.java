package com.wrbug.datafinder.server.vo;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * class: FileInfo
 * author: wrbug
 * date: 2019-12-30
 * description：
 */
public class FileInfo extends BaseFileInfo {

    private boolean readable;
    private boolean writable;
    private long modifyTime;
    private long size;

    public FileInfo(File path) {
        super(path);
        readable = path.canRead();
        writable = path.canWrite();
        modifyTime = path.lastModified();
        size = path.length();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Override
    public String getPath() {
        return path;
    }

    @Override
    public long getSize() {
        return size;
    }
}
