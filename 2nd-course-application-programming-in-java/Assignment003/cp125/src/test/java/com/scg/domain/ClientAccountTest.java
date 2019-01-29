package com.scg.domain;

import com.scg.util.PersonalName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientAccountTest {

    @Test
    void testIsBillable(){
        PersonalName personalName = new PersonalName("Charlie", "Misner", "D");
        ClientAccount testClientAccount = new ClientAccount("Client", personalName);
        assertEquals(testClientAccount.isBillable(), true);
    }

}