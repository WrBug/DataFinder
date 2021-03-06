package com.wrbug.datafinder.server.vo;

import com.google.gson.annotations.SerializedName;
import com.wrbug.datafinder.preview.PreviewManager;
import com.wrbug.datafinder.server.api.DownloadController;
import com.wrbug.datafinder.server.download.FileCache;
import com.wrbug.datafinder.util.MD5Utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * class: FileInfo
 * author: wrbug
 * date: 2019-12-30
 * description:
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
    @SerializedName("fileId")
    private int fileId;

    public FileInfo(File path) {
        this(path, true);
    }

    public FileInfo(File path, boolean calcMd5) {
        super(path);
        readable = path.canRead();
        writable = path.canWrite();
        modifyTime = path.lastModified();
        size = path.length();
        preview = PreviewManager.match(path);
        createDownloadUrl();
        if (calcMd5) {
            md5 = MD5Utils.encode(path);
        }
    }

    private void createDownloadUrl() {
        int downloadId = FileCache.getId(file);
        fileId = downloadId;
        downloadUrl = DownloadController.downloadPath + "/" + file.getName() + "?id=" + downloadId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
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
