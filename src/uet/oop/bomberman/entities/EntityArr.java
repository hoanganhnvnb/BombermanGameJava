package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.blocks.Brick;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.enemy.*;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityArr {
    public static List<Entity> grasses = new ArrayList<>();

    public static List<Entity> walls = new ArrayList<>();

    public static List<Brick> bricks = new ArrayList<>();

    public static List<Entity> portals = new ArrayList<>();

    public static List<Item> items = new ArrayList<>();

    public static List<Enemy> enemies = new ArrayList<>();

    public static Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());;

    public static void removeEnemy() {
        Iterator<Enemy> enemyIterator = enemies.listIterator();
        Enemy enemy;
        while (enemyIterator.hasNext()) {
            enemy = enemyIterator.next();
            if (!enemy.isAlive()) {
                if (enemy instanceof Pass) {
                    if (((Pass) enemy).shield == 1) {
                        ((Pass) enemy).shield = 0;
                        enemy.setAlive(true);
                    } else {
                        Sound.play("noooooo");
                        BombermanGame.sumPoint += enemy.getPoint();
                        enemyIterator.remove();
                    }
                } else {
                    Sound.play("noooooo");
                    BombermanGame.sumPoint += enemy.getPoint();
                    enemyIterator.remove();
                }
            }
        }
        System.out.println(BombermanGame.sumPoint);
    }

    public static void removeBrick() {
        Iterator<Brick> brickIterator = bricks.listIterator();
        while (brickIterator.hasNext()) {
            Brick brick = brickIterator.next();
            if (brick.isBroken()) {
                brickIterator.remove();
            }
        }
    }

    public static void removeBomb() {
        Iterator<Bomb> bombIterator = bomberman.bombs.listIterator();
        while (bombIterator.hasNext()) {
            Bomb bomb = bombIterator.next();
            if (bomb.isExploded()) {
                bombIterator.remove();
            }
        }
    }

    public static void clearArr() {
        grasses.clear();
        bricks.clear();
        walls.clear();
        items.clear();
        portals.clear();
        enemies.clear();
    }
}
