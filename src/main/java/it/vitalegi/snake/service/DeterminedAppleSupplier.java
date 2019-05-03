package it.vitalegi.snake.service;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.snake.model.SnakeModel;
import it.vitalegi.snake.util.RandomUtil;

@Service
public class DeterminedAppleSupplier implements AppleSupplier {

	@Autowired
	SnakeService snakeService;

	protected static List<Point> apples;

	public Point nextApple(SnakeModel model) {
		if (snakeService.isComplete(model)) {
			return null;
		}
		if (apples == null) {
			setupApples(model.getWidth(), model.getHeight());
		}
		int score = 0;
		if (model.getStatus() != null) {
			score = model.getStatus().getScore();
		}
		return apples.get(score);
	}

	protected static void setupApples(int width, int height) {
		apples = new ArrayList<>();
		Point lastApple = new Point(RandomUtil.nextInt(width), RandomUtil.nextInt(height));
		for (int i = 0; i < 50; i++) {
			Point nextApple = lastApple;
			while (nextApple.distance(lastApple) < 4 && nextApple.distance(lastApple) > 6) {
				nextApple = new Point(RandomUtil.nextInt(width), RandomUtil.nextInt(height));
			}
			apples.add(nextApple);
		}
	}
}
