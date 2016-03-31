package go.pirategame.Sprites.PowerUp;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;
import go.pirategame.Sprites.Pirate;

/**
 * Created by Amy on 30/3/16.
 */
public class Shoes extends PowerUp {
    private boolean used;

    public Shoes(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        used = false;

//        setRegion(screen.getAtlas().findRegion("shield"), 0, 0, 4, 4);
//        velocity = new Vector2(0.7f, 0);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PirateGame.PPM);
        fdef.filter.categoryBits = PirateGame.POWERUP_BIT;
        fdef.filter.maskBits = PirateGame.PLAYER_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Pirate pirate) {
        if (!used) {
            destroy();
            System.out.println("Take shoes");
            used = true;
        } else {
            pirate.takePowerUp(Pirate.PowerUp.SHOES);
//            Hud.updatePowerUp(pirate.getExtraWeapon());
        }


    }

    public void update(float dt) {
        super.update(dt);
    }
}

