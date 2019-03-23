package com.scg.net.server;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.net.cmd.*;

import java.net.Socket;
import java.util.List;

public class CommandProcessor {

    private List<ClientAccount> clientList;
    private List<Consultant> consultantList;

    CommandProcessor(Socket connection, List<ClientAccount> clientList, List<Consultant> consultantList, InvoiceServer server){
        this.clientList = clientList;
        this.consultantList = consultantList;
    }

    public void execute(AddClientCommand command){
        clientList.add(command.getTarget());
    }

    public void execute(AddConsultantCommand command){
        consultantList.add(command.getTarget());
    }

    public void execute(AddTimeCardCommand command){
        
    }

    public void execute(CreateInvoicesCommand command){

    }

    public void execute(DisconnectCommand command){

    }

    public void execute(ShutdownCommand command){

    }


}
