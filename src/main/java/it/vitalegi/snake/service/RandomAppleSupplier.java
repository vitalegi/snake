package it.vitalegi.snake.service;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.snake.model.SnakeModel;
import it.vitalegi.snake.util.RandomUtil;

@Service
public class RandomAppleSupplier implements AppleSupplier {

	@Autowired
	SnakeService snakeService;

	public Point nextApple(SnakeModel model) {
		List<Point> freePoints = getFreepoints(model);
		return freePoints.get(RandomUtil.nextInt(freePoints.size()));
	}

	private List<Point> getFreepoints(SnakeModel model) {
		List<Point> freePoints = new ArrayList<>();
		for (int x = 0; x < model.getWidth(); x++) {
			for (int y = 0; y < model.getHeight(); y++) {
				if (!snakeService.isSnakeBody(model, x, y)) {
					freePoints.add(new Point(x, y));
				}
			}
		}
		return freePoints;
	}
}