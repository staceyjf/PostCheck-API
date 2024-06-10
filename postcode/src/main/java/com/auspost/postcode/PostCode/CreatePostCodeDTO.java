package com.auspost.postcode.PostCode;

import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class CreatePostCodeDTO {
    @NotNull
    private String postcode;

    private Set<Long> suburbIds = new HashSet<>();

    public Set<Long> getSuburbIds() {
        return suburbIds;
    }

    public void setSuburbIds(Set<Long> suburbIds) {
        this.suburbIds = suburbIds;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode.toUpperCase();
    }

}
