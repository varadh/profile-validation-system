package com.quickbooks.profileservice.entity;


import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(columnList = "taxIdentifier")
})
public class Profile {
        @Id
        @Column(name="taxIdentifier")
        private String taxIdentifier;

        @Column(name="companyName")
        private String companyName;

        @Column(name="legalName")
        private String legalName;

        @OneToOne(cascade = CascadeType.ALL)
        private Address businessAddress;

        @OneToOne(cascade = CascadeType.ALL)
        private Address legalAddress;

        @Column(name="email")
        private String email;

        @Column(name="website")
        private String website;

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getLegalName() {
            return legalName;
        }

        public void setLegalName(String legalName) {
            this.legalName = legalName;
        }

        public Address getBusinessAddress() {
            return businessAddress;
        }

        public void setBusinessAddress(Address businessAddress) {
            this.businessAddress = businessAddress;
        }

        public Address getLegalAddress() {
            return legalAddress;
        }

        public void setLegalAddress(Address legalAddress) {
            this.legalAddress = legalAddress;
        }

        public String getTaxIdentifier() {
            return taxIdentifier;
        }

        public void setTaxIdentifier(String taxIdentifier) {
            this.taxIdentifier = taxIdentifier;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

}
