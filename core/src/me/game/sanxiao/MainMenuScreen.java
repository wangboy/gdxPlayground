package me.game.sanxiao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import game.kt.MyGame;

import static me.game.sanxiao.Assests.BASE_HEIGHT;
import static me.game.sanxiao.Assests.BASE_WIDTH;

/**
 * Created by wangbo on 16/6/6.
 */
public class MainMenuScreen extends ScreenAdapter {


	MyGame game;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	Rectangle playerBounds;

	ShapeRenderer shapeRenderer;

	BitmapFont font;

	public MainMenuScreen(MyGame game) {
		this.game = game;

		guiCam = new OrthographicCamera(BASE_WIDTH, BASE_HEIGHT);
		guiCam.position.set(BASE_WIDTH / 2, BASE_HEIGHT / 2, 0);

		playerBounds = new Rectangle(0, BASE_HEIGHT / 3, BASE_WIDTH, BASE_HEIGHT / 3);

		font = new BitmapFont();
//		font.setColor(Color.WHITE);

		shapeRenderer = new ShapeRenderer();

		touchPoint = new Vector3();

		Gdx.input.setInputProcessor(null);


	}

	public void update() {
		if (Gdx.input.justTouched()) {
			Gdx.app.log(" main ", " touched " + Gdx.input.getX() + "_" + Gdx.input.getY());
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			Gdx.app.log(" main ", " touched " + touchPoint.x + "_" + touchPoint.y);

			if (playerBounds.contains(touchPoint.x, touchPoint.y)) {
				Assests.playerSound(Assests.clickSound);
				Gdx.app.log(" main ", " change to game ");

				game.setScreen(new GameScreen(game));
			}
		}
	}

	public void draw() {

		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		guiCam.update();
		game.batch.setProjectionMatrix(guiCam.combined);

		game.batch.disableBlending();

		game.batch.begin();
		//
//		game.batch.draw(Assests.bg, 0, 0, BASE_WIDTH, BASE_HEIGHT);

		font.draw(game.batch, "Click here to start!! ", 0, BASE_HEIGHT / 2, BASE_WIDTH, Align.center, true);

		game.batch.end();

		shapeRenderer.setProjectionMatrix(game.batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(playerBounds.getX(), playerBounds.getY(), playerBounds.getWidth(), playerBounds.getHeight());
		shapeRenderer.end();
	}

	@Override
	public void render(float delta) {
		this.update();
		this.draw();
	}
}
