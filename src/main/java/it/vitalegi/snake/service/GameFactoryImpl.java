package it.vitalegi.snake.service;

import java.awt.Point;
import java.util.LinkedList;

import it.vitalegi.snake.Direction;
import it.vitalegi.snake.model.GameStatus;
import it.vitalegi.snake.model.SnakeHistory;
import it.vitalegi.snake.model.SnakeModel;

public class GameFactoryImpl {
	int width;
	int height;
	SnakeServiceImpl snakeService;
	AppleSupplier supplier;

	public GameFactoryImpl(SnakeServiceImpl snakeService, AppleSupplier supplier, int width, int height) {
		this.snakeService = snakeService;
		this.supplier = supplier;
		this.width = width;
		this.height = height;
	}

	public SnakeModel createGame() {
		SnakeModel model = new SnakeModel();
		model.setHeight(height);
		model.setWidth(width);
		model.setSnake(new LinkedList<>());
		model.getSnake().add(new Point(width / 2, height / 2));
		model.setApple(supplier.nextApple(model));
		model.setStatus(new GameStatus());
		model.setLastDirection(Direction.NORTH);
		model.setHistory(new SnakeHistory());
		return model;
	}

}
