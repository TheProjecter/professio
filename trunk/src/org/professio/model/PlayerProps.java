package org.professio.model;

import java.io.Serializable;
import java.util.HashMap;

public abstract class PlayerProps implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String myUsername;
	protected String myPassword;
	protected int myHeight;
	protected int myX;
	protected int myY;
	protected int[] myEquipment;
	protected int[] myEquipStacks;
	protected HashMap<Integer, Integer> myItems;
}