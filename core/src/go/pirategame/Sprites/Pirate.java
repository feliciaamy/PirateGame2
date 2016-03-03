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

/**
 * Created by Amy on 25/2/16.
 */
public class Pirate extends Sprite {
    public Direction direction;
    ;
    public State currentState;
    ;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion pirateWalk;
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
    private boolean runningRight;
    private boolean marioIsBig;
    private boolean runGrowAnimation;
    private boolean timeToDefineBigMario;
    private boolean timeToRedefineMario;
    private boolean pirateIsDead;
    private PlayScreen screen;

    public Pirate(PlayScreen screen) {
        //initialize default values
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.SWIMMING;
        previousState = State.SWIMMING;
        direction = Direction.UP;
        stateTimer = 0;
        runningRight = true;

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
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.LOOP);
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


    }

    public void update(float dt) {

        // time is up : too late mario dies T_T
        // the !isDead() method is used to prevent multiple invocation
        // of "die music" and jumping
        // there is probably better ways to do that but it works for now.
        if (screen.getHud().isTimeUp() && !isDead()) {
            die();
        }
        //update our sprite to correspond with the position of our Box2D body
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        //update sprite with the correct frame depending on marios current action
        setRegion(getFrame(dt));
    }

//    private Array<FireBall> fireballs;

    public TextureRegion getFrame(float dt){
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:
                region = pirateDead.getKeyFrame(stateTimer, true);
                break;
            case WALKING: //JUST FOR NOW
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

//        //if mario is running left and the texture isnt facing left... flip it.
//        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
//            region.flip(true, false);
//            runningRight = false;
//        }
//
//        //if mario is running right and the texture isnt facing right... flip it.
//        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
//            region.flip(true, false);
//            runningRight = true;
//        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }

    public State getState(){
        //Test to Box2D for velocity on the X and Y-Axis
        //if mario is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if(pirateIsDead)
            return State.DEAD;
        else if(b2body.getLinearVelocity().y > 0) {
            direction = Direction.UP;
            return State.SWIMMING;
        }
        else if(b2body.getLinearVelocity().y < 0){
            direction = Direction.DOWN;
            return State.SWIMMING;
        }
        else if(b2body.getLinearVelocity().x > 0){
            direction = Direction.RIGHT;
            return State.SWIMMING;
        }
        else if(b2body.getLinearVelocity().x > 0){
            direction = Direction.LEFT;
            return State.SWIMMING;
        }
        else
            return State.IDLING;
    }

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

            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
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
        bdef.linearDamping = 12f;

        b2body = world.createBody(bdef);
        CircleShape shape = new CircleShape();
//        shape.setColor
        shape.setRadius(0.08f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = PirateGame.PLAYER_BIT;
        fixtureDef.filter.maskBits = PirateGame.PLAYER_BIT |
                    PirateGame.ROCK_BIT |
                    PirateGame.REEF_BIT |
                    PirateGame.BORDER_BIT;
        b2body.createFixture(fixtureDef);
//        shape.dispose();

//        Renderer renderer = new Renderer(new TextureRegion(textureRegion, 0, 0, 16, 24), 16 / GameManager.PPM, 24 / GameManager.PPM);
//        renderer.setOrigin(16 / GameManager.PPM / 2, 16 / GameManager.PPM / 2);
//
//        // entity
//        Entity e = new com.artemis.utils.EntityBuilder(world)
//                .with(
//                        new Player(resetPlayerAbilities),
//                        new Transform(x, y, 1, 1, 0),
//                        new RigidBody(body),
//                        new State("idling_down"),
//                        renderer,
//                        new Anim(anims)
//                )
//                .build();
//
//        body.setUserData(e);
    }

//    public void fire(){
//        fireballs.add(new FireBall(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
//    }
//
    public void draw(Batch batch){
        super.draw(batch);
    }

    public enum State {SWIMMING, WALKING, HIT, DEAD, IDLING}

    public enum Direction {UP, DOWN, LEFT, RIGHT}
}
