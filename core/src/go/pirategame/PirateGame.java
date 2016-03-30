package go.pirategame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import go.pirategame.Screen.PlayScreen;

public class PirateGame extends Game {
	//Virtual Screen size and Box2D Scale(Pixels Per Meter)
	public static final float PPM = 40;
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 300;
	public static final float EDGE_POSITION_X = 8f;
	public static final float EDGE_POSITION_Y = 8f;

	//Player Info
	public static final int MAX_VELOCITY=4;
	public static final float BOARDER_OFFSET=0.5f;

	//player select
	public static final int THIS_PLAYER=0;

	//Hud Split Ratio
	public static final int FULL_WIDTH = 16;
	public static final int MAP_WIDTH = 12;
	public static final int HUD_PAD=5;

	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short HIT_BIT = 1;
	public static final short PLAYER_0_BIT = 2;
	public static final short ROCK_BIT = 4;
	public static final short REEF_BIT = 8;
	public static final short EXPLOSION_BIT = 16;
	public static final short TNT_BIT = 32;
	public static final short TREASURE_BIT = 64;
	public static final short BORDER_BIT = 128;
	public static final short BOMB_BIT = 256;
	public static final short BULLET_BIT = 2048;
	public static final short SWORD_BIT = 4096;
	public static final short SHIELD_BIT = 8192;

	public static SpriteBatch batch;

	/* WARNING Using AssetManager in a static way can cause issues, especially on Android.
    Instead you may want to pass around Assetmanager to those the classes that need it.
    We will use it in the static context to save time for now. */
	public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this, THIS_PLAYER));
	}

	@Override
	public void dispose() {
		super.dispose();
//		manager.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}

	public static PlayServices playServices;

	public PirateGame(){}

	public PirateGame(PlayServices playServices)
	{
		PirateGame.playServices = playServices;
	}
}


