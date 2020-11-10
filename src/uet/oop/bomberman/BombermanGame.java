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
import uet.oop.bomberman.entities.enemy.Balloom;
import uet.oop.bomberman.entities.enemy.Oneal;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static int WIDTH = 20;
    public static int HEIGHT = 15;
    
    private GraphicsContext gc;
    private Canvas canvas;

    // Các đối tượng trong game
    Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> grasses = new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private List<Entity> bricks = new ArrayList<>();
    private List<Entity> portals = new ArrayList<>();
    private List<Balloom> balloms = new ArrayList<>();

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

                // Ballom di chuyeb
                for (Balloom ballom : balloms) {
                    if (!checkBounds(ballom)) {
                        ballom.update();
                        if (checkBounds(ballom)) {
                            ballom.setSpeed(ballom.getSpeed() * -1);
                            ballom.update();
                        }
                    }
                }

            }
        };
        timer.start();
        entities.add(bomberman);

        /**
         * Hanh dong cua bomber
         */
        scene.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("RIGHT")) {
                if (!checkBounds(bomberman)) {
                    bomberman.goRight();
                    if (checkBounds(bomberman)) {
                        bomberman.goLeft();
                    }
                }
                bomberman.setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1,
                        Sprite.player_right_2, bomberman.getX(), Sprite.DEFAULT_SIZE).getFxImage());
            } else if (event.getCode().toString().equals("LEFT")) {
                if (!checkBounds(bomberman)) {
                    bomberman.goLeft();
                    if (checkBounds(bomberman)) {
                        bomberman.goRight();
                    }
                }
                bomberman.setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1,
                        Sprite.player_left_2, bomberman.getX(), Sprite.DEFAULT_SIZE).getFxImage());
            } else if (event.getCode().toString().equals("UP")) {
                if (!checkBounds(bomberman)) {
                    bomberman.goUp();
                    if (checkBounds(bomberman)) {
                        bomberman.goDown();
                    }
                }
                bomberman.setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1,
                        Sprite.player_up_2, bomberman.getY(), Sprite.DEFAULT_SIZE).getFxImage());
            } else if (event.getCode().toString().equals("DOWN")) {
                if (!checkBounds(bomberman)) {
                    bomberman.goDown();
                    if (checkBounds(bomberman)) {
                        bomberman.goUp();
                    }
                }
                bomberman.setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1,
                        Sprite.player_down_2, bomberman.getY(), Sprite.DEFAULT_SIZE).getFxImage());
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
                    Entity object;
                    Balloom balloom;
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
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        bricks.add(object);
                    } else if (maps[j][i] == '1') {
                        balloom = new Balloom(i, j, Sprite.balloom_left1.getFxImage());
                        balloms.add(balloom);
                    } else if (maps[j][i] == '2') {
                        object = new Oneal(i, j, Sprite.oneal_right1.getFxImage());
                        entities.add(object);
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
    }
    // vẽ
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grasses.forEach(g -> g.render(gc));
        walls.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        portals.forEach(g -> g.render(gc));
        balloms.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

}
