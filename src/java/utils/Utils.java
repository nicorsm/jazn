package utils;

import java.util.List;
import java.util.Random;

public class Utils {

	public static <T> T randomIn(List<T> list) {
		return list.get(new Random().nextInt(list.size()));
	}
	
	public static <T> T randomIn(T[] list) {
		return list[new Random().nextInt(list.length)];
	}
}

