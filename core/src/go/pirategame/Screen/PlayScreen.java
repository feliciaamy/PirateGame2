package go.pirategame.Screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.concurrent.LinkedBlockingQueue;

import go.pirategame.Control.Controller;
import go.pirategame.PirateGame;
import go.pirategame.Scene.Hud;
import go.pirategame.Sprites.Pirate;
import go.pirategame.Tools.B2WorldCreator;
import go.pirategame.Tools.WorldContactListener;


/**
 * Created by Amy on 25/2/16.
 */
public class PlayScreen implements Screen {
    //Reference to our Game, used to set Screens
    private PirateGame game;
    private TextureAtlas atlas;
    public static boolean alreadyDestroyed = false;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprites
    private Pirate player;

    private Music music;

    private boolean changeScreen;
    private Stage stage;
    private Texture fadeOutTexture;

//    private Array<Item> items;
//    private LinkedBlockingQueue<ItemDef> itemsToSpawn;
//
    private Controller controller;

    public PlayScreen(PirateGame game){


        atlas = new TextureAtlas("img/actors.pack");

        this.game = game;
        //create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();

        gamecam.setToOrtho(false,PirateGame.V_WIDTH,PirateGame.V_HEIGHT);

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(PirateGame.V_WIDTH / PirateGame.PPM, PirateGame.V_HEIGHT / PirateGame.PPM, gamecam);

        //create our game HUD for scores/timers/level info
        hud = new Hud(game.batch);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("Pirate.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1  / PirateGame.PPM);

        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, 0), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        //create mario in our game world
        player = new Pirate(this);

        controller = new Controller();
        world.setContactListener(new WorldContactListener());
//
//        music = PirateGame.manager.get("audio/music/mario_music.ogg", Music.class);
//        music.setLooping(true);
//        music.setVolume(0.3f);
//        music.play();
//
//        items = new Array<Item>();
//        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
    }

    /*
    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }


    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Mushroom.class){
                items.add(new Mushroom(this, idef.position.x, idef.position.y));
            }
        }
    }
    */


    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {
        /*
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(PirateGame.V_WIDTH, PirateGame.V_HEIGHT, gamecam);
        gamecam.position.set(PirateGame.V_WIDTH / 2, PirateGame.V_HEIGHT / 2, 0);

        world = new World(new Vector2(), true);
        world.setContactListener(new WorldContactListener());
        b2dr = new Box2DDebugRenderer();

//
//        WorldBuilder worldBuilder = new WorldBuilder(b2dWorld, world);
//        worldBuilder.build(level);
//        groundSprite = worldBuilder.getGroundSprite();
//
//        mapWidth = worldBuilder.getMapWidth();
//        mapHeight = worldBuilder.getMapHeight();
//
//        hud = new Hud(batch, WIDTH, HEIGHT);
//        hud.setLevelInfo(level);
//
//        b2dTimer = 0;

        changeScreen = false;
        stage = new Stage(gamePort);
        Pixmap pixmap = new Pixmap((int) PirateGame.V_WIDTH, (int) PirateGame.V_HEIGHT, Pixmap.Format.RGB888);
        pixmap.setColor(0.2f, 0.2f, 0.2f, 1f);
        pixmap.fill();
        fadeOutTexture = new Texture(pixmap);
        pixmap.dispose();
        Image image = new Image(fadeOutTexture);
        stage.addActor(image);
        stage.addAction(Actions.fadeOut(0.5f));

//        paused = false;
//
//        skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
//
//        stage2 = new Stage(new FitViewport(640, 480), batch);
//        pauseWindow = new Window("Pause", skin);
//        pauseWindow.setPosition((640 - pauseWindow.getWidth()) / 2, (480 - pauseWindow.getHeight()) / 2);
//        pauseWindow.setVisible(paused);

//        TextButton continueButton = new TextButton("Continue", skin);
//        continueButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                paused = false;
//                GameManager.getInstance().playMusic();
//            }
//        });
//
//        TextButton exitButton = new TextButton("Exit", skin);
//        exitButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new MainMenuScreen(game));
//            }
//
//        });
//        pauseWindow.add(continueButton).padBottom(16f);
//        pauseWindow.row();
//        pauseWindow.add(exitButton);
//
//        stage2.addActor(pauseWindow);
//        Gdx.input.setInputProcessor(stage2);
        */
    }

    public void handleInput(float dt){
        if(player.currentState != Pirate.State.DEAD) {
            if (controller.isUpPressed())
                player.b2body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2body.getWorldCenter(), true);
            if (controller.isDownPressed() && player.b2body.getLinearVelocity().y >= -2)
                player.b2body.applyLinearImpulse(new Vector2(0,-0.1f), player.b2body.getWorldCenter(), true);
            if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (controller.isLeftPressed())
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float dt){
        //handle user input first
        handleInput(dt);
//        handleSpawningItems();

        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        player.update(dt);


        hud.update(dt);

        //attach our gamecam to our players.x coordinate
        if(player.currentState != Pirate.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        //update our gamecam with correct coordinates after changes
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);

    }


    @Override
    public void render(float delta) {
        //separate our update logic from render

        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

//        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
//        hud.stage.draw();
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

    }

    public boolean gameOver(){
        if(player.currentState == Pirate.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width,height);
        controller.resize(width,height);
    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public Hud getHud(){ return hud; }
}