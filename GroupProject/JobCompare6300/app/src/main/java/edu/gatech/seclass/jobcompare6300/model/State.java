package edu.gatech.seclass.jobcompare6300.model;

public enum State {

    /**
     * List of States as Enum
     */
    AK("Alaska"),
    AL("Alabama"),
    AR("Arkansas"),
    AZ("Arizona"),
    CA("California"),
    CO("Colorado"),
    CT("Connecticut"),
    DE("Delaware"),
    FL("Florida"),
    GA("Georgia"),
    HI("Hawaii"),
    IA("Iowa"),
    ID("Idaho"),
    IL("Illinois"),
    IN("Indiana"),
    KS("Kansas"),
    KY("Kentucky"),
    LA("Louisiana"),
    MA("Massachusetts"),
    MD("Maryland"),
    ME("Maine"),
    MI("Michigan"),
    MN("Minnesota"),
    MO("Missouri"),
    MS("Mississippi"),
    MT("Montana"),
    NC("NorthCarolina"),
    ND("NorthDakota"),
    NE("Nebraska"),
    NH("New Hampshire"),
    NJ("New Jersey"),
    NM("New Mexico"),
    NV("Nevada"),
    NY("NewYork"),
    OH("Ohio"),
    OK("Oklahoma"),
    OR("Oregon"),
    PA("Pennsylvania"),
    RI("Rhode Island"),
    SC("South Carolina"),
    SD("South Dakota"),
    TN("Tennessee"),
    TX("Texas"),
    UT("Utah"),
    VA("Virginia"),
    VT("Vermont"),
    WA("Washington"),
    WI("Wisconsin"),
    WV("WestVirginia"),
    WY("Wyoming");

    private final String stateName;

    State(String stateKey) {
        this.stateName = stateKey;
    }

    public String getStateName() {
        return stateName;
    }

    public static State valueOfStateName(String stateName) {
        for (State state : values()) {
            if (state.stateName.equals(stateName)) {
                return state;
            }
        }
        return null;
    }

}