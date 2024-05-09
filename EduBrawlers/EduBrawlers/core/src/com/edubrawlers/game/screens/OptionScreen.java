package com.edubrawlers.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.edubrawlers.game.EduBrawlersGame;
import com.edubrawlers.game.resources.ResourceManager;

public class OptionScreen extends AbstractScreen{
    // label style
    protected Slider.SliderStyle sliderStyle;
    protected Label.LabelStyle labelStyle;
    protected TextButton.TextButtonStyle buttonStyle;
    protected Label.LabelStyle optionLabelStyle;
    protected TextButton.TextButtonStyle backButtonStyle;

    //tables
    protected Table BGtable;
    protected Table backTable;
    protected Table optionTable;

    //BG Image
    protected Image optionBG;

    //Buttons
    protected TextButton backButton;

    //Labels
    protected Label musicLabel;
    protected Label sfxLabel;
    protected Label fullScLabel;

    //Sliders
    protected Slider musicSlider;
    protected Slider sfxSlider;

    //Checkbox
    protected CheckBox resolution;

    public OptionScreen(EduBrawlersGame game, ResourceManager rm) {
        super(game, rm);
        this.game = game;
        this.rm = rm;
        this.labelStyle = rm.labelStyle; // Set labelStyle from ResourceManager
        this.buttonStyle = rm.buttonStyle;
        this.sliderStyle = rm.sliderStyle;
        this.optionLabelStyle = rm.optionLabelStyle;
        this.backButtonStyle = rm.backButtonStyle;
    }
    @Override
    public void show() {

        //Create table for background
        BGtable = new Table();
        BGtable.setFillParent(true);
        stage.addActor(BGtable);

        //Load title screen background
        optionBG = new Image(rm.titleScreenBackground);
        BGtable.add(optionBG).expand().fill();

        //Create table for back button
        backTable = new Table();
        backTable.setFillParent(true);
        backTable.top().left();
        stage.addActor(backTable);

        //Add back button to table
        backButton = new TextButton("", rm.backButtonStyle);
        backTable.add(backButton).width(60).height(60).padBottom(15).padLeft(5).padTop(5);

        //Back button action
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rm.buttonClick.play(rm.getSFXVolume());
                game.gotoTitleScreen();
            }
        });

        //Create table for option buttons and sliders
        optionTable = new Table();
        optionTable.setFillParent(true);
        optionTable.center();
        stage.addActor(optionTable);

        // Create a label for music volume
        musicLabel = new Label("Music Volume", rm.optionLabelStyle);
        optionTable.row().width(200);
        optionTable.add(musicLabel).expandX().right().padRight(3); //Add the music label

        // Create a slider for music volume
        musicSlider = new Slider(0, 1, 0.1f, false, sliderStyle);
        musicSlider.setValue(rm.getMusicVolume()); // set the initial value
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = musicSlider.getValue();
                rm.setMusicVolume(volume); // update the game's music volume
            }
        });
        optionTable.add(musicSlider).expandX();

        //Create a label for sound effects volume
        sfxLabel = new Label("SFX Volume", rm.optionLabelStyle);
        optionTable.row().width(200);
        optionTable.add(sfxLabel).expandX().right().padRight(3); //Add the sound label

        // Create a slider for sfx volume
        sfxSlider = new Slider(0, 1, 0.1f, false, sliderStyle);
        sfxSlider.setValue(rm.getSFXVolume()); // set the initial value
        sfxSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float newSFXVolume = sfxSlider.getValue();
                rm.setSFXVolume(newSFXVolume); // update the sfx volume
            }
        });
        optionTable.add(sfxSlider).expandX();

    // Create a label for Full Screen
        fullScLabel = new Label("Full Screen", rm.optionLabelStyle);
        optionTable.row().width(200);
        optionTable.add(fullScLabel).expandX().right().padRight(3);



        resolution = new CheckBox(" ", rm.checkboxStyle);
        resolution.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox checkBox = (CheckBox) actor;
                boolean isChecked = checkBox.isChecked();

                if (isChecked) {
                    // Fullscreen mode is on
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                } else {
                    // Fullscreen mode is off
                    Gdx.graphics.setWindowedMode(1280, 720);
                }
            }
        });
        // Add the checkbox to the new row and center it
        optionTable.add(resolution).expandX().row();
        
    }
    @Override
    public void render(float delta) {
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
    public void dispose() {
        // Dispose of any resources here
        super.dispose();
    }
}
