package it.vitalegi.snake.player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.vitalegi.snake.Direction;
import it.vitalegi.snake.model.SnakeModel;
import it.vitalegi.snake.service.SnakeService;
import it.vitalegi.snake.util.RandomUtil;

public class DummyBotPlayer implements SnakePlayer {

	protected SnakeModel model;
	protected SnakeService snakeService;

	public DummyBotPlayer(SnakeModel model, SnakeService snakeService) {
		this.model = model;
		this.snakeService = snakeService;
	}

	@Override
	public Direction nextMove() {
		List<Direction> availableMoves = getAvailableMoves();
		if (log.isDebugEnabled()) {
			log.debug("Snake Body: {}",
					model.getSnake().stream().map(Point::toString).collect(Collectors.joining(", ")));
			log.debug("Last direction: {}", model.getLastDirection());
			log.debug("Available moves: {}",
					availableMoves.stream().map(Direction::name).collect(Collectors.joining(", ")));
		}
		if (availableMoves.isEmpty()) {
			return null;
		}
		Direction next = availableMoves.get(RandomUtil.nextInt(availableMoves.size()));
		if (log.isDebugEnabled()) {
			log.debug("Move to {}", next);
		}
		return next;
	}

	public SnakeModel getModel() {
		return model;
	}

	protected List<Direction> getAvailableMoves() {
		List<Direction> directions = new ArrayList<>();
		if (isAvailableMove(Direction.NORTH)) {
			directions.add(Direction.NORTH);
		}
		if (isAvailableMove(Direction.EAST)) {
			directions.add(Direction.EAST);
		}
		if (isAvailableMove(Direction.SOUTH)) {
			directions.add(Direction.SOUTH);
		}
		if (isAvailableMove(Direction.WEST)) {
			directions.add(Direction.WEST);
		}
		return directions;
	}

	protected boolean isAvailableMove(Direction direction) {
		Point dest = Direction.move(snakeService.getHead(model), direction);
		if (!snakeService.isValidPoint(model, dest)) {
			return false;
		}
		if (snakeService.isSnakeBody(model, dest)) {
			return false;
		}
		if (direction.isOpposite(model.getLastDirection())) {
			return false;
		}
		return true;
	}

	Logger log = LoggerFactory.getLogger(DummyBotPlayer.class);
}
