package com.dartin.project.net;

import java.io.Serializable;

public class ServerMessage implements Serializable {

	private static final long serialVersionUID = 5411953243451956583L;
	public static final int SIG_RUN_STORY = 0;
	public static final int SIG_UPD_SET = 1;

	private final String name;
	private final int signal;
	private final Object content;

	public ServerMessage(String name, int signal, Object content) {
		this.name = name;
		this.signal = signal;
		this.content = content;
	}

	public ServerMessage(String name, int signal) {
		this(name, signal, null);
	}

	public String getName() {
		return name;
	}

	public int getSignal() {
		return signal;
	}

	public Object getContent() {
		return content;
	}
}
