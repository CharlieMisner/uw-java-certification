package com.scg.domain;

import com.scg.util.PersonalName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsultantTest {

    @Test
    void testConstructor(){
        PersonalName personalName = new PersonalName("Charlie", "Misner", "D");
        Consultant testConsultant = new Consultant(personalName);
        assertTrue(testConsultant instanceof Consultant);
    }


}