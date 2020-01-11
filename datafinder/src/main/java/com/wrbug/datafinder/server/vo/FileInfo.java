package com.wrbug.datafinder.server.vo;

import com.google.gson.annotations.SerializedName;
import com.wrbug.datafinder.preview.PreviewManager;
import com.wrbug.datafinder.server.api.DownloadController;
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
    @SerializedName("readable")
    private boolean readable;
    @SerializedName("writable")
    private boolean writable;
    @SerializedName("modifyTime")
    private long modifyTime;
    @SerializedName("preview")
    private boolean preview;
    @SerializedName("size")
    private long size;
    @SerializedName("md5")
    private String md5;
    @SerializedName("downloadUrl")
    private String downloadUrl;

    public FileInfo(File path) {
        super(path);
        readable = path.canRead();
        writable = path.canWrite();
        modifyTime = path.lastModified();
        size = path.length();
        preview = PreviewManager.match(path);
        createDownloadUrl();
        md5 = MD5Utils.encode(path);
    }

    private void createDownloadUrl() {
        int downloadId = DownloadCache.getDownloadId(file);
        downloadUrl = DownloadController.downloadPath + "/" + file.getName() + "?id=" + downloadId;
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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean isReadable() {
        return readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
}
