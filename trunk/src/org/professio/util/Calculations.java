package org.professio.util;

public class Calculations implements Constants {
	public static String Hex(final byte[] b, final int i, final int j) {
		String temp = "";
		for (int cntr = 0; cntr < j; cntr++) {
			int num = b[i + cntr] & 0xFF;
			String myStr;
			if (num < 16)
				myStr = "0";
			else
				myStr = "";
			temp += myStr + Integer.toHexString(num) + " ";
		}
		return temp.toUpperCase().trim();
	}

	public static int HexToInt(final byte[] b, final int i, final int j) {
		int temp = 0;
		int k = 1000;
		for (int cntr = 0; cntr < j; cntr++) {
			int num = (b[i + cntr] & 0xFF) * k;
			temp += (int) num;
			if (k > 1)
				k /= 1000;
		}
		return temp;
	}

	public static int random(final int i) {
		return (int) (Math.random() * (i + 1));
	}

	public static long playerNameToInt64(final String s) {
		long l = 0L;
		for (int i = 0; i < s.length() && i < 12; i++) {
			char c = s.charAt(i);
			l *= 37L;
			if (c >= 'A' && c <= 'Z')
				l += (1 + c) - 65;
			else if (c >= 'a' && c <= 'z')
				l += (1 + c) - 97;
			else if (c >= '0' && c <= '9')
				l += (27 + c) - 48;
		}
		while (l % 37L == 0L && l != 0L)
			l /= 37L;
		return l;
	}

	public static String textUnpack(final byte[] b, final int i) {
		char[] decodeBuf = new char[4096];
		int idx = 0, highNibble = -1;
		for (int j = 0; j < i * 2; j++) {
			int val = b[j / 2] >> (4 - 4 * (j % 2)) & 0xf;
			if (highNibble == -1) {
				if (val < 13)
					decodeBuf[idx++] = XLATE_TABLE[val];
				else
					highNibble = val;
			} else {
				decodeBuf[idx++] = XLATE_TABLE[((highNibble<<4) + val) - 195];
				highNibble = -1;
			}
		}
		return new String(decodeBuf, 0, idx);
	}
	
	public static boolean isSpam(final String s) {
		final String[] TLDs = { "www.", ".com", ".c0m", ".net", ".n3t", ".tk", ".org", ".0rg", ".info", ".1nf0", ".inf0", "1nfo" };
		for (final String tld : TLDs) {
			if (s.toLowerCase().contains(tld))
				return true;
		}
		return false;
	}

	public static String optimizeText(final String s) {
		String output = s.toLowerCase();
		try {
			int lastMarker = -1;
			for (int i = 0; i < s.length(); i++) {
				if (i + 2 < s.length()) {
					if (i - 1 >= 0)
						if (s.substring(i - 1, i).equals(" ") && s.substring(i, i + 1).equals("i")) {
							if (s.substring(i + 1, i + 2).equals(" "))
								lastMarker = i - 1;
						}
					if (s.substring(i, i + 1).equals("!") || s.substring(i, i + 1).equals(".") || s.substring(i, i + 1).equals("?")) {
						if (s.substring(i + 1, i + 2).equals(" "))
							lastMarker = i + 1;
						else
							lastMarker = i;
					}
				}
				if (i == lastMarker + 1)
					output = output.substring(0, i) + output.substring(i, i + 1).toUpperCase() + output.substring(i + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	public static void textPack(byte[] b, final String s) {
		String text = s;
		if (s.length() > 80)
			text = s.substring(0, 80);
		text = text.toLowerCase();

		int carryOverNibble = -1;
		int ofs = 0;
		for (int idx = 0; idx < text.length(); idx++) {
			char c = text.charAt(idx);
			int tableIdx = 0;
			for (int i = 0; i < XLATE_TABLE.length; i++) {
				if (c == XLATE_TABLE[i]) {
					tableIdx = i;
					break;
				}
			}
			if (tableIdx > 12)
				tableIdx += 195;
			if (carryOverNibble == -1) {
				if (tableIdx < 13)
					carryOverNibble = tableIdx;
				else
					b[ofs++] = (byte) (tableIdx);
			} else if(tableIdx < 13) {
				b[ofs++] = (byte) ((carryOverNibble << 4) + tableIdx);
				carryOverNibble = -1;
			} else {
				b[ofs++] = (byte) ((carryOverNibble << 4) + (tableIdx >> 4));
				carryOverNibble = tableIdx & 0xf;
			}
		}
		if (carryOverNibble != -1)
			b[ofs++] = (byte) (carryOverNibble << 4);
	}

	public static int direction(final int srcX, final int srcY, final int destX, final int destY) {
		int dx = destX - srcX;
		int dy = destY - srcY;
		if (dx < 0) {
			if (dy < 0) {
				if (dx < dy)
					return 11;
				else
					if (dx > dy)
						return 9;
				else
					return 10;
			} else if (dy > 0) {
				if (-dx < dy)
					return 15;
				else if (-dx > dy)
					return 13;
				else
					return 14;
			} else {
				return 12;
			}
		} else if(dx > 0) {
			if (dy < 0) {
				if (dx < -dy)
					return 7;
				else if (dx > -dy)
					return 5;
				else
					return 6;
			} else if(dy > 0) {
				if (dx < dy)
					return 1;
				else
					if (dx > dy)
						return 3;
				else
					return 2;
			} else {
				return 4;
			}
		} else {
			if(dy < 0) {
				return 8;
			} else if(dy > 0) {
				return 0;
			} else {
				return -1;
			}
		}
	}
}
