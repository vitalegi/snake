package it.vitalegi.snake.gui;

import java.awt.Color;
import java.awt.Graphics;

public class SimpleColorTile implements Tile {

	private Color color;

	public static SimpleColorTile newInstance(Color color) {
		SimpleColorTile tile = new SimpleColorTile();
		tile.color = color;
		return tile;
	}

	@Override
	public void paintComponent(Graphics g, int x, int y, int width, int height) {
		Color curr = g.getColor();
		g.setColor(color);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		g.setColor(curr);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
