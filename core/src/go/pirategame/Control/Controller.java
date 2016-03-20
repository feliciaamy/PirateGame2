package go.pirategame.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import go.pirategame.PirateGame;

/**
 * Created by Amy on 26/2/16.
 */
public class Controller {
    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed;
    boolean pistolPressed, powerUpPressed, bombPressed, swordPressed;
    OrthographicCamera cam;

    public Controller() {
        cam = new OrthographicCamera();
        viewport = new FitViewport(800, 480, cam);
        stage = new Stage(viewport, PirateGame.batch);
        Gdx.input.setInputProcessor(stage);

        stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                    case Input.Keys.X:
                        pistolPressed = true;
                        break;
                    case Input.Keys.SPACE:
                        powerUpPressed = true;
                        break;
                    case Input.Keys.Z:
                        bombPressed = true;
                        break;
                    case Input.Keys.C:
                        swordPressed = true;
                        break;
                }
                return true;

            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                    case Input.Keys.X:
                        pistolPressed = false;
                        break;
                    case Input.Keys.SPACE:
                        powerUpPressed = false;
                        break;
                    case Input.Keys.Z:
                        bombPressed = false;
                        break;
                    case Input.Keys.C:
                        swordPressed = false;
                        break;
                }
                return true;
            }
        });

        Table left = new Table();
        left.left().bottom();

        Image upImg = new Image(new Texture("controller/flatDark25.png"));
        upImg.setSize(50,50);
        upImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image downImg = new Image(new Texture("controller/flatDark26.png"));
        downImg.setSize(50,50);
        downImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("controller/flatDark23.png"));
        leftImg.setSize(50, 50);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        Image rightImg = new Image(new Texture("controller/flatDark24.png"));
        rightImg.setSize(50, 50);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        left.add();
        left.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        left.add();
        left.row().pad(5, 5, 5, 5);
        left.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        left.add();
        left.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        left.row().padBottom(5);
        left.add();
        left.add(downImg).size(downImg.getWidth(), downImg.getHeight());
        left.add();

        stage.addActor(left);


        //right
        Table right = new Table();
        right.bottom().left();
        right.setPosition(left.getX()+620,left.getY());

        Image bomb = new Image(new Texture("controller/Pistol.png"));
        bomb.setSize(50, 50);
        bomb.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pistolPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pistolPressed = false;
            }
        });

        //Power up
        //// TODO: 17/3/16 make a condition for power ups
        downImg = new Image(new Texture("controller/empty.png"));
        downImg.setSize(50, 50);
        downImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                powerUpPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                powerUpPressed = false;
            }
        });

        //Bomb
        leftImg = new Image(new Texture("controller/Bomb.png"));
        leftImg.setSize(50, 50);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bombPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bombPressed = false;
            }
        });


        //Sword
        rightImg = new Image(new Texture("controller/Sword.png"));
        rightImg.setSize(50, 50);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                swordPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                swordPressed = false;
            }
        });

        right.add();
        right.add(bomb).size(bomb.getWidth(), bomb.getHeight());
        right.add();
        right.row().pad(5, 5, 5, 5);
        right.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        right.add();
        right.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        right.row().padBottom(5);
        right.add();
        right.add(downImg).size(downImg.getWidth(), downImg.getHeight());
        right.add();

        stage.addActor(right);

    }

    public void draw(){
        stage.draw();
    }

    public boolean isUpPressed(){
        return upPressed;
    }

    public boolean isDownPressed(){
        return downPressed;
    }

    public boolean isLeftPressed(){
        return leftPressed;
    }

    public boolean isRightPressed(){
        return rightPressed;
    }

    public boolean isPistolPressed() {
        return pistolPressed;
    }

    public boolean isPowerUpPressed() {
        return powerUpPressed;
    }

    public boolean isBombPressed() {
        return bombPressed;
    }

    public boolean isSwordPressed() {
        return swordPressed;
    }
    public void resize(int width, int height){
        viewport.update(width,height);
    }
}
