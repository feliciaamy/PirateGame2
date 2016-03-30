package go.pirategame.Sprites.TileObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;
import go.pirategame.Sprites.Pirate;

/**
 * Created by Amy on 1/3/16.
 */

public class Reef extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;

    public Reef(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset");
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.REEF_BIT);
    }


    @Override
    public void onHit(Pirate pirate) {
        setCategoryFilter(PirateGame.HIT_BIT);
        getCell().setTile(null);
        PirateGame.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }

    // TODO: 27/3/16 Handle reef destroyed
    public void destroyReef() {
        Gdx.app.log("reef", "collision");
        getCell().setTile(null);
        setCategoryFilter(PirateGame.NOTHING_BIT);
    }
}
