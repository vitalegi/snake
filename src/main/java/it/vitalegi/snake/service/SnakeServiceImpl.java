package it.vitalegi.snake.service;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import org.springframework.stereotype.Service;

import it.vitalegi.snake.Direction;
import it.vitalegi.snake.gui.SimpleColorTile;
import it.vitalegi.snake.gui.Tile;
import it.vitalegi.snake.model.GameStatus;
import it.vitalegi.snake.model.SnakeModel;

@Service
public class SnakeServiceImpl implements SnakeService {

	@Override
	public GameStatus move(SnakeModel model, Direction direction, AppleSupplier supplier) {
		boolean die = false;
		boolean eatApple = false;

		Point target = Direction.move(getHead(model), direction);
		model.setLastDirection(direction);
		if (isApple(model, target)) {
			eatApple = true;
		} else {
			die = (isSnakeBody(model, target) && !isSnakeTail(model, target)) || !isValidPoint(model, target);
		}

		if (die) {
			model.getStatus().gameover();
			return model.getStatus();
		}
		model.getStatus().step();
		if (eatApple) {
			model.getStatus().incrementScore();
			model.getSnake().addFirst(target);
			model.setApple(supplier.nextApple(model));
			model.getHistory().eatApple(model.getStatus().getSteps());
		} else {
			model.getSnake().addFirst(target);
			model.getSnake().removeLast();
		}
		return model.getStatus();
	}

	@Override
	public Tile[][] getPrintableModel(List<SnakeModel> models, int width, int height) {
		Tile[][] tiles = new Tile[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = SimpleColorTile.newInstance(Color.WHITE);
			}
		}
		models.stream().forEach(model -> {
			model.getSnake().stream() //
					.filter(p -> isValidPoint(model, p.x, p.y))//
					.iterator()//
					.forEachRemaining(p -> {
						tiles[p.x][p.y] = SimpleColorTile.newInstance(Color.BLACK);
					});
		});
		models.stream().filter(model -> model.getApple() != null)//
				.forEach(model -> {
					tiles[model.getApple().x][model.getApple().y] = SimpleColorTile.newInstance(Color.RED);
					tiles[getHead(model).x][getHead(model).y] = SimpleColorTile.newInstance(Color.GRAY);
				});

		return tiles;
	}

	@Override
	public boolean isComplete(SnakeModel model) {
		return model.getSnake().size() == getArea(model);
	}

	@Override
	public int getArea(SnakeModel model) {
		return model.getWidth() * model.getHeight();
	}

	@Override
	public boolean isValidPoint(SnakeModel model, int x, int y) {
		return x >= 0 && x < model.getWidth() && y >= 0 && y < model.getHeight();
	}

	@Override
	public boolean isValidPoint(SnakeModel model, Point p) {
		return isValidPoint(model, p.x, p.y);
	}

	@Override
	public Point getHead(SnakeModel model) {
		return model.getSnake().getFirst();
	}

	@Override
	public boolean isApple(SnakeModel model, int x, int y) {
		return isApple(model, new Point(x, y));
	}

	@Override
	public boolean isApple(SnakeModel model, Point target) {
		return target.equals(model.getApple());
	}

	@Override
	public boolean isSnakeBody(SnakeModel model, int x, int y) {
		return isSnakeBody(model, new Point(x, y));
	}

	@Override
	public boolean isSnakeBody(SnakeModel model, Point target) {
		return model.getSnake().stream().anyMatch(p -> p.equals(target));
	}

	@Override
	public boolean isSnakeTail(SnakeModel model, Point target) {
		return model.getSnake().getLast().equals(target);
	}

}
