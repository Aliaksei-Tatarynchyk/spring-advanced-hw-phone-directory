package com.epam.phone.directory.model.json;

public class PhoneCompany {
    Long id;
    String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.epam.phone.directory.model.db.PhoneCompany toJPA() {
        return com.epam.phone.directory.model.db.PhoneCompany.newBuilder()
                .withId(this.id)
                .withName(this.getName())
                .build();
    }

}
