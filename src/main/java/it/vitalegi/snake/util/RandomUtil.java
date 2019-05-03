package it.vitalegi.snake.util;

import java.util.Random;

public class RandomUtil {

	private static Random random = new Random();

	public static void init() {
		random = new Random();
	}

	public static int nextInt(int bound) {
		return random.nextInt(bound);
	}
}
