package go.pirategame.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import go.pirategame.PirateGame;
import go.pirategame.Scene.Hud;
import go.pirategame.Sprites.Pirate;
import go.pirategame.Sprites.TileObject.InteractiveTileObject;
import go.pirategame.Sprites.TileObject.Reef;

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
            // Player vs. bullet(pistol)
            case PirateGame.PLAYER_BIT | PirateGame.BULLET_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).hitByBullet((Pirate) fixB.getUserData());
                else
                    ((InteractiveTileObject) fixB.getUserData()).hitByBullet((Pirate) fixA.getUserData());
                break;
            // Player vs. bomb || TNT
            case PirateGame.PLAYER_BIT | PirateGame.BOMB_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).hitByBomb((Pirate) fixB.getUserData());
                else
                    ((InteractiveTileObject) fixB.getUserData()).hitByBomb((Pirate) fixA.getUserData());
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

            // Reef vs. bomb
            case PirateGame.REEF_BIT | PirateGame.BOMB_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.REEF_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).destroyReef((Reef) fixB.getUserData());
                else
                    ((InteractiveTileObject) fixB.getUserData()).destroyReef((Reef) fixA.getUserData());
                break;
            case PirateGame.PLAYER_BIT | PirateGame.BORDER_BIT:
                Hud.setTestMsg("Testing: C");
                break;
        }
//        switch (cDef){
//            case MarioBros.MARIO_HEAD_BIT | MarioBros.BRICK_BIT:
//            case MarioBros.MARIO_HEAD_BIT | MarioBros.COIN_BIT:
//                if(fixA.getFilterData().categoryBits == MarioBros.MARIO_HEAD_BIT)
//                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Mario) fixA.getUserData());
//                else
//                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Mario) fixB.getUserData());
//                break;
//            case MarioBros.ENEMY_HEAD_BIT | MarioBros.MARIO_BIT:
//                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_HEAD_BIT)
//                    ((Enemy)fixA.getUserData()).hitOnHead((Mario) fixB.getUserData());
//                else
//                    ((Enemy)fixB.getUserData()).hitOnHead((Mario) fixA.getUserData());
//                break;
//            case MarioBros.ENEMY_BIT | MarioBros.OBJECT_BIT:
//                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_BIT)
//                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
//                else
//                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
//                break;
//            case MarioBros.MARIO_BIT | MarioBros.ENEMY_BIT:
//                if(fixA.getFilterData().categoryBits == MarioBros.MARIO_BIT)
//                    ((Mario) fixA.getUserData()).hit((Enemy)fixB.getUserData());
//                else
//                    ((Mario) fixB.getUserData()).hit((Enemy)fixA.getUserData());
//                break;
//            case MarioBros.ENEMY_BIT | MarioBros.ENEMY_BIT:
//                ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
//                ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
//                break;
//            case MarioBros.ITEM_BIT | MarioBros.OBJECT_BIT:
//                if(fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT)
//                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
//                else
//                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
//                break;
//            case MarioBros.ITEM_BIT | MarioBros.MARIO_BIT:
//                if(fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT)
//                    ((Item)fixA.getUserData()).use((Mario) fixB.getUserData());
//                else
//                    ((Item)fixB.getUserData()).use((Mario) fixA.getUserData());
//                break;
//            case MarioBros.FIREBALL_BIT | MarioBros.OBJECT_BIT:
//                if(fixA.getFilterData().categoryBits == MarioBros.FIREBALL_BIT)
//                    ((FireBall)fixA.getUserData()).setToDestroy();
//                else
//                    ((FireBall)fixB.getUserData()).setToDestroy();
//                break;
//        }
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
