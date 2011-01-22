package org.professio.managers;

import org.professio.model.Client;
import org.professio.model.ClientAssistant;
import org.professio.util.Constants;

public class PlayerManager implements Constants {
	private static Client[] onlinePlayers;

	public static void Initialize() throws Exception {
		onlinePlayers = new Client[MAX_PLAYERS];
	}
	
	public static int getPlayerCount() {
		int online = 0;
		for (final Client c : onlinePlayers)
			if (c != null && !c.isDisconnected())
				online++;
		return online;
	}
	
	private static int findSlot() {
		for (int i = 1; i < onlinePlayers.length; i++) {
			if (onlinePlayers[i] != null && !onlinePlayers[i].isDisconnected())
				continue;
			return i;
		}
		return -1;
	}

	public static Client getClient(final int ID) {
		for (final Client p : onlinePlayers) {
			if (p == null)
				continue;
			if (p.getSessionID() == ID)
				return p;
		}
		return null;
	}
	
	public static Client getPlayer(final String username) {
		for (final Client p : onlinePlayers) {
			if (p == null)
				continue;
			if (p.getPlayer().getName().equalsIgnoreCase(username))
				return p;
		}
		return null;
	}

	public static void process() {
		for (int i = 0; i < onlinePlayers.length; i++) {
			if (onlinePlayers[i] == null || onlinePlayers[i].getPlayer() == null || onlinePlayers[i].getPlayer() == null)
				continue;
			if (onlinePlayers[i].updateRequired())
				onlinePlayers[i].saveChar();
			onlinePlayers[i].preProcessing();
			while (onlinePlayers[i].packetProcess());
			onlinePlayers[i].postProcessing();
			onlinePlayers[i].getNextPlayerMovement();
			if (onlinePlayers[i].isDisconnected()) {
				onlinePlayers[i] = null;
				continue;
			}
			if (onlinePlayers[i].isInitialized())
				onlinePlayers[i].Update();
			else
				onlinePlayers[i].prepareClient();
			onlinePlayers[i].clearUpdateFlags();
		}
	}

	public static int addPlayer(final ClientAssistant c) {
		final int slot = findSlot();
		if (slot == -1)
			return -1;
		onlinePlayers[slot] = (Client) c;
		return slot;
	}
}