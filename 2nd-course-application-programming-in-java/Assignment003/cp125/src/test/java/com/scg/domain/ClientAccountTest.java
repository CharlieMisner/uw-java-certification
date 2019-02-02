package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.PersonalName;
import com.scg.util.StateCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientAccountTest {

    @Test
    void testIsBillable(){
        Address address = new Address("15", "Seattle", StateCode.WA, "98102");
        PersonalName personalName = new PersonalName("Charlie", "Misner", "D");
        ClientAccount testClientAccount = new ClientAccount("Client", personalName, address);
        assertEquals(testClientAccount.isBillable(), true);
    }

}