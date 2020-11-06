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
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        createMap();
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

        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);

        scene.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("RIGHT")) {
                bomberman.goRight();
                bomberman.setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1,
                        Sprite.player_right_2, bomberman.getX(), Sprite.SCALED_SIZE).getFxImage());
            } else if (event.getCode().toString().equals("LEFT")) {
                bomberman.goLeft();
                bomberman.setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1,
                        Sprite.player_left_2, bomberman.getX(), Sprite.SCALED_SIZE).getFxImage());
            } else if (event.getCode().toString().equals("UP")) {
                bomberman.goUp();
                bomberman.setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1,
                        Sprite.player_up_2, bomberman.getY(), Sprite.SCALED_SIZE).getFxImage());
            } else if (event.getCode().toString().equals("DOWN")) {
                bomberman.goDown();
                bomberman.setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1,
                        Sprite.player_down_2, bomberman.getY(), Sprite.SCALED_SIZE).getFxImage());
            }
        });
    }

    public void createMap() {
        createMapByLevel(1);
    }

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
                    Entity entity;
                    if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1 || maps[j][i] == '#') {
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                    } else if (maps[j][i] == 'x') {
                        object = new Portal(i, j, Sprite.portal.getFxImage());
                    }
                    else {
                        object = new Grass(i, j, Sprite.grass.getFxImage());
                    }

                    if (maps[j][i] == 'x' || maps[j][i] == '*') {
                        entity = new Brick(i, j, Sprite.brick.getFxImage());
                        entities.add(entity);
                    } else if (maps[j][i] == '1') {
                        entity = new Balloom(i, j, Sprite.balloom_left1.getFxImage());
                        entities.add(entity);
                    } else if (maps[j][i] == '2') {
                        entity = new Oneal(i, j, Sprite.oneal_right1.getFxImage());
                        entities.add(entity);
                    }
                    stillObjects.add(object);
                }
            }
            fileReader.close();
            buffReader.close();
        } catch (Exception exception) {
            System.out.println("Error: " + exception);
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
