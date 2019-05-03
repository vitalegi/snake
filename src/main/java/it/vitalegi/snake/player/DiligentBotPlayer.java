package it.vitalegi.snake.player;

import java.awt.Point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.vitalegi.snake.Direction;
import it.vitalegi.snake.model.SnakeModel;
import it.vitalegi.snake.service.SnakeService;

public class DiligentBotPlayer implements SnakePlayer {

	protected SnakeModel model;
	protected SnakeService snakeService;

	public DiligentBotPlayer(SnakeModel model, SnakeService snakeService) {
		this.model = model;
		this.snakeService = snakeService;
	}

	/**
	 * Eseguo una serpentina in senso orario, utilizzando la colonna a sinistra
	 * per risalire.
	 */
	@Override
	public Direction nextMove() {
		Direction lastDir = model.getLastDirection();
		Point head = snakeService.getHead(model);
		switch (lastDir) {
		case NORTH:
			if (head.y == 0) {
				return Direction.EAST;
			}
			return Direction.NORTH;
		case EAST:
			if (head.x == model.getWidth() - 1) {
				return Direction.SOUTH;
			}
			return Direction.EAST;
		case SOUTH:
			if (head.x == model.getWidth() - 1) {
				return Direction.WEST;
			} else {
				return Direction.EAST;
			}
		case WEST:
			if (head.x > 1) {
				return Direction.WEST;
			}
			if (head.y < model.getHeight() - 1) {
				return Direction.SOUTH;
			}
			if (head.x == 1) {
				return Direction.WEST;
			}
			return Direction.NORTH;
		}
		return null;
	}

	public SnakeModel getModel() {
		return model;
	}

	Logger log = LoggerFactory.getLogger(DiligentBotPlayer.class);
}
