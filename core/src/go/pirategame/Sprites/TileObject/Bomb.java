package go.pirategame.Sprites.TileObject;

import com.badlogic.gdx.maps.MapObject;

import go.pirategame.Screen.PlayScreen;
import go.pirategame.Sprites.Pirate;

/**
 * Created by zhanghao on 20/3/16.
 */
public class Bomb extends InteractiveTileObject{
    public Bomb(PlayScreen screen, MapObject object) {
        super(screen, object);
    }

    @Override
    public void onHit(Pirate pirate) {

    }
}
