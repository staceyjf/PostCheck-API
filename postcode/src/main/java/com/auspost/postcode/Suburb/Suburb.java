package com.auspost.postcode.Suburb;

import java.util.Set;

import com.auspost.postcode.PostCode.PostCode;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
@Table(name = "suburbs")
public class Suburb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private AUSTRALIANSTATE state;

    // a set will ensure we don't have duplicate postcodes associated
    // set interface so can hold any object that implements Set
    @ManyToMany(mappedBy = "associatedSuburbs")
    // Set<PostCode> associatedPostcodes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AUSTRALIANSTATE getState() {
        return state;
    }

    public void setState(AUSTRALIANSTATE state) {
        this.state = state;
    }

    // public Set<PostCode> getAssociatedPostcodes() {
    // return this.associatedPostcodes;
    // }

    // public void setAssociatedPostcodes(Set<PostCode> associatedPostcodes) {
    // this.associatedPostcodes = associatedPostcodes;
    // }

    @Override
    public String toString() {
        return String.format(
                "{id: %d, name: %s, state: %s}",
                getId(), getName(), getState());
    }

}
