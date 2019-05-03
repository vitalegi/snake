package it.vitalegi.snake.model;

import java.util.ArrayList;
import java.util.List;

public class SnakeHistory {

	protected List<Integer> appleEaten;

	public SnakeHistory() {
		appleEaten = new ArrayList<>();
	}

	public void eatApple(int step) {
		appleEaten.add(step);
	}

	public int getLastEatStep() {
		if (appleEaten.isEmpty()) {
			return 0;
		}
		return appleEaten.get(appleEaten.size() - 1);
	}
}
