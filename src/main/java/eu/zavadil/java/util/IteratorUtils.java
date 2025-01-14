package eu.zavadil.java.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorUtils {

	public static <T> List<T> createChunk(Iterator<T> iterator, int chunkSize) {
		int chunked = 0;
		List<T> chunk = new ArrayList<>(chunkSize);
		while (iterator.hasNext() && chunked < chunkSize) {
			chunk.add(iterator.next());
			chunked++;
		}
		return chunk;
	}

}
