package com.tvkapps.connect.models;

public class ModelPost {
    String uid, uName, uEmail,pLikes, uDp, pId, pTitle, pDescr,pComments,  pImage, pTime;


    public ModelPost() {
    }


    public ModelPost(String uid, String uName, String uEmail, String pLikes, String uDp, String pId, String pTitle, String pDescr, String pComments, String pImage, String pTime) {
        this.uid = uid;
        this.uName = uName;
        this.uEmail = uEmail;
        this.pLikes = pLikes;
        this.uDp = uDp;
        this.pId = pId;
        this.pTitle = pTitle;
        this.pDescr = pDescr;
        this.pComments = pComments;
        this.pImage = pImage;
        this.pTime = pTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getpLikes() {
        return pLikes;
    }

    public void setpLikes(String pLikes) {
        this.pLikes = pLikes;
    }

    public String getuDp() {
        return uDp;
    }

    public void setuDp(String uDp) {
        this.uDp = uDp;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpDescr() {
        return pDescr;
    }

    public void setpDescr(String pDescr) {
        this.pDescr = pDescr;
    }

    public String getpComments() {
        return pComments;
    }

    public void setpComments(String pComments) {
        this.pComments = pComments;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    @Override
    public String toString() {
        return "ModelPost{" +
                "uid='" + uid + '\'' +
                ", uName='" + uName + '\'' +
                ", uEmail='" + uEmail + '\'' +
                ", pLikes='" + pLikes + '\'' +
                ", uDp='" + uDp + '\'' +
                ", pId='" + pId + '\'' +
                ", pTitle='" + pTitle + '\'' +
                ", pDescr='" + pDescr + '\'' +
                ", pComments='" + pComments + '\'' +
                ", pImage='" + pImage + '\'' +
                ", pTime='" + pTime + '\'' +
                '}';
    }
}
