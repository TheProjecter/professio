package org.professio.model;

/**
 * Edited by Professio (removed some unused shit, improved syntax)
 * @author winterLove
 */
public class Item {
	private static int[] plateBody = { 3140, 1115, 1117, 1119, 1121, 1123, 1125, 1127, 2583, 2591, 2599, 2607, 2615, 2623, 2653, 2669, 3481, 4720, 4728, 4749 };
	private static int[] fullHelm = { 1153, 1155, 1157, 1159, 1161, 1163, 1165, 2587, 2595, 2605, 2613, 2619, 2627, 2657, 2673, 3486 };
	private static int[] fullMask = { 1053, 1055, 1057 };
	
	public static boolean isPlate(final int ID) {
		for (int i = 0; i < plateBody.length; i++)
			if (plateBody[i] == ID)
				return true;
		return false;
	}

	public static boolean isFullHelm(final int ID) {
		for (int i = 0; i < fullHelm.length; i++)
			if (fullHelm[i] == ID)
				return true;
		return false;
	}

	public static boolean isFullMask(final int ID) {
		for (int i = 0; i < fullMask.length; i++)
			if (fullMask[i] == ID)
				return true;
		return false;
	}
}