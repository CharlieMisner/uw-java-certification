package com.scg.domain;

/**
 * Enum for nonbillable accounts.
 * @author Charlie Misner
 */
public enum NonBillableAccount implements Account {
    BUSINESS_DEVELOPMENT(){
        @Override
        public String toString(){
            return "Business Development";
        }
    },
    SICK_LEAVE(){
        @Override
        public String toString(){
            return "Sick Leave";
        }
    },
    VACATION(){
        @Override
        public String toString(){
            return "Vacation";
        }
    };

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

}
