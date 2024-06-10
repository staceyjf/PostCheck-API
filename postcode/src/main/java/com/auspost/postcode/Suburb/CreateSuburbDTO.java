package com.auspost.postcode.Suburb;

import java.util.Set;

import jakarta.validation.constraints.NotNull;

public class CreateSuburbDTO {
    @NotNull
    private String name;

    @NotNull
    private String state;

    // private Set<Long> postcodeIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    // public Set<Long> getPostcodeIds() {
    // return postcodeIds;
    // }

    // public void setPostcodeIds(Set<Long> postcodeIds) {
    // this.postcodeIds = postcodeIds;
    // }

}
