package com.rikardo308.sharecontacts.Model;

public class EmployeeInfo {
    private String FirstName;
    private String PhoneNo;

    public EmployeeInfo(){}

    public EmployeeInfo(String firstName, String phoneNo) {
        FirstName = firstName;
        PhoneNo   = phoneNo;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }
}