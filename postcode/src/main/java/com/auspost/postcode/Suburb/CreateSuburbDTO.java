package com.auspost.postcode.Suburb;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateSuburbDTO {
    @NotBlank
    private String name;

    @NotNull
    private String state;

    // TODO do i need to turn my string from the request body into a instance of
    // australian state

    private Set<Long> postcodeIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public Set<Long> getPostcodeIds() {
        return postcodeIds;
    }

    public void setPostcodeIds(Set<Long> postcodeIds) {
        this.postcodeIds = postcodeIds;
    }

}
