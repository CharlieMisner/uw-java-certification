package com.scg.domain;

import java.io.Serializable;

/**
 * Enum for nonbillable accounts.
 * @author Charlie Misner
 */
public enum NonBillableAccount implements Account, Serializable {
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
