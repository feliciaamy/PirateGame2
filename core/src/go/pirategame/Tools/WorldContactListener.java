package go.pirategame.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;
import go.pirategame.Sprites.TileObject.Reef;
import go.pirategame.Sprites.TileObject.Treasure;
import go.pirategame.Weapon.Pistol;
import go.pirategame.Weapon.Sword;

/**
 * Created by Amy on 25/2/16.
 */
public class WorldContactListener implements ContactListener {
    private PlayScreen screen;

    public WorldContactListener(PlayScreen screen){
        this.screen=screen;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

            //**************FINISHED*************//
            // Player vs. treasure
            case PirateGame.PLAYER_BIT | PirateGame.TREASURE_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
                    ((Treasure) fixB.getUserData()).findTreasure();
                else
                    ((Treasure) fixA.getUserData()).findTreasure();
                break;
            // Reef vs. bomb
            case PirateGame.REEF_BIT | PirateGame.EXPLOSION_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.REEF_BIT)
                    ((Reef) fixA.getUserData()).destroyReef();
                else
                    ((Reef) fixB.getUserData()).destroyReef();
                break;
            // Player vs. bullet(pistol)
            case PirateGame.PLAYER_BIT | PirateGame.BULLET_BIT:
                if(fixA.getFilterData().categoryBits != PirateGame.PLAYER_BIT)
                    ((Pistol) fixA.getUserData()).hitByBullet();
                else
                    ((Pistol) fixB.getUserData()).hitByBullet();
                break;
            //**************FINISHED*************//



            //**************NOT SURE (HAVEN'T TEST)**********//
            //  Player vs. sword
            case PirateGame.PLAYER_BIT | PirateGame.SWORD_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
                    ((Sword) fixA.getUserData()).hitBySword();
                else
                    ((Sword) fixB.getUserData()).hitBySword();
                break;




            // Player vs. bomb || TNT
            case PirateGame.PLAYER_BIT | PirateGame.EXPLOSION_BIT:
                screen.getPirate(PirateGame.THIS_PLAYER).decreaseHealth(25);
                break;



        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
