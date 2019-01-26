package com.scg.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillTest {
    @Test
    public void testSoftwareEngineer(){
        assertEquals("Software Engineer", Skill.SOFTWARE_ENGINEER.toString());
    }

    @Test
    public void testSystemArchitect(){
        assertEquals("System Architect", Skill.SYSTEM_ARCHITECT.toString());
    }

    @Test
    public void testSoftwareTester(){
        assertEquals("Software Tester", Skill.SOFTWARE_TESTER.toString());
    }

    @Test
    public void testProjectManager(){
        assertEquals("Project Manager", Skill.PROJECT_MANAGER.toString());
    }

    @Test
    public void testUnknown(){
        assertEquals("Unknown Skill", Skill.UNKNOWN_SKILL.toString());
    }


}