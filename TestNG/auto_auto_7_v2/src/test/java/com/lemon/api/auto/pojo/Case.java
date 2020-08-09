package com.lemon.api.auto.pojo;

public class Case {

    private  String CaseId;
    private String apiId;
    private String desc;
    private String params;
    private  String expectedResponseData;
    private String actualResponseData;
    private  String PreValidataSql;
    private String  PreValidataResult;
    private  String AfterValidateSql;
    private  String AfterValidataResult;

    public String getPreValidataSql() {
        return PreValidataSql;
    }

    public void setPreValidataSql(String preValidataSql) {
        PreValidataSql = preValidataSql;
    }

    public String getPreValidataResult() {
        return PreValidataResult;
    }

    public void setPreValidataResult(String preValidataResult) {
        PreValidataResult = preValidataResult;
    }

    public String getAfterValidateSql() {
        return AfterValidateSql;
    }

    public void setAfterValidateSql(String afterValidateSql) {
        AfterValidateSql = afterValidateSql;
    }

    public String getAfterValidataResult() {
        return AfterValidataResult;
    }

    public void setAfterValidataResult(String afterValidataResult) {
        AfterValidataResult = afterValidataResult;
    }

    public String getCaseId() {
        return CaseId;
    }

    public String getExpectedResponseData() {
        return expectedResponseData;
    }

    public void setExpectedResponseData(String expectedResponseData) {
        this.expectedResponseData = expectedResponseData;
    }

    public String getActualResponseData() {
        return actualResponseData;
    }

    public void setActualResponseData(String actualResponseData) {
        this.actualResponseData = actualResponseData;
    }

    public void setCaseId(String caseId) {
        CaseId = caseId;

    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString(){
        return "caseID="+CaseId+",apiID+"+apiId+",desc="+desc+",params+"+params+
                ",expectedResponseData=『"+expectedResponseData+"』,actualResponseData=『"+actualResponseData+"』";
    }
}
