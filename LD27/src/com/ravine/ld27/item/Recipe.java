package com.ravine.ld27.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.ravine.ld27.LD27;
import com.ravine.ld27.screen.GameScreen;


/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 8/23/13
 * Time: 10:16 PM
 */
public class Recipe extends Actor{

    private TextureRegion cardRegion, gridRegion, greyRegion;
    private TextureRegion[] materialRegionArray;
    private String name;
    private Texture materialTexture;
    private boolean revealed, greyed, stepRecipe, getBonus, completed;
    private BitmapFont font;
    private BitmapFont.TextBounds textBounds, valueBounds;
    private float textXOffset, valueXOffset;
    private GameScreen parent;
    private String[] materialArray;
    private Recipe reference;
    //Type is either 1st or 2nd
    private int type;
    private int numMaterials, numMaterialTypes, pointValue;
    private int setNumber;



    public Recipe(String name, int t, int set, GameScreen screen)
    {
        super();

        this.name = name;

        completed = false;
        type = t;

        setNumber = set;

        cardRegion = new TextureRegion(screen.assMan.get("data/card.png", Texture.class));
        setWidth(cardRegion.getRegionWidth());
        setHeight(cardRegion.getRegionHeight());

        gridRegion = new TextureRegion(screen.assMan.get("data/grid.png", Texture.class));

        greyRegion = new TextureRegion(screen.assMan.get("data/greyOut.png", Texture.class));

        materialRegionArray = new TextureRegion[25];
        for(int i = 0; i < materialRegionArray.length; i++){
            materialRegionArray[i] = null;
        }
        materialTexture = screen.assMan.get("data/materials.png", Texture.class);

        revealed = false;

        font = new BitmapFont(Gdx.files.internal("data/alagard.fnt"),false);
        font.setColor(Color.BLACK);
        font.setScale(0.5f);
        textBounds = font.getBounds(name);
        textXOffset = (178 -textBounds.width)/2;
        valueBounds = null;

        parent = screen;

        greyed = false;

        materialArray = new String[25];
        numMaterials = 0;
        numMaterialTypes = 0;

        reference = this;

        stepRecipe = false;

        getBonus = true;
        pointValue = 0;

        addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if(!greyed && !completed && !revealed && !stepRecipe) {
                    //setRevealed(true);
                    parent.setStepRecipe(type, reference);
                    if(type == 1) {
                        parent.setStep(2);
                    } else if(type == 2) {
                        parent.setStep(3);
                    }
                }
            }

        });

    }

    public int getSetNumber() { return setNumber; }

    public int getPointValue() { return pointValue; }
    public boolean getBonus() { return getBonus; }

    public void setCompleted(boolean tf) {
        completed = tf;
    }

    public void setGetBonus(boolean tf) {
        getBonus = tf;
    }

    @Override
    public String getName() { return name; }

    public Recipe clone() {
        Recipe temp = new Recipe(this.name, type, setNumber, parent);
        temp.greyed = this.greyed;
        temp.revealed = this.revealed;
        temp.setPosition(this.getX(),this.getY());
        temp.materialArray = this.materialArray.clone();
        temp.materialRegionArray = this.materialRegionArray.clone();
        temp.stepRecipe = this.stepRecipe;
        temp.getBonus = this.getBonus;
        temp.numMaterials = this.numMaterials;
        temp.pointValue = this.pointValue;
        temp.numMaterialTypes = this.numMaterialTypes;
        temp.valueBounds = this.valueBounds;
        temp.valueXOffset = this.valueXOffset;
        temp.completed = this.completed;

        return temp;

    }

    public void setStepRecipe(boolean tf) {
        stepRecipe = tf;
    }

    public void setGreyed(boolean tf) {
        greyed = tf;
    }

    public void act(float deltaTime){
        super.act(deltaTime);

    }

    public void setRevealed(boolean tf) {
        revealed = tf;
    }

    public String[] getMaterialArray() { return materialArray; }

    // Add a material at the given index. Materials will draw from left to right in order of index
    public void addMaterial(int index, char type)
    {
        int typeIndex = 0;
        boolean skip = false;
        switch (type)
        {
            case 'w':
                typeIndex = 0;
                materialArray[index] = "Wood";
                break;
            case 's':
                typeIndex = 1;
                materialArray[index] = "Stone";
                break;
            case 'i':
                typeIndex = 2;
                materialArray[index] = "Iron";
                break;
            case 'c':
                typeIndex = 3;
                materialArray[index] = "Copper";
                break;
            case 'l':
                typeIndex = 4;
                materialArray[index] = "Leather";
                break;
            case 'g':
                typeIndex = 5;
                materialArray[index] = "Gold";
                break;
            case 'a':
                typeIndex = 6;
                materialArray[index] = "Glass";
                break;
            case 'b':
                typeIndex = 7;
                materialArray[index] = "Silver";
                break;
            case '#':
                skip = true;
                materialArray[index] = "BLANK";
                break;
            default:
                Gdx.app.log(LD27.LOG, "Something went wrong: " + type);
                break;
        }
        if(!skip) materialRegionArray[index] = new TextureRegion(materialTexture, 32*typeIndex, 0, 32, 32);
    }

    public void calculatePointValue() {
        int numWood = 0, numStone = 0, numIron = 0, numCopper = 0, numLeather = 0,
        numGold = 0, numGlass = 0, numSilver = 0;
        for(int i = 0; i < materialArray.length; i++) {
            if(materialArray[i] != null && !materialArray[i].equals("BLANK")){
                if(materialArray[i].equals("Wood")) numWood++;
                else if(materialArray[i].equals("Stone")) numStone++;
                else if(materialArray[i].equals("Iron")) numIron++;
                else if(materialArray[i].equals("Copper")) numCopper++;
                else if(materialArray[i].equals("Leather")) numLeather++;
                else if(materialArray[i].equals("Gold")) numGold++;
                else if(materialArray[i].equals("Glass")) numGlass++;
                else if(materialArray[i].equals("Silver")) numSilver++;
                numMaterials++;
            }
        }
        if(numWood > 0) numMaterialTypes++;
        if(numStone > 0) numMaterialTypes++;
        if(numIron > 0) numMaterialTypes++;
        if(numCopper > 0) numMaterialTypes++;
        if(numLeather > 0) numMaterialTypes++;
        if(numGold > 0) numMaterialTypes++;
        if(numGlass > 0) numMaterialTypes++;
        if(numSilver > 0) numMaterialTypes++;
        float pointModifier = 1.25f*numMaterialTypes;
        if(numMaterials > 0 && numMaterials < 7) pointModifier -= 0.25f;
        else if(numMaterials > 12 && numMaterials < 16) pointModifier += 0.25f;
        else if(numMaterials > 16 && numMaterials < 22) pointModifier += 0.40f;
        else if(numMaterials > 22) pointModifier += 0.55f;

        pointValue = (int) ((5*pointModifier));

        valueBounds = font.getBounds(String.valueOf(pointValue + " gold"));
        valueXOffset = (178 - valueBounds.width)/2;

    }

    public void draw(SpriteBatch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        batch.draw(cardRegion, getX(), getY());
        font.draw(batch, name, getX()+textXOffset, getY()+getHeight()-8);
        font.draw(batch, String.valueOf(pointValue + " gold"), getX()+valueXOffset, getY()+24);
        if(revealed || completed)
        {
            batch.draw(gridRegion, getX()+6, getY()+26);
            //Only draw materials that are part of the recipe
            for(int i = 0; i < materialRegionArray.length; i++)
            {
                if(materialRegionArray[i] != null)
                {
                    batch.draw(materialRegionArray[i],
                            getX()+8+((i%5)*34),
                            getY()+164-((i/5)*34));
                }
            }
        }
        if(greyed || completed) { batch.draw(greyRegion, getX(), getY()); }

    }
}
