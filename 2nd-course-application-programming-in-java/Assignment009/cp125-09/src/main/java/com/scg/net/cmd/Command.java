package com.scg.net.cmd;

import com.scg.net.server.CommandProcessor;

public interface Command<T> {

    public void execute();

    public CommandProcessor getReceiver();

    public void setReceiver(CommandProcessor receiver);

    public T getTarget();
}
