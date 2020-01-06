package com.wrbug.datafinder.server.vo;

import com.wrbug.datafinder.preview.PreviewManager;
import com.wrbug.datafinder.server.download.DownloadCache;
import com.wrbug.datafinder.util.MD5Utils;

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
    private boolean preview;
    private long size;
    private int downloadId;
    private String md5;

    public FileInfo(File path) {
        super(path);
        readable = path.canRead();
        writable = path.canWrite();
        modifyTime = path.lastModified();
        size = path.length();
        preview = PreviewManager.match(path);
        downloadId = DownloadCache.getDownloadId(file);
        md5 = MD5Utils.encode(path);
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

    public boolean isPreview() {
        return preview;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public int getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(int downloadId) {
        this.downloadId = downloadId;
    }
}
