package com.auspost.postcode.Suburb;

public enum AustralianState {
    NSW("NEW SOUTH WALES"),
    QLD("QUEENSLAND"),
    SA("SOUTH AUSTRALIA"),
    TAS("TASMANIA"),
    VIC("VICTORIA"),
    WA("WESTERN AUSTRALIA"),
    ACT("AUSTRALIAN CAPITAL TERRITORY"),
    NT("NORTHERN TERRITORY");

    private final String fullName;

    AustralianState(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public static AustralianState from(String state) {
        // loop through to check if request state matches either the abbreviation or
        // full state name
        for (AustralianState australianState : AustralianState.values()) {
            String upperCaseState = state.toUpperCase(); // to ensure case insensitivity
            if (australianState.name().equals(upperCaseState) || australianState.getFullName().equals(upperCaseState)) {
                return australianState;
            }
        }
        return null;
    }
}