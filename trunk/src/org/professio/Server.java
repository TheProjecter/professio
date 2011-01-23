package org.professio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.professio.managers.PlayerManager;
import org.professio.model.Client;
import org.professio.util.Constants;

public class Server implements Runnable, Constants {
	public static void main(final String[] args) {
		new Server();
	}
	
	public Server() {
		System.out.println("Starting Professio Framework...");
		try {
			PlayerManager.Initialize();
		} catch (Exception e) {
			System.err.println("Could not start Professio Framework:");
			e.printStackTrace();
			System.err.println("Server will shutdown.");
			return;
		}
		ServerSocket clientAcceptor;
		try {
			clientAcceptor = new ServerSocket(SERVER_PORT, 25, null);
		} catch (IOException e) {
			System.err.println("Port " + SERVER_PORT + " is not available. Probably already in use.");
			System.err.println("Server will shutdown.");
			return;
		}
		new Thread(this).start();
		System.out.println("Successfully started Professio Framework. Ready for connections.");
		while (true) {
			try {
				Socket s = clientAcceptor.accept();
				s.setTcpNoDelay(true);
				new Client(s).processLogin();
			} catch (Exception e) { /* ignored */ }
		}
	}

	public void run() {
		while (true) {
			final long startTime = System.nanoTime();
			PlayerManager.process();
			final long timeSpend = (System.nanoTime() - startTime) / 1000000;
			try {
				Thread.sleep(CYCLE_TIME - timeSpend);
			} catch (InterruptedException e) { /* ignored */ }
		}
	}
}