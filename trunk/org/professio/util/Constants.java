package org.professio.util;

public interface Constants {
	public static final int SERVER_PORT = 43594;
	public static final int CYCLE_TIME = 500;
	public static final int MAX_PLAYERS = 512;
	public static final char XLATE_TABLE[] = {
		' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 
		'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p', 
		'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', 
		'3', '4', '5', '6', '7', '8', '9', ' ', '!', '?', 
		'.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', 
		'\'', '@', '#', '+', '=', '\243', '$', '%', '"', '[', 
		']'
	};
	public static final byte DIRECTION_DELTAX[] = new byte[] { 0, 1, 1, 1, 0, -1, -1, -1 };
	public static final byte DIRECTION_DELTAY[] = new byte[] { 1, 1, 0, -1, -1, -1, 0, 1 };
	public static final byte XLATE_DIRECTION[] = new byte[] { 1, 2, 4, 7, 6, 5, 3, 0 };
	public static final int BUFFER_SIZE = 5000;
	public static final int WALKING_QUEUE_SIZE = 50;
	public static final int EQUIP_HEAD = 0;
	public static final int EQUIP_BACK = 1;
	public static final int EQUIP_NECK = 2;
	public static final int EQUIP_WEAPON = 3;
	public static final int EQUIP_PLATE = 4;
	public static final int EQUIP_SHIELD = 5;
	public static final int EQUIP_LEGS = 7;
	public static final int EQUIP_HANDS = 9;
	public static final int EQUIP_FEET = 10;
	public static final int EQUIP_RING = 12;
	public static final int EQUIP_ARROWS = 13;
	public static final int HOME_X = 3200;
	public static final int HOME_Y = 3200;
	public static final int BANK_SIZE = 200;
	public static final int PACKET_SIZES[] = {
		0, 0, 0, 1, -1, 0, 0, 0, 0, 0, //0
		0, 0, 0, 0, 8, 0, 6, 2, 2, 0,  //10
		0, 2, 0, 6, 0, 12, 0, 0, 0, 0, //20
		0, 0, 0, 0, 0, 8, 4, 0, 0, 2,  //30
		2, 6, 0, 6, 0, -1, 0, 0, 0, 0, //40
		0, 0, 0, 12, 0, 0, 0, 0, 8, 0, //50
		0, 8, 0, 0, 0, 0, 0, 0, 0, 0,  //60
		6, 0, 2, 2, 8, 6, 0, -1, 0, 6, //70
		0, 0, 0, 0, 0, 1, 4, 6, 0, 0,  //80
		0, 0, 0, 0, 0, 3, 0, 0, -1, 0, //90
		0, 13, 0, -1, 0, 0, 0, 0, 0, 0,//100
		0, 0, 0, 0, 0, 0, 0, 6, 0, 0,  //110
		1, 0, 6, 0, 0, 0, -1, 0, 2, 6, //120
		0, 4, 6, 8, 0, 6, 0, 0, 0, 2,  //130
		0, 0, 0, 0, 0, 6, 0, 0, 0, 0,  //140
		0, 0, 1, 2, 0, 2, 6, 0, 0, 0,  //150
		0, 0, 0, 0, -1, -1, 0, 0, 0, 0,//160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  //170
		0, 8, 0, 3, 0, 2, 0, 0, 8, 1,  //180
		0, 0, 12, 0, 0, 0, 0, 0, 0, 0, //190
		2, 0, 0, 0, 0, 0, 0, 0, 4, 0,  //200
		4, 0, 0, 0, 7, 8, 0, 0, 10, 0, //210
		0, 0, 0, 0, 0, 0, -1, 0, 6, 0, //220
		1, 0, 0, 0, 6, 0, 6, 8, 1, 0,  //230
		0, 4, 0, 0, 0, 0, -1, 0, -1, 4,//240
		0, 0, 6, 6, 0, 0, 0            //250
	};
}