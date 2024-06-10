package com.auspost.postcode.PostCode;

import java.util.HashSet;
import java.util.Set;

public class UpdatePostCodeDTO {
    private Long id;

    private String postcode;

    private Set<Long> suburbIds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
