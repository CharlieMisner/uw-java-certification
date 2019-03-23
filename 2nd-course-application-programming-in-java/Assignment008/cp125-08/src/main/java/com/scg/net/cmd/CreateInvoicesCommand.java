package com.scg.net.cmd;

import java.io.Serializable;
import java.time.LocalDate;

public class CreateInvoicesCommand extends AbstractCommand<LocalDate> implements Serializable {

    public CreateInvoicesCommand(LocalDate target){
        this.target = target;
    }

    public void execute(){
        receiver.execute(this);
    }

}