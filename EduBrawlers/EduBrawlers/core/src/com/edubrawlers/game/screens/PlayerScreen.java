package com.edubrawlers.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.edubrawlers.game.EduBrawlersGame;
import com.edubrawlers.game.resources.ResourceManager;

public class PlayerScreen extends TitleScreen {
    //BG Image
    protected Image backgroundImage;
    protected Image mechanics;

    //Sprite
    protected Sprite backgroundSprite;

    // label style
    protected Label.LabelStyle labelStyle;
    protected TextButton.TextButtonStyle buttonStyle;
    protected TextField.TextFieldStyle textFieldStyle;

    //label
    protected Label player1Label;
    protected Label player2Label;

    //tables
    protected Table BGtable;
    protected Table playerTable;

    //text field
    protected TextField textField;
    protected TextField textField2;

    //Buttons
    protected Button backBtn;
    protected Button continueBtn;

    //Player Names
    public String player1Name;
    public String player2Name;

    public PlayerScreen(EduBrawlersGame game, ResourceManager rm) {
        super(game, rm);
        this.game = game;
        this.rm = rm;
        this.labelStyle = rm.labelStyle; // Set labelStyle from ResourceManager
        this.buttonStyle = rm.buttonStyle;
        this.textFieldStyle = rm.textFieldStyle;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        //title screen music
        rm.titleTheme.setLooping(true);
        rm.titleTheme.play();

        //Create table for background
        BGtable = new Table();
        BGtable.setFillParent(true);
        stage.addActor(BGtable);

        // Create a Sprite with the texture region and set it to fill the stage
        backgroundSprite = new Sprite(rm.titleScreenBackground);
        backgroundSprite.setSize(stage.getWidth(), stage.getHeight());
        backgroundSprite.setOriginCenter();
        backgroundSprite.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);

        // Create an Image with the Sprite and add it to a Table
        backgroundImage = new Image(backgroundSprite.getTexture());
        BGtable.add(backgroundImage).expand().fill();

        //Create mechanics image
        mechanics = new Image(rm.mechanics);

        //Set the image's size and position
        mechanics.setPosition(800 , 100); // Set the position of the image
        mechanics.setSize(360, 640); // Set the size of the image

        //Add the image to the screen
        stage.addActor(mechanics);


        //Create table for player name
        playerTable = new Table();
        playerTable.setFillParent(true);
        playerTable.right().top().padTop(30).padLeft(-150);

        //Create label for player 1
        player1Label = new Label("Player 1: ", rm.labelStyle);

        textField = new TextField("", rm.textFieldStyle);
        textField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String player1 = textField.getText();
                System.out.println(player1);
                textField.setText(player1);
            }
        });

        //Add objects to player table
        playerTable.add(player1Label).expandX();
        playerTable.add(textField).expand().left().padLeft(-200).width(300).height(60);

        player2Label = new Label("Player 2: ", rm.labelStyle);

        textField2 = new TextField("", rm.textFieldStyle);
        textField2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String player2 = textField2.getText();
                System.out.println(player2);
                textField2.setText(player2);
            }
        });

        // Add the text field to the playerTable
        playerTable.row();
        playerTable.add(player2Label).padTop(-300);
        playerTable.add(textField2).expand().left().padTop(-300).padLeft(-200).width(300).height(60);

        // Add the playerTable to the stage
        stage.addActor(playerTable);

        // Create Back button
        backBtn = new TextButton("BACK", rm.buttonStyle);

        //Back button action
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rm.buttonClick.play(rm.getSFXVolume());
                game.gotoTitleScreen();
            }
        });

        //Create Continue button
        continueBtn = new TextButton("START GAME", rm.buttonStyle);

        //Continue button action
        continueBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                rm.buttonClick.play(rm.getSFXVolume());

                // Assign values from text fields to player names
                player1Name = textField.getText();
                player2Name = textField2.getText();

                // Check if player names are not empty
                if (!player1Name.isEmpty() && !player2Name.isEmpty()) {
                    game.gotoGameScreen();
                    rm.titleTheme.stop();
                } else {
                    // If any player name is empty, set them to "Player 1" and "Player 2"
                    if (player1Name.isEmpty()) {
                        textField.setText("Player 1");
                    }
                    if (player2Name.isEmpty()) {
                        textField2.setText("Player 2");
                    }
                }
            }
        });

        //Add buttons to the table
        playerTable.row().padBottom(30);
        playerTable.add(backBtn).width(120).height(50);
        playerTable.add(continueBtn).width(300).height(50);
    }

    // Getter methods for player names
    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

}
