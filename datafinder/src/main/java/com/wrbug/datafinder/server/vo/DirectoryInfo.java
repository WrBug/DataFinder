package com.wrbug.datafinder.server.vo;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * class: DirectoryInfo
 * author: wrbug
 * date: 2019-12-30
 * description：
 */
public class DirectoryInfo extends BaseFileInfo {
    @SerializedName("children")
    private ChildrenInfo children;

    public DirectoryInfo(File path) {
        super(path);
        children = new ChildrenInfo(path);
    }

    @NotNull
    @Override
    public String getPath() {
        return path;
    }

    @Override
    public long getSize() {
        return children.size;
    }

    @Keep
    public static class ChildrenInfo {
        @SerializedName("total")
        private int total;
        @SerializedName("directoryCount")
        private int directoryCount;
        @SerializedName("fileCount")
        private int fileCount;
        @SerializedName("size")
        private long size;

        ChildrenInfo(File file) {
            total = file.listFiles().length;
            for (File listFile : file.listFiles()) {
                if (listFile.isFile()) {
                    size = listFile.length();
                    fileCount++;
                } else {
                    directoryCount++;
                }
            }
        }
    }
}
