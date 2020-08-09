package com.lemon.api.auto.pojo;

public class WriteBackData {

    private String result;
    private String sheetName;
    private String rowIdentifier;
    private  String cellName;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getrowIdentifier() {
        return rowIdentifier;
    }

    public void setrowIdentifier(String caseId) {
        this.rowIdentifier = caseId;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public WriteBackData(String result, String sheetName, String rowIdentifier, String cellName) {
        this.result = result;
        this.sheetName = sheetName;
        this.rowIdentifier = rowIdentifier;
        this.cellName = cellName;
    }

    public WriteBackData() {
    }
}
