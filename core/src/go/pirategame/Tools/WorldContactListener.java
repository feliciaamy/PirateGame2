package go.pirategame.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import go.pirategame.PirateGame;
import go.pirategame.Sprites.Items.Bomb;
import go.pirategame.Sprites.Pirate;
import go.pirategame.Sprites.TileObject.InteractiveTileObject;
import go.pirategame.Sprites.TileObject.Reef;
import go.pirategame.Sprites.TileObject.Treasure;
import go.pirategame.Weapon.Pistol;

/**
 * Created by Amy on 25/2/16.
 */
public class WorldContactListener implements ContactListener {
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




            // TODO: 27/3/16 fix pistol
            // Player vs. bullet(pistol)
            case PirateGame.PLAYER_BIT | PirateGame.BULLET_BIT:
                if(fixA.getFilterData().categoryBits != PirateGame.PLAYER_BIT)
                    ((Pistol) fixA.getUserData()).hitByBullet();
                else
                    ((Pistol) fixB.getUserData()).hitByBullet();
                break;
            // Player vs. bomb || TNT
            //// TODO: 30/3/16 avoid once killed by putting bomb
            case PirateGame.PLAYER_BIT | PirateGame.EXPLOSION_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.BOMB_BIT)
                    ((Bomb) fixA.getUserData()).hitByBomb((Pirate) fixB.getUserData());
                else
                    ((Bomb) fixB.getUserData()).hitByBomb((Pirate) fixA.getUserData());
                break;

            case PirateGame.PLAYER_BIT | PirateGame.TNT_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).hitByTNT((Pirate) fixB.getUserData());
                else
                    ((InteractiveTileObject) fixB.getUserData()).hitByTNT((Pirate) fixA.getUserData());
                break;

            //  Player vs. sword
            case PirateGame.PLAYER_BIT | PirateGame.SWORD_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).hitBySword((Pirate) fixB.getUserData());
                else
                    ((InteractiveTileObject) fixB.getUserData()).hitBySword((Pirate) fixA.getUserData());
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
