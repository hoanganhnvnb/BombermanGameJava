package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.blocks.Brick;
import uet.oop.bomberman.entities.blocks.Grass;
import uet.oop.bomberman.entities.blocks.Portal;
import uet.oop.bomberman.entities.blocks.Wall;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.bomb.FlameV;
import uet.oop.bomberman.entities.enemy.Balloom;
import uet.oop.bomberman.entities.enemy.Oneal;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BombermanGame extends Application {
    
    public static int WIDTH = 20;
    public static int HEIGHT = 15;
    
    private GraphicsContext gc;
    private Canvas canvas;

    // Các đối tượng trong game
    Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> grasses = new ArrayList<>();
    private static List<Entity> walls = new ArrayList<>();
    public static List<Brick> bricks = new ArrayList<>();
    private static List<Entity> portals = new ArrayList<>();
    private static List<Balloom> balloms = new ArrayList<>();
    private static List<Oneal> oneals = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        createMapByLevel(1);
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();
        entities.add(bomberman);

        /**
         * Hanh dong cua bomber
         */
        scene.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("RIGHT")) {
                    bomberman.goRight();
            } else if (event.getCode().toString().equals("LEFT")) {
                    bomberman.goLeft();
            } else if (event.getCode().toString().equals("UP")) {
                    bomberman.goUp();
            } else if (event.getCode().toString().equals("DOWN")) {
                    bomberman.goDown();
            } else if (event.getCode().toString().equals("SPACE")) {
                Bomb bomb = new Bomb(bomberman.getX() / Sprite.SCALED_SIZE, bomberman.getY() / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage());
                bomberman.bombs.add(bomb);
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        bomb.setImg(Sprite.bomb_exploded.getFxImage());
                        bomb.setExploded(true);
                    }
                };
                TimerTask timerTask1 = new TimerTask() {
                    @Override
                    public void run() {
                        for (Brick b : bricks) {
                            if (b.isBroken()) bricks.remove(b);
                        }
                        bomberman.bombs.remove(bomb);
                        bomb.removeFlame();
                    }
                };
                Timer timerEx = new Timer();
                timerEx.schedule(timerTask, 2000);
                Timer timerRev = new Timer();
                timerRev.schedule(timerTask1, 3000L);
            }
        });
    }

    public boolean checkBounds(Entity entity) {
        for (Entity e : walls) {
            if (entity.intersects(e)) return true;
        }

        for (Entity e : bricks) {
            if (entity.intersects(e)) return true;
        }
        return false;
    }


    // Tạo Map
    public void createMapByLevel(int level) {
        try {
            String path = "res/levels/Level" + level + ".txt";
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String line = buffReader.readLine().trim();
            String[] str = line.split(" ");
            HEIGHT = Integer.parseInt(str[1]);
            WIDTH = Integer.parseInt(str[2]);
            char [][] maps = new char[HEIGHT][WIDTH];

            for (int i = 0; i < HEIGHT; ++i) {
                line = buffReader.readLine();
                for (int j = 0; j < WIDTH; ++j) {
                    maps[i][j] = line.charAt(j);
                }
            }

            for (int i = 0; i < WIDTH; ++i) {
                for (int j = 0 ; j < HEIGHT; ++j) {
                    Brick brick;
                    Entity object;
                    Balloom balloom;
                    Oneal oneal;
                    // create wall and grass
                    if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1 || maps[j][i] == '#') {
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                        walls.add(object);
                    } else {
                        object = new Grass(i, j, Sprite.grass.getFxImage());
                        grasses.add(object);
                    }
                    // create portal
                    if (maps[j][i] == 'x') {
                        object = new Portal(i, j, Sprite.portal.getFxImage());
                        grasses.add(object);
                    }
                    // create brick
                    if (maps[j][i] == 'x' || maps[j][i] == '*') {
                        brick = new Brick(i, j, Sprite.brick.getFxImage());
                        bricks.add(brick);
                    } else if (maps[j][i] == '1') {
                        balloom = new Balloom(i, j, Sprite.balloom_left1.getFxImage());
                        balloms.add(balloom);
                    } else if (maps[j][i] == '2') {
                        oneal = new Oneal(i, j, Sprite.oneal_right1.getFxImage());
                        oneals.add(oneal);
                    }
                }
            }
            fileReader.close();
            buffReader.close();
        } catch (Exception exception) {
            System.out.println("Error: " + exception);
        }
    }

    // update
    public void update() {
        entities.forEach(Entity::update);
        balloms.forEach(Balloom::update);
        oneals.forEach(Oneal::update);
        bomberman.bombs.forEach(Bomb::update);
    }
    // vẽ
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grasses.forEach(g -> g.render(gc));
        walls.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        portals.forEach(g -> g.render(gc));
        balloms.forEach(g -> g.render(gc));
        oneals.forEach(g -> g.render(gc));
        bomberman.bombs.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        Bomb.flames.forEach(g -> g.render(gc));
    }

    public static List<Entity> getWalls() {
        return walls;
    }

    public static List<Entity> getPortals() {
        return portals;
    }

    public static List<Balloom> getBalloms() {
        return balloms;
    }

    public static List<Oneal> getOneals() {
        return oneals;
    }

}
