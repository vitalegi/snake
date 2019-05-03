package it.vitalegi.snake.model;

import java.awt.Point;
import java.util.Deque;

import it.vitalegi.snake.Direction;

public class SnakeModel {

	protected int width;
	protected int height;
	protected Deque<Point> snake;
	protected Point apple;
	protected GameStatus status;
	protected Direction lastDirection;
	protected SnakeHistory history;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Point getApple() {
		return apple;
	}

	public void setApple(Point apple) {
		this.apple = apple;
	}

	public Deque<Point> getSnake() {
		return snake;
	}

	public void setSnake(Deque<Point> snake) {
		this.snake = snake;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public Direction getLastDirection() {
		return lastDirection;
	}

	public void setLastDirection(Direction lastDirection) {
		this.lastDirection = lastDirection;
	}

	public SnakeHistory getHistory() {
		return history;
	}

	public void setHistory(SnakeHistory history) {
		this.history = history;
	}

}