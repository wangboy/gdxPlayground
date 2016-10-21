package me.game.sanxiao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import game.kt.MyGame;

import static me.game.sanxiao.Assests.*;

/**
 * Created by wangbo on 16/6/6.
 */
public class GameScreen extends ScreenAdapter implements InputProcessor {

    private MyGame game;

    private OrthographicCamera camera;

    private World world;

    private WorldRenderer worldRenderer;

    private Vector3 touchSrc;

    private Vector3 touchDes;

    public GameScreen(MyGame game) {
        this.game = game;

        this.camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        this.camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);


		Gdx.input.setInputProcessor(this);

        world = new World(game);

        worldRenderer = new WorldRenderer(game, world);

		world.setListener(worldRenderer);

        touchDes = new Vector3();
        touchSrc = new Vector3();
    }

    public void update(float delta) {
//        if (Gdx.input.justTouched()) {
//            this.game.setScreen(new MainMenuScreen(this.game));
//        }

        this.world.update(delta);

    }

    public void draw() {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(this.camera.combined);

        game.batch.begin();

        game.batch.draw(bg, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        game.batch.end();

        worldRenderer.render();

    }

    @Override
    public void render(float delta) {
        this.update(delta);
        this.draw();
    }

		/* ===============================*/

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        Gdx.app.log(this.toString(), " typed char = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (this.world.isTouchable()) {
            this.camera.unproject(touchSrc.set(screenX, screenY, 0));
        }
        Gdx.app.log(this.toString(),
                " touchDown at " + touchSrc.x + "_" + touchSrc.y
                        + ", pointer= " + pointer
                        + ", button = " + button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (this.world.isTouchable()) {
            this.camera.unproject(touchDes.set(screenX, screenY, 0));

			if (touchDes.cpy().sub(touchSrc).len() < CUBE_WIDTH /2) {
				this.world.click(touchSrc);
			}

        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if (this.world.isTouchable()) {
            this.camera.unproject(touchDes.set(screenX, screenY, 0));

            if (touchDes.cpy().sub(touchSrc).len() > CUBE_WIDTH /2) {
                this.world.touchDrag(touchSrc, touchDes);
            }
        }

//        Gdx.app.log(this.toString(),
//                " touchDragged to " + screenX + "_" + screenY
//                        + ", pointer= " + pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

//        Gdx.app.log(this.toString(),
//                " mouseMoved to " + screenX + "_" + screenY);

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    /* ===============================*/
    @Override
    public void dispose() {
        super.dispose();
    }
}
