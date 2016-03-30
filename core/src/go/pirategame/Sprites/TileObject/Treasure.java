package go.pirategame.Sprites.TileObject;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;
import go.pirategame.Sprites.Pirate;

/**
 * Created by Amy on 2/3/16.
 */
public class Treasure extends InteractiveTileObject{
    private static TiledMapTileSet tileSet;

    public Treasure(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.TREASURE_BIT);
    }

    @Override
    public void onHit(Pirate pirate) {

    }
}
