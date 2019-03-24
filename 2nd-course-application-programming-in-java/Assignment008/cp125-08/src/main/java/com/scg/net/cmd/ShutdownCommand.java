package com.scg.net.cmd;

import java.io.Serializable;

public class ShutdownCommand extends AbstractCommand<Void> implements Serializable {

    public ShutdownCommand() {
    }

    public void execute() {
        receiver.execute(this);
    }
}