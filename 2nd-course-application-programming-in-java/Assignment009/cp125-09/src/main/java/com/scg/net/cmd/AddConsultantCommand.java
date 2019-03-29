package com.scg.net.cmd;

import com.scg.domain.Consultant;

import java.io.Serializable;

public class AddConsultantCommand extends AbstractCommand<Consultant> implements Serializable {

    public AddConsultantCommand(Consultant target){
        this.target = target;
    }

    public void execute(){
        receiver.execute(this);
    }
}
