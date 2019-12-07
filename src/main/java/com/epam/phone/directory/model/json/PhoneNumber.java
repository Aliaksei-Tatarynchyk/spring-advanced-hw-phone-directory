package com.epam.phone.directory.model.json;

public class PhoneNumber {
    Long id;
    String value;
    Long userId;
    Long companyId;

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public com.epam.phone.directory.model.db.PhoneNumber toJPA() {
        return com.epam.phone.directory.model.db.PhoneNumber.newBuilder()
                .withId(this.id)
                .withValue(this.value)
                .build();
    }
}
