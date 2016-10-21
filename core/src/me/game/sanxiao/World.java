package me.game.sanxiao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import game.kt.MyGame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static me.game.sanxiao.Assests.*;
import static me.game.sanxiao.Cube.*;

/**
 * Created by wangbo on 16/6/6.
 */
public class World {

	public interface WorldListener {
		void onMatch(Set<Cube> matches);
	}

	public static final int STATIC = 0;

	public static final int MOVING = 1;

	public static final int MATCHING = 2;


	MyGame game;

	Cube[][] matrix;

	//
	Cube[] dragCubes;
	int dragDirection;
	//

	boolean isMoving;
	boolean needMatching = false;

	int state = STATIC;

	Set<Cube> match = new HashSet<>();
	Set<Cube> newCubes = new HashSet<>();

	Cube[][] cpMatrix = new Cube[7][7];

	WorldListener listener;

	public World(MyGame game) {
		this.game = game;

		matrix = new Cube[7][7];

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				Cube cube = genCube();
				cube.i = i;
				cube.j = j;
				matrix[i][j] = cube;
				cube.update(0);
				//init to fall
				cube.y += MATRIX_OFFSET_Y;
				cube.state = FALL;

			}
		}

		printMatirx(" new ");

		dragCubes = new Cube[2];
		isMoving = false;
	}

	public WorldListener getListener() {
		return listener;
	}

	public void setListener(WorldListener listener) {
		this.listener = listener;
	}

	//从上往下打印
	private void printMatirx(String info) {
		String blank = "          ";
		System.out.println("==========" + info + "==============");
		for (int j = 6; j >= 0; j--) {
			for (int i = 0; i < 7; i++) {
				Cube cube = matrix[i][j];
				StringBuilder out = new StringBuilder("[" + cube.getColor() + "]");
				if (this.match.contains(cube)) {
					out.append("--");
				}
				out.append(blank);
				out.delete(10, out.length());
				System.out.print(out);
			}
			System.out.println();
		}
	}

	public void update(float delta) {
		//播放动画中
//		if (state == MATCHING) {
//			return;
//		}

		if (needMatching) {
			needMatching = false;
			boolean result = checkMatch();

			if (result) {
				clearDrag();
			} else {
				//回退
				if (dragCubes[0] != null) {
					cancelDrag();
				}
			}
		}

		if (this.state != MATCHING) {

			boolean moving = false;
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					int state = matrix[i][j].update(delta);
					if (state != STATIC) {
						moving = true;
					}
				}
			}

			if (isMoving && !moving) {
				needMatching = true;
			}
			if (moving) {
				isMoving = moving;
				this.state = MOVING;
			} else {
				this.state = STATIC;
			}
		}
	}

	private Cube genCube() {
		int index = MathUtils.random(cubes.size - 1);
		return new Cube(index);
	}

	private void debug(Cube src, Cube des) {
		Gdx.app.log(this.toString(), " switch \nsrc = " + src + " \ndes = " + des);
	}

	public void clearDrag() {
		Arrays.setAll(dragCubes, (i) -> null);
	}

	public void cancelDrag() {
		Cube srcCube = dragCubes[0];
		Cube desCube = dragCubes[1];
		switch (this.dragDirection) {
			case DRAG_DOWN:
				srcCube.state = UP;
				desCube.state = FALL;
				srcCube.j++;
				desCube.j--;
				break;
			case DRAG_UP:
				srcCube.state = FALL;
				desCube.state = UP;
				srcCube.j--;
				desCube.j++;
				break;
			case DRAG_LEFT:
				srcCube.state = RIGHT;
				desCube.state = LEFT;
				srcCube.i++;
				desCube.i--;
				break;
			case DRAG_RIGHT:
				srcCube.state = LEFT;
				desCube.state = RIGHT;
				srcCube.i--;
				desCube.i++;
				break;
			default:
		}

		matrix[srcCube.i][srcCube.j] = srcCube;
		matrix[desCube.i][desCube.j] = desCube;

		clearDrag();
	}

	public void click(Vector3 position) {
		int[] clkIndex = getMatrixPosition(position.x, position.y);
		if (clkIndex[0] < 0 || clkIndex[0] > 6 || clkIndex[1] > 6 || clkIndex[1] < 0) {
			isMoving = false;
			return;
		}
		Cube clickCube = matrix[clkIndex[0]][clkIndex[1]];

		if (dragCubes[0] != null) {

		} else {


		}

	}

	public void touchDrag(Vector3 src, Vector3 des) {
		isMoving = true;

		System.out.println(src + " to " + des);

		int[] srcIndex = getMatrixPosition(src.x, src.y);
		if (srcIndex[0] < 0 || srcIndex[0] > 6 || srcIndex[1] > 6 || srcIndex[1] < 0) {
			isMoving = false;
			return;
		}
		Cube srcCube = matrix[srcIndex[0]][srcIndex[1]];
		Cube desCube = null;


		Vector3 direct = des.sub(src);
		//left right
		if (Math.abs(direct.x) > Math.abs(direct.y)) {
			//right
			if (direct.x > 0) {

				if (srcIndex[0] >= 6) {
					isMoving = false;
					return;
				}

				desCube = matrix[srcIndex[0] + 1][srcIndex[1]];

				debug(srcCube, desCube);

				srcCube.state = RIGHT;
				desCube.state = LEFT;
				srcCube.i = srcIndex[0] + 1;
				desCube.i = srcIndex[0];

				dragDirection = DRAG_RIGHT;
			}
			//left
			else {
				if (srcIndex[0] <= 0) {
					isMoving = false;
					return;
				}

				desCube = matrix[srcIndex[0] - 1][srcIndex[1]];
				debug(srcCube, desCube);

				srcCube.state = LEFT;
				desCube.state = RIGHT;
				srcCube.i = srcIndex[0] - 1;
				desCube.i = srcIndex[0];

				dragDirection = DRAG_LEFT;

			}
		}
		// up down
		else {
			//up
			if (direct.y > 0) {
				if (srcIndex[1] >= 6) {
					isMoving = false;
					return;
				}

				desCube = matrix[srcIndex[0]][srcIndex[1] + 1];

				debug(srcCube, desCube);

				srcCube.state = UP;
				desCube.state = FALL;
				srcCube.j = srcIndex[1] + 1;
				desCube.j = srcIndex[1];

				dragDirection = DRAG_UP;

			}
			//down
			else {
				if (srcIndex[1] <= 0) {
					isMoving = false;
					return;
				}

				desCube = matrix[srcIndex[0]][srcIndex[1] - 1];

				debug(srcCube, desCube);

				srcCube.state = FALL;
				desCube.state = UP;
				srcCube.j = srcIndex[1] - 1;
				desCube.j = srcIndex[1];

				dragDirection = DRAG_DOWN;

			}
		}
		matrix[srcCube.i][srcCube.j] = srcCube;
		matrix[desCube.i][desCube.j] = desCube;

		dragCubes[0] = srcCube;
		dragCubes[1] = desCube;
	}

	public boolean isTouchable() {
		return this.state == STATIC;
	}

	public static float[] getPosition(int i, int j) {
		float[] ret = new float[2];
		//x
		ret[0] = MATRIX_OFFSET_X + i * (CUBE_WIDTH);
//		ret[0] = MATRIX_OFFSET_X + i * (CUBE_WIDTH + 1);

		// y
		ret[1] = MATRIX_OFFSET_Y + j * (CUBE_WIDTH);
//		ret[1] = MATRIX_OFFSET_Y + j * (CUBE_WIDTH + 1);
		return ret;
	}

	public static int[] getMatrixPosition(float x, float y) {
		int[] ret = new int[2];
		ret[0] = (int) ((x - MATRIX_OFFSET_X) / (CUBE_WIDTH));
		ret[1] = (int) ((y - MATRIX_OFFSET_Y) / (CUBE_WIDTH));
		return ret;
	}


	public boolean checkMatch() {

		newCubes.clear();
		match.clear();

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {

				Cube cube = matrix[i][j];

				//left
				if (i - 1 >= 0 && i - 2 >= 0) {
					Cube left1 = matrix[i - 1][j];
					Cube left2 = matrix[i - 2][j];

					if (left1.getColor() == left2.getColor()
							&& left1.getColor() == cube.getColor()) {
						match.add(cube);
						match.add(left1);
						match.add(left2);
					}
				}
				//mid
				if (i - 1 >= 0 && i + 1 < 7) {
					Cube left1 = matrix[i - 1][j];
					Cube right1 = matrix[i + 1][j];

					if (left1.getColor() == right1.getColor()
							&& left1.getColor() == cube.getColor()) {
						match.add(cube);
						match.add(left1);
						match.add(right1);
					}
				}
				//right
				if (i + 1 < 7 && i + 2 < 7) {
					Cube right1 = matrix[i + 1][j];
					Cube right2 = matrix[i + 2][j];

					if (right1.getColor() == right2.getColor()
							&& right1.getColor() == cube.getColor()) {
						match.add(cube);
						match.add(right1);
						match.add(right2);
					}
				}

				//down
				if (j - 1 >= 0 && j - 2 >= 0) {
					Cube down1 = matrix[i][j - 1];
					Cube down2 = matrix[i][j - 2];
					if (down1.getColor() == down2.getColor()
							&& down1.getColor() == cube.getColor()) {
						match.add(down1);
						match.add(down2);
						match.add(cube);
					}
				}
				//mid
				if (j - 1 >= 0 && j + 1 < 7) {
					Cube down1 = matrix[i][j - 1];
					Cube up1 = matrix[i][j + 1];
					if (down1.getColor() == up1.getColor()
							&& down1.getColor() == cube.getColor()) {
						match.add(down1);
						match.add(up1);
						match.add(cube);
					}
				}
				//up
				if (j + 1 < 7 && j + 2 < 7) {
					Cube up1 = matrix[i][j + 1];
					Cube up2 = matrix[i][j + 2];
					if (up1.getColor() == up2.getColor()
							&& up1.getColor() == cube.getColor()) {
						match.add(up1);
						match.add(up2);
						match.add(cube);
					}
				}
			}
		}

		//debug
		for (Cube cube : match) {
//			System.out.println(" find match " + cube);
		}

		if (!match.isEmpty()) {
			printMatirx(" match ");
		}

		if (match.isEmpty()) {
			isMoving = false;
			state = STATIC;
			return false;
		}
		clearMatch();
		return true;
	}

	private void clearMatch() {

//		if (match.isEmpty()) {
//			isMoving = false;
//			state = STATIC;
//			return;
//		} else {

		this.state = MATCHING;

		///从左往右
		for (int i = 0; i < 7; i++) {
			//本列有多少消除
			int fall = 0;
			//从上往下
			for (int j = 6; j >= 0; j--) {
				Cube cube = matrix[i][j];
				if (match.contains(cube)) {
					fall++;
					continue;
				}
				//数当前方块下面有多少个能消除，有一个，纵坐标减一
				for (int k = j - 1; k >= 0; k--) {
					Cube down = matrix[i][k];
					if (match.contains(down)) {
						cube.j--;
						cube.state = FALL;
					}
				}

			}

			//填补本列的空缺
			for (int j = 1; j <= fall; j++) {
				Cube genCube = genCube();

				newCubes.add(genCube);

				genCube.i = i;
				genCube.j = 6 + j;
				//先设置到上面，更新下设置y，然后设置正确的纵坐标
				genCube.update(0);

				genCube.j -= fall;
				genCube.state = FALL;
			}
		}

		//set matrix
		cpyMatrix();
		matrix = new Cube[7][7];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				Cube cube = cpMatrix[i][j];
				if (!match.contains(cube)) {
					if (matrix[cube.i][cube.j] != null) {
						throw new RuntimeException(" eeeeeeeeeeeeee at " + matrix[cube.i][cube.j] + " " + cube);
					}
					matrix[cube.i][cube.j] = cube;
				}
			}
		}
		for (Cube newCube : newCubes) {
			if (matrix[newCube.i][newCube.j] != null) {
				throw new RuntimeException(" eeeeeeeeeeeeee new at " + matrix[newCube.i][newCube.j] + " " + newCube);
			}
			matrix[newCube.i][newCube.j] = newCube;
		}
//		}

//		this.match.clear();
		this.newCubes.clear();

		this.listener.onMatch(this.match);

		printMatirx(" static ");

	}

	public void onClick() {

	}

	public void cpyMatrix() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				cpMatrix[i][j] = matrix[i][j];
			}
		}
	}


}
