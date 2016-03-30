package go.pirategame.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import go.pirategame.PirateGame;
import go.pirategame.Sprites.Items.Bomb;
import go.pirategame.Sprites.Pirate;
import go.pirategame.Sprites.PowerUp.PowerUp;
import go.pirategame.Sprites.TileObject.InteractiveTileObject;
import go.pirategame.Sprites.TileObject.Treasure;

/**
 * Created by Amy on 25/2/16.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        System.out.println(cDef);

        switch (cDef) {

            //**************FINISHED*************//
            // Player vs. treasure
            case PirateGame.PLAYER_BIT | PirateGame.TREASURE_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
                    ((Treasure) fixB.getUserData()).onHit((Pirate) fixA.getUserData());
                else
                    ((Treasure) fixA.getUserData()).onHit((Pirate) fixB.getUserData());
                break;

            // Pistol vs. Reef 
            // TODO: 31/3/16 destroy bullet
//            case PirateGame.PLAYER_BIT | PirateGame.REEF_BIT:
//                if(fixA.getFilterData().categoryBits != PirateGame.REEF_BIT)
//                    ((InteractiveTileObject) fixA.getUserData()).hitByBullet((Pirate) fixB.getUserData());
//                else
//                    ((InteractiveTileObject) fixB.getUserData()).hitByBullet((Pirate) fixA.getUserData());
//                break;

            // TODO: 27/3/16 fix pistol
            // Player vs. bullet(pistol)
            case PirateGame.PLAYER_BIT | PirateGame.BULLET_BIT:
                if(fixA.getFilterData().categoryBits != PirateGame.PLAYER_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).hitByBullet((Pirate) fixB.getUserData());
                else
                    ((InteractiveTileObject) fixB.getUserData()).hitByBullet((Pirate) fixA.getUserData());
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
            // Player vs. power up
            case PirateGame.POWERUP_BIT | PirateGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.POWERUP_BIT)
                    ((PowerUp) fixA.getUserData()).use((Pirate) fixB.getUserData());
                else
                    ((PowerUp) fixB.getUserData()).use((Pirate) fixA.getUserData());
                break;

//            case PirateGame.PLAYER_BIT | PirateGame.POWERUP_BIT:
//                System.out.println("collide");
//                if(fixA.getFilterData().categoryBits == PirateGame.POWERUP_BIT)
//                                    ((PowerUp) fixA.getUserData()).use((Pirate) fixB.getUserData());
//                else
//                    ((PowerUp) fixB.getUserData()).use((Pirate) fixA.getUserData());
//                break;
            // Reef vs. bomb
            case PirateGame.REEF_BIT | PirateGame.EXPLOSION_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.REEF_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).onHit();
                else
                    ((InteractiveTileObject) fixB.getUserData()).onHit();
                break;
//            case PirateGame.PLAYER_BIT | PirateGame.BORDER_BIT:
//                Hud.setTestMsg("Testing: C");
//                break;
//            case PirateGame.PLAYER_BIT | PirateGame.TREASURE_BIT:
//                System.out.println("Win");
//                if(fixA.getFilterData().categoryBits == PirateGame.TREASURE_BIT)
//                    ((InteractiveTileObject) fixA.getUserData()).onHit((Pirate) fixA.getUserData());
//                else
//                    ((InteractiveTileObject) fixB.getUserData()).onHit((Pirate) fixB.getUserData());
//                break;

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
