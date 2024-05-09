package com.edubrawlers.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.edubrawlers.game.resources.ResourceManager;
import com.edubrawlers.game.screens.GameScreen;
import com.edubrawlers.game.screens.OptionScreen;
import com.edubrawlers.game.screens.PlayerScreen;
import com.edubrawlers.game.screens.TitleScreen;

public class EduBrawlersGame extends Game {

	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public BitmapFont font;
	public ResourceManager rm;

	//Screens
	public TitleScreen titleScreen;
	public OptionScreen optionScreen;
	public PlayerScreen playerScreen;
	public GameScreen gameScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		font.getData().setScale(1.75f, 1.75f);

		// Instantiate ResourceManager
		rm = new ResourceManager();

		// Create Screens and set TitleScreen with ResourceManager
		titleScreen = new TitleScreen(this, rm);

		this.setScreen(titleScreen);
	}

	public void gotoTitleScreen(){
		titleScreen = new TitleScreen(this, rm);
		setScreen(titleScreen);
	}

	public void gotoPlayerScreen(){
		playerScreen = new PlayerScreen(this, rm);
		setScreen(playerScreen);
	}

	public void gotoOptionScreen(){
		optionScreen = new OptionScreen(this, rm);
		setScreen(optionScreen);
	}

	public void gotoGameScreen(){
		gameScreen = new GameScreen(this, rm, playerScreen);
		setScreen(gameScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();


	}
}