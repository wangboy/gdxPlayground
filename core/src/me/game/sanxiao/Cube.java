package me.game.sanxiao;

import static me.game.sanxiao.Assests.*;
import static me.game.sanxiao.Assests.CUBE_FALL_SPEED;

/**
 * Created by wangbo on 16/6/6.
 */
public class Cube {

	public static final int STATIC = 0;
	public static final int FALL = 1;
	public static final int UP = 2;
	public static final int RIGHT = 3;
	public static final int LEFT = 4;


	public int i;
	public int j;

	public float x;
	public float y;

	public int state = STATIC;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	Color color;

	public Cube(int color) {
		this.color = Color.valueOf(color);
	}

	public int update(float delta) {

		float[] pos = World.getPosition(i, j);
		switch (state) {
			case STATIC:
				this.x = pos[0];
				this.y = pos[1];
				break;
			case FALL:
				this.y -= delta * CUBE_FALL_SPEED;
				if (this.y < pos[1]) {
					this.y = pos[1];
					state = STATIC;
				}
				break;
			case UP:
				this.y += delta * CUBE_FALL_SPEED;
				if (this.y > pos[1]) {
					this.y = pos[1];
					state = STATIC;
				}
				break;
			case RIGHT:
				this.x += delta * CUBE_FALL_SPEED;
				if (this.x > pos[0]) {
					this.x = pos[0];
					state = STATIC;
				}
				break;

			case LEFT:
				this.x -= delta * CUBE_FALL_SPEED;
				if (this.x < pos[0]) {
					this.x = pos[0];
					state = STATIC;
				}
				break;
			default:
				break;
		}

		return state;
	}


	@Override
	public String toString() {
		return "Cube{" +
				"i=" + i +
				", j=" + j +
				", x=" + x +
				", y=" + y +
				", state=" + state +
				", color=" + color +
				'}';
	}
}
