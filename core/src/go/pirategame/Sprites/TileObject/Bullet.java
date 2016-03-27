package go.pirategame.Sprites.TileObject;

import com.badlogic.gdx.maps.MapObject;

import go.pirategame.Screen.PlayScreen;
import go.pirategame.Sprites.Pirate;

/**
 * Created by zhanghao on 27/3/16.
 */
public class Bullet extends InteractiveTileObject {
    public Bullet(PlayScreen screen, MapObject object) {
        super(screen, object);
    }

    @Override
    public void onHit(Pirate pirate) {

    }
}
