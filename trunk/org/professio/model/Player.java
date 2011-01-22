package org.professio.model;

import java.util.HashMap;

import org.professio.util.Calculations;

public class Player extends PlayerProps {
	private static final long serialVersionUID = 1L;

	public Player(final String Username) {
		myUsername = Calculations.optimizeText(Username);
		myEquipment = new int[14];
		for (int i = 0; i < 14; i++)
			myEquipment[i] = -1;
		myEquipStacks = new int[14];
	}
	
	public static Player newPlayer(final String s, final String s1) {
		final Player p = new Player(s);
		p.myPassword = s1;
		return p;
	}
	
	public HashMap<Integer, Integer> getItems() {
		return myItems;
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
}