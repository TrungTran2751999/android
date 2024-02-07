package com.huewaco.cskh.objects;

import com.huewaco.cskh.helper.CommonHelper;

import java.util.Date;

public class ContractDetailObject {
    private int ContractYear;
    private String ContractNo;
    private Date ContractIssueDate;
    private String EContractPdf;
    private String ContractStatus;
    private String MaDDK;
    private String CustomerSignerName;
    private String CustomerSignerNote;
    private String BuyerName;
    public int getContractYear() {
        return ContractYear;
    }

    public void setContractYear(int contractYear) {
        ContractYear = contractYear;
    }

    public String getContractNo() {
        return ContractNo;
    }

    public void setContractNo(String contractNo) {
        ContractNo = contractNo;
    }

    public Date getContractIssueDate() {
        return ContractIssueDate;
    }

    public void setContractIssueDate(Date contractIssueDate) {
        ContractIssueDate = contractIssueDate;
    }
    public String getContratus() {
        return ContractStatus;
    }

    public void setContractStatus(String contractStatus) {
        ContractStatus = contractStatus;
    }
    public String getEContractPdf() {
        return EContractPdf;
    }

    public void setEContractPdf(String EContractPdf) {
        this.EContractPdf = EContractPdf;
    }
    public String getMaDDK() {
        return MaDDK;
    }

    public void setMaDDK(String maDDK) {
        MaDDK = maDDK;
    }
    public String getCustomerSignerName() {
        return CustomerSignerName == "null" ? "": CustomerSignerName;
    }

    public void setCustomerSignerName(String customerSignerName) {
        CustomerSignerName = customerSignerName;
    }
    public String getCustomerSignerNote() {
        return CustomerSignerNote == "null" ? "":CustomerSignerNote;
    }

    public void setCustomerSignerNote(String customerSignerNote) {
        CustomerSignerNote = customerSignerNote;
    }
    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }
}
