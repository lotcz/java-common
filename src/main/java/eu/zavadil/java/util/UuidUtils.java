package eu.zavadil.java.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtils {

	public static UUID bytesToUuid(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		long firstLong = bb.getLong();
		long secondLong = bb.getLong();
		return new UUID(firstLong, secondLong);
	}

	public static byte[] uuidToBytes(UUID uuid) {
		ByteBuffer bb = ByteBuffer.allocate(16);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}

	public static boolean isValidUuid(String uuid) {
		try {
			UUID.fromString(uuid);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

}
