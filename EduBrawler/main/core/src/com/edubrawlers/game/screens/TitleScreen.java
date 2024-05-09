package com.edubrawlers.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.edubrawlers.game.EduBrawlersGame;
import com.edubrawlers.game.resources.ResourceManager;

public class TitleScreen extends AbstractScreen{
    //BG Image
    protected Image titleBG;

    //buttons
    protected TextButton startButton;
    protected TextButton optionButton;
    protected TextButton exitButton;

    // label style
    protected Label.LabelStyle labelStyle;
    protected Label titleLabel;
    protected TextButton.TextButtonStyle buttonStyle;

    //tables
    protected Table BGtable;
    protected Table titleTable;
    protected Table buttonsTable;

    public TitleScreen(final EduBrawlersGame game, final ResourceManager rm) {
        super(game, rm);
        this.game = game;
        this.rm = rm;
        this.labelStyle = rm.labelStyle; // Set labelStyle from ResourceManager
        this.buttonStyle = rm.buttonStyle;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        renderBatch = false;
        batchFade = true;

        //Create table for background
        BGtable = new Table();
        BGtable.setFillParent(true);
        stage.addActor(BGtable);

        //Load title screen background
        titleBG = new Image(rm.titleScreenBackground);
        BGtable.add(titleBG).expand().fill();

        //Create table for title
        titleTable = new Table();
        titleTable.setFillParent(true);
        titleTable.center();
        stage.addActor(titleTable);

        //Create Title
        titleLabel = new Label("EDUBRAWLERS", labelStyle);
        titleLabel.setPosition(centerX - titleLabel.getWidth()/2,centerY + row_height);
        titleLabel.setAlignment(Align.center);

        // Add the Label to the table
        titleTable.add(titleLabel).expandX().padTop(-400);

        //Create table for buttons
        buttonsTable = new Table();
        buttonsTable.setFillParent(true);
        buttonsTable.center().padTop(30);
        stage.addActor(buttonsTable);

        startButton = new TextButton("Start Game", buttonStyle);
        optionButton = new TextButton("Option", buttonStyle);
        exitButton = new TextButton("Exit Game", buttonStyle);

        //add buttons to table
        buttonsTable.add(startButton).width(400).height(80).padBottom(10);
        buttonsTable.row();
        buttonsTable.add(optionButton).width(400).height(80).padBottom(10);
        buttonsTable.row();
        buttonsTable.add(exitButton).width(400).height(80);

        //title screen music
        rm.titleTheme.setLooping(true);
        rm.titleTheme.play();

        // create button listeners
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rm.buttonClick.play(rm.getSFXVolume());
                game.gotoPlayerScreen();


            }
        });

        optionButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rm.buttonClick.play(rm.getSFXVolume());
                game.gotoOptionScreen();

            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rm.buttonClick.play(rm.getSFXVolume());
                Gdx.app.exit();
                rm.titleTheme.stop();
            }
        });
    }

    @Override
    public void dispose() {
        // Dispose of any resources here
        super.dispose(); // Dispose of resources from AbstractScreen

        // Dispose additional resources specific to TitleScreen
        rm.titleTheme.stop(); // Stop the music if it's playing
        rm.titleTheme.dispose(); // Dispose the music
    }
}
