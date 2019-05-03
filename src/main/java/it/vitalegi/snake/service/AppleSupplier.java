package it.vitalegi.snake.service;

import java.awt.Point;

import it.vitalegi.snake.model.SnakeModel;

public interface AppleSupplier {

	public Point nextApple(SnakeModel model);
}
