package me.game.sanxiao;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by wangbo on 16/6/6.
 */
public class SanXiao extends Game {

	//TODO 点击会有框提示
	//TODO 第二次点击会交换
	//DO 没有可以消除的退回
	//TODO 4个和5个的特殊效果

	SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		Assests.init();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
}
