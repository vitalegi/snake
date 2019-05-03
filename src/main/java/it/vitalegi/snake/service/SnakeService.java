package it.vitalegi.snake.service;

import java.awt.Point;
import java.util.List;

import it.vitalegi.snake.Direction;
import it.vitalegi.snake.gui.Tile;
import it.vitalegi.snake.model.GameStatus;
import it.vitalegi.snake.model.SnakeModel;

public interface SnakeService {

	GameStatus move(SnakeModel model, Direction direction, AppleSupplier supplier);

	Tile[][] getPrintableModel(List<SnakeModel> models, int width, int height);

	boolean isComplete(SnakeModel model);

	int getArea(SnakeModel model);

	boolean isValidPoint(SnakeModel model, int x, int y);

	boolean isValidPoint(SnakeModel model, Point p);

	Point getHead(SnakeModel model);

	boolean isApple(SnakeModel model, int x, int y);

	boolean isApple(SnakeModel model, Point target);

	boolean isSnakeBody(SnakeModel model, int x, int y);

	boolean isSnakeBody(SnakeModel model, Point target);

	boolean isSnakeTail(SnakeModel model, Point target);

}