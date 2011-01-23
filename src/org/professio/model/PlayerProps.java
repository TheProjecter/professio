package org.professio.model;

import java.io.Serializable;

public abstract class PlayerProps implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String myUsername;
	protected String myPassword;
	protected int myHeight;
	protected int myX;
	protected int myY;
	protected int[] myEquipment;
	protected int[] myEquipStacks;
	protected int[] myItems;
	protected int[] myItemStacks;
	protected int[] bankItems;
	protected int[] bankStacks;
}