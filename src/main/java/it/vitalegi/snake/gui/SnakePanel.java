package it.vitalegi.snake.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnakePanel extends JPanel {

	public static final int TILE_WIDTH = 20;
	public static final int TILE_HEIGHT = 20;

	Tile[][] tiles;

	@Override
	public void paintComponent(Graphics g) {
		if (tiles == null) {
			return;
		}
		for (int y = 0; y < tiles[0].length; y++) {
			for (int x = 0; x < tiles.length; x++) {
				Tile tile = tiles[x][y];
				int xPos = x * TILE_WIDTH;
				int yPos = y * TILE_HEIGHT;
				tile.paintComponent(g, xPos, yPos, TILE_WIDTH, TILE_HEIGHT);
			}
		}
		super.paintComponents(g);
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	Logger log = LoggerFactory.getLogger(SnakePanel.class);

}
