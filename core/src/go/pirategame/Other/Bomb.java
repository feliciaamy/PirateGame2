package go.pirategame.Other;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;



/**
 * Created by zhaojuan on 20/3/16.
 */
public class Bomb extends Sprite {

    PlayScreen screen;
    World world;
    Array<TextureRegion> frames;
    Animation bombAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;
    Body b2body;


    public Bomb(PlayScreen screen, float x, float y) {


        Array<TextureRegion> frames=new Array<TextureRegion>();
        this.screen = screen;
        this.world = screen.getWorld();
        frames = new Array<TextureRegion>();

        ////////////////////////

       /* TextureAtlas textureAtlas = assetManager.get("img/actors.pack", TextureAtlas.class);
        HashMap<String, Animation> anims = new HashMap<String,Animation>();
        TextureRegion textureRegion = textureAtlas.findRegion("Bomb");

        Animation anim;
        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        if (playerbombPower >= player.MAX_BOMB_POWER) {
            for (int i = 0; i < 3; i++) {
                keyFrames.add(new TextureRegion(textureRegion, i * 16, 16 * 1, 16, 16));
           }
        } else {
            for (int i = 0; i < 3; i++) {
                keyFrames.add(new TextureRegion(textureRegion, i * 16, 0, 16, 16));
            }
        }*/








        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Bomb"), i * 8, 0, 8, 8));
        }
        bombAnimation = new Animation(0.2f, frames);
        setRegion(bombAnimation.getKeyFrame(0));
        setBounds(x, y, 8 / PirateGame.PPM, 8 / PirateGame.PPM);
        defineBomb();



    }


    public void defineBomb() {

        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type=BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
           b2body=world.createBody(bdef);


        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(40/ PirateGame.PPM);

        fdef.shape=shape;
        fdef.filter.categoryBits = PirateGame.BOMB_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.BORDER_BIT |
                PirateGame.ROCK_BIT;
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
