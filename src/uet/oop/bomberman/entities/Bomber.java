package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private int speed = Sprite.SCALED_SIZE / 4;
    private boolean isAlive = true;
    public static List<Bomb> bombs = new ArrayList<>();

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {

    }

    public void goRight() {
        this.x += this.speed;
        if (checkBounds()) this.x -= this.speed;
        setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1,
                Sprite.player_right_2, this.getX(), Sprite.DEFAULT_SIZE).getFxImage());
    }

    public void goLeft() {
        this.x -= this.speed;
        if (checkBounds()) this.x += this.speed;
        setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1,
                Sprite.player_left_2, this.getX(), Sprite.DEFAULT_SIZE).getFxImage());
    }

    public void goUp() {
        this.y -= this.speed;
        if (checkBounds()) this.y += this.speed;
        setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1,
                Sprite.player_up_2, this.getY(), Sprite.DEFAULT_SIZE).getFxImage());
    }
    public void goDown() {
        this.y += this.speed;
        if (checkBounds()) this.y -= this.speed;
        setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1,
                Sprite.player_down_2, this.getY(), Sprite.DEFAULT_SIZE).getFxImage());
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean checkBounds() {
        for (Entity e : walls) {
            if (this.intersects(e)) return true;
        }

        for (Entity e : bricks) {
            if (this.intersects(e)) return true;
        }
        return false;
    }
}
