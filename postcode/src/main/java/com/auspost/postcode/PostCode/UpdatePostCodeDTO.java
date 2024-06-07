package com.auspost.postcode.PostCode;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdatePostCodeDTO {
    @NotBlank
    @Size(min = 4, max = 4, message = "Postcodes need to be 4 digits long")
    @Pattern(regexp = "[\\d]{4}")
    private String postcode;

    private Set<Long> suburbIds;

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Set<Long> getSuburbIds() {
        return suburbIds;
    }

    public void setSuburbIds(Set<Long> suburbIds) {
        this.suburbIds = suburbIds;
    }

}
