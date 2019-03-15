package com.scg.domain;

import java.io.Serializable;

/**
 * Enum for skills.
 * @author Charlie Misner
 */
public enum Skill implements Serializable {
    PROJECT_MANAGER(150){
        @Override
        public String toString(){
            return "Project Manager";
        }
    },
    SOFTWARE_ENGINEER(150){
        @Override
        public String toString(){
            return "Software Engineer";
        }
    },
    SOFTWARE_TESTER(150){
        @Override
        public String toString(){
            return "Software Tester";
        }
    },
    SYSTEM_ARCHITECT(200){
        @Override
        public String toString(){
            return "System Architect";
        }
    },
    UNKNOWN_SKILL(150){
        @Override
        public String toString(){
            return "Unknown Skill";
        }
    };

    public int rate;

    Skill(int rate){
        this.rate = rate;
    }
    /**
     * Getter for rate
     * @return rate
     */
    public int getRate() {
        return this.rate;
    }
}
