package com.scg.net.cmd;

import com.scg.net.server.CommandProcessor;

import java.io.Serializable;

public abstract class AbstractCommand<T> implements Command<T>, Serializable {

    public CommandProcessor receiver;
    public T target;

    public AbstractCommand(T target){

    };

    public AbstractCommand(){

    }

    public CommandProcessor getReceiver(){
        return this.receiver;
    }

    public void setReceiver(CommandProcessor receiver){
        this.receiver = receiver;
    }

    public T getTarget(){
        return this.target;
    }

    public String toString(){
        return this.getClass().getName();
    }

    public void setTarget(T target) {
        this.target = target;
    }
}
