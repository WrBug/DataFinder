package com.wrbug.datafinder.server.dao.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DownloadInfo {
    @Id
    @Property(nameInDb = "downloadId")
    private Long downloadId;
    @Property(nameInDb = "path")
    private String path;
    @Generated(hash = 1440686837)
    public DownloadInfo(Long downloadId, String path) {
        this.downloadId = downloadId;
        this.path = path;
    }
    @Generated(hash = 327086747)
    public DownloadInfo() {
    }
    public Long getDownloadId() {
        return this.downloadId;
    }
    public void setDownloadId(Long downloadId) {
        this.downloadId = downloadId;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    
   
}
