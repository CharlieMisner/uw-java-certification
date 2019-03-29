package com.scg.domain;

import java.io.Serializable;

/**
 * Interface for account.
 * @author Charlie Misner
 */
public interface Account extends Serializable {

    /**
     * Gets account name.
     * @return accountName
     */
    String getName();

    /**
     * Returns true if the account is billable.
     * @return isBillable;
     */
    boolean isBillable();

}