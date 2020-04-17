package ru.alttabber.ludum.gameobjects.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ru.alttabber.ludum.inputs.PlayerInput;
import ru.alttabber.ludum.memory.Assets;
import ru.alttabber.ludum.memory.GameController;
import ru.alttabber.ludum.ui.Inventory;
import ru.alttabber.ludum.utils.SpriteAnimation;

public class Player extends Unit {

    private Texture behindTexture;
    private Texture frontTexture;
    private Texture sideTexture;
    private Texture sideFrontTexture;
    private Texture sideBehindTexture;
    private Texture currentTexture;

    private SpriteAnimation upAnimation;
    private SpriteAnimation downAnimation;
    private SpriteAnimation rightAnimation;
    private SpriteAnimation leftAnimation;
    private SpriteAnimation upRightAnimation;
    private SpriteAnimation upLeftAnimation;
    private SpriteAnimation downRightAnimation;
    private SpriteAnimation downLeftAnimation;
    private SpriteAnimation currentAnimation;
    private float animationTime = 0;

    private Vector2 nextXY;

    private Sound footStep;

    private Sprite sprite;

    private PlayerInput input = PlayerInput.IDLE;

    private Inventory inventory;

    private Circle collisionCircle;

    private Vector2 movementVector;

    float footStepTime = 1f;
    boolean nowGo = false;

    int counter = 0;
    
    int speed = 400;

    public Player() {
        super();
        this.height = 200;
        this.width = 200;
        this.hits = 100;

    }

    @Override
    public void init(Batch batch) {
        behindTexture = GameController.getInstance().getAssetManager().get(Assets.behindTexture);
        frontTexture = GameController.getInstance().getAssetManager().get(Assets.frontTexture);
        sideTexture = GameController.getInstance().getAssetManager().get(Assets.sideTexture);
        sideFrontTexture = GameController.getInstance().getAssetManager().get(Assets.sideFrontTexture);
        sideBehindTexture = GameController.getInstance().getAssetManager().get(Assets.sideBehindTexture);
        currentTexture = frontTexture;
        footStep = GameController.getInstance().getAssetManager().get(Assets.footStep);

        this.XY.x = 400;
        this.XY.y = 400;

        this.nextXY = new Vector2(XY.x, XY.y);

        this.upAnimation = new SpriteAnimation(Assets.frontTextureAnimation, this.width, this.height);
        this.downAnimation = new SpriteAnimation(Assets.behindTextureAnimation, this.width, this.height);
        this.rightAnimation = new SpriteAnimation(Assets.sideTextureAnimation, this.width, this.height);
        this.upRightAnimation = new SpriteAnimation(Assets.behindSideTextureAnimation, this.width, this.height);
        this.downRightAnimation = new SpriteAnimation(Assets.frontSideTextureAnimation, this.width, this.height);

        this.leftAnimation = new SpriteAnimation(Assets.sideTextureAnimation, this.width, this.height);
        this.leftAnimation.transformSprites(animationSprite -> {
            animationSprite.flip(true,false);
        });
        this.upLeftAnimation = new SpriteAnimation(Assets.behindSideTextureAnimation, this.width, this.height);
        this.upLeftAnimation.transformSprites(animationSprite -> {
            animationSprite.flip(true,false);
        });
        this.downLeftAnimation = new SpriteAnimation(Assets.frontSideTextureAnimation, this.width, this.height);
        this.downLeftAnimation.transformSprites(animationSprite -> {
            animationSprite.flip(true,false);
        });

        this.currentAnimation = this.downAnimation;

        Sprite[] frames =  this.currentAnimation.getAnimation().getKeyFrames();
        this.sprite = frames[frames.length - 1];

        this.collisionCircle = new Circle();
        refreshCollisionRect();

        this.movementVector = new Vector2();

        this.batch = batch;
    }

    @Override
    public void draw() {
        this.input = GameController.getInstance().getInputController().getCurrentPlayerInput();
        this.doActionByInput(this.input);
        refreshCollisionRect();
        nextXY.x = this.XY.x + speed * Gdx.graphics.getDeltaTime() * this.movementVector.x;
        nextXY.y = this.XY.y + speed * Gdx.graphics.getDeltaTime() * this.movementVector.y;
        if(GameController.getInstance().getCollisionController().isMovementPossible(getCollisionCircleByCoord(nextXY))){
            this.XY.x = this.nextXY.x;
            this.XY.y = this.nextXY.y;
        }
        sprite.setPosition(this.XY.x, this.XY.y);
        this.sprite.draw(batch);

        this.input = PlayerInput.IDLE;
    }

    public void doActionByInput(PlayerInput input){
        this.animationTime += Gdx.graphics.getDeltaTime();
        switch (input) {
            case UP:
                this.currentAnimation = this.downAnimation;
                this.sprite = this.currentAnimation.getKeyFrame(animationTime);
                this.movementVector.y = 1;
                this.movementVector.x = 0;
                footStepTime = footStepTime + Gdx.graphics.getDeltaTime();
                nowGo = true;
                break;
            case DOWN:
                this.currentAnimation = this.upAnimation;
                this.sprite = this.currentAnimation.getKeyFrame(animationTime);
                this.movementVector.y = -1;
                this.movementVector.x = 0;
                footStepTime = footStepTime + Gdx.graphics.getDeltaTime();
                nowGo = true;
                break;
            case RIGHT:
                this.currentAnimation = this.rightAnimation;
                this.sprite = this.currentAnimation.getKeyFrame(animationTime);
                this.movementVector.y = 0;
                this.movementVector.x = 1;
                footStepTime = footStepTime + Gdx.graphics.getDeltaTime();
                nowGo = true;
                break;
            case LEFT:
                this.currentAnimation = this.leftAnimation;
                this.sprite = this.currentAnimation.getKeyFrame(animationTime);
                this.movementVector.y = 0;
                this.movementVector.x = -1;
                footStepTime = footStepTime + Gdx.graphics.getDeltaTime();
                nowGo = true;
                break;
            case UPLEFT:
                this.currentAnimation = this.upLeftAnimation;
                this.sprite = this.currentAnimation.getKeyFrame(animationTime);
                this.movementVector.y = 0.7f;
                this.movementVector.x = -0.7f;
                footStepTime = footStepTime + Gdx.graphics.getDeltaTime();
                nowGo = true;
                break;
            case UPRIGHT:
                this.currentAnimation = this.upRightAnimation;
                this.sprite = this.currentAnimation.getKeyFrame(animationTime);
                this.movementVector.y = 0.7f;
                this.movementVector.x = 0.7f;
                footStepTime = footStepTime + Gdx.graphics.getDeltaTime();
                nowGo = true;
                break;
            case DOWNLEFT:
                this.currentAnimation = this.downLeftAnimation;
                this.sprite = this.currentAnimation.getKeyFrame(animationTime);
                this.movementVector.y = -0.7f;
                this.movementVector.x = -0.7f;
                footStepTime = footStepTime + Gdx.graphics.getDeltaTime();
                nowGo = true;
                break;
            case DOWNRIGHT:
                this.currentAnimation = this.downRightAnimation;
                this.sprite = this.currentAnimation.getKeyFrame(animationTime);
                this.movementVector.y = -0.7f;
                this.movementVector.x = 0.7f;
                footStepTime = footStepTime + Gdx.graphics.getDeltaTime();
                nowGo = true;
                break;
            case IDLE:
                animationTime = 0;
                Sprite[] frames =  this.currentAnimation.getAnimation().getKeyFrames();
                this.movementVector.y = 0;
                this.movementVector.x = 0;
                this.sprite = frames[frames.length - 1];
                footStepTime = 1f;
                nowGo = false;
                break;
        }
        if((footStepTime > 0.4f)&&(nowGo))
        {
            footStep.play();
            footStepTime = 0f;
        }
    }

    public void makeFootStepSound(){
        footStep.play();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Circle getCollisionCircle() {
        return collisionCircle;
    }

    private void refreshCollisionRect(){
        this.refreshCollisionRect(this.collisionCircle, this.XY);
    }

    private void refreshCollisionRect(Circle circle, Vector2 XY){
        circle.x = XY.x + this.width/2;
        circle.y = XY.y + this.height/8;
        circle.radius = this.width/8;
    }

    public Circle getCollisionCircleByCoord(Vector2 XY){
        Circle circle = new Circle();
        refreshCollisionRect(circle, XY);
        return circle;
    }

}
