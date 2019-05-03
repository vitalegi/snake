package it.vitalegi.snake.ai;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.neat.impl.Generation;
import it.vitalegi.neat.impl.analysis.EvolutionAnalysis;
import it.vitalegi.neat.impl.function.CompatibilityDistance;
import it.vitalegi.neat.impl.function.CompatibilityDistanceImpl;
import it.vitalegi.neat.impl.player.Player;
import it.vitalegi.neat.impl.player.PlayerFactory;
import it.vitalegi.snake.service.GameFactoryImpl;
import it.vitalegi.snake.service.SnakeService;

@Service
public class NeatTrainer {

	Logger log = LoggerFactory.getLogger(NeatTrainer.class);

	@Autowired
	protected SnakeService snakeService;

	public Generation createFirstGeneration(GameFactoryImpl gameFactory, int population, double[] biases, double deltaT,
			double c1, double c2) {

		PlayerFactory playerFactory = new AiPlayerFactory(biases, gameFactory, snakeService);
		CompatibilityDistance cd = new CompatibilityDistanceImpl(deltaT, c1, c2);
		return Generation.createGen0(playerFactory, AiPlayer.inputsCount() + biases.length, AiPlayer.outputsCount(), 0,
				population, cd);
	}

	public EvolutionAnalysis createEvolutionAnalysis() {
		return new EvolutionAnalysis();
	}

	public void updateEvolutionAnalysis(EvolutionAnalysis analysis, Generation computedGeneration) {
		analysis.add(computedGeneration);
	}

	public void logStatus(EvolutionAnalysis analysis) {
		Generation lastGen = analysis.getGenerations().get(analysis.getGenerations().size() - 1);

		Player bestPlayer = lastGen.getPlayers().stream() //
				.sorted(Comparator.comparing(Player::getFitness).reversed()) //
				.findFirst().orElse(null);

		if (log.isDebugEnabled()) {
			log.debug(lastGen.stringify());
			analysis.logAnalysis(log);
			log.debug("Networks");
		}
		if (log.isInfoEnabled()) {
			log.info("Best Player {}: ", bestPlayer.getGene().stringify(true));
		}
	}

	protected Generation perform(Generation generation) {

		generation.computeFitnesses();
		return generation.nextGeneration();
	}

}
