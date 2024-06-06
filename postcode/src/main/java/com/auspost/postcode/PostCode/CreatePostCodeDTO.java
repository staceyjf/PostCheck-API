package com.auspost.postcode.PostCode;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

import com.auspost.postcode.validation.NumericValidation;

public class CreatePostCodeDTO {
    @NotBlank
    @Size(min = 4, max = 4, message = "Postcodes need to be 4 digits long")
    @NumericValidation
    private String postcode;

    private Set<Long> suburbIds;

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
        this.postcode = postcode;
    }

}
