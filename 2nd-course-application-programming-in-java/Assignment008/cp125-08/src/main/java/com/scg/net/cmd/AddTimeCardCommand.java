package com.scg.net.cmd;

import com.scg.domain.TimeCard;

import java.io.Serializable;

public class AddTimeCardCommand extends AbstractCommand<TimeCard> implements Serializable {

    public AddTimeCardCommand(TimeCard target){
        this.target = target;
    }

    public void execute(){
        receiver.execute(this);
    }
}
