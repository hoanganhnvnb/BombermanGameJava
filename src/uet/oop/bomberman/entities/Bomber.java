package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.blocks.Brick;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.*;

public class Bomber extends Entity {
    private int numBombs = 1;

    private int flameLength = 1;

    private int speed = Sprite.DEFAULT_SIZE / 6;

    private boolean isAlive = true;

    private boolean bombPass = false;

    private boolean wallPass = false;

    private boolean flamePass = false;

    private int time = 0;

    public List<Bomb> bombs = new ArrayList<>();

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        this.animate += Sprite.DEFAULT_SIZE / 10;
        if (!isAlive()) {
            bomberDead();
        }
        if (checkPortal()) {
            if (this.time == 0) {
                this.time++;
                BombermanGame.level++;
                CreateMap.createMapByLevel(BombermanGame.level);
            }
        }
    }

    public void goRight() {
        for (int i = 1; i <= this.speed; ++i) {
            this.x += 1;
            if (checkBrick() || checkWall() || checkBomb()) {
                this.x -= 1;
                if (this.y % Sprite.SCALED_SIZE >= 2 * Sprite.SCALED_SIZE / 3) {
                    this.y = Sprite.SCALED_SIZE * (this.y / Sprite.SCALED_SIZE) + Sprite.SCALED_SIZE;
                } else if (this.y % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 3) {
                    this.y = Sprite.SCALED_SIZE * (this.y / Sprite.SCALED_SIZE);
                }
                break;
            }
        }
        setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1,
                Sprite.player_right_2, this.animate, Sprite.DEFAULT_SIZE).getFxImage());
    }

    public void goLeft() {
        for (int i = 1; i <= this.speed; ++i) {
            this.x -= 1;
            if (checkBrick() || checkWall() || checkBomb()) {
                this.x += 1;
                if (this.y % Sprite.SCALED_SIZE >= 2 * Sprite.SCALED_SIZE / 3) {
                    this.y = Sprite.SCALED_SIZE * (this.y / Sprite.SCALED_SIZE) + Sprite.SCALED_SIZE;
                } else if (this.y % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 3) {
                    this.y = Sprite.SCALED_SIZE * (this.y / Sprite.SCALED_SIZE);
                }
                break;
            }
        }
        setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1,
                Sprite.player_left_2, this.animate, Sprite.DEFAULT_SIZE).getFxImage());
    }

    public void goUp() {
        for (int i = 1; i <= this.speed; ++i) {
            this.y -= 1;
            if (checkBrick() || checkWall() || checkBomb()) {
                this.y += 1;
                if (this.x % Sprite.SCALED_SIZE >= 2 * Sprite.SCALED_SIZE / 3) {
                    this.x = Sprite.SCALED_SIZE * (this.x / Sprite.SCALED_SIZE) + Sprite.SCALED_SIZE;
                } else if (this.x % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 3) {
                    this.x = Sprite.SCALED_SIZE * (this.x / Sprite.SCALED_SIZE);
                }
                break;
            }
        }
        setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1,
                Sprite.player_up_2, this.animate, Sprite.DEFAULT_SIZE).getFxImage());
    }

    public void goDown() {
        for (int i = 1; i <= this.speed; ++i) {
            this.y += 1;
            if (checkBrick() || checkWall() || checkBomb()) {
                this.y -= 1;
                if (this.x % Sprite.SCALED_SIZE >= 2 * Sprite.SCALED_SIZE / 3) {
                    this.x = Sprite.SCALED_SIZE * (this.x / Sprite.SCALED_SIZE) + Sprite.SCALED_SIZE;
                } else if (this.x % Sprite.SCALED_SIZE <= Sprite.SCALED_SIZE / 3) {
                    this.x = Sprite.SCALED_SIZE * (this.x / Sprite.SCALED_SIZE);
                }
                break;
            }
        }
        setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1,
                Sprite.player_down_2, this.animate, Sprite.DEFAULT_SIZE).getFxImage());
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this. speed= speed;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public void setNumBombs(int numBombs) {
        this.numBombs = numBombs;
    }

    public int getFlameLength() {
        return flameLength;
    }

    public void setFlameLength(int flameLength) {
        this.flameLength = flameLength;
    }

    public boolean isBombPass() {
        return bombPass;
    }

    public void setBombPass(boolean bombPass) {
        this.bombPass = bombPass;
    }

    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }

    public boolean isFlamePass() {
        return flamePass;
    }

    public void setFlamePass(boolean flamePass) {
        this.flamePass = flamePass;
    }

    private boolean duplicateBomb(Bomb bomb) {
        for (Bomb b : this.bombs) {
            if (b.getX() == bomb.getX() && b.getY() == bomb.getY()) {
                return true;
            }
        }
        for (Brick b : EntityArr.bricks) {
            if (b.getX() == bomb.getX() && b.getY() == bomb.getY()) {
                return true;
            }
        }

        return false;
    }

    public void putBomb() {
        int xBomb, yBomb;
        if (getX() % Sprite.SCALED_SIZE > Sprite.SCALED_SIZE / 3) {
            xBomb = (getX() / Sprite.SCALED_SIZE)  + 1;
        } else {
            xBomb = (getX() / Sprite.SCALED_SIZE);
        }
        if (getY() % Sprite.SCALED_SIZE > Sprite.SCALED_SIZE / 3) {
            yBomb = (getY() / Sprite.SCALED_SIZE) + 1;
        } else {
            yBomb = (getY() / Sprite.SCALED_SIZE);
        }
        Bomb bomb = new Bomb(xBomb, yBomb, Sprite.bomb.getFxImage());

        if (!this.duplicateBomb(bomb)
                && getNumBombs() >= this.bombs.size() + 1) {
            Sound.play("BOM_SET");
            this.bombs.add(bomb);
        }
    }

    @Override
    public boolean checkBomb() {
        if (isBombPass()) return false;
        for (Bomb e : EntityArr.bomberman.bombs) {
            double diffX = this.getX() - e.getX();
            double diffY = this.getY() - e.getY();
            if (!(diffX > -32 && diffX < 32 && diffY > -32 && diffY < 32)) {
                e.allowedToPassThruBomber = false;
            }
            if (e.allowedToPassThruBomber) return false;
            if (this.intersects(e)) return true;
        }
        return false;
    }


    private void bomberDead() {
        setImg(Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3
                , this.animate, Sprite.DEFAULT_SIZE).getFxImage());
    }

    @Override
    public boolean checkBrick() {
        if (this.wallPass) return false;
        return super.checkBrick();
    }

    public boolean checkPortal() {
        for (Entity portal : EntityArr.portals) {
            if (EntityArr.enemies.size() != 0) break;
            if (this.intersects(portal)) {
                Sound.play("CRYST_UP");
                  return true;
            }
        }
        return false;
    }

    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, 2 * Sprite.SCALED_SIZE / 3, 4 * Sprite.SCALED_SIZE / 5);
    }
}
