package go.pirategame.Sprites.TileObject;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;
import go.pirategame.Sprites.Items.ItemDef;
import go.pirategame.Sprites.PowerUp.Shield;
import go.pirategame.Sprites.PowerUp.Shoes;
import go.pirategame.Sprites.PowerUp.Tnt;

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
        if (object.getProperties().containsKey("shield")) {
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                    Shield.class));
        } else if (object.getProperties().containsKey("shoes")) {
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                    Shoes.class));
        } else if (object.getProperties().containsKey("TNT")) {
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                    Tnt.class));
        }
    }
//
//    // TODO: 27/3/16 Handle reef destroyed
//    public void destroyReef() {
//
//    }
}
