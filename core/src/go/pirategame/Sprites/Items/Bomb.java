package go.pirategame.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;
import go.pirategame.Sprites.Pirate;


/**
 * Created by zhaojuan on 20/3/16.
 */
public class Bomb extends Sprite {
    private enum State{BURNING,EXPLODING,DISAPPEARING}
    PlayScreen screen;
    World world;
    Array<TextureRegion> frames;
    com.badlogic.gdx.graphics.g2d.Animation bombAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;
    Body b2body;


    public Bomb(PlayScreen screen, float x, float y) {

//        setRegion(screen.getAtlas().findRegion("Bomb"), 163, 35, 16, 16);
//        setBounds(x,y, 16 / PirateGame.PPM,16/PirateGame.PPM);
        this.screen = screen;
        this.world = screen.getWorld();
        //Array<TextureRegion> frames=new Array<TextureRegion>();
        this.screen = screen;
        this.world = screen.getWorld();
        frames = new Array<TextureRegion>();
        // TODO: 18/3/16 Bullet Animation
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Bomb"), i * 8, 0, 8, 8));
        }
        bombAnimation = new com.badlogic.gdx.graphics.g2d.Animation(0.2f, frames);
        setRegion(bombAnimation.getKeyFrame(0));
        setBounds(x, y, 6 / PirateGame.PPM, 6 / PirateGame.PPM);
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