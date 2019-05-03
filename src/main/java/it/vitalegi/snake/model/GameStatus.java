package it.vitalegi.snake.model;

public class GameStatus {

	private int score;
	private boolean gameover;
	private int steps;

	public GameStatus() {
		super();
	}

	@Override
	public String toString() {
		return "Status: score: " + score + " gameover: " + gameover + " steps=" + steps;
	}

	public GameStatus incrementScore() {
		score++;
		return this;
	}

	public GameStatus gameover() {
		gameover = true;
		return this;
	}

	public GameStatus step() {
		steps++;
		return this;
	}

	public int getScore() {
		return score;
	}

	public boolean isGameover() {
		return gameover;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

}
