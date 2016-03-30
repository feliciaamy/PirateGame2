package go.pirategame.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import go.pirategame.Control.Controller;
import go.pirategame.PirateGame;
import go.pirategame.Scene.Hud;
import go.pirategame.Sprites.Items.ItemDef;
import go.pirategame.Sprites.Pirate;
import go.pirategame.Sprites.PowerUp.PowerUp;
import go.pirategame.Tools.B2WorldCreator;
import go.pirategame.Tools.WorldContactListener;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Amy on 25/2/16.
 */
public class PlayScreen implements Screen {
    public static boolean alreadyDestroyed = false;
    //Reference to our Game, used to set Screens
    private PirateGame game;
    private TextureAtlas atlas;

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
//    private Pirate player;
    private ArrayList<Pirate> players = new ArrayList<Pirate>(4);
    private int thisPlayerIndex;

    private Music music;

    private boolean changeScreen;
    private Stage stage;
    private Texture fadeOutTexture;

    private Array<PowerUp> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    private Controller controller;

    public PlayScreen(PirateGame game, int thisPlayerIndex){
        atlas = new TextureAtlas("img/actors.pack");
        this.game = game;
        this.thisPlayerIndex=thisPlayerIndex;
        //create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false,PirateGame.V_WIDTH,PirateGame.V_HEIGHT);
        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(PirateGame.V_WIDTH / PirateGame.PPM, PirateGame.V_HEIGHT / PirateGame.PPM, gamecam);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map=maploader.load("testingworld.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1  / PirateGame.PPM);
        //initialize gamecame
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, 0), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        //create mario in our game world
        for (int i = 0; i < 4; i++) {
            players.add(new Pirate(this,i));
        }
//        player = new Pirate(this);


        //create our game HUD for scores/timers/level info
        hud = new Hud(PirateGame.batch,players.get(thisPlayerIndex));

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


    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void handleInput(float dt){
        Pirate player=players.get(thisPlayerIndex);
        if(player.currentState != Pirate.State.DEAD) {
            if (controller.isUpPressed() && player.b2body.getLinearVelocity().y <= PirateGame.MAX_VELOCITY)
                player.b2body.applyLinearImpulse(new Vector2(0, 0.2f), player.b2body.getWorldCenter(), true);
            if (controller.isDownPressed() && player.b2body.getLinearVelocity().y >= -PirateGame.MAX_VELOCITY)
                player.b2body.applyLinearImpulse(new Vector2(0,-0.2f), player.b2body.getWorldCenter(), true);
            if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= PirateGame.MAX_VELOCITY)
                player.b2body.applyLinearImpulse(new Vector2(0.2f, 0), player.b2body.getWorldCenter(), true);
            if (controller.isLeftPressed() && player.b2body.getLinearVelocity().x >= -PirateGame.MAX_VELOCITY)
                player.b2body.applyLinearImpulse(new Vector2(-0.2f, 0), player.b2body.getWorldCenter(), true);
            if (controller.isPistolPressed())
                player.fire();
            else if (controller.isSwordPressed())
                player.useSword();
            else if (controller.isPowerUpPressed())
                player.usePowerup();
        }
    }

    public void update(float dt){
        //handle user input first
        handleInput(dt);
//        handleSpawningItems();

        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);
        for (int i = 0; i < 4; i++) {
            players.get(i).update(dt);
        }
//        player.update(dt);
        hud.update(dt);

        /*//attach our gamecam to our players.x coordinate
        if(player.currentState != Pirate.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }*/

        Pirate player=players.get(thisPlayerIndex);
        //update our gamecam with correct coordinates after changes
        //x position
        gamecam.setToOrtho(false, PirateGame.V_WIDTH / PirateGame.PPM, PirateGame.V_HEIGHT / PirateGame.PPM);
        if (player.b2body.getPosition().x<(PirateGame.V_WIDTH / PirateGame.PPM)/2){
            gamecam.position.x=gamePort.getWorldWidth()/2;
        }else {
            if (player.b2body.getPosition().x>PirateGame.EDGE_POSITION_X)
                gamecam.position.x=PirateGame.EDGE_POSITION_X;
            else
                gamecam.position.x = player.b2body.getPosition().x;
        }
        //y position
        if (player.b2body.getPosition().y<(PirateGame.V_HEIGHT / PirateGame.PPM) /2){
            gamecam.position.y = gamePort.getWorldHeight()/2;
        }else {
            if (player.b2body.getPosition().y>PirateGame.EDGE_POSITION_Y)
                gamecam.position.y=PirateGame.EDGE_POSITION_Y;
            else
                gamecam.position.y = player.b2body.getPosition().y;
        }
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() / PirateGame.FULL_WIDTH * PirateGame.MAP_WIDTH, Gdx.graphics.getHeight());

        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        PirateGame.batch.begin();
        PirateGame.batch.setProjectionMatrix(gamecam.combined);
        for (int i = 0; i < 4; i++) {
            players.get(i).draw(PirateGame.batch);
        }
//        player.draw(PirateGame.batch);
        PirateGame.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        PirateGame.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        Gdx.gl.glViewport(Gdx.graphics.getWidth() / PirateGame.FULL_WIDTH * PirateGame.MAP_WIDTH, 0, Gdx.graphics.getWidth() / PirateGame.FULL_WIDTH * (PirateGame.FULL_WIDTH-PirateGame.MAP_WIDTH), Gdx.graphics.getHeight());
        hud.stage.draw();
        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        controller.draw();


        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

    }

    public boolean gameOver(){
//        return player.currentState == Pirate.State.DEAD && player.getStateTimer() > 3;
        return players.get(thisPlayerIndex).currentState == Pirate.State.DEAD && players.get(thisPlayerIndex).getStateTimer() > 3;
    }

    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width,height);
        controller.resize(width, height);
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