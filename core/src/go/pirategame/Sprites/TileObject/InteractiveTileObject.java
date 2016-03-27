package go.pirategame.Sprites.TileObject;

/**
 * Created by Amy on 1/3/16.
 */

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;
import go.pirategame.Sprites.Pirate;


/**
 * Created by brentaureli on 8/28/15.
 */
public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected PlayScreen screen;
    protected MapObject object;

    protected Fixture fixture;

    public InteractiveTileObject(PlayScreen screen, MapObject object){
        this.object = object;
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / PirateGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / PirateGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / PirateGame.PPM, bounds.getHeight() / 2 / PirateGame.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public abstract void onHit(Pirate pirate);

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * PirateGame.PPM / 16),
                (int)(body.getPosition().y * PirateGame.PPM / 16));
    }

    // TODO: 27/3/16 Handle hit by bullet
    public void hitByBullet(Pirate pirate) {
        pirate.decreaseHealth(20);
    }

    // TODO: 27/3/16 Handle hit by bomb
    public void hitByBomb(Pirate pirate) {
        pirate.decreaseHealth(25);
    }

    // TODO: 27/3/16 Handle hit by TNT
    public void hitByTNT(Pirate pirate) {
        pirate.decreaseHealth(50);
    }

    // TODO: 27/3/16 Handle hit by Sword
    public void hitBySword(Pirate pirate) {
        pirate.decreaseHealth(10);
    }

    // TODO: 27/3/16 Handle reef destroyed
    public void destroyReef(Reef reef) {
        reef.getCell().setTile(null);
    }
}
