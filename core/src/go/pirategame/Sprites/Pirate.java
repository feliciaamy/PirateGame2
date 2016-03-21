package go.pirategame.Sprites;

/**
 * Created by Amy on 1/3/16.
 */

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;
import go.pirategame.Weapon.Pistol;
import go.pirategame.Weapon.Shield;
import go.pirategame.Weapon.Sword;

/**
 * Created by Amy on 25/2/16.
 */
public class Pirate extends Sprite {
    public static Direction direction;
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;

    private Animation swimUp;
    private Animation swimDown;
    private Animation swimRight;
    private Animation swimLeft;
    private Animation idleUp;
    private Animation idleDown;
    private Animation idleLeft;
    private Animation idleRight;
    private TextureRegion pirateHit;
    private Animation pirateDead;

    private float stateTimer;
    private boolean pirateIsDead;
    private PlayScreen screen;
    private Sword sword;
    private Shield shield;
    private float powerUpTime;
    private Array<Pistol> bullets;
    private HandledWeapon weapon;
    private PowerUp extraWeapon;
    private boolean timeToRedefinePirate;
    private boolean timeToDefineShield;

    public Pirate(PlayScreen screen) {
        //initialize default values
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.SWIMMING;
        previousState = State.SWIMMING;
        direction = Direction.UP;
        stateTimer = 0;
        weapon = HandledWeapon.NONE;
//        extraWeapon = PowerUp.NONE;
        extraWeapon = PowerUp.SHIELD; // for test only
        timeToRedefinePirate = false;
        timeToDefineShield = false;

        // animation
        HashMap<String, Animation> anims = new HashMap<String, Animation>();

        Animation anim;

        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        // walking up
        for (int i = 0; i < 3; i++) {
            keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), i * 16, 0, 16, 24));
        }
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.LOOP);
        anims.put("walking_up", anim);
        swimUp = anim;

        // walking left
        keyFrames.clear();
        for (int i = 3; i < 6; i++) {
            keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), i * 16, 0, 16, 24));
        }
        anim = new Animation(0.1f, keyFrames);
        anims.put("walking_left", anim);
        swimLeft = anim;

        // walking down
        keyFrames.clear();
        for (int i = 6; i < 9; i++) {
            keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), i * 16, 0, 16, 24));
        }
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.LOOP);
        anims.put("walking_down", anim);
        swimDown = anim;

        // walking right
        keyFrames.clear();
        for (int i = 9; i < 12; i++) {
            keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), i * 16, 0, 16, 24));
        }
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.LOOP);
        anims.put("walking_right", anim);
        swimRight = anim;

        // idling up
        keyFrames.clear();
        keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), 1 * 16, 0, 16, 24));
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.NORMAL);
        anims.put("idling_up", anim);
        idleUp = anim;

        // idling left
        keyFrames.clear();
        keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), 3 * 16, 0, 16, 24));
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.NORMAL);
        anims.put("idling_left", anim);
        idleLeft = anim;

        // idling down
        keyFrames.clear();
        keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), 7 * 16, 0, 16, 24));
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.NORMAL);
        anims.put("idling_down", anim);
        idleDown = anim;

        // idling right
        keyFrames.clear();
        keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), 9 * 16, 0, 16, 24));
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.NORMAL);
        anims.put("idling_right", anim);
        idleRight = anim;

        // dying
        keyFrames.clear();
        for (int i = 12; i < 18; i++) {
            keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), i * 16, 0, 16, 24));
        }
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.NORMAL);
        anims.put("dying", anim);
        pirateDead = anim;

        //define mario in Box2d
        definePirate(0.2f, 0.2f);
        setBounds(0, 0, 16 / PirateGame.PPM, 16 / PirateGame.PPM);
        setRegion(idleUp.getKeyFrame(stateTimer, true));

        bullets = new Array<Pistol>();

    }

    public void update(float dt) {
        powerUpTime += dt;
        if (screen.getHud().isTimeUp() && !isDead()) {
            die();
        }
        //update our sprite to correspond with the position of our Box2D body
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        //update sprite with the correct frame depending on marios current action
        setRegion(getFrame(dt));

        if (timeToDefineShield)
            defineShield();
        if (timeToRedefinePirate)
            redefinePirate();

        for (Pistol bullet : bullets) {
            bullet.update(dt);
            if (bullet.isDestroyed())
                bullets.removeValue(bullet, true);
        }

        if (weapon == HandledWeapon.SWORD) {
            if (sword.isDestroyed()) {
                weapon = HandledWeapon.NONE;
            } else sword.update(dt, b2body.getPosition().x, b2body.getPosition().y);
        }
        // TODO: 21/3/16 Set an accurate timing
        if (powerUpTime >= 12) {
            weapon = HandledWeapon.NONE;
            powerUpTime = 0;
            timeToRedefinePirate = true;
            extraWeapon = PowerUp.NONE;
        }
//        else if (weapon == HandledWeapon.SHIELD) {
//            if (shield.isDestroyed()) {
//                weapon = HandledWeapon.NONE;
//            } else shield.update(dt, b2body.getPosition().x, b2body.getPosition().y);
//        }

    }

    public TextureRegion getFrame(float dt){
        //get pirate current state. ie. swimming, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:
                region = pirateDead.getKeyFrame(stateTimer, true);
                break;
            case WALKING:
            case SWIMMING:
                switch (direction){
                    case DOWN:
                        region = swimDown.getKeyFrame(stateTimer,true);
                        break;
                    case LEFT:
                        region = swimLeft.getKeyFrame(stateTimer,true);
                        break;
                    case RIGHT:
                        region = swimRight.getKeyFrame(stateTimer,true);
                        break;
                    case UP:
                        region = swimUp.getKeyFrame(stateTimer,true);
                        break;
                    default:
                        region = swimUp.getKeyFrame(stateTimer,true);
                        break;
                }
                break;
            case HIT:
            case IDLING:
            default:
                switch (direction){
                    case UP:
                        region = idleUp.getKeyFrame(stateTimer,true);
                        break;
                    case DOWN:
                        region = idleDown.getKeyFrame(stateTimer,true);
                        break;
                    case LEFT:
                        region = idleLeft.getKeyFrame(stateTimer,true);
                        break;
                    case RIGHT:
                        region = idleRight.getKeyFrame(stateTimer,true);
                        break;
                    default:
                        region = idleUp.getKeyFrame(stateTimer,true);
                        break;
                }
                break;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }

    public State getState(){
        if(pirateIsDead)
            return State.DEAD;
        if (b2body.getLinearVelocity().x > 0.08) {
            direction = Direction.RIGHT;
            return State.SWIMMING;
        } else if (b2body.getLinearVelocity().x < -0.08) {
            direction = Direction.LEFT;
            return State.SWIMMING;
        } else if (b2body.getLinearVelocity().y > 0.08) {
            direction = Direction.UP;
            return State.SWIMMING;
        } else if (b2body.getLinearVelocity().y < -0.08) {
            direction = Direction.DOWN;
            return State.SWIMMING;
        }
        else
            return State.IDLING;
    }

    // TODO: 18/3/16 GameOver screen and some animation (to be decided later)
    public void die() {

        if (!isDead()) {

//            PirateGame.manager.get("audio/music/mario_music.ogg", Music.class).stop();
//            PirateGame.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            //test commit
            pirateIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = PirateGame.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }
        }
    }

    public boolean isDead(){
        return pirateIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void definePirate(float x, float y){
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x, y);
        bdef.linearDamping = 11f;

        b2body = world.createBody(bdef);
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / PirateGame.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = PirateGame.PLAYER_BIT;
        fixtureDef.filter.maskBits = PirateGame.PLAYER_BIT |
                    PirateGame.ROCK_BIT |
                    PirateGame.REEF_BIT |
                PirateGame.BORDER_BIT |
                PirateGame.BULLET_BIT;
        b2body.createFixture(fixtureDef);
        shape.dispose();

        b2body.createFixture(fixtureDef).setUserData(this);
    }

    //Put the pirate in a spherical shield
    public void defineShield() {
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 11f;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shield = new CircleShape();
        shield.setRadius(10 / PirateGame.PPM);
        fdef.filter.categoryBits = PirateGame.PLAYER_BIT;
        fdef.filter.maskBits = PirateGame.PLAYER_BIT |
                PirateGame.BULLET_BIT;

        fdef.shape = shield;
        b2body.createFixture(fdef).setUserData(this);

        CircleShape body = new CircleShape();
        body.setRadius(7 / PirateGame.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = body;
        fixtureDef.filter.categoryBits = PirateGame.PLAYER_BIT;
        fixtureDef.filter.maskBits = PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT |
                PirateGame.REEF_BIT |
                PirateGame.BORDER_BIT |
                PirateGame.BULLET_BIT;
        b2body.createFixture(fixtureDef);
//        shape.dispose();

        b2body.createFixture(fixtureDef).setUserData(this);
        timeToDefineShield = false;

    }

    //After shield is gone
    public void redefinePirate() {
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 11f;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / PirateGame.PPM);
        fdef.filter.categoryBits = PirateGame.PLAYER_BIT;
        fdef.filter.maskBits = PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT |
                PirateGame.REEF_BIT |
                PirateGame.BORDER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        b2body.createFixture(fdef).setUserData(this);

        timeToRedefinePirate = false;
    }

    public void fire() {
        if (bullets.size == 0) {
            weapon = HandledWeapon.PISTOL;
            bullets.add(new Pistol(screen, b2body.getPosition().x, b2body.getPosition().y, direction));
        }
    }

    public void draw(Batch batch) {
        super.draw(batch);
        for (Pistol bullet : bullets)
            bullet.draw(batch);
        // TODO: 18/3/16 draw other weapon also 
    }

    /*Not used for now
    public String getDirection() {
        switch (direction) {
            case DOWN:
                return "Down";
            case LEFT:
                return "Left";
            case RIGHT:
                return "Right";
            case UP:
                return "Up";
            default:
                return "IDK";
        }
    }*/

    public void useSword() {
        if (weapon != HandledWeapon.SWORD) {
            weapon = HandledWeapon.SWORD;
            sword = new Sword(screen, b2body.getPosition().x, b2body.getPosition().y, direction);
        }
    }

    public void usePowerup() {
        if (extraWeapon == PowerUp.NONE) return;
        else if (extraWeapon == PowerUp.SHIELD) useShield();
        else if (extraWeapon == PowerUp.OCTOPUS) useOctopus();
        else if (extraWeapon == PowerUp.TNT) useTNT();
    }

    public void useShield() {
        if (weapon != HandledWeapon.SHIELD) {
            timeToDefineShield = true;
            weapon = HandledWeapon.SHIELD;
            // TODO: 21/3/16 decide whether we want it to be like pistol or just a sphere around the player
//            shield = new Shield(screen, b2body.getPosition().x, b2body.getPosition().y, direction);
        }
    }

    // TODO: 18/3/16
    public void useOctopus() {

    }

    // TODO: 18/3/16
    public void useTNT() {
    }

    //Note: This will be needed for Contact Listener
    public boolean shieldOn() {
        return (weapon == HandledWeapon.SHIELD);
    }

    public enum State {SWIMMING, WALKING, HIT, DEAD, IDLING}

    public enum Direction {UP, DOWN, LEFT, RIGHT}

    public enum HandledWeapon {PISTOL, SWORD, SHIELD, NONE}

    public enum PowerUp {OCTOPUS, SHIELD, TNT, NONE}
}
