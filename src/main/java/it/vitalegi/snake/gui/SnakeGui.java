package it.vitalegi.snake.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.vitalegi.neat.impl.Generation;
import it.vitalegi.neat.impl.analysis.EvolutionAnalysis;
import it.vitalegi.snake.Direction;
import it.vitalegi.snake.ai.AiPlayer;
import it.vitalegi.snake.ai.NeatTrainer;
import it.vitalegi.snake.model.SnakeModel;
import it.vitalegi.snake.player.DiligentBotPlayer;
import it.vitalegi.snake.player.DummyBotPlayer;
import it.vitalegi.snake.player.SnakePlayer;
import it.vitalegi.snake.service.AppleSupplier;
import it.vitalegi.snake.service.DeterminedAppleSupplier;
import it.vitalegi.snake.service.GameFactoryImpl;
import it.vitalegi.snake.service.SnakeServiceImpl;
import it.vitalegi.snake.util.IOUtil;

@Component
@Profile("!test")
public class SnakeGui implements ApplicationRunner {

	protected JFrame frame;
	protected List<SnakePlayer> players;
	protected SnakePanel panel;
	@Autowired
	protected NeatTrainer neatTrainer;
	@Autowired
	protected SnakeServiceImpl snakeService;

	private static final int TIMEOUT = 5;

	public void init(AppleSupplier supplier, int tileX, int tileY, int population) {

		GameFactoryImpl gameFactory = new GameFactoryImpl(snakeService, supplier, tileX, tileY);

		players = new ArrayList<>();
		for (int i = 0; i < population; i++) {
			players.add(new DummyBotPlayer(gameFactory.createGame(), snakeService));
		}
		players.add(new DiligentBotPlayer(gameFactory.createGame(), snakeService));

		frame = new JFrame();
		frame.setSize(tileX * SnakePanel.TILE_WIDTH + 25, tileY * SnakePanel.TILE_HEIGHT + 50);

		panel = new SnakePanel();
		panel.setBounds(0, 0, tileX * SnakePanel.TILE_WIDTH, tileY * SnakePanel.TILE_HEIGHT);
		frame.add(panel);

		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void dispose() {
		frame.dispose();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		int tileX = 10;
		int tileY = 10;
		int population = 150;
		int generations = 500;
		AppleSupplier supplier = new DeterminedAppleSupplier();
		GameFactoryImpl gameFactory = new GameFactoryImpl(snakeService, supplier, tileX, tileY);

		init(tileX, tileY, population);
		runAi(gameFactory, generations, population);
	}

	protected void runAi(GameFactoryImpl gameFactory, int generations, int population) {
		Generation gen = neatTrainer.createFirstGeneration(gameFactory, population, new double[] { 1.0 }, 20.0, 1.0,
				5.0);
		EvolutionAnalysis analysis = neatTrainer.createEvolutionAnalysis();

		for (int genNumber = 0; genNumber < generations; genNumber++) {
			log.info("Start Generation {}", genNumber);
			players = gen.getPlayers().stream().map(p -> (SnakePlayer) p).collect(Collectors.toList());
			runBots(genNumber % 20 == 0);
			log.info("End Generation {}", genNumber);
			gen.computeFitnesses();
			neatTrainer.updateEvolutionAnalysis(analysis, gen);
			if (genNumber % 20 == 0 && genNumber > 0) {
				neatTrainer.logStatus(analysis);
			}
			gen = gen.nextGeneration();
		}
		neatTrainer.logStatus(analysis);
	}

	protected void humanPlayer() {
		SnakeKeyboardListener listener = new SnakeKeyboardListener(this, null, snakeService);
		getFrame().setFocusTraversalKeysEnabled(true);
		getFrame().setFocusable(true);
		getFrame().addKeyListener(listener);
	}

	protected void runBots(boolean refreshScreen) {

		int counter = 0;
		while (isAnyAlive()) {
			if (refreshScreen) {
				IOUtil.sleep(TIMEOUT);
			}

			players.forEach(player -> {
				Direction direction = player.nextMove();
				if (direction == null) {
					if (log.isDebugEnabled()) {
						log.debug("No direction found, proceed straight");
					}
					direction = player.getModel().getLastDirection();
				}
				snakeService.move(player.getModel(), direction);
			});

			players.stream().filter(p -> {
				int lastAppleEatStep = p.getModel().getHistory().getLastEatStep();
				int currentStep = p.getModel().getStatus().getSteps();
				return currentStep - lastAppleEatStep > 20;
			}).forEach(p -> {
				p.getModel().getStatus().gameover();
			});
			if (++counter % 50 == 0) {
				logStatus();
			}
			if (refreshScreen) {
				List<SnakeModel> models = players.stream() //
						.map(p -> (AiPlayer) p).sorted(Comparator.comparing(AiPlayer::getFitness).reversed())
						.map(SnakePlayer::getModel)//
						.filter(model -> !model.getStatus().isGameover()) //
						.limit(1).collect(Collectors.toList());
				int width = players.get(0).getModel().getWidth();
				int height = players.get(0).getModel().getHeight();

				panel.setTiles(snakeService.getPrintableModel(models, width, height));
				getFrame().repaint();
			}
		}
	}

	protected void logStatus() {
		if (log.isInfoEnabled()) {
			log.info("Total: {} Alive: {} Best Alive: {} Best Score: {} Min Score: {} Avg Score: {}", players.size(),
					players.stream().filter(p -> !p.getModel().getStatus().isGameover()).count(), //
					players.stream().filter(p -> !p.getModel().getStatus().isGameover()) //
							.mapToInt(p -> p.getModel().getStatus().getScore())//
							.max().getAsInt(), //
					players.stream().mapToInt(p -> p.getModel().getStatus().getScore()).max().getAsInt(), //
					players.stream().mapToInt(p -> p.getModel().getStatus().getScore()).min().getAsInt(), //
					players.stream().mapToInt(p -> p.getModel().getStatus().getScore()).average().getAsDouble());
		}
	}

	protected boolean isAnyAlive() {
		return players.stream()//
				.map(SnakePlayer::getModel)//
				.map(SnakeModel::getStatus)//
				.anyMatch(status -> !status.isGameover());
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public SnakePanel getPanel() {
		return panel;
	}

	public void setPanel(SnakePanel panel) {
		this.panel = panel;
	}

	static Logger log = LoggerFactory.getLogger(SnakeGui.class);
}
