package it.vitalegi.snake.player;

import it.vitalegi.snake.Direction;
import it.vitalegi.snake.model.SnakeModel;

public interface SnakePlayer {

	public Direction nextMove();

	public SnakeModel getModel();
}