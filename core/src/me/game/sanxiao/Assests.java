package me.game.sanxiao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

/**
 * Created by wangbo on 16/6/6.
 */
public class Assests {


	public static final float BASE_WIDTH = 1080 / 4;
	public static final float BASE_HEIGHT = 1920 / 4;
//
//    public static final int CUBE_RED = 0;
//    public static final int CUBE_YELLOW = 1;
//    public static final int CUBE_GREEN = 2;
//    public static final int CUBE_BLUE = 3;
//    public static final int CUBE_PURPLE = 4;

	public static enum Color {

		RED(0),

		YELLOW(1),

		GREEN(2),

		BLUE(3),

		PURPLE(4),;

		private final int code;

		Color(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public static Color valueOf(int code) {
			for (Color color : Color.values()) {
				if (color.code == code) {
					return color;
				}
			}
			return null;
		}
	}

	//////world coordinate
//	public static final float WORLD_WIDTH = 80;
	public static final float WORLD_WIDTH = 73;
	public static final float WORLD_HEIGHT = WORLD_WIDTH * (BASE_HEIGHT / BASE_WIDTH);

	public static final int CUBE_WIDTH = 10;

	public static final float MATRIX_OFFSET_X = 1;

	public static final float MATRIX_OFFSET_Y = 28;

	public static final float CUBE_FALL_SPEED = 40f;
	//////
	public static final int DRAG_LEFT = 1;
	public static final int DRAG_RIGHT = 2;
	public static final int DRAG_UP = 3;
	public static final int DRAG_DOWN = 4;


	//////////////////////////////

	public static TextureAtlas.AtlasRegion red, yellow, green, purple, blue;

	public static Array<TextureAtlas.AtlasRegion> cubes;

	public static Array<TextureAtlas.AtlasRegion> moneys;
	public static Array<TextureAtlas.AtlasRegion> times;
	public static Array<TextureAtlas.AtlasRegion> scores;

	public static Sound clickSound;

	public static Music gameMusic;

	public static Texture bg;

	public static void init() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("./sanxiao/sanxiao.atlas"));
		red = atlas.findRegion("01Red");
		yellow = atlas.findRegion("02Yallow");
		green = atlas.findRegion("03Green");
		blue = atlas.findRegion("04Blue");
		purple = atlas.findRegion("05Purple");

		cubes = new Array<>();
		cubes.add(red);
		cubes.add(yellow);
		cubes.add(green);
		cubes.add(blue);
		cubes.add(purple);

		moneys = atlas.findRegions("Money");
		times = atlas.findRegions("Time");
		scores = atlas.findRegions("Score");

		bg = new Texture(Gdx.files.internal("./sanxiao/BG.jpg"));

		clickSound = Gdx.audio.newSound(Gdx.files.internal("./sanxiao/click.wav"));
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("./sanxiao/music.mp3"));

	}

	public static void playerSound(Sound sound) {
		sound.play(1f);
	}

}
