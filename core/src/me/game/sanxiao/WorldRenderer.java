package me.game.sanxiao;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import game.kt.MyGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static me.game.sanxiao.Assests.*;

/**
 * Created by wangbo on 16/6/6.
 */
public class WorldRenderer implements World.WorldListener {

	@Override
	public void onMatch(Set<Cube> matches) {

		//run match animation
		for (Cube match : matches) {
			CubeImage cubeImage = cubeMap.get(match);

			Action endAction = Actions.run(new Runnable() {
				@Override
				public void run() {
					System.out.println("All action is completed");
					cubeImage.remove();
					matches.remove(match);
				}
			});

			cubeImage.setZIndex(100);
			cubeImage.setOrigin(cubeImage.getWidth() / 2, cubeImage.getHeight() / 2);


			SequenceAction alpha = Actions.sequence(
//					Actions.fadeOut(0.5f),
					Actions.scaleTo(0.5f, 0.5f, 0.5f),
//					Actions.scaleBy(0.1f, 0.1f, 1),
					Actions.moveTo(10, WORLD_HEIGHT - 10, 0.5f),
					endAction);

			this.cubeMap.get(match).addAction(alpha);

		}

	}

	public static class CubeImage extends Image {
		Cube cube;

		public CubeImage(Cube cube) {
			super(cubes.get(cube.getColor().getCode()));
			this.cube = cube;
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			if (cube.y < MATRIX_OFFSET_Y + WORLD_WIDTH) {
				super.draw(batch, parentAlpha);
			}
		}
	}

	private World world;

	private MyGame game;

	private OrthographicCamera camera;

	private Stage stage;

	private Map<Cube, CubeImage> cubeMap = new HashMap<>();
	private Map<Cube, CubeImage> oldMap = new HashMap<>();

	public WorldRenderer(MyGame game, World world) {
		this.game = game;
		this.world = world;

		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);

		Viewport viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		viewport.apply(true);

		stage = new Stage(viewport, game.batch);

	}

	public void render() {

		if (world.state == World.MATCHING) {
			stage.act();
			stage.draw();

			if (world.match.isEmpty()) {
				world.state = World.MOVING;
			}
			return;
		}

		camera.update();
		this.game.batch.setProjectionMatrix(camera.combined);

//		this.game.batch.begin();

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				Cube cube = world.matrix[i][j];
//				Color color = cube.getColor();
				CubeImage image = cubeMap.get(cube);
				if (image == null) {
					image = new CubeImage(cube);
					image.setWidth(CUBE_WIDTH);
					image.setHeight(CUBE_WIDTH);

//					image.draw(game.batch, 1);

					this.cubeMap.put(cube, image);

					this.stage.addActor(image);
				}

				image.setX(cube.x);
				image.setY(cube.y);


//				if (cube.y < MATRIX_OFFSET_Y + WORLD_WIDTH) {
//					Image image = new Image(cubes.get(color.getCode()));
//					image.setWidth(CUBE_WIDTH);
//					image.setHeight(CUBE_WIDTH);
//					image.setX(cube.x);
//					image.setY(cube.y);
//					image.draw(game.batch, 1);
//
//					Actions.fadeOut(0.5f);
////					game.batch.draw(cubes.get(color.getCode()), cube.x, cube.y, CUBE_WIDTH, CUBE_WIDTH);
//				}

			}
		}

		oldMap.clear();
		oldMap.putAll(cubeMap);
		for (Cube cube : oldMap.keySet()) {
			Cube cur = world.matrix[cube.i][cube.j];

			if (cur != cube) {
				oldMap.get(cube).remove();
				cubeMap.remove(cube);
			}
		}


//		this.game.batch.end();
		stage.act();
		stage.draw();
	}


}
