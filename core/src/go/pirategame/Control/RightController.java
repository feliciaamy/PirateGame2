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
 * Created by Amy on 17/3/16.
 */
public class RightController {
    Viewport viewport;
    Stage stage;
    boolean pistolPressed, powerUpPressed, bombPressed, swordPressed;
    OrthographicCamera cam;
    // TODO: 17/3/16 Make variables for powerups

    public RightController() {
        cam = new OrthographicCamera();
        viewport = new FitViewport(800, 480, cam);
        stage = new Stage(viewport, PirateGame.batch);
        Gdx.input.setInputProcessor(stage);

        stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
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
                //nothign changed
                //test

            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
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

        Table table = new Table();
        table.right().bottom();

        Image upImg = new Image(new Texture("controller/Pistol.png"));
        upImg.setSize(50, 50);
        upImg.addListener(new InputListener() {

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
        Image downImg = new Image(new Texture("controller/empty.png"));
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
        Image leftImg = new Image(new Texture("controller/Bomb.png"));
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
        Image rightImg = new Image(new Texture("controller/Sword.png"));
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

        table.add();
        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        table.add();
        table.row().pad(5, 5, 5, 5);
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.row().padBottom(5);
        table.add();
        table.add(downImg).size(downImg.getWidth(), downImg.getHeight());
        table.add();

        stage.addActor(table);

    }

    public void draw() {
        stage.draw();
    }

    public boolean ispistolPressed() {
        return pistolPressed;
    }

    public boolean ispowerUpPressed() {
        return powerUpPressed;
    }

    public boolean isbombPressed() {
        return bombPressed;
    }

    public boolean isswordPressed() {
        return swordPressed;
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
