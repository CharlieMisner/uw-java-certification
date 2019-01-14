package com.scg.domain;

/**
 * Enum for skills.
 * @author Charlie Misner
 */
public enum Skill {
    PROJECT_MANAGER, SOFTWARE_ENGINEER, SOFTWARE_TESTER, SYSTEM_ARCHITECT, UNKNOWN_SKILL;

    int rate;

    /**
     * Returns the friendly name of this enum
     * @return String name
     */
    @Override
    public String toString() {
        switch(this) {
            case PROJECT_MANAGER: return "Project Manager";
            case SOFTWARE_ENGINEER: return "Software Engineer";
            case SOFTWARE_TESTER: return "Software Tester";
            case SYSTEM_ARCHITECT: return "System Architect";
            case UNKNOWN_SKILL: return "Unknown Skill";
            default: throw new IllegalArgumentException();
        }
    }

    /**
     * Getter for rate
     * @return rate
     */
    public int getRate() {
        return rate;
    }
}
