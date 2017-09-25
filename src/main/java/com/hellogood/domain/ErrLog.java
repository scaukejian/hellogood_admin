package com.hellogood.domain;

import java.util.Date;

public class ErrLog {
    private Integer id;

    private String empName;

    private Integer empId;

    private String type;

    private Integer targetId;

    private Date operationTime;

    private Integer processor;

    private Date processTime;

    private String processStatus;

    private String remark;

    public ErrLog() {
    }

    public ErrLog(String empName, Integer empId, String type, Integer targetId, String remark) {
        this.empName = empName;
        this.empId = empId;
        this.type = type;
        this.targetId = targetId;
        this.operationTime = new Date();
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getProcessor() {
        return processor;
    }

    public void setProcessor(Integer processor) {
        this.processor = processor;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus == null ? null : processStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}