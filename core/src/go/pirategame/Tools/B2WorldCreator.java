package go.pirategame.Tools;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import javax.swing.ImageIcon;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;
import go.pirategame.Sprites.Pirate;
import go.pirategame.Sprites.TileObject.Border;
import go.pirategame.Sprites.TileObject.Reef;
import go.pirategame.Sprites.TileObject.Rock;
import go.pirategame.Sprites.TileObject.Treasure;


/**
 * Created by Amy on 1/3/16.
 */
public class B2WorldCreator {
//    private Array<Goomba> goombas;
//    private Array<Turtle> turtles;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

//        //create ground bodies/fixtures
//        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PirateGame.PPM, (rect.getY() + rect.getHeight() / 2) / PirateGame.PPM);
//
//            body = world.createBody(bdef);
//
//            shape.setAsBox(rect.getWidth() / 2 / PirateGame.PPM, rect.getHeight() / 2 / PirateGame.PPM);
//            fdef.shape = shape;
//            body.createFixture(fdef);
//        }

//        //create rock bodies/fixtures
//        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PirateGame.PPM, (rect.getY() + rect.getHeight() / 2)
//                    / PirateGame.PPM);
//
//            body = world.createBody(bdef);
//
//            shape.setAsBox(rect.getWidth() / 2 / PirateGame.PPM, rect.getHeight() / 2 / PirateGame.PPM);
//            fdef.shape = shape;
//            fdef.filter.categoryBits = PirateGame.OBJECT_BIT;
//            body.createFixture(fdef);
//        }

        // TODO: 27/3/16 testing Zhang Hao
//        //create reef bodies/fixtures
//        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
//            new Reef(screen, object);
//        }
//
//        //create Border bodies/fixtures
//        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
//            new Border(screen, object);
//        }
//
//        //create Rock bodies/fixtures
//        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
//            new Rock(screen, object);
//        }
//
//        //create Treasure bodies/fixtures
//        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
//            new Treasure(screen, object);
//        }
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(MapObject.class)){
            new Reef(screen, object);
        }
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(MapObject.class)){
            new Rock(screen, object);
        }












        //create all goombas
//        goombas = new Array<Goomba>();
//        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//            goombas.add(new Goomba(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
//        }
//        turtles = new Array<Turtle>();
//        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//            turtles.add(new Turtle(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
//        }
    }

}