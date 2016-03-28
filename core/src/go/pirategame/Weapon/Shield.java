package go.pirategame.Weapon;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;
import go.pirategame.Sprites.Pirate;

/**
 * Created by Amy on 17/3/16.
 */
public class Shield extends Sprite {
    public Pirate.Direction dir;
    PlayScreen screen;
    World world;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    Body b2body;
    float oldX;
    float oldY;

    public Shield(PlayScreen screen, float x, float y, Pirate.Direction dir) {
        this.dir = dir;
        this.screen = screen;
        this.world = screen.getWorld();

        // TODO: 18/3/16 Shield Animation
        /*
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Bomb"), i * 8, 0, 8, 8));
        }
        fireAnimation = new Animation(0.2f, frames);
        setRegion(fireAnimation.getKeyFrame(0));
        */
        setBounds(x, y, 6 / PirateGame.PPM, 6 / PirateGame.PPM);
        defineShield();
        oldX = x;
        oldY = y;
    }

    public void defineShield() {
        BodyDef bdef = new BodyDef();
        switch (dir) {
            case DOWN:
                bdef.position.set(getX() - 5 / PirateGame.PPM, getY() - 12 / PirateGame.PPM);
                break;
            case LEFT:
                bdef.position.set(getX() - 20 / PirateGame.PPM, getY() - 5 / PirateGame.PPM);
                break;
            case RIGHT:
                bdef.position.set(getX() + 2 / PirateGame.PPM, getY() - 5 / PirateGame.PPM);
                break;
            case UP:
                bdef.position.set(getX() - 5 / PirateGame.PPM, getY() + 3 / PirateGame.PPM);
                break;
            default:
                bdef.position.set(getX(), getY());
        }

        bdef.type = BodyDef.BodyType.DynamicBody;
        if (!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shield = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        if (dir == Pirate.Direction.RIGHT || dir == Pirate.Direction.LEFT) {
            vertice[0] = new Vector2(8, 10).scl(1 / PirateGame.PPM);
            vertice[1] = new Vector2(15, 10).scl(1 / PirateGame.PPM);
            vertice[2] = new Vector2(8, -1).scl(1 / PirateGame.PPM);
            vertice[3] = new Vector2(15, -1).scl(1 / PirateGame.PPM);
        } else {
            vertice[0] = new Vector2(1, 6).scl(1 / PirateGame.PPM);
            vertice[1] = new Vector2(12, 6).scl(1 / PirateGame.PPM);
            vertice[2] = new Vector2(1, -1).scl(1 / PirateGame.PPM);
            vertice[3] = new Vector2(12, -1).scl(1 / PirateGame.PPM);
        }

        shield.set(vertice);
        fixtureDef.filter.categoryBits = PirateGame.SHIELD_BIT;
        fixtureDef.shape = shield;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = PirateGame.SHIELD_BIT;
        fixtureDef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_0_BIT |
                PirateGame.BORDER_BIT |
                PirateGame.ROCK_BIT;

        fixtureDef.restitution = 1;
        fixtureDef.friction = 0;
        b2body.createFixture(fixtureDef).setUserData(this);

    }

    public void update(float dt, float x, float y,Pirate pirate) {
        System.out.println("old " + b2body.getPosition().x + " " + b2body.getPosition().y);
        stateTime += dt;
        if (oldX != x)
            b2body.setTransform(b2body.getPosition().x + x - oldX, b2body.getPosition().y, 0);
        if (oldY != y)
            b2body.setTransform(b2body.getPosition().x, (b2body.getPosition().y + y - oldY), 0);

        oldX = x;
        oldY = y;
        if ((stateTime > 3 || setToDestroy || pirate.direction != dir) && !destroyed) {
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

