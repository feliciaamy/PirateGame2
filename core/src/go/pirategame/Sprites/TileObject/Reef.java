package go.pirategame.Sprites.TileObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;

/**
 * Created by Amy on 1/3/16.
 */

public class Reef extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Reef(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset");
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.REEF_BIT);
    }

    @Override
    public void onNextBomb() {
        Gdx.app.log("reef","collision");
        setCategoryFilter(PirateGame.EXPLOSION_BIT);

        getCell().setTile(null);

    }

   /*
    @Override
    public void onHeadHit(Mario mario) {
        if(getCell().getTile().getId() == BLANK_COIN)
            PirateGame.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else {
            if(object.getProperties().containsKey("mushroom")) {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / PirateGame.PPM),
                        Mushroom.class));
                PirateGame.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            }
            else
                PirateGame.manager.get("audio/sounds/coin.wav", Sound.class).play();
            getCell().setTile(tileSet.getTile(BLANK_COIN));
            Hud.addScore(100);
        }
    }*/
}
