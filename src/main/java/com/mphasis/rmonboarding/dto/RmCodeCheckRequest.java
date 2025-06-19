package com.mphasis.rmonboarding.dto;

public class RmCodeCheckRequest {
    private String rmCode;

    public RmCodeCheckRequest() {}

    public RmCodeCheckRequest(String rmCode) {
        this.rmCode = rmCode;
    }

    public String getRmCode() {
        return rmCode;
    }

    public void setRmCode(String rmCode) {
        this.rmCode = rmCode;
    }
}
