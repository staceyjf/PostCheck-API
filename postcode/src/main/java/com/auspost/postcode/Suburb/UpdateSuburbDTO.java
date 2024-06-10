package com.auspost.postcode.Suburb;

public class UpdateSuburbDTO {
    private Long id;

    private String name;

    private String state;

    // private Set<Long> postcodeIds;

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
