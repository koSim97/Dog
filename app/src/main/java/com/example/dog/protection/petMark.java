package com.example.dog.protection;

public class petMark {
    String popfile;
    String kindCd;
    String info;
    String noticeNo;
    String happenPlace;
    String specialMark;
    String happenDt;
    String noticeDt;
    String careNm;
    String careTel;
    String careAddr;


    public petMark() {
    }

    public petMark(String popfile, String kindCd, String info, String noticeNo, String happenPlace, String specialMark, String happenDt, String noticeDt, String careNm, String careTel, String careAddr) {
        this.popfile = popfile;
        this.kindCd = kindCd;
        this.info = info;
        this.noticeNo = noticeNo;
        this.happenPlace = happenPlace;
        this.specialMark = specialMark;
        this.happenDt = happenDt;
        this.noticeDt = noticeDt;
        this.careNm = careNm;
        this.careTel = careTel;
        this.careAddr = careAddr;

    }

    public String getPopfile() {
        return popfile;
    }

    public void setPopfile(String popfile) {
        this.popfile = popfile;
    }

    public String getKindCd() {
        return kindCd;
    }

    public void setKindCd(String kindCd) {
        this.kindCd = kindCd;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getHappenPlace() {
        return happenPlace;
    }

    public void setHappenPlace(String happenPlace) {
        this.happenPlace = happenPlace;
    }

    public String getSpecialMark() {
        return specialMark;
    }

    public void setSpecialMark(String specialMark) {
        this.specialMark = specialMark;
    }

    public String getHappenDt() {
        return happenDt;
    }

    public void setHappenDt(String happenDt) {
        this.happenDt = happenDt;
    }

    public String getNoticeDt() {
        return noticeDt;
    }

    public void setNoticeDt(String noticeDt) {
        this.noticeDt = noticeDt;
    }

    public String getCareNm() {
        return careNm;
    }

    public void setCareNm(String careNm) {
        this.careNm = careNm;
    }

    public String getCareTel() {
        return careTel;
    }

    public void setCareTel(String careTel) {
        this.careTel = careTel;
    }

    public String getCareAddr() {
        return careAddr;
    }

    public void setCareAddr(String careAddr) {
        this.careAddr = careAddr;
    }

}