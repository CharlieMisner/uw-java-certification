package com.scg.net.cmd;

import com.scg.domain.ClientAccount;
import com.scg.net.server.CommandProcessor;

import java.io.Serializable;

public final class AddClientCommand extends AbstractCommand<ClientAccount> implements Serializable {

    CommandProcessor receiver;

    public AddClientCommand(ClientAccount target){
        this.target = target;
    }

    public void execute(){
        System.out.println(this);
        System.out.println(receiver.toString());
        receiver.execute(this);
    }

}
