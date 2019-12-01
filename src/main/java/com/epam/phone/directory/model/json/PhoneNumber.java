package com.epam.phone.directory.model.json;

public class PhoneNumber {
    Long id;
    String value;
    Long userId;
    Long companyId;

    public com.epam.phone.directory.model.db.PhoneNumber toJPA() {
        return com.epam.phone.directory.model.db.PhoneNumber.newBuilder()
                .withId(this.id)
                .withValue(this.value)
                .build();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
