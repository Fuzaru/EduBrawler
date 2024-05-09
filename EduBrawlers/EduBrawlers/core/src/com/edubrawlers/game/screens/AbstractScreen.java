package com.edubrawlers.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.edubrawlers.game.EduBrawlersGame;
import com.edubrawlers.game.resources.ResourceManager;


public abstract class AbstractScreen implements Screen {

    protected EduBrawlersGame game;
    protected ResourceManager rm;

    // camera that focuses on the player
    protected OrthographicCamera camera;
    // viewport that keeps aspect ratios of the game when resizing
    protected Viewport viewport;
    // main stage of each screen
    protected Stage stage;

    public static final int screenWidth = Gdx.graphics.getWidth();
    public static final int screenHeight = Gdx.graphics.getHeight();
    public static final int row_height = screenHeight/8;
    public static float centerX = (float) screenWidth /2;
    public static float centerY = (float) screenHeight/2;

    public AbstractScreen(final EduBrawlersGame game, final ResourceManager rm) {
        this.game = game;
        this.rm = rm;


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        // the game will retain it's scaled dimensions regardless of resizing
        viewport = new StretchViewport(1280, 720, camera);

        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        stage.act(dt);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }

}