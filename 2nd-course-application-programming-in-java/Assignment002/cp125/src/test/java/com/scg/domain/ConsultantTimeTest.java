package com.scg.domain;

import com.scg.util.PersonalName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ConsultantTimeTest {

    private ConsultantTime consultantTime;
    private ConsultantTime consultantTimeOther;

    @BeforeEach
    public void init() {
        PersonalName personalName = new PersonalName("Charlie", "Misner", "D");
        ClientAccount testClientAccount = new ClientAccount("Client", personalName);
        this.consultantTime = new ConsultantTime(LocalDate.of(2019,12,04),testClientAccount, Skill.SOFTWARE_ENGINEER, 8);
        this.consultantTimeOther = new ConsultantTime(LocalDate.of(2019,8,04),testClientAccount, Skill.SYSTEM_ARCHITECT, 4);
    }

    @Test
    public void verifyConstructor() {
        PersonalName personalName = new PersonalName("Charlie", "Misner", "D");
        ClientAccount testClientAccount = new ClientAccount("Client", personalName);
        assertThrows(IllegalArgumentException.class, () -> {
            new ConsultantTime(LocalDate.of(2019,12,04),testClientAccount, Skill.SOFTWARE_ENGINEER, 0);
        });
    }

    @Test
    public void testToString(){
        assertEquals("2019-12-04, Client, Software Engineer, 8", this.consultantTime.toString());
    }

    @Test
    public void testEquals(){
        assertFalse(this.consultantTime.equals(this.consultantTimeOther));
        assertTrue(this.consultantTime.equals(this.consultantTime));
    }

    @Test
    public void testHashCode(){
        assertEquals(this.consultantTime.hashCode(), 611696631);
    }
}