package com.wrbug.datafinder.server.vo;


import android.support.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * class: DirectoryInfo
 * author: wrbug
 * date: 2019-12-30
 * description：
 */
public class DirectoryInfo extends BaseFileInfo {
    @SerializedName("info")
    private ChildrenInfo info;
    @SerializedName("parent")
    private String parent;
    @SerializedName("children")
    private List<BaseFileInfo> children;

    public DirectoryInfo(File path) {
        this(path, true);
    }

    private DirectoryInfo(File path, boolean calcChildren) {
        super(path);
        parent = path.getParent();
        if (calcChildren) {
            info = new ChildrenInfo(path);
        }
    }

    public List<BaseFileInfo> getChildren() {
        return children;
    }

    public void setChildren(List<BaseFileInfo> children) {
        this.children = children;
    }

    @NotNull
    @Override
    public String getPath() {
        return path;
    }

    @Override
    public long getSize() {
        return info.size;
    }

    @Keep
    public class ChildrenInfo {
        @SerializedName("total")
        private int total;
        @SerializedName("directoryCount")
        private int directoryCount;
        @SerializedName("fileCount")
        private int fileCount;
        @SerializedName("size")
        private long size;

        ChildrenInfo(File file) {
            children = new ArrayList<>();
            total = file.listFiles().length;
            for (File listFile : file.listFiles()) {
                if (listFile.isFile()) {
                    children.add(new FileInfo(listFile, false));
                    size += listFile.length();
                    fileCount++;
                } else {
                    children.add(new DirectoryInfo(listFile, false));
                    directoryCount++;
                }
            }
        }
    }
}
