package uet.oop.bomberman.entities.blocks;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntityArr;
import uet.oop.bomberman.entities.item.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Brick extends Entity {
    private boolean isBroken = false;
    private  int timeBroken = 0;

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (isBroken) {
            this.animate += Sprite.DEFAULT_SIZE / 10;
            this.setImg(Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1
                    , Sprite.brick_exploded2, animate, Sprite.DEFAULT_SIZE).getFxImage());
            if (timeBroken == 0) {
                this.timeBroken++;
                TimerTask createItem = new TimerTask() {
                    @Override
                    public void run() {
                        Item item = randomItem();
                        if (item != null) {
                            if (item != null){
                                EntityArr.items.add(item);
                            }
                            item.setVisible(true);
                        }
                    }
                };
                Timer timer = new Timer();
                timer.schedule(createItem, 400L);
            }
        }
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public boolean isBroken() {
        return isBroken;
    }

    private Item randomItem() {
        Random random = new Random();
        int num = random.nextInt(10);
        switch (num) {
            case 1:
                return new BombItem(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE
                        , Sprite.powerup_bombs.getFxImage());
            case 2:
                return new FlameItem(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE
                        , Sprite.powerup_flames.getFxImage());
            case 3:
                return new SpeedItem(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE
                        , Sprite.powerup_speed.getFxImage());
            case 4:
                return new BombPassItem(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE
                        , Sprite.powerup_bombpass.getFxImage());
            case 5:
                return new WallPassItem(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE
                        , Sprite.powerup_wallpass.getFxImage());
            case 6:
                return new FlamePassItem(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE
                        , Sprite.powerup_flamepass.getFxImage());
            default:
                return null;
        }
    }
}
