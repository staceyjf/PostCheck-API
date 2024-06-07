package com.auspost.postcode.PostCode;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Set;

public class PostCodeDTO {
    // @NotBlank
    // @Size(min = 4, max = 4, message = "Postcodes need to be 4 digits long")
    // @Pattern(regexp = "[\\d]{4}")
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
