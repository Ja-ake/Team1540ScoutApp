package org.team1540.incubator.console;

public abstract class CommandListener {
	public final String commandName;

	public CommandListener(String command) {
		commandName = command;
	}

	public abstract void runCommand(String[] args);
}
