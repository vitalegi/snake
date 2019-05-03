package it.vitalegi.snake;

import java.awt.Point;

public enum Direction {

	NORTH {
		public Direction getOpposite() {
			return Direction.SOUTH;
		}
	},
	EAST {
		public Direction getOpposite() {
			return Direction.WEST;
		}
	},
	SOUTH {
		public Direction getOpposite() {
			return Direction.NORTH;
		}
	},
	WEST {
		public Direction getOpposite() {
			return Direction.EAST;
		}
	};

	protected abstract Direction getOpposite();

	public static Point move(Point point, Direction direction) {
		if (direction == null) {
			throw new NullPointerException("Null direction");
		}
		switch (direction) {
		case NORTH:
			return new Point(point.x, point.y - 1);
		case EAST:
			return new Point(point.x + 1, point.y);
		case SOUTH:
			return new Point(point.x, point.y + 1);
		case WEST:
			return new Point(point.x - 1, point.y);
		default:
			return point;
		}
	}

	public boolean isOpposite(Direction dir2) {
		if (dir2 == null) {
			return false;
		}
		return getOpposite() == dir2;
	}
}
