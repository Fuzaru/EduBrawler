package com.edubrawlers.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.edubrawlers.game.EduBrawlersGame;
import com.edubrawlers.game.equations.Equations;
import com.edubrawlers.game.equations.EquationsSet;
import com.edubrawlers.game.resources.ResourceManager;


import java.util.Random;

public class GameScreen extends AbstractScreen{
    //Player Names
    protected String playerName1;
    protected String playerName2;

    //Player Status
    final float[] player1HP = {1.0f};
    final float[] player2HP = {1.0f};
    protected boolean isPlayer1Turn = true;

    //Damage initializer
    float minDamage = 0.2f;
    float maxDamage = 0.8f;
    float damage;

    //Random
    protected Random random;
    protected float randomNumber;

    //Game Status
    protected boolean gameOver = false;

    //Equations
    protected int currentEquationIndex;
    protected Equations currentEquation;

    // label style
    protected Slider.SliderStyle sliderStyle;
    protected Label.LabelStyle labelStyle;
    protected Label.LabelStyle popupLabelStyle;
    protected Label.LabelStyle equationLabelStyle;
    protected TextButton.TextButtonStyle buttonStyle;
    protected Window.WindowStyle popupStyle;
    protected TextButton.TextButtonStyle pauseBtnStyle;

    //Labels
    protected Label musicLabel;
    protected Label sfxLabel;
    protected Label fullScLabel;
    protected Label eq;
    protected Label player1HPLabel;
    protected Label player2HPLabel;
    protected Label gameOverLabel;

    //Sliders
    protected Slider musicSlider;
    protected Slider sfxSlider;

    //Checkbox
    protected CheckBox resolution;

    //tables
    protected Table BGtable;
    protected Table innerTable;
    protected Table pauseTable;
    protected Table windowTable;
    protected Table innerPauseTable;
    protected Table pauseBtnsTable;
    protected Table player1Table;
    protected Table player2Table;
    protected Table gameOverTable;
    protected Table innergameOverTable;

    //BG Image
    protected Image gameBG;

    //Buttons
    protected TextButton optionBtn1;
    protected TextButton optionBtn2;
    protected TextButton optionBtn3;
    protected TextButton optionBtn4;
    protected TextButton menuBtn;
    protected TextButton continueBtn;
    protected Button pauseBtn;
    protected TextButton playAgainBtn;

    //Arrays
    protected String[] options;

    //Windows
    protected Window details;
    protected Window pause;
    protected Window gameOverWindow;

    private boolean isPopupVisible = false;

    public GameScreen(EduBrawlersGame game, ResourceManager rm, PlayerScreen playerScreen) {
        super(game, rm);
        this.game = game;
        this.rm = rm;
        this.labelStyle = rm.labelStyle; // Set labelStyle from ResourceManager
        this.buttonStyle = rm.buttonStyle;
        this.popupStyle = rm.popupStyle;
        this.pauseBtnStyle = rm.pauseButtonStyle;
        this.sliderStyle = rm.sliderStyle;
        this.popupLabelStyle = rm.popupLabelStyle;
        Gdx.input.setInputProcessor(stage);

        //Get player name from player screen
        playerName1 = playerScreen.getPlayer1Name();
        playerName2 = playerScreen.getPlayer2Name();

        //Random
        random = new Random();

        //Get Equation
        currentEquationIndex = random.nextInt(EquationsSet.EQUATIONS_LIST.size());
        currentEquation = EquationsSet.EQUATIONS_LIST.get(currentEquationIndex);

    }
    @Override
    public void show(){
        //title screen music
        rm.gameTheme.setLooping(true);
        rm.gameTheme.play();

        //Create table for background
        BGtable = new Table();
        BGtable.setFillParent(true);
        stage.addActor(BGtable);

        //Load title screen background
        gameBG = new Image(rm.gameScreenBG);
        BGtable.add(gameBG).expand().fill();

        // Add the Table to the stage
        stage.addActor(BGtable);

        // Create a label for music volume
        musicLabel = new Label("Music Volume", rm.optionLabelStyle);

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

        //Create a label for sound effects volume
        sfxLabel = new Label("SFX Volume", rm.optionLabelStyle);

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

        // Create a label for Full Screen
        fullScLabel = new Label("Full Screen", rm.optionLabelStyle);

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
        }); //CHECKPOINT

        //Create table for popup-window
        windowTable = new Table();
        windowTable.setFillParent(true);
        windowTable.center();

        //LabelStyle for equation
        equationLabelStyle = new Label.LabelStyle();
        equationLabelStyle.font = game.font;
        equationLabelStyle.fontColor = Color.BLACK;

        eq = new Label("Equation: " + currentEquation.getEquation(), equationLabelStyle);



        //Create table for inner popup-window
        innerTable = new Table();
        innerTable.row();
        innerTable.add(new Label( playerName1 + "'s TURN!", popupLabelStyle)).left().top().padBottom(50).padLeft(-50).expand();
        innerTable.row();
        innerTable.add(eq).center().top().expand();

        details = new Window("", rm.popupStyle);

        details.addAction(Actions.sequence(
                Actions.alpha(0), // Start with an opacity of 0
                Actions.fadeIn(2) // Fade in over 2 seconds
        ));

        details.add(innerTable);
        windowTable.row().fill();

        windowTable.add(details).width(1000).height(600).fill().expand();
        stage.addActor(windowTable);

        //Create buttons for user choices
        options = currentEquation.getOptions();
        optionBtn1 = new TextButton(options[0], buttonStyle);
        optionBtn2 = new TextButton(options[1], buttonStyle);
        optionBtn3 = new TextButton(options[2], buttonStyle);
        optionBtn4 = new TextButton(options[3], buttonStyle);

        innerTable.row();
        innerTable.add(optionBtn1).right().padBottom(100).padTop(50).padRight(130).size(230,50).fill();
        innerTable.add(optionBtn3).right().padBottom(100).padTop(50).size(230,50).fill();

        innerTable.row();
        innerTable.add(optionBtn2).right().padBottom(100).padRight(130).size(230,50).fill();
        innerTable.add(optionBtn4).right().padBottom(100).size(230,50).fill();


        // Create a Label for player 1's HP
        player1HPLabel = new Label(playerName1 + "'s GWA: " + player1HP[0], popupLabelStyle);

        // Create a Label for player 2's HP
        player2HPLabel = new Label(playerName2 + "'s GWA: " + player2HP[0], popupLabelStyle);

        // Create a Table for player 1's HP label
        player1Table = new Table();
        player1Table.row();
        player1Table.add(player1HPLabel).padBottom(100).padLeft(280);
        player1Table.row();
        stage.addActor(player1Table);

        // Create a Table for player 2's HP label
        player2Table = new Table();
        player2Table.row();
        player2Table.add(player2HPLabel).padBottom(60).padLeft(280);
        player2Table.row();
        stage.addActor(player2Table);

        optionBtn1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (isPlayer1Turn) {
                    // Player 1's turn
                    // Check if the answer is correct
                    if (currentEquation.getCorrectAnswer() == 0) {
                        // Correct answer
                        System.out.println(currentEquation + ":" + "Correct Answer!");
                        // Switch to player 2's turn
                        isPlayer1Turn = false;

                        damage = randomDamage();

                        updatePlayer1GameState(playerName2, player2HPLabel, player2HP, damage);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);


                    } else {
                        // Incorrect answer
                        System.out.println("Wrong Answer!");
                        // Switch to player 2's turn
                        isPlayer1Turn = false;

                        updatePlayer1GameState(playerName2, player2HPLabel, player2HP, 0);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);

                    }

                } else {
                    // Player 2's turn
                    // Check if the answer is correct
                    if (currentEquation.getCorrectAnswer() == 0) {
                        // Correct answer
                        System.out.println("Correct Answer!");
                        // Switch to player 1's turn
                        isPlayer1Turn = true;

                        damage = randomDamage();

                        updatePlayer2GameState(playerName1, player1HPLabel, player1HP, damage);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);

                    } else {
                        // Incorrect answer
                        System.out.println("Wrong Answer!");
                        // Switch to player 1's turn
                        isPlayer1Turn = true;
                        updatePlayer2GameState(playerName1, player1HPLabel, player1HP, 0);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);

                    }
                }
            }
        });
        optionBtn2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (isPlayer1Turn) {
                    // Player 1's turn
                    // Check if the answer is correct
                    if (currentEquation.getCorrectAnswer() == 1) {
                        // Correct answer
                        System.out.println("Correct Answer!");
                        // Switch to player 2's turn
                        isPlayer1Turn = false;

                        damage = randomDamage();

                        updatePlayer1GameState(playerName2, player2HPLabel, player2HP, damage);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);


                    } else {
                        // Incorrect answer
                        System.out.println("Wrong Answer!");
                        // Switch to player 2's turn
                        isPlayer1Turn = false;

                        updatePlayer1GameState(playerName2, player2HPLabel, player2HP, 0);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);

                    }

                } else {
                    // Player 2's turn
                    // Check if the answer is correct
                    if (currentEquation.getCorrectAnswer() == 1) {
                        // Correct answer
                        System.out.println("Correct Answer!");
                        // Switch to player 1's turn
                        isPlayer1Turn = true;


                        damage = randomDamage();

                        updatePlayer2GameState(playerName1, player1HPLabel, player1HP, damage);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);
                    } else {
                        // Incorrect answer
                        System.out.println("Wrong Answer!");
                        // Switch to player 1's turn
                        isPlayer1Turn = true;

                        updatePlayer2GameState(playerName1, player1HPLabel, player1HP, 0);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);

                    }

                }
            }
        });

        optionBtn3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (isPlayer1Turn) {
                    // Player 1's turn
                    // Check if the answer is correct
                    if (currentEquation.getCorrectAnswer() == 2) {
                        // Correct answer
                        System.out.println("Correct Answer!");
                        // Switch to player 2's turn
                        isPlayer1Turn = false;

                        damage = randomDamage();

                        updatePlayer1GameState(playerName2, player2HPLabel, player2HP, damage);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);


                    } else {
                        // Incorrect answer
                        System.out.println("Wrong Answer!");
                        // Switch to player 2's turn
                        isPlayer1Turn = false;

                        updatePlayer1GameState(playerName2, player2HPLabel, player2HP, 0);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);


                    }

                } else {
                    // Player 2's turn
                    // Check if the answer is correct
                    if (currentEquation.getCorrectAnswer() == 2) {
                        // Correct answer
                        System.out.println("Correct Answer!");
                        // Switch to player 1's turn
                        isPlayer1Turn = true;


                        damage = randomDamage();

                        updatePlayer2GameState(playerName1, player1HPLabel, player1HP, damage);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);

                    } else {
                        // Incorrect answer
                        System.out.println("Wrong Answer!");
                        // Switch to player 1's turn
                        isPlayer1Turn = true;

                        updatePlayer2GameState(playerName1, player1HPLabel, player1HP, 0);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);
                    }

                }
            }
        });

        optionBtn4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (isPlayer1Turn) {
                    // Player 1's turn
                    // Check if the answer is correct
                    if (currentEquation.getCorrectAnswer() == 3) {
                        // Correct answer
                        System.out.println("Correct Answer!");
                        // Switch to player 2's turn
                        isPlayer1Turn = false;

                        damage = randomDamage();

                        updatePlayer1GameState(playerName2, player2HPLabel, player2HP, damage);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);


                    } else {
                        // Incorrect answer
                        System.out.println("Wrong Answer!");
                        // Switch to player 2's turn
                        isPlayer1Turn = false;

                        updatePlayer1GameState(playerName2, player2HPLabel, player2HP, 0);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);


                    }

                } else {
                    // Player 2's turn
                    // Check if the answer is correct
                    if (currentEquation.getCorrectAnswer() == 3) {
                        // Correct answer
                        System.out.println("Correct Answer!");
                        // Switch to player 1's turn
                        isPlayer1Turn = true;

                        damage = randomDamage();

                        updatePlayer2GameState(playerName1, player1HPLabel, player1HP, damage);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);

                    } else {
                        // Incorrect answer
                        System.out.println("Wrong Answer!");
                        // Switch to player 1's turn
                        isPlayer1Turn = true;

                        updatePlayer2GameState(playerName1, player1HPLabel, player1HP, 0);
                        setButtonTextsForEquation(currentEquation, optionBtn1, optionBtn2, optionBtn3, optionBtn4);

                    }

                }
            }
        });

        //STOPPED HERE (EQUATIONS)

        // Create a label for Full Screen
        fullScLabel = new Label("Full Screen", rm.optionLabelStyle);

        //Create table for pause window
        pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.center();

        pause = new Window("", rm.pauseWindowStyle);

        pause.addAction(Actions.sequence(
                Actions.alpha(0), // Start with an opacity of 0
                Actions.fadeIn(1)// Fade in over 2 seconds
        ));

        innerPauseTable = new Table();
        innerPauseTable.setFillParent(true);
        innerPauseTable.center();

        pauseBtnsTable = new Table();
        pauseBtnsTable.setFillParent(true);
        pauseBtnsTable.top().right().padRight(10);

        //Create main menu button
        menuBtn = new TextButton("Main Menu", rm.buttonStyle);
        menuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rm.buttonClick.play(rm.getSFXVolume());
                game.gotoTitleScreen();
                rm.gameTheme.stop();
            }
        });

        continueBtn = new TextButton("Continue", rm.buttonStyle);
        continueBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                rm.buttonClick.play();
                pause.remove();// Remove pause table from stage
                pauseTable.clear(); // Clear the contents of the table
                innerPauseTable.clear();
                isPopupVisible = false;
                rm.gameTheme.play();
            }
        });

        pauseBtn = new TextButton("", rm.pauseButtonStyle);
        pauseBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (isPopupVisible) { // If the pause table is not visible
                    pause.remove();// Remove pause table from stage
                    pauseTable.clear(); // Clear the contents of the table
                    innerPauseTable.clear();
                    isPopupVisible = false;
                    rm.gameTheme.play();
                } else { // If the pause table is already visible
                    stage.addActor(pauseTable);
                    pauseTable.setFillParent(true);
                    pauseTable.add(pause).width((float) screenWidth / 1.25f).height((float) screenHeight / 1.25f).fill();// Add pause table to stage
                    stage.addActor(innerPauseTable);

                    innerPauseTable.row();
                    innerPauseTable.add(new Label("GAME PAUSED", labelStyle)).left().padTop(50).padLeft(200).fill();

                    innerPauseTable.row();
                    innerPauseTable.add(musicLabel).center().padRight(40).padTop(20);
                    innerPauseTable.add(musicSlider).center().padTop(20).size(200, 50);

                    innerPauseTable.row();
                    innerPauseTable.add(sfxLabel).center().padRight(40).padTop(10);
                    innerPauseTable.add(sfxSlider).center().padTop(10).size(200,50);

                    innerPauseTable.row();
                    innerPauseTable.add(fullScLabel).center().padRight(40).padTop(10);
                    innerPauseTable.add(resolution).center().padTop(10).row();

                    innerPauseTable.row();
                    innerPauseTable.add(menuBtn).size(200, 50).left().padLeft(10).padBottom(15).padTop(20);
                    innerPauseTable.add(continueBtn).size(200, 50).right().padRight(10).padBottom(15).padTop(20);

                    pauseTable.row().fill();
                    isPopupVisible = true;
                    rm.gameTheme.pause();

                }

                rm.buttonClick.play();
                return true;
            }
        });

        pauseBtnsTable.add(pauseBtn).right();

        stage.addActor(pauseBtnsTable);
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        // Check if either player's HP has reached zero
        if (!gameOver && (player1HP[0] >= 5.0f || player2HP[0] >= 5.0f)) {
            // Create and display the game over window
            showGameOverWindow();
            gameOver = true; // Set game over state to true
        } else if (gameOver && player1HP[0] < 5.0f && player2HP[0] < 5.0f) {
            // If game over state is true and both players' HP is above 5.0, remove the game over window
            stage.clear(); // Clear the stage to remove the game over window
            gameOver = false; // Reset game over state
        }
    }

    private void showGameOverWindow() {
        // Check if either player's HP has reached zero
        if (player1HP[0] <= 5.0f || player2HP[0] <= 5.0f) {
            // Create the game over window
            gameOverTable = new Table();
            gameOverTable.setFillParent(true);
            gameOverTable.center();

            gameOverWindow = new Window("", popupStyle);
            gameOverWindow.addAction(Actions.sequence(
                    Actions.alpha(0), // Start with an opacity of 0
                    Actions.fadeIn(1) // Fade in over 1 second
            ));

            // Create the game over message Label
            gameOverLabel = new Label("", popupLabelStyle);

            // Check which player has lost and display the appropriate message
            if (player1HP[0] >= 5.0f) {
                // Player 1 has lost, display congratulatory message to Player 2
                gameOverLabel.setText("Congratulations, " + playerName2 + " wins!");
            } else if (player2HP[0] >= 5.0f) {
                // Player 2 has lost, display congratulatory message to Player 1
                gameOverLabel.setText("Congratulations, " + playerName1 + " wins!");
            }

            // Create the inner pause table
            innergameOverTable = new Table();
            innergameOverTable.setFillParent(true);
            innergameOverTable.center();

            playAgainBtn = new TextButton("PLAY AGAIN?", buttonStyle);
            playAgainBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    rm.buttonClick.play(rm.getSFXVolume());
                    game.gotoPlayerScreen();
                    rm.gameTheme.stop();
                }
            });

            stage.addActor(gameOverTable);
            innergameOverTable.row();
            innergameOverTable.add(menuBtn).size(200, 60).left().padRight(180).padBottom(30).padTop(150);
            innergameOverTable.add(playAgainBtn).size(250, 60).right().padBottom(30).padTop(150);

            // Add the game over label to the game over window
            gameOverWindow.add(gameOverLabel).center().pad(20).row();

            // Add the inner pause table to the game over window
            gameOverWindow.add(innergameOverTable).center().pad(20).row();

            // Add the game over window to the stage
            gameOverTable.add(gameOverWindow).width((float) screenWidth / 1.5f).height((float) screenHeight / 1.5f).fill().expand();

            stage.addActor(innergameOverTable);
            innergameOverTable.center().pad(30);
        }
    }

    private float randomDamage(){
        // Generate a random number between 0 and 1
        randomNumber = random.nextFloat();

        // Calculate the randomized damage value
        damage = minDamage + (maxDamage - minDamage) * randomNumber;

        // Round damage to two decimal places
        damage = Float.parseFloat(String.format("%.2f", damage));
        return damage;
    }

    private void setButtonTextsForEquation(Equations equation,TextButton optionBtn1,TextButton optionBtn2,TextButton optionBtn3,TextButton optionBtn4) {
        optionBtn1.setText(equation.getOptions()[0]);
        optionBtn2.setText(equation.getOptions()[1]);
        optionBtn3.setText(equation.getOptions()[2]);
        optionBtn4.setText(equation.getOptions()[3]);
    }
    private void updatePlayer1GameState(String playerName2, Label player2HPLabel, float[] player2HP, float damage) {
        Random random1 = new Random();

        currentEquationIndex =  random1.nextInt(EquationsSet.EQUATIONS_LIST.size());
        currentEquation = EquationsSet.EQUATIONS_LIST.get(currentEquationIndex);

        Label equationLabel = (Label) innerTable.getChildren().get(1);
        equationLabel.setText("Equation: " + currentEquation.getEquation());

        // Update the player name label
        Label playerNameLabel = (Label) innerTable.getChildren().get(0);
        playerNameLabel.setText(playerName2.toUpperCase() + "'s TURN!");


        // Ensure GWA is rounded to two decimal places
        player2HP[0] = Math.min(player2HP[0] + damage, 5.0f); // Make sure HP doesn't exceed
        String formattedGWA = String.format("%.2f", player2HP[0]);
        player2HPLabel.setText(playerName2.toUpperCase() + "'s GWA: " + formattedGWA);
    }

    private void updatePlayer2GameState(String playerName1, Label player1HPLabel, float[] player1HP, float damage) {
        Random random2 = new Random();

        currentEquationIndex = random2.nextInt((EquationsSet.EQUATIONS_LIST.size()));
        currentEquation = EquationsSet.EQUATIONS_LIST.get(currentEquationIndex);

        Label equationLabel = (Label) innerTable.getChildren().get(1);
        equationLabel.setText("Equation: " + currentEquation.getEquation());

        // Update the player name label
        Label playerNameLabel = (Label) innerTable.getChildren().get(0);
        playerNameLabel.setText(playerName1.toUpperCase() + "'s TURN!");

        // Ensure GWA is rounded to two decimal places
        player1HP[0] = Math.min(player1HP[0] + damage, 5.0f); // Make sure HP doesn't exceed
        String formattedGWA = String.format("%.2f", player1HP[0]);
        player1HPLabel.setText(playerName1.toUpperCase() + "'s GWA: " + formattedGWA);
    }
}

