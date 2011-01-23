package org.professio.model;

import org.professio.util.Calculations;
import org.professio.util.Constants;

public class Player extends PlayerProps implements Constants {
	private static final long serialVersionUID = 1L;

	public Player(final String Username) {
		myUsername = Calculations.optimizeText(Username);
		myEquipment = new int[14];
		for (int i = 0; i < 14; i++)
			myEquipment[i] = -1;
		myEquipStacks = new int[14];
		myItems = new int[28];
		myItemStacks = new int[28];
		bankItems = new int[BANK_SIZE];
		bankStacks = new int[BANK_SIZE];
		for (int i = 0; i < myItems.length; i++)
			myItems[i] = -1;
		for (int i = 0; i < bankItems.length; i++)
			bankItems[i] = -1;
		myItems[0] = 1333;
		myItemStacks[0] = 1;
		myItems[1] = 1332;
		myItemStacks[1] = 1;
		bankItems[0] = 1331;
		bankStacks[0] = 1;
	}
	
	public static Player newPlayer(final String s, final String s1) {
		final Player p = new Player(s);
		p.myPassword = s1;
		return p;
	}

	public int getItem(final int slot) {
		return myItems[slot];
	}
	
	public int getStack(final int slot) {
		return myItemStacks[slot];
	}

	public int getBankItem(final int slot) {
		return bankItems[slot];
	}
	
	public int getBankStack(final int slot) {
		return bankStacks[slot];
	}
	
	public String getName() {
		return myUsername;
	}
	
	public int getX() {
		return myX;
	}
	
	public int getY() {
		return myY;
	}
	
	public int getHeight() {
		return myHeight;
	}

	public int setX(final int x) {
		return myX = x;
	}

	public int setY(final int y) {
		return myY = y;
	}

	public boolean correctPassword(final String s) {
		return myPassword.equals(s);
	}
	
	public int getWearing(final int slot) {
		return myEquipment[slot];
	}
	
	public int getWearingStack(final int slot) {
		return myEquipStacks[slot];
	}

	public void wearItem(final int ID, final int amount, final int slot) {
		myEquipment[slot] = ID;
		myEquipStacks[slot] = amount;
	}

	public void deleteItem(final int slot) {
		myItems[slot] = -1;
		myItemStacks[slot] = 0;
	}

	public void removeItem(final int slot) {
		myEquipment[slot] = -1;
		myEquipStacks[slot] = 0;
	}

	public void addItem(final int ID, final int amount) {
		for (int i = 0; i < myItems.length; i++) {
			if (myItems[i] == -1) { // TODO: notes and stack support
				myItems[i] = ID;
				myItemStacks[i] = amount;
				return;
			}
		}
	}

	public void addItem(final int ID, final int amount, final int slot) {
		myItems[slot] = ID;
		myItemStacks[slot] = amount;
	}

	public void swapItems(final int from, final int to) {
		final int temp1 = myItems[from];
		final int temp2 = myItemStacks[from];
		myItems[from] = myItems[to];
		myItemStacks[from] = myItemStacks[to];
		myItems[to] = temp1;
		myItemStacks[to] = temp2;
	}
}