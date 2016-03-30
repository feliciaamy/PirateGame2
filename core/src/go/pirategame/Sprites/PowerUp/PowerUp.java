package go.pirategame.Sprites.PowerUp;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;
import go.pirategame.Sprites.Pirate;

/**
 * Created by Amy on 30/3/16.
 */
public abstract class PowerUp extends Sprite{
    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;
    protected TiledMap map;

    public PowerUp(PlayScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        setPosition(x, y);
        setBounds(getX(),getY(), 16 / PirateGame.PPM, 16 / PirateGame.PPM);
        defineItem();
        toDestroy = false;
        destroyed = false;
    }

    public abstract void defineItem();
    public abstract void use(Pirate pirate);
    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
            getCell().setTile(null);
        }
    }
    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int) (body.getPosition().x * PirateGame.PPM / 16),
                (int) (body.getPosition().y * PirateGame.PPM / 16));
    }

    public void destroy(){
        toDestroy = true;
    }

}
