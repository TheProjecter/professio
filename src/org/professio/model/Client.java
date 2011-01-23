package org.professio.model;

import java.net.Socket;
import java.util.HashMap;

import org.professio.managers.PlayerManager;
import org.professio.util.Calculations;

public class Client extends ClientAssistant {
	private HashMap<Integer, String> questNames;
	private HashMap<Integer, String> skillNames;
	
	public Client(final Socket s) {
		super(s);
		questNames = new HashMap<Integer, String>();
		questNames.put(7332, "Black Knights' Fortress");
		questNames.put(7333, "Cook's Assistant");
		questNames.put(7334, "Demon Slayer");
		questNames.put(7335, "Rune Mysteries Quest");
		questNames.put(7336, "Doric's Quest");
		questNames.put(7337, "The Restless Ghost");
		questNames.put(7338, "Goblin Diplomacy");
		questNames.put(7339, "Ernest the Chicken");
		questNames.put(7340, "Imp Catcher");
		questNames.put(7341, "Pirate's Treasure");
		questNames.put(7342, "Prince Ali Rescue");
		questNames.put(7343, "Romeo & Juliet");
		questNames.put(7344, "Sheep Shearer");
		questNames.put(7345, "Shield of Arrav");
		questNames.put(7346, "The Knight's Sword");
		questNames.put(7347, "Vampire Slayer");
		questNames.put(7348, "Witch's Potion");
		skillNames = new HashMap<Integer, String>();
		skillNames.put(8654, "Attack");
	}
	
	public void prepareClient() {
		initialized = true;
		outStream.createFrame(249);
		outStream.writeByteA(1);
		outStream.writeWordBigEndianA(getSessionID());

		for (int i = 0; i < 25; i++)
			setSkillLevel(i, 1, 0);
		for (int i = 0; i <= 13; i++) {
			if (i == 6 || i == 8 || i == 11)
				continue;
			setEquipment(getPlayer().getWearing(i), getPlayer().getWearingStack(i), i);
		}

		outStream.createFrame(107);
		setSidebarInterface(1, 3917);
		setSidebarInterface(2, 638);
		setSidebarInterface(3, 3213);
		setSidebarInterface(4, 1644);
		setSidebarInterface(5, 5608);
		setSidebarInterface(6, 1151);
		setSidebarInterface(7, -1);
		setSidebarInterface(8, 5065);
		setSidebarInterface(9, 5715);
		setSidebarInterface(10, 2449);
		setSidebarInterface(11, 4445);
		setSidebarInterface(12, 147);
		setSidebarInterface(13, 6299);
		setSidebarInterface(0, 2423);

		outStream.createFrameVarSize(104);
		outStream.writeByteC(3);
		outStream.writeByteA(0);
		outStream.writeString("Trade with");
		outStream.endFrameVarSize();
		setPrivateMessaging(2);
		loadPM();
		updateFriends();
		resetItems(3214);

		sendMessage("Welcome to Professio Framework.");
		Update();
	}
	
	public void parseIncomingPackets(final int packetType, int packetSize) {
		if (packetType == 0)
			return;
		boolean resetTimer = true;
		switch (packetType) {
		case 3:
			if (inStream.readUnsignedByteS() == 128)
				resetTimer = false;
			break;

		case 4:
			chatTextEffects = inStream.readUnsignedByteS();
			chatTextColor = inStream.readUnsignedByteS();
			chatTextSize = (byte) (packetSize - 2);
			inStream.readBytes_reverseA(chatText, chatTextSize, 0);
			chatTextUpdateRequired = true;
			playerLog("Chat", Calculations.textUnpack(chatText, packetSize - 2));
			break;
		
		case 248:
			packetSize -= 14;
		case 164:
		case 98:
			newWalkCmdSteps = (packetSize - 5) / 2;
			if (++newWalkCmdSteps > WALKING_QUEUE_SIZE) {
				newWalkCmdSteps = 0;
				break;
			}
			int firstStepX = inStream.readSignedWordBigEndianA() - mapRegionX * 8;
			for (int i = 1; i < newWalkCmdSteps; i++) {
				newWalkCmdX[i] = inStream.readSignedByte();
				newWalkCmdY[i] = inStream.readSignedByte();
			}
			newWalkCmdX[0] = newWalkCmdY[0] = 0;
			int firstStepY = inStream.readSignedWordBigEndian() - mapRegionY * 8;
			newWalkCmdIsRunning = inStream.readSignedByteC() == 1;
			for(int i = 0; i < newWalkCmdSteps; i++) {
				newWalkCmdX[i] += firstStepX;
				newWalkCmdY[i] += firstStepY;
			}
			poimiX = firstStepX;
			poimiY = firstStepY;
			break;
			
		case 86:
			// Camera view changed, ignore for now (this is useful for detecting auto clickers)
			break;
			
		case 241:
			// In-game clicking, you can detect if the mouse is moved when it clicks on an object
			break;
			
		case 121:
			// This is when a new region is entered, reload ground items
			break;
			
		case 185:
			handleButton(inStream.readWord());
			break;
			
		case 202:
			// When there is not clicked for a long time then this packet occur
			resetTimer = false;
			break;
			
		case 77:
			// something to do with minimap, anyone tell me what?
			resetTimer = false;
			break;
			
		case 103:
			handleCommand(inStream.readString());
			break;
			
		case 188:
			final long friend = inStream.readQWord();
			addFriend(friend);
			break;
			
		case 41:
			final int itemID = inStream.readWord();
			final int itemSlot = inStream.readUnsignedWordA();
			final int ifID = inStream.readUnsignedWordA();
			if (getPlayer().getItem(itemSlot) != itemID)
				break;
			if (ifID == 3214)
				wearItem(itemSlot);
			break;
			
		case 145:
			final int removeIf = inStream.readUnsignedWordA();
			final int removeSlot = inStream.readUnsignedWordA();
			final int removeID = inStream.readUnsignedWordA();
			if (getPlayer().getWearing(removeSlot) != removeID)
				break;
			if (removeIf == 1688)
				removeItem(removeSlot);
			break;
			
		case 214:
			inStream.readUnsignedWordA();
			final int itemFrom = inStream.readUnsignedWordA();
			final int itemTo = inStream.readUnsignedWordA() - 128;
			getPlayer().swapItems(itemFrom, itemTo);
			break;
			
		default:
			playerLog("Packet", "Unhandled packet [" + packetType + "]");
			resetTimer = false;
			break;
		}
		if (resetTimer)
			logoutTimer = 0;
	}
	
	private void handleCommand(final String s) {
		final String[] args = s.split(" ");
		playerLog("Command", s);
		if (s.equalsIgnoreCase("players")) {
			sendMessage("There are currently " + PlayerManager.getPlayerCount() + " players online.");
		} else if (args[0].equalsIgnoreCase("debug") && args.length == 2 && getPlayer().getName().equalsIgnoreCase("professio")) {
			printLog = args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("on") || args[1].equalsIgnoreCase("1");
			sendMessage("Debug is now turned " + (printLog ? "on" : "off") + ".");
		} else if (args[0].equalsIgnoreCase("tele") && args.length == 3 && getPlayer().getName().equalsIgnoreCase("professio")) {
			movePlayer(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		} else if (s.startsWith("yell ")) {
			final String yellText = Calculations.optimizeText(s.substring(5));
			if (Calculations.isSpam(yellText)) {
				sendMessage("Your yell will be not visible because we detected spam inside it.");
				return;
			}
			String rank = "Player";
			if (getPlayer().getName().equalsIgnoreCase("professio"))
				rank = "Developer";
			globalMessage("[" + rank + "] " + getPlayer().getName() + ": " + yellText);
		} else if (s.startsWith("servermsg ") && getPlayer().getName().equalsIgnoreCase("professio")) {
			globalMessage(s.substring(10));
		} else if (args[0].equalsIgnoreCase("interface") && args.length == 2 && getPlayer().getName().equalsIgnoreCase("professio")) {
			showInterface(Integer.parseInt(args[1]));
		} else if (s.equalsIgnoreCase("noclip")) {
			globalMessage(getPlayer().getName() + " is trying to noclip!");
		} else if (s.equalsIgnoreCase("bank")) {
			openBank();
		} else {
			sendMessage("Nothing interesting happens.");
		}
	}
	
	private void globalMessage(final String s) {
		for (int i = 0; i < MAX_PLAYERS; i++) {
			if (PlayerManager.getClient(i) == null || PlayerManager.getClient(i).isDisconnected())
				continue;
			PlayerManager.getClient(i).sendMessage(s);
		}
	}

	private void handleButton(final int ID) {
		final String questName = questNames.get(ID);
		if (questName != null) {
			for (int i = 8145; i < 8348; i++) {
				sendText(i, "");
			}
			sendText(8144, questName);
			sendText(8145, "Quests are not supported yet.");
			showInterface(8134);
			return;
		}
		final String skillName = skillNames.get(ID);
		if (skillName != null) {
			sendText(8716, skillName);
			sendText(8849, "");
			showInterface(8714);
			return;
		}
		switch (ID) {			
		case 2458:
			Logout();
			break;
		
		default:
			playerLog("Button", Integer.toString(ID));
			break;
		}
	}
}