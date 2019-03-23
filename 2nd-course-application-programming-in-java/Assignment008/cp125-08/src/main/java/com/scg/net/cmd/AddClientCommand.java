package com.scg.net.cmd;

import com.scg.domain.ClientAccount;

import java.io.Serializable;

public final class AddClientCommand extends AbstractCommand<ClientAccount> implements Serializable {

    public AddClientCommand(ClientAccount target){
        System.out.println(target);
        this.target = target;
    }

    public void execute(){
        receiver.execute(this);
    }

}
