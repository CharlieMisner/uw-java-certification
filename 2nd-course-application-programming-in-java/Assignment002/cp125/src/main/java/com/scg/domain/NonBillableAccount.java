package com.scg.domain;

/**
 * Enum for nonbillable accounts.
 * @author Charlie Misner
 */
public enum NonBillableAccount implements Account {
    BUSINESS_DEVELOPMENT, SICK_LEAVE, VACATION;

    /**
     * Getter for name.
     * @return accountName
     */
    public String getName(){
        return this.toString();
    }

    /**
     * Returns true if the account is billable.
     * @return isBillable;
     */
    public boolean isBillable() {
        return false;
    }

    /**
     * Returns the friendly name of this enum
     * @return String name
     */
    @Override
    public String toString() {
        switch(this) {
            case BUSINESS_DEVELOPMENT: return "Business Development";
            case SICK_LEAVE: return "Sick Leave";
            case VACATION: return "Vacation";
            default: throw new IllegalArgumentException();
        }
    }

}
