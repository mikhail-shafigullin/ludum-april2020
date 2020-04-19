package ru.alttabber.ludum.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import ru.alttabber.ludum.gameobjects.AutomaticUsableObject;
import ru.alttabber.ludum.gameobjects.Wall;
import ru.alttabber.ludum.gameobjects.items.Item;

import java.util.ArrayList;
import java.util.List;

public class CollisionController {

    private List<Wall> impassableObjects;
    private List<Item> usableObjects;
    private List<AutomaticUsableObject> autoUseObjects;

    public CollisionController() {
        impassableObjects = new ArrayList<>();
        usableObjects = new ArrayList<>();
        autoUseObjects = new ArrayList<>();
    }

    public void addWalls(List<Wall> walls){
        impassableObjects.addAll(walls);
    }

    public boolean isMovementPossible(Circle playerCircle){
        for(Wall wall: impassableObjects){
            if(Intersector.overlaps(playerCircle, wall.getRectangle())){
                return false;
            }
        }
        return true;
    }

    public Item getUsableObject(Circle playerCircle){
        Item returnedObject = null;
        for(Item usableObject: usableObjects){
            if(Intersector.overlaps(playerCircle, usableObject.getRectangle())){
                returnedObject = usableObject;
            }
        }
        return returnedObject;
    }

    public AutomaticUsableObject getAutoUseObject(Circle playerCircle){
        AutomaticUsableObject autoUseObject = null;
        for(AutomaticUsableObject obj : autoUseObjects){
            if(Intersector.overlaps(playerCircle, obj.getRectangle())){
                autoUseObject = obj;
            }
        }
        return autoUseObject;
    }

    public void addUsableObject(Item obj){
        usableObjects.add(obj);
    }

    public List<Item> getUsableObjects() {
        return usableObjects;
    }

    public List<AutomaticUsableObject> getAutoUseObjects() {
        return autoUseObjects;
    }

    public void clear(){
        this.impassableObjects.clear();
        this.autoUseObjects.clear();
        this.usableObjects.clear();
    }

    public void removeItem(Item item) {
        usableObjects.remove(item);
    }

    public void addAutoUsableObject(AutomaticUsableObject autoUsableObject) {
        autoUseObjects.add(autoUsableObject);
    }

    public List<Wall> getWalls() {
        return impassableObjects;
    }
}
