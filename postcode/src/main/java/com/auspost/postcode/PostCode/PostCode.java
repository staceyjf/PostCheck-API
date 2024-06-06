package com.auspost.postcode.PostCode;

import java.util.Set;

import com.auspost.postcode.Suburb.Suburb;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

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

    // postcode is the owner so responsible for updating the join table
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "postcode_suburb", joinColumns = @JoinColumn(name = "postcode_id"), inverseJoinColumns = @JoinColumn(name = "suburb_id"))
    Set<Suburb> associatedSuburbs;

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

    public Set<Suburb> getAssociatedSuburbs() {
        return associatedSuburbs;
    }

    public void setAssociatedSuburbs(Set<Suburb> associatedSuburbs) {
        this.associatedSuburbs = associatedSuburbs;
    }

    // using the getter to adhere to encapsulation (internal state should only be
    // access via its methods)
    @Override
    public String toString() {
        return String.format(
                "{id: %d, postcode: %s, associated suburbs: %s}",
                getId(), getPostcode(), getAssociatedSuburbs());
    }
}
