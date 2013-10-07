package com.ravine.ld27.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ravine.ld27.LD27;
import com.ravine.ld27.container.*;
import com.ravine.ld27.item.Material;
import com.ravine.ld27.item.Recipe;
import com.ravine.ld27.table.CraftTable;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 8/23/13
 * Time: 10:13 PM
 */
public class GameScreen extends AbstractScreen {

    private ArrayList<Recipe> recipeArrayList;
    private ArrayList<Recipe> recipeArrayList2;
    private Image bgImage;
    //Keep it simple. Step 1 is pick card, step 2 is build
    private int step;
    private boolean stepChanged;
    private RecipeContainer recipeContainer, recipeContainer2;
    private MaterialContainer materialContainer;
    private CraftTable table;
    private Recipe[] stepRecipes;
    private ArrayList<Recipe> completedRecipes;
    private Timer timer;
    private Score scoreBar;
    //0 - Reveal, 1 - Start, 2 - Next
    private Button[] buttons;
    //[i][j] - i: Button, j: Button position
    private Drawable[][] buttonDrawables;
    private int roundsCompleted;

    private boolean startPressed, revealPressed, nextPressed;


    public GameScreen(LD27 game)
    {
        super(game);

        roundsCompleted = 0;

        recipeArrayList = new ArrayList<Recipe>();
        recipeArrayList.addAll(loadRecipes("data/recipes.txt"));

        recipeArrayList2 = new ArrayList<Recipe>();
        recipeArrayList2.addAll(loadRecipes("data/recipes2.txt"));

        recipeContainer = new RecipeContainer(recipeArrayList, true);
        recipeContainer.setPosition(0,stage.getHeight()-240);

        recipeContainer2 = new RecipeContainer(recipeArrayList2, false);
        recipeContainer2.setPosition(-64, stage.getHeight()-380);

        materialContainer = new MaterialContainer();
        materialContainer.setPosition(0, 0);
        materialContainer.addMaterial(new Material("Wood", false));
        materialContainer.addMaterial(new Material("Stone", false));
        materialContainer.addMaterial(new Material("Iron", false));
        materialContainer.addMaterial(new Material("Copper", false));
        materialContainer.addMaterial(new Material("Leather", false));
        materialContainer.addMaterial(new Material("Gold", false));
        materialContainer.addMaterial(new Material("Glass", false));
        materialContainer.addMaterial(new Material("Silver", false));

        step = 1;
        stepChanged = false;
        stepRecipes = new Recipe[2];
        completedRecipes = new ArrayList<Recipe>();

        buttons = new Button[5];
        buttonDrawables = new Drawable[5][2];
        setUpButtons();

        bgImage = new Image(assMan.get("data/bg.png", Texture.class));

        stage.addListener(new InputListener() {
           @Override
            public boolean keyTyped(InputEvent event, char character) {
               if(character == 'e') stage.addActor(new BonusNote(1.5f, 2));
               return true;
           }
        });

        table = new CraftTable(this, (int)(stage.getWidth()/2)-86, (int) stage.getHeight()-304);
        table.setScale(1.25f);
        table.addGridSlots();
        table.setVisible(false);
        table.setRecipe(recipeArrayList.get(0));

        timer = new Timer();
        timer.setPosition(12, stage.getHeight()-96 );
        timer.setVisible(false);

        buttons[3].setPosition(stage.getWidth()-256, 0);
        buttons[3].setVisible(true);
        buttons[4].setPosition(stage.getWidth()-256, 0);
        buttons[4].setVisible(false);

        scoreBar = new Score();
        scoreBar.setPosition(stage.getWidth()-228, 72);
        scoreBar.setVisible(true);
    }

    private void setUpButtons() {
        Texture buttonTexture = assMan.get("data/buttons.png", Texture.class);
        for(int i = 0; i < buttonDrawables.length; i++) {
            for(int j = 0; j < buttonDrawables[i].length; j++) {
                buttonDrawables[i][j] = new TextureRegionDrawable(new TextureRegion(buttonTexture,
                        j*256, (i == 3 ? 5 : i == 4 ? 6 : i)*64, 256, 64));
            }
        }
        for(int i = 0; i < buttons.length; i ++) {
            buttons[i] = new Button(buttonDrawables[i][0], buttonDrawables[i][1]);
            buttons[i].setSize(256,64);
            buttons[i].setPosition(stage.getWidth()/2-128, stage.getHeight()/4+42);
            buttons[i].setVisible(false);
        }
        startPressed = false;
        nextPressed = false;
        revealPressed = false;
        buttons[0].addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Button) event.getListenerActor()).toggle();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(event.getStageX() <= event.getListenerActor().getX()+ event.getListenerActor().getWidth() &&
                        event.getStageX() >= event.getListenerActor().getX() &&
                        event.getStageY() >= event.getListenerActor().getY() &&
                        event.getStageY() <= event.getListenerActor().getY() + event.getListenerActor().getHeight()) {
                    revealPressed = true;

                ((Button) event.getListenerActor()).toggle();
                }
            }

        });
        buttons[1].addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Button) event.getListenerActor()).toggle();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(event.getStageX() <= event.getListenerActor().getX()+ event.getListenerActor().getWidth() &&
                        event.getStageX() >= event.getListenerActor().getX() &&
                        event.getStageY() >= event.getListenerActor().getY() &&
                        event.getStageY() <= event.getListenerActor().getY() + event.getListenerActor().getHeight()) {
                    startPressed = true;
                }
                ((Button) event.getListenerActor()).toggle();
            }

        });
        buttons[2].addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Button) event.getListenerActor()).toggle();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(event.getStageX() <= event.getListenerActor().getX()+ event.getListenerActor().getWidth() &&
                        event.getStageX() >= event.getListenerActor().getX() &&
                        event.getStageY() >= event.getListenerActor().getY() &&
                        event.getStageY() <= event.getListenerActor().getY() + event.getListenerActor().getHeight()) {
                    nextPressed = true;
                }
                ((Button) event.getListenerActor()).toggle();
            }

        });
        buttons[3].addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Button) event.getListenerActor()).toggle();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getStageX() <= event.getListenerActor().getX() + event.getListenerActor().getWidth() &&
                        event.getStageX() >= event.getListenerActor().getX() &&
                        event.getStageY() >= event.getListenerActor().getY() &&
                        event.getStageY() <= event.getListenerActor().getY() + event.getListenerActor().getHeight()) {
                    Gdx.app.exit();

                    ((Button) event.getListenerActor()).toggle();
                }
            }

        });
        buttons[4].addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Button) event.getListenerActor()).toggle();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getStageX() <= event.getListenerActor().getX() + event.getListenerActor().getWidth() &&
                        event.getStageX() >= event.getListenerActor().getX() &&
                        event.getStageY() >= event.getListenerActor().getY() &&
                        event.getStageY() <= event.getListenerActor().getY() + event.getListenerActor().getHeight()) {
                    setStep(1);
                    if(stepRecipes[0] != null) stepRecipes[0].setGetBonus(false);
                    if(stepRecipes[1] != null) stepRecipes[1].setGetBonus(false);
                    for(Recipe r : completedRecipes) {
                        r.setCompleted(true);
                    }
                    nextPressed = false;
                    startPressed = false;
                    revealPressed = false;

                    table.clearTable();

                    ((Button) event.getListenerActor()).toggle();
                }
            }

        });
    }

    private ArrayList<Recipe> loadRecipes(String file){
        ArrayList<Recipe> tempList = new ArrayList<Recipe>();
        FileHandle recipes = Gdx.files.internal(file);
        String[] lines = recipes.readString().split("\n");
        Recipe temp = new Recipe("error", 0, 0, this);
        int index = 0;
        for(int y = 0; y < lines.length; y++) {
            String curLine = lines[y];
            if(curLine.startsWith("$")) {
                temp = new Recipe(curLine.substring(1,curLine.indexOf('-')),
                        (file.contains("2") ? 2 : 1),
                        curLine.contains("1") ? 1 : curLine.contains("2") ? 2 :
                        curLine.contains("3") ? 3 : curLine.contains("4") ? 4 : 5, this);
                index = 0;
            } else {
                for(int x = 0; x < curLine.length(); x++) {
                    if(curLine.charAt(x) != ' ')
                    {
                        temp.addMaterial(index, curLine.charAt(x));
                        Gdx.app.log(LD27.LOG,"addMaterial(" + index + ", " + curLine.charAt(x));
                        index++;
                        if(index != 0 && index%5 == 0) x++;
                    }
                }
                temp.calculatePointValue();
            }

            tempList.add(temp);
        }
        return tempList;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(roundsCompleted == 10) {
            game.setScreen(new EndScreen(game, scoreBar.getScore()));
        }
        if(stepChanged) {
            stepChanged = false;
            if(step == 1) {
                timer.setVisible(false);
                buttons[2].setVisible(false);
                buttons[4].setVisible(false);
                buttons[3].setVisible(true);
                buttons[1].setVisible(false);
                buttons[0].setVisible(false);
                timer.stopTimer();
                recipeContainer.setVisible(true);
                recipeContainer2.setVisible(true);
                recipeContainer.setActive(true);
                recipeContainer2.setActive(false);
                if(stepRecipes[1].getBonus()) stage.addActor(new BonusNote(1.25f, 1));
                if(stepRecipes[0] != null) {
                    stepRecipes[0].setGetBonus(true);
                    stepRecipes[0].setRevealed(false);
                    stepRecipes[0].setVisible(false);
                }
                if(stepRecipes[1] != null) {
                    stepRecipes[1].setGetBonus(true);
                    stepRecipes[1].setRevealed(false);
                    stepRecipes[1].setVisible(false);
                }
                table.setVisible(false);
            } else if( step == 2) {
                recipeContainer.setActive(false);
                recipeContainer2.setActive(true);
            } else if( step == 3 ) {
                if(stepRecipes[0].getSetNumber() == stepRecipes[1].getSetNumber()) {
                    stage.addActor(new BonusNote(1.25f, 2));
                }
                recipeContainer.setVisible(false);
                recipeContainer2.setVisible(false);
                stepRecipes[0].setVisible(true);
                timer.setVisible(true);
                timer.resetTime(5.00f);
                revealPressed = false;
                buttons[4].setVisible(true);
                buttons[3].setVisible(false);
                buttons[0].setVisible(true);
            } else if( step == 4 ) {
                buttons[0].setVisible(false);
                stepRecipes[0].setVisible(false);
                timer.resetTime(10.00f);
                startPressed = false;
                buttons[1].setVisible(true);
                table.setVisible(true);
                table.setRecipe(stepRecipes[0]);
                table.setActive(false);
            } else if( step == 5 ) {
                table.setActive(false);
                table.setVisible(false);
                buttons[1].setVisible(false);
                buttons[2].setVisible(false);
                stepRecipes[1].setVisible(true);
                timer.setVisible(true);
                timer.resetTime(5.00f);
                revealPressed = false;
                buttons[0].setVisible(true);
            } else if( step == 6 ) {
                buttons[0].setVisible(false);
                stepRecipes[1].setVisible(false);
                buttons[1].setVisible(true);
                timer.setVisible(true);
                timer.resetTime(10.00f);
                table.setVisible(true);
                table.clearTable();
                table.setRecipe(stepRecipes[1]);
                table.setActive(false);
                startPressed = false;
            }
        }
        if(step == 3) {
            if(timer.isTimeUp()) {
                stepRecipes[0].setRevealed(false);
                setStep(4);
                buttons[0].setVisible(false);
            } else {
                if(revealPressed) {
                    stepRecipes[0].setRevealed(true);
                    revealPressed = false;
                    timer.startTimer();
                }
            }
        }else if(step == 4) {
            if(timer.isTimeUp()) {
                stepRecipes[0].setGetBonus(false);
            }
            if(startPressed) {
                startPressed = false;
                buttons[1].setVisible(false);
                timer.startTimer();
                table.setActive(true);
            }
            if(table.checkForCorrect()) {
                timer.stopTimer();
                buttons[2].setVisible(true);

                if(nextPressed) {
                    if(stepRecipes[0].getBonus()) stage.addActor(new BonusNote(1.25f, 1));
                    scoreBar.modifyScore((int) (stepRecipes[0].getPointValue()*(stepRecipes[0].getBonus() ? 1.25 : 1)));
                    setStep(5);
                    table.clearTable();
                    nextPressed = false;
                }
            }
        } else if(step == 5) {
            if(timer.isTimeUp()) {
                stepRecipes[1].setRevealed(false);
                setStep(6);
                buttons[0].setVisible(false);
            } else {
                if(revealPressed) {
                    stepRecipes[1].setRevealed(true);
                    revealPressed = false;
                    timer.startTimer();
                }
            }
        } else if(step == 6) {
            if(timer.isTimeUp()) {
                stepRecipes[1].setGetBonus(false);
            }
            if(startPressed) {
                startPressed = false;
                buttons[1].setVisible(false);
                timer.startTimer();
                table.setActive(true);
            }
            if(table.checkForCorrect()) {
                timer.stopTimer();
                buttons[2].setVisible(true);

                if(nextPressed) {

                    scoreBar.modifyScore((int) (stepRecipes[1].getPointValue()*(stepRecipes[1].getBonus() ? 1.25 : 1)));
                    roundsCompleted++;
                    setStep(1);
                    table.clearTable();
                    nextPressed = false;
                    for(Recipe r : completedRecipes) {
                        r.setCompleted(true);
                    }
                }
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        //recipeContainer.scale(this.vWidth/width, this.vHeight/height);
    }

    @Override
    public void show() {
        super.show();
        stage.addActor(bgImage);
        //for(Recipe r : recipeArrayList)
        //    stage.addActor(r);
        stage.addActor(recipeContainer2);
        stage.addActor(recipeContainer);
        stage.addActor(materialContainer);
        stage.addActor(table);
        stage.addActor(timer);
        stage.addActor(scoreBar);
        scoreBar.createCoins();
        for(int i = 0; i < buttons.length; i++) {
            stage.addActor(buttons[i]);
        }

    }

    public MaterialContainer getMaterialContainer() {
        return materialContainer;
    }

    public void setStepRecipe(int step, Recipe r) {
        if(stepRecipes[step-1] != null) {
            stepRecipes[step-1].remove();
        }

        if(step > 0 && step < 3)
        {
            stepRecipes[step-1] = r.clone();
            stepRecipes[step-1].setGreyed(false);
            stepRecipes[step-1].setRevealed(false);
            stepRecipes[step-1].setPosition(stage.getWidth()-492, stage.getHeight()-320);
            stepRecipes[step-1].setStepRecipe(true);
            stepRecipes[step-1].setVisible(false);
            stage.addActor(stepRecipes[step-1]);
            completedRecipes.add(r);

        }
    }

    public void setStep(int i) {
        if(step != i && (i > 0 && i < 7))
        {
            step = i;
            stepChanged = true;
        }
    }
}
