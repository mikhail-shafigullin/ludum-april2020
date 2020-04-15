package ru.alttabber.ludum;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ru.alttabber.ludum.bars.BarManager;
import ru.alttabber.ludum.bars.HitBar;
import ru.alttabber.ludum.bars.ManaBar;
import ru.alttabber.ludum.items.SwordItem;
import ru.alttabber.ludum.memory.Assets;
import ru.alttabber.ludum.memory.GameController;
import ru.alttabber.ludum.units.Player;
import ru.alttabber.ludum.utils.SpriteAnimation;

public class LudumRogue extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img;
	BarManager barManager;
	ShapeRenderer shapeRenderer;

	Player player;
	SwordItem item;
	SpriteAnimation animation;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		barManager = new BarManager();
		barManager.addNewBar(new HitBar());
		barManager.addNewBar(new ManaBar());
		shapeRenderer = new ShapeRenderer();

		GameController.getInstance().getAssetController().loadPlayerAssets();
		GameController.getInstance().getAssetController().loadItems();
		GameController.getInstance().getAssetManager().finishLoading();

		animation = new SpriteAnimation(Assets.frontTextureAnimation, 200,200);
		animation.init(batch);

		player = new Player();
		player.init(batch);

		GameController.getInstance().setPlayer(player);

		item = new SwordItem();
		item.init(batch);
	}

	@Override
	public void render () {
//		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		item.draw();
		player.draw();

		animation.draw();

		batch.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (int i = 0; i < barManager.getNumbOfBars(); i++) {
			shapeRenderer.setColor(barManager.getBarList().get(i).getEmptyColor());
			shapeRenderer.rect(barManager.getStartX(), barManager.getYOfBar(i), barManager.getBarList().get(i).getMaxLength(), barManager.getBarList().get(i).getHight());
			shapeRenderer.setColor(barManager.getBarList().get(i).getColor());
			shapeRenderer.rect(barManager.getStartX(), barManager.getYOfBar(i), barManager.getBarList().get(i).getLength(), barManager.getBarList().get(i).getHight());
		}
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
