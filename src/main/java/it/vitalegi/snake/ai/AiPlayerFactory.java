package it.vitalegi.snake.ai;

import it.vitalegi.neat.impl.Gene;
import it.vitalegi.neat.impl.player.Player;
import it.vitalegi.neat.impl.player.PlayerFactory;
import it.vitalegi.snake.service.GameFactoryImpl;
import it.vitalegi.snake.service.SnakeService;

public class AiPlayerFactory implements PlayerFactory {

	double[] biases;
	SnakeService snakeService;
	GameFactoryImpl gameFactory;

	public AiPlayerFactory(double[] biases, GameFactoryImpl gameFactory, SnakeService snakeService) {
		super();
		this.biases = biases;
		this.gameFactory = gameFactory;
		this.snakeService = snakeService;
	}

	@Override
	public Player newPlayer(Gene gene) {
		return new AiPlayer(gene, biases, gameFactory.createGame(), snakeService);
	}
}