package com.wrbug.datafinder.server.vo;

import com.wrbug.datafinder.server.type.FileType;
import java.io.File;

/**
 * class: BaseFileInfo
 * author: wrbug
 * date: 2019-12-30
 * description：
 */
public abstract class BaseFileInfo implements IFileInfo {
    protected String name;
    protected transient File file;
    protected String path;
    protected FileType type;

    public BaseFileInfo(File path) {
        this.file = path;
        this.name = path.getName();
        this.path = file.getAbsolutePath();
        type = path.isDirectory() ? FileType.DIRECTORY : FileType.FILE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }


}
