package org.team1540.incubator.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConsoleManager {

	protected final Map<String,CommandListener> commands=new ConcurrentHashMap<String,CommandListener>();
	protected final BufferedReader consoleInput;

	public ConsoleManager(final BufferedReader input){
		consoleInput=input;
		new Thread(new Runnable(){
			@Override
			public void run() {
				String line;
				try {
					while((line=input.readLine())!=null){
						String[] lineComponents=line.split(" ");
						commands.get(lineComponents[0]).runCommand(Arrays.copyOfRange(lineComponents, 1, lineComponents.length));
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
	}

	public ConsoleManager(InputStream input){
		this(new BufferedReader(new InputStreamReader(input)));
	}

	public void addCommand(CommandListener l){
		commands.put(l.commandName, l);
	}
}
