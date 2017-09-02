package com.lightwind.connactionmysqltest;

/**
 * 功能：检验实体类
 * 作者：刘洋
 * 时间：2017/9/1
 */

public class ResultNumber {
    // 检验结果
    private int inspectorCheckResult;
    // 设备工作台编号
    private int workbenchCode;

    public int getWorkbenchCode() {
        return workbenchCode;
    }

    public void setWorkbenchCode(int workbenchCode) {
        this.workbenchCode = workbenchCode;
    }

    public int getInspectorCheckResult() {
        return inspectorCheckResult;
    }

    public void setInspectorCheckResult(int inspectorCheckResult) {
        this.inspectorCheckResult = inspectorCheckResult;
    }
}
