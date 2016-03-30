package go.pirategame.Sprites.TileObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
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
        Gdx.app.log("reef", "collision");
        MapProperties temp = object.getProperties();
        if (temp.containsKey("TNT")) {
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                    Tnt.class));
        }
        if (temp.containsKey("shield")) {
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                    Shield.class));
        }
        if (temp.containsKey("shoes")) {
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                    Shoes.class));
        }
        Thread a=new Thread(new Runnable(){
            public void run(){
                long startTime=System.currentTimeMillis();
                long endTime=System.currentTimeMillis();

                while(endTime-startTime<3000){

                    endTime=System.currentTimeMillis();
                }
                getCell().setTile(null);
                setCategoryFilter(PirateGame.NOTHING_BIT);
            }
        });
        a.start();
//        System.out.println(object.getProperties().toString());

    }
//
//    // TODO: 27/3/16 Handle reef destroyed
//    public void destroyReef() {
//
//    }
}
