package com.auspost.postcode.Suburb;

import jakarta.validation.constraints.NotNull;

public class CreateSuburbDTO {
    @NotNull
    private String name;

    @NotNull
    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}
