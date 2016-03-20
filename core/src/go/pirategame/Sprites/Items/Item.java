package go.pirategame.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import go.pirategame.PirateGame;
import go.pirategame.Screen.PlayScreen;

/**
 * Created by zhaojuan on 20/3/16.
 */
public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;
    public Item(PlayScreen screen,float x,float y){
        this.screen=screen;
        this.world=screen.getWorld();
        setPosition(x,y);
        setBounds(x,y,16/ PirateGame.PPM,16/ PirateGame.PPM);
        defineItem();
        toDestroy=false;
        destroyed=false;

    }
    public abstract void defineItem();
    public abstract void use();
    public void update(float dt){
        if(toDestroy&&!destroyed){
            world.destroyBody(body);
            destroyed=true;
        }
    }
    public void draw(Batch batch){
        if(!destroyed){
            super.draw(batch);
        }
    }
    public void destroy(){
        toDestroy=true; 
    }


}
