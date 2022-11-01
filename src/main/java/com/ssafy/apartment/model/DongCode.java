package com.ssafy.apartment.model;

public class DongCode {
    private String dongCode;
    private String sidoName;
    private String gugunName;
    private String dongName;

    public DongCode() {
    }

    public DongCode(String dongCode, String sidoName, String gugunName, String dongName) {
        this.dongCode = dongCode;
        this.sidoName = sidoName;
        this.gugunName = gugunName;
        this.dongName = dongName;
    }

    public String getDongCode() {
        return dongCode;
    }

    public void setDongCode(String dongCode) {
        this.dongCode = dongCode;
    }

    public String getSidoName() {
        return sidoName;
    }

    public void setSidoName(String sidoName) {
        this.sidoName = sidoName;
    }

    public String getGugunName() {
        return gugunName;
    }

    public void setGugunName(String gugunName) {
        this.gugunName = gugunName;
    }

    public String getDongName() {
        return dongName;
    }

    public void setDongName(String dongName) {
        this.dongName = dongName;
    }

    @Override
    public String toString() {
        return "DongCode [dongCode=" + dongCode + ", dongName=" + dongName + ", gugunName=" + gugunName + ", sidoName="
                + sidoName + "]";
    }

}