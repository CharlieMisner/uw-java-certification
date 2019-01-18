package com.scg.domain;

/**
 * Enum for skills.
 * @author Charlie Misner
 */
public enum Skill {
    PROJECT_MANAGER(){
        @Override
        public String toString(){
            return "Project Manager";
        }
    },
    SOFTWARE_ENGINEER(){
        @Override
        public String toString(){
            return "Software Engineer";
        }
    },
    SOFTWARE_TESTER(){
        @Override
        public String toString(){
            return "Software Tester";
        }
    },
    SYSTEM_ARCHITECT(){
        @Override
        public String toString(){
            return "System Architect";
        }
    },
    UNKNOWN_SKILL(){
        @Override
        public String toString(){
            return "Unknown Skill";
        }
    };

    int rate;


    /**
     * Getter for rate
     * @return rate
     */
    public int getRate() {
        return rate;
    }
}
