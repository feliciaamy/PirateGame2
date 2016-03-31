package go.pirategame.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
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
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;
    Body b2body;

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
        shape.setRadius(16 / PirateGame.PPM);

        fdef.shape = shape;
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
    }

    @Override
    public void redefineItem() {

    }

    @Override
    public void use() {

    }

    @Override
    public void update(float dt) {
        if (toDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        }
    }

    @Override
    public void draw(Batch batch) {
        if (!destroyed) {
            super.draw(batch);
        }
    }

}

