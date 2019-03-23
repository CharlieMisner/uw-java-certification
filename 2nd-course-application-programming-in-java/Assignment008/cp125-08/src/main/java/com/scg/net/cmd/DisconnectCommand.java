package com.scg.net.cmd;

import java.io.Serializable;

public class DisconnectCommand extends AbstractCommand<Void> implements Serializable {

    public DisconnectCommand() {
    }

    public void execute() {
        receiver.execute(this);
    }
}
