package go.pirategame.Sprites.TileObject;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;

/**
 * Created by Amy on 2/3/16.
 */
public class Treasure extends InteractiveTileObject{
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Treasure(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.TREASURE_BIT);
    }
}
