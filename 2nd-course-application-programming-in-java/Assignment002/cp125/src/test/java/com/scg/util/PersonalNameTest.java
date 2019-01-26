package com.scg.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalNameTest {

    PersonalName personalName;

    @BeforeEach
    public void init() {
        this.personalName = new PersonalName();
        this.personalName = new PersonalName("Charlie", "Misner");
        this.personalName = new PersonalName("Charlie", "Misner", "David");
    }

    @Test
    public void testToString(){
        assertEquals("Charlie, Misner David", this.personalName.toString());
    }

    @Test void testHashCode(){
        assertEquals(409008797, this.personalName.hashCode());
    }

}