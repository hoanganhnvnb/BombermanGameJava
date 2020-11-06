package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class Enemy extends Entity {
    private static boolean isAlive = true;
    private static int speed = 1;
    private static int health = 1;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }


}
