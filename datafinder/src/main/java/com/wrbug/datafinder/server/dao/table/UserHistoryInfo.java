package com.wrbug.datafinder.server.dao.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "user_history_info")
public class UserHistoryInfo {
    @Id
    @Property(nameInDb = "h_id")
    private String hId;
    @Property(nameInDb = "path")
    private String path;
    @Index
    @Property(nameInDb = "deviceId")
    private String deviceId;
    @Property(nameInDb = "update_time")
    private long updateTime;
    @Generated(hash = 1115858305)
    public UserHistoryInfo(String hId, String path, String deviceId,
            long updateTime) {
        this.hId = hId;
        this.path = path;
        this.deviceId = deviceId;
        this.updateTime = updateTime;
    }
    @Generated(hash = 959562572)
    public UserHistoryInfo() {
    }
    public String getHId() {
        return this.hId;
    }
    public void setHId(String hId) {
        this.hId = hId;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public long getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

}
