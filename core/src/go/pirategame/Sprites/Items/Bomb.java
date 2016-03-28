package go.pirategame.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;


/**
 * Created by zhaojuan on 20/3/16.
 */
public class Bomb extends Sprite {
    private enum State{BURNING,EXPLODING,DISAPPEARING}
    PlayScreen screen;
    World world;

    Animation bombAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;
    Body b2body;


    public Bomb(PlayScreen screen, float x, float y) {

        setRegion(screen.getAtlas().findRegion("Bomb"), 163, 35, 80, 32);
        setBounds(x,y, 32 / PirateGame.PPM,32/PirateGame.PPM);
        this.screen = screen;
//        this.world = screen.getWorld();
//         Array<TextureRegion> frames=new Array<TextureRegion>();
////
//         frames = new Array<TextureRegion>();
//        // TODO: 18/3/16 Bullet Animation
//        for (int i = 0; i < 4; i++) {
//            frames.add(new TextureRegion(screen.getAtlas().findRegion("Bomb"), i*8,0,8,8));
//        }
////        bombAnimation = new com.badlogic.gdx.graphics.g2d.Animation(0.2f, frames);
////        setRegion(bombAnimation.getKeyFrame(0));
////        setBounds(x, y, 6 / PirateGame.PPM, 6 / PirateGame.PPM);
//        bombAnimation = new Animation(0.2f, frames);

//
       defineItem();


    }


    public void defineItem() {

        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);


        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(6 / PirateGame.PPM);

        fdef.shape=shape;
        b2body.createFixture(fdef).setUserData(this);
        EdgeShape exploEdge1=new EdgeShape();
        exploEdge1.set(new Vector2(-30 / PirateGame.PPM, 0 / PirateGame.PPM), new Vector2(30 / PirateGame.PPM, 0 / PirateGame.PPM));

        fdef.shape=exploEdge1;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData("explosionCross1");

        EdgeShape exploEdge2=new EdgeShape();
        exploEdge2.set(new Vector2(0 / PirateGame.PPM, -30 / PirateGame.PPM), new Vector2(0 / PirateGame.PPM, 30 / PirateGame.PPM));

        fdef.shape=exploEdge2;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData("explosionCross2");



    }

    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if ((stateTime > 3 || setToDestroy ) && !destroyed) {
            setToDestroy();
            world.destroyBody(b2body);
            destroyed = true;
        }
    }

    public void setToDestroy() {
        setToDestroy = true;
    }
    public boolean isDestroyed() {
        return destroyed;
    }



}
