package go.pirategame.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
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
 * Created by zhanghao on 31/3/16.
 */
public class TNT extends Item {
    PlayScreen screen;
    World world;
    Animation tntAnimation;
    float stateTime;

    boolean fireRight;

    boolean destroyed;
    boolean setToDestroy;
    boolean exploded;
    boolean redefined;
    Body b2body;
    private int tntExplosionCountDown=4;

    public TNT(PlayScreen screen, float x, float y) {

        setRegion(screen.getAtlas().findRegion("TNT"), 163, 35, 80, 32);
        setBounds(x, y, 32 / PirateGame.PPM, 32 / PirateGame.PPM);
        this.screen = screen;
        this.world = screen.getWorld();

        defineItem();


    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PirateGame.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = PirateGame.NOTHING_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void redefineItem() {
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PirateGame.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = PirateGame.BOMB_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void use() {

        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PirateGame.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = PirateGame.NOTHING_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        EdgeShape exploEdge1 = new EdgeShape();
        exploEdge1.set(new Vector2(-30 / PirateGame.PPM, 0 / PirateGame.PPM), new Vector2(30 / PirateGame.PPM, 0 / PirateGame.PPM));

        fdef.shape = exploEdge1;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("explosionCross1");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge2 = new EdgeShape();
        exploEdge2.set(new Vector2(0 / PirateGame.PPM, -30 / PirateGame.PPM), new Vector2(0 / PirateGame.PPM, 30 / PirateGame.PPM));

        fdef.shape = exploEdge2;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("explosionCross2");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
        EdgeShape exploEdge3 = new EdgeShape();
        exploEdge3.set(new Vector2(-30 / PirateGame.PPM, 0 / PirateGame.PPM), new Vector2(30 / PirateGame.PPM, 0 / PirateGame.PPM));

        fdef.shape = exploEdge3;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("explosionCross3");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge4 = new EdgeShape();
        exploEdge4.set(new Vector2(0 / PirateGame.PPM, -30 / PirateGame.PPM), new Vector2(0 / PirateGame.PPM, 30 / PirateGame.PPM));

        fdef.shape = exploEdge4;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("explosionCross4");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge5 = new EdgeShape();
        exploEdge4.set(new Vector2(-30 / PirateGame.PPM, -30 / PirateGame.PPM), new Vector2(30 / PirateGame.PPM, 30 / PirateGame.PPM));
        fdef.shape = exploEdge5;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("explosionCross5");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge6 = new EdgeShape();
        exploEdge4.set(new Vector2(30 / PirateGame.PPM, 30 / PirateGame.PPM), new Vector2(-30 / PirateGame.PPM, -30 / PirateGame.PPM));

        fdef.shape = exploEdge6;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("explosionCross6");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;


    }



    @Override
    public void draw(Batch batch) {
        if (!destroyed) {
            super.draw(batch);
        }
    }
    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if ((stateTime > 2 || setToDestroy) && !destroyed && ! redefined){
            redefineItem();
            redefined=true;
        }
        //set Explosion time to 4
        else if ((stateTime > tntExplosionCountDown || setToDestroy) && !destroyed && !exploded){
            use();
            exploded=true;
        }
        if ((stateTime > tntExplosionCountDown+1 || setToDestroy) && !destroyed && exploded){
            setToDestroy();
            world.destroyBody(b2body);
            destroyed=true;
        }
    }

    public void setToDestroy() {
        setToDestroy = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

}

