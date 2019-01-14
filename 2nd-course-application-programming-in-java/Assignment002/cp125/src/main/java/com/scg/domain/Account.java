package com.scg.domain;

/**
 * Interface for account.
 * @author Charlie Misner
 */
public interface Account {

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