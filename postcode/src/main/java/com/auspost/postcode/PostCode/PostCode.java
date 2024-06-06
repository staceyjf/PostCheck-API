package com.auspost.postcode.PostCode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import jakarta.persistence.Id;

@Entity
@Table(name = "postcodes")
public class PostCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // sqlite doesn't support unique constraint
    @NotBlank
    @Column
    private String postcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    // using the getter to adhere to encapsulation (internal state should only be
    // access via its methods)
    @Override
    public String toString() {
        return String.format(
                "{id: %d, postcode: %s,}",
                getId(), getPostcode());
    }
}
