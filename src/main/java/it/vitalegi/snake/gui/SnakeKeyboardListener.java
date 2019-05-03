package it.vitalegi.snake.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.vitalegi.snake.Direction;
import it.vitalegi.snake.model.GameStatus;
import it.vitalegi.snake.model.SnakeModel;
import it.vitalegi.snake.service.SnakeService;

public class SnakeKeyboardListener implements KeyListener {

	SnakeService snakeService;
	SnakeGui gui;
	SnakeModel model;

	public SnakeKeyboardListener(SnakeGui gui, SnakeModel model, SnakeService snakeService) {
		super();
		this.gui = gui;
		this.snakeService = snakeService;
		this.model = model;
	}

	@Override
	public void keyTyped(KeyEvent evt) {
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		int key = evt.getKeyCode();
		log.info("Key Pressed ");
		switch (key) {
		case KeyEvent.VK_UP:
			log.info("Key UP");
			snakeService.move(model, Direction.NORTH);
			break;
		case KeyEvent.VK_RIGHT:
			log.info("Key RIGHT");
			snakeService.move(model, Direction.EAST);
			break;
		case KeyEvent.VK_DOWN:
			log.info("Key DOWN");
			snakeService.move(model, Direction.SOUTH);
			break;
		case KeyEvent.VK_LEFT:
			log.info("Key LEFT");
			snakeService.move(model, Direction.WEST);
			break;
		default:
			break;
		}
		GameStatus status = model.getStatus();
		log.info("Gameover: {} Score: {}", status.isGameover(), status.getScore());
		if (status.isGameover()) {
			gui.dispose();
		}
		gui.getFrame().repaint();
	}

	@Override
	public void keyReleased(KeyEvent evt) {

	}

	static Logger log = LoggerFactory.getLogger(SnakeKeyboardListener.class);
}
