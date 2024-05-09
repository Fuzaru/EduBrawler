package com.edubrawlers.game.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class ResourceManager {
    public AssetManager assetManager;

    // Texture Atlas that contains every sprite
    public TextureAtlas atlas;

    //Images
    public Texture titleScreenBackground;
    public Texture gameScreenBG;
    public Texture mechanics;

    // Skin
    public Skin skin;

    // Music
    public Music titleTheme;
    public Music gameTheme;

    //Sound Effect
    public Sound buttonClick;
    public Sound hit;

    //Initialize volume
    private float musicVolume = 0.5f;
    private float sfxVolume = 0.5f;

    // Button style
    public TextButton.TextButtonStyle buttonStyle;
    public TextButton.TextButtonStyle backButtonStyle;
    public TextButton.TextButtonStyle pauseButtonStyle;

    // Slide style
    public Slider.SliderStyle sliderStyle;

    // LabelStyle
    public Label.LabelStyle labelStyle;
    public Label.LabelStyle optionLabelStyle;
    public Label.LabelStyle popupLabelStyle;

    //Label
    public Label oneCharSizeCalibrationThrowAway;
    public Label largeSizeCalibrationThrowAway;

    //Text Field Style
    public TextField.TextFieldStyle textFieldStyle;

    //Checkbox
    public CheckBox.CheckBoxStyle checkboxStyle;

    //Popup window
    public Window.WindowStyle popupStyle;
    public Window.WindowStyle pauseWindowStyle;

    // Font
    public BitmapFont buttonFont;
    public BitmapFont titleFont;
    public BitmapFont optionFont;
    public BitmapFont popupFont;

    //PixMap
    public Pixmap cursorColor;

    //ttf Fonts
    public FreetypeFontLoader.FreeTypeFontLoaderParameter size1Params;
    public FreetypeFontLoader.FreeTypeFontLoaderParameter size2Params;
    public FreetypeFontLoader.FreeTypeFontLoaderParameter size3Params;
    public FreetypeFontLoader.FreeTypeFontLoaderParameter size4Params;
    public FileHandleResolver resolver;



    public ResourceManager() {
        assetManager = new AssetManager();

        //Load title background
        assetManager.load("title_bg.png", Texture.class);
        assetManager.load("gameScreen.png", Texture.class);
        assetManager.load("playerScreenHTP.png", Texture.class);

        //Load atlas
        assetManager.load("ui-blue.atlas", TextureAtlas.class);

        //Load music
        assetManager.load("gameScreenMusic.ogg", Music.class);
        assetManager.load("titleScreenMusic.ogg", Music.class);
        assetManager.load("gameScreenBGM.mp3", Music.class);

        //Load sound
        assetManager.load("buttonClick.ogg", Sound.class);
        assetManager.load("hit.ogg", Sound.class);

        atlas = new TextureAtlas(Gdx.files.internal("ui-blue.atlas"));

        //load background images
        titleScreenBackground = new Texture(Gdx.files.internal("title_bg.png"));
        gameScreenBG = new Texture(Gdx.files.internal("gameScreen.png"));
        mechanics = new Texture(Gdx.files.internal("playerScreenHTP.png"));

        //Load FreeTypeFontGenerator to use .ttf
        resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        //Create font style size 24
        size1Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        size1Params.fontFileName = "kenvector_future.ttf";
        size1Params.fontParameters.size = 24;
        size1Params.fontParameters.minFilter = Texture.TextureFilter.Linear;
        size1Params.fontParameters.magFilter = Texture.TextureFilter.Linear;


        //Create font style for title
        size2Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        size2Params.fontFileName = "kenvector_future.ttf";
        size2Params.fontParameters.size = 56;
        size2Params.fontParameters.minFilter = Texture.TextureFilter.Linear;
        size2Params.fontParameters.magFilter = Texture.TextureFilter.Linear;

        //Create font style for option
        size3Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        size3Params.fontFileName = "kenvector_future.ttf";
        size3Params.fontParameters.size = 32;
        size3Params.fontParameters.minFilter = Texture.TextureFilter.Linear;
        size3Params.fontParameters.magFilter = Texture.TextureFilter.Linear;

        //Create font style for popup
        size4Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        size4Params.fontFileName = "kenvector_future.ttf";
        size4Params.fontParameters.size = 18;
        size4Params.fontParameters.minFilter = Texture.TextureFilter.Linear;
        size4Params.fontParameters.magFilter = Texture.TextureFilter.Linear;

        //Load Font sizes
        assetManager.load("buttonFont.ttf", BitmapFont.class, size1Params);
        assetManager.load("titleFont.ttf", BitmapFont.class, size2Params);
        assetManager.load("optionFont.ttf", BitmapFont.class, size3Params);
        assetManager.load("popupFont.ttf", BitmapFont.class, size4Params);

        // wait for the font to finish loading
        while (!assetManager.isFinished()) {
            assetManager.update();
        }

        buttonFont = assetManager.get("buttonFont.ttf", BitmapFont.class);
        titleFont = assetManager.get("titleFont.ttf", BitmapFont.class);
        optionFont = assetManager.get("optionFont.ttf", BitmapFont.class);
        popupFont = assetManager.get("popupFont.ttf", BitmapFont.class);

        //Load skin
        skin = new Skin();
        skin.addRegions(atlas);

        //LabelStyle for title
        labelStyle = new Label.LabelStyle();
        labelStyle.font = titleFont;
        labelStyle.fontColor = Color.BLACK; // Set the color as desired

        //LabelStyle for option
        optionLabelStyle = new Label.LabelStyle();
        optionLabelStyle.font = optionFont;
        optionLabelStyle.fontColor = Color.BLACK;

        //LabelStyle for popup
        popupLabelStyle = new Label.LabelStyle();
        popupLabelStyle.font = popupFont;
        popupLabelStyle.fontColor = Color.BLACK;


        //Create button style
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = buttonFont;
        buttonStyle.up = skin.getDrawable("button_03");
        buttonStyle.down = skin.getDrawable("button_01");
        buttonStyle.over = skin.getDrawable("button_04");

        //Create button style for back button
        backButtonStyle = new TextButton.TextButtonStyle();
        backButtonStyle.font = buttonFont;
        backButtonStyle.up = skin.getDrawable("icon_back");
        backButtonStyle.down = skin.getDrawable("icon_back");
        backButtonStyle.over = skin.getDrawable("icon_back");

        //Create button style for pause button
        pauseButtonStyle = new TextButton.TextButtonStyle();
        pauseButtonStyle.font = buttonFont;
        pauseButtonStyle.up = skin.getDrawable("icon_tools");


        // OneCharThrowAway
        oneCharSizeCalibrationThrowAway = new Label("|", labelStyle);
        largeSizeCalibrationThrowAway = new Label("large", labelStyle); // Initialize largeSizeCalibrationThrowAway here
        cursorColor = new Pixmap((int) oneCharSizeCalibrationThrowAway.getWidth(),
                (int) largeSizeCalibrationThrowAway.getHeight(),
                Pixmap.Format.RGB888);
        cursorColor.setColor(Color.BLACK);
        cursorColor.fill();


        //Create text field style
        textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = buttonFont;
        textFieldStyle.fontColor = Color.BLACK;
        textFieldStyle.cursor = new Image(new Texture(cursorColor)).getDrawable();
        textFieldStyle.background = skin.getDrawable("textbox_02");


        // Create a SliderStyle
        sliderStyle = new Slider.SliderStyle();
        sliderStyle.knob = skin.getDrawable("knob_03"); // replace with the drawable for the knob
        sliderStyle.background = skin.getDrawable("slider_back_hor"); // replace with the drawable for the background

        // Set the width and height of the slider
        sliderStyle.knob.setMinWidth(20);
        sliderStyle.knob.setMinHeight(20);

        //Create a checkbox
        checkboxStyle = new CheckBox.CheckBoxStyle();
        checkboxStyle.checkboxOn = skin.getDrawable("checkbox_on");
        checkboxStyle.checkboxOff = skin.getDrawable("checkbox_off");
        checkboxStyle.font = optionFont;

        //Create Window for popup
        popupStyle = new Window.WindowStyle();
        popupStyle.background = skin.getDrawable("window_02");
        popupStyle.titleFont = buttonFont;

        //Create window for pause options
        pauseWindowStyle = new Window.WindowStyle();
        pauseWindowStyle.background = skin.getDrawable("window_01");
        pauseWindowStyle.titleFont = buttonFont;

        // wait for the font to finish loading
        while (!assetManager.isFinished()) {
            assetManager.update();
        }

        //load music
        titleTheme = assetManager.get("titleScreenMusic.ogg", Music.class);
        gameTheme = assetManager.get("gameScreenBGM.mp3", Music.class);

        //load sfx
        buttonClick = assetManager.get("buttonClick.ogg", Sound.class);
        hit = assetManager.get("hit.ogg", Sound.class);

        //wait for the assetManager to finish loading
        assetManager.finishLoading();
    }

    // Method to get current music volume
    public float getMusicVolume() {
        return musicVolume;
    }

    // Method to get current sound effects volume
    public float getSFXVolume() {
        return sfxVolume;
    }

    public void setMusicVolume(float volume) {
        musicVolume = volume; // Update the music volume variable
        titleTheme.setVolume(volume);
        gameTheme.setVolume(volume);
    }

    public void setSFXVolume(float volume){
        sfxVolume = volume; // Update the sound effects volume variable
        long buttonClickID = buttonClick.play(volume); // Play the sound and get its ID
        buttonClick.setVolume(buttonClickID, volume); // Set the volume for the specific sound ID

        long hitID = hit.play(volume);
        hit.setVolume(hitID, volume);
    }


}
