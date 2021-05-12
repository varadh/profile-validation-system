package com.quickbooks.profileservice.entity;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(columnList = "taxIdentifier, id")
})
public class ProfileStatus {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="taxIdentifier")
    private String taxIdentifier;

    @Column(name="operation")
    private String operationType;

    @Column(name="status")
    private String status;

    @Column(name="additionalInformation")
    private String additionalInformation;

    public String getTaxIdentifier() {
        return taxIdentifier;
    }

    public void setTaxIdentifier(String taxIdentifier) {
        this.taxIdentifier = taxIdentifier;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
