package it.vitalegi.snake.ai;

import java.awt.Point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.vitalegi.neat.impl.Gene;
import it.vitalegi.neat.impl.player.AbstractPlayer;
import it.vitalegi.snake.Direction;
import it.vitalegi.snake.model.GameStatus;
import it.vitalegi.snake.model.SnakeModel;
import it.vitalegi.snake.player.SnakePlayer;
import it.vitalegi.snake.service.SnakeService;

public class AiPlayer extends AbstractPlayer implements SnakePlayer {

	protected double[] biases;
	protected double fitness;
	protected SnakeModel snakeModel;
	protected SnakeService snakeService;

	public AiPlayer(Gene gene, double[] biases, SnakeModel snakeModel, SnakeService snakeService) {
		setGene(gene);
		this.biases = biases;
		this.snakeModel = snakeModel;
		this.snakeService = snakeService;
	}

	protected double[] getInputs() {
		double[] inputs = new double[inputsCount()];

		Point head = snakeService.getHead(snakeModel);
		// inputs[0] = getInput(head, 1, 0);
		// inputs[1] = getInput(head, 1, 1);
		// inputs[2] = getInput(head, 0, 1);
		// inputs[3] = getInput(head, -1, 1);
		// inputs[4] = getInput(head, -1, 0);
		// inputs[5] = getInput(head, -1, -1);
		// inputs[6] = getInput(head, 0, -1);
		// inputs[7] = getInput(head, 1, -1);

		// int id = 0;
		// for (double incX = -1; incX <= 1; incX += 0.5) {
		// for (double incY = -1; incY <= 1; incY += 0.5) {
		// if (incX == 0 && incY == 0) {
		// continue;
		// }
		// if (Math.abs(incX) == 0.5 && Math.abs(incY) == 0.5) {
		// continue;
		// }
		// inputs[id] = getInput(head, incX, incY);
		// id++;
		// }
		// }
		inputs[0] = getInput(head, 1, 0);
		inputs[1] = getInput(head, -1, 0);
		inputs[2] = getInput(head, 0, 1);
		inputs[3] = getInput(head, 0, -1);
		inputs[4] = (head.getX() - snakeModel.getApple().x) / snakeModel.getWidth();
		inputs[5] = (head.getY() - snakeModel.getApple().y) / snakeModel.getHeight();

		Logger log = LoggerFactory.getLogger(AiPlayer.class);

		log.debug("Head in {}, apple in {}, inputs {}", head, snakeModel.getApple(), inputs);
		return inputs;
	}

	protected double getInput(Point from, double incX, double incY) {
		int c = 1;
		while (snakeService.isValidPoint(snakeModel, getIndex(from.x, incX, c), getIndex(from.y, incY, c))) {
			int x = getIndex(from.x, incX, c);
			int y = getIndex(from.y, incY, c);
			if (snakeService.isApple(snakeModel, x, y)) {
				return -from.distance(x, y) / getMaxDistance();
			}
			if (snakeService.isSnakeBody(snakeModel, x, y)) {
				return from.distance(x, y) / getMaxDistance();
			}
			c++;
		}
		return from.distance(getIndex(from.x, incX, c - 1), getIndex(from.y, incY, c - 1)) / getMaxDistance();
	}

	protected int getIndex(int from, double inc, int c) {
		return (int) Math.round(from + inc * c);
	}

	protected double getMaxDistance() {
		return new Point(0, 0).distance(snakeModel.getWidth(), snakeModel.getHeight());
	}

	public static int inputsCount() {
		return 4 + 2;
	}

	public static int outputsCount() {
		return 4;
	}

	@Override
	public double getFitness() {
		GameStatus status = snakeModel.getStatus();
		int eatenAppleScore = status.getScore() * 500 - status.getSteps();
		Point head = snakeService.getHead(snakeModel);
		Point nextApple = snakeModel.getApple();
		double distanceToNextApple = 0;
		if (nextApple != null) {
			distanceToNextApple = nextApple.distance(head);
		}
		return eatenAppleScore + getMaxDistance() - distanceToNextApple;
	}

	@Override
	public Direction nextMove() {
		double[] outputs = super.feedForward(getInputs(), biases);
		return getDirection(outputs);
	}

	@Override
	public SnakeModel getModel() {
		return snakeModel;
	}

	protected Direction getDirection(double[] outputs) {
		int maxIndex = 0;
		for (int i = 0; i < outputs.length; i++) {
			if (outputs[i] > outputs[maxIndex]) {
				maxIndex = i;
			}
		}
		if (maxIndex == 0) {
			return Direction.NORTH;
		}
		if (maxIndex == 1) {
			return Direction.EAST;
		}
		if (maxIndex == 2) {
			return Direction.SOUTH;
		}
		return Direction.WEST;
	}
}
