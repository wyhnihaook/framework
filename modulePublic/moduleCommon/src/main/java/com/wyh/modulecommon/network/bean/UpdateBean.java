package com.wyh.modulecommon.network.bean;

import com.wyh.modulecommon.utils.BasePojo;

/**
 * Created by 翁益亨 on 2020/1/19.
 */
public class UpdateBean extends BasePojo {
    private int id;

    private int device;

    private String releaseVersion;

    private int buildVersion;

    private int pkgSize;

    private String description;

    private String issueDate;

    private String downloadUrl;

    private int forceUpdate;

    private int version;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setDevice(int device){
        this.device = device;
    }
    public int getDevice(){
        return this.device;
    }
    public void setReleaseVersion(String releaseVersion){
        this.releaseVersion = releaseVersion;
    }
    public String getReleaseVersion(){
        return this.releaseVersion;
    }
    public void setBuildVersion(int buildVersion){
        this.buildVersion = buildVersion;
    }
    public int getBuildVersion(){
        return this.buildVersion;
    }
    public void setPkgSize(int pkgSize){
        this.pkgSize = pkgSize;
    }
    public int getPkgSize(){
        return this.pkgSize;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setIssueDate(String issueDate){
        this.issueDate = issueDate;
    }
    public String getIssueDate(){
        return this.issueDate;
    }
    public void setDownloadUrl(String downloadUrl){
        this.downloadUrl = downloadUrl;
    }
    public String getDownloadUrl(){
        return this.downloadUrl;
    }
    public void setForceUpdate(int forceUpdate){
        this.forceUpdate = forceUpdate;
    }
    public int getForceUpdate(){
        return this.forceUpdate;
    }
    public void setVersion(int version){
        this.version = version;
    }
    public int getVersion(){
        return this.version;
    }

}
