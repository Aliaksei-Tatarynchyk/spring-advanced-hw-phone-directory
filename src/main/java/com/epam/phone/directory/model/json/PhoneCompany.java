package com.epam.phone.directory.model.json;

public class PhoneCompany {
    Long id;
    String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public com.epam.phone.directory.model.db.PhoneCompany toJPA() {
        return com.epam.phone.directory.model.db.PhoneCompany.newBuilder()
                .withId(this.id)
                .withName(this.getName())
                .build();
    }

}
