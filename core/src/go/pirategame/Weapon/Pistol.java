package go.pirategame.Weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
 * Created by Amy on 17/3/16.
 */
public class Pistol extends Sprite {
    public Pirate.Direction dir;
    PlayScreen screen;
    World world;
    Array<TextureRegion> frames;
    Animation fireAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    Body b2body;

    public Pistol(PlayScreen screen, float x, float y, Pirate.Direction dir) {
        this.dir = dir;
        this.screen = screen;
        this.world = screen.getWorld();

        // TODO: 18/3/16 Bullet Animation

        frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Bomb"), i * 8, 0, 8, 8));
        }
        fireAnimation = new Animation(0.2f, frames);
        setRegion(fireAnimation.getKeyFrame(0));

        setBounds(x, y, 6 / PirateGame.PPM, 6 / PirateGame.PPM);
        defineFireBall();
    }

    public void defineFireBall() {
        BodyDef bdef = new BodyDef();
        bdef.position.set((dir == Pirate.Direction.LEFT) ? getX() - 12 / PirateGame.PPM : getX() + 5 / PirateGame.PPM, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if (!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / PirateGame.PPM);
        fdef.filter.categoryBits = PirateGame.BULLET_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.BORDER_BIT |
                PirateGame.ROCK_BIT;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        switch (dir) {
            case DOWN:
                b2body.setLinearVelocity(new Vector2(0, -2));
                break;
            case LEFT:
                b2body.setLinearVelocity(new Vector2(-2, 0));
                break;
            case RIGHT:
                b2body.setLinearVelocity(new Vector2(2, 0));
                break;
            case UP:
                b2body.setLinearVelocity(new Vector2(0, 2));
                break;
            default:
                b2body.setLinearVelocity(new Vector2(0, 0));
        }

    }

    public void update(float dt) {
        stateTime += dt;
//        setRegion(fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if ((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
//        if (b2body.getLinearVelocity().y > 2f)
//            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
        if ((dir == Pirate.Direction.RIGHT && b2body.getLinearVelocity().x < 0) ||
                (dir == Pirate.Direction.LEFT && b2body.getLinearVelocity().x > 0) ||
                (dir == Pirate.Direction.UP && b2body.getLinearVelocity().y < 0) ||
                (dir == Pirate.Direction.DOWN && b2body.getLinearVelocity().y > 0))
            setToDestroy();
    }

    public void setToDestroy() {
        setToDestroy = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}

