package com.scg.net.server;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.net.cmd.AddClientCommand;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CommandProcessor {

    private List<ClientAccount> clientList;

    CommandProcessor(Socket connection, List<ClientAccount> clientList, List<Consultant> consultantList, InvoiceServer server){
        this.clientList = clientList;
    }
    public void execute(AddClientCommand command){
        System.out.println(command.getTarget());
        clientList.add(command.getTarget());

    }
}
