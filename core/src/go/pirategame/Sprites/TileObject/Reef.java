package go.pirategame.Sprites.TileObject;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;

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
    public void onHit() {
        getCell().setTile(null);
        setCategoryFilter(PirateGame.NOTHING_BIT);
    }
//
//    // TODO: 27/3/16 Handle reef destroyed
//    public void destroyReef() {
//
//    }
}
