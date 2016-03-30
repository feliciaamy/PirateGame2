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
import go.pirategame.Sprites.Pirate;


/**
 * Created by zhaojuan on 20/3/16.
 */
public class Bomb extends Sprite {
    PlayScreen screen;
    World world;
    Animation bombAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean exploded;
    boolean redefined;
    Body b2body;
    private int bombExplosionCountDown=4;

    public Bomb(PlayScreen screen, float x, float y) {

        setRegion(screen.getAtlas().findRegion("Bomb"), 163, 35, 80, 32);
        setBounds(x, y, 32 / PirateGame.PPM, 32 / PirateGame.PPM);
        this.screen = screen;
        this.world = screen.getWorld();
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
        defineBomb();
    }

    public void defineBomb() {
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

    public void reDefineBomb(){
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

    public void explodeBomb(){
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
        exploEdge3.set(new Vector2(-15 / PirateGame.PPM, 0 / PirateGame.PPM), new Vector2(15 / PirateGame.PPM, 0 / PirateGame.PPM));

        fdef.shape = exploEdge3;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("explosionCross3");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge4 = new EdgeShape();
        exploEdge4.set(new Vector2(0 / PirateGame.PPM, -15 / PirateGame.PPM), new Vector2(0 / PirateGame.PPM, 15 / PirateGame.PPM));

        fdef.shape = exploEdge4;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("explosionCross4");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.REEF_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
    }

    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if ((stateTime > 2 || setToDestroy) && !destroyed && ! redefined){
            System.out.println("redifi");

            reDefineBomb();
            redefined=true;
        }
        //set Explosion time to 4
        else if ((stateTime > bombExplosionCountDown || setToDestroy) && !destroyed && !exploded){
            System.out.println("Explode");
            explodeBomb();
            exploded=true;
        }
        if ((stateTime > bombExplosionCountDown+1 || setToDestroy) && !destroyed && exploded){
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

    public void hitByBomb(Pirate pirate) {
        pirate.decreaseHealth(25);
    }

    private enum State {BURNING, EXPLODING, DISAPPEARING}


}
