package com.wrbug.datafinder.server.vo;

import androidx.annotation.Keep;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * class: DirectoryInfo
 * author: wrbug
 * date: 2019-12-30
 * description：
 */
public class DirectoryInfo extends BaseFileInfo {
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
        private int total;
        private int directoryCount;
        private int fileCount;
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
