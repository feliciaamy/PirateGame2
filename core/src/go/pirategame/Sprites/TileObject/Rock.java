package go.pirategame.Sprites.TileObject;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;

/**
 * Created by Amy on 1/3/16.
 */

public class Rock extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;

    public Rock(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset");
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.BORDER_BIT);
    }

    @Override
    public void onNextBomb() {

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
