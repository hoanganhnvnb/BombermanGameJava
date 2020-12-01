package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.CreateMap;
import uet.oop.bomberman.entities.EntityArr;
import uet.oop.bomberman.entities.blocks.Brick;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.Timer;
import java.util.TimerTask;

public class BombermanGame extends Application {
    
    public static int WIDTH = 20;
    public static int HEIGHT = 15;
    
    private GraphicsContext gc;
    private Canvas canvas;

    boolean up, down, left, right;

    public static boolean gameOver = false;

    public static int sumPoint = 0;

    public static final int TIME = 500;

    public static int level = 1;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        CreateMap.createMapByLevel(1);
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
//        Group root = new Group();

        VBox vBox = new VBox();
        vBox.setPrefSize(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 1));

        HBox hBox = new HBox();
        hBox.setPrefSize(WIDTH * Sprite.SCALED_SIZE,  Sprite.SCALED_SIZE);

        Label lv = new Label();
        lv.setPrefSize(WIDTH * Sprite.SCALED_SIZE / 3,  Sprite.SCALED_SIZE);
        lv.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-color: white" );

        Label time = new Label();
        time.setPrefSize(WIDTH * Sprite.SCALED_SIZE / 3,  Sprite.SCALED_SIZE);
        time.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-color: white" );

        Label point = new Label();
        point.setPrefSize(WIDTH * Sprite.SCALED_SIZE / 3,  Sprite.SCALED_SIZE);
        point.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-color: white;");

        hBox.getChildren().add(lv);
        hBox.getChildren().add(time);
        hBox.getChildren().add(point);

//        root.getChildren().add(hBox);
//        root.getChildren().add(canvas);

        vBox.getChildren().add(hBox);
        vBox.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(vBox);

        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("Bomberman");
        stage.show();



        long  startTime = System.currentTimeMillis();
        final int[] timeRun = {1};
        Sound.play("soundtrack");
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                point.setText("Point : " + sumPoint);
                time.setText("Time : " + (TIME - timeRun[0]));
                lv.setText("Level: " + level);
                if((System.currentTimeMillis() - startTime) / timeRun[0] >= 1000) {
                    timeRun[0]++;
                }
                if (up) {
                    EntityArr.bomberman.goUp();
                }
                else if (down) {
                    EntityArr.bomberman.goDown();
                }
                else if (left) {
                    EntityArr.bomberman.goLeft();
                }
                else if (right) {
                    EntityArr.bomberman.goRight();
                }
                render();
                update();
                if (timeRun[0] == TIME || !EntityArr.bomberman.isAlive()) {
                    this.stop();
                    stage.setScene(SceneGame.getSceneEndGame(level, sumPoint, TIME - timeRun[0]));
                    Sound.play("endgame3");
                }
            }
        };
        timer.start();


        /**
         * Hanh dong cua bomber
         */
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                    up = true;
                    break;
                case DOWN:
                    down = true;
                    break;
                case LEFT:
                    left = true;
                    break;
                case RIGHT:
                    right = true;
                    break;
            }
            if (e.getCode() == KeyCode.SPACE) {
                EntityArr.bomberman.putBomb();
            }

            if (e.getCode() == KeyCode.A) {
                EntityArr.enemies.clear();
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case UP:
                    up = false;
                    break;
                case DOWN:
                    down = false;
                    break;
                case LEFT:
                    left = false;
                    break;
                case RIGHT:
                    right = false;
                    break;
            }
        });
    }

    // update
    public void update() {
        EntityArr.bomberman.update();
        EntityArr.enemies.forEach(Enemy::update);
        EntityArr.bomberman.bombs.forEach(Bomb::update);
        EntityArr.bricks.forEach(Brick::update);
        // update flame
        EntityArr.bomberman.bombs.forEach(g -> g.getfUp().forEach(g1 -> g1.update()));
        EntityArr.bomberman.bombs.forEach(g -> g.getfDown().forEach(g1 -> g1.update()));
        EntityArr.bomberman.bombs.forEach(g -> g.getfLeft().forEach(g1 -> g1.update()));
        EntityArr.bomberman.bombs.forEach(g -> g.getfRight().forEach(g1 -> g1.update()));
        // Update item
        EntityArr.items.forEach(g -> {
            if (g.isVisible()) g.update();
        });
        EntityArr.portals.forEach(g -> g.update());
    }

    // vẽ
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        EntityArr.grasses.forEach(g -> g.render(gc));
        EntityArr.portals.forEach(g -> g.render(gc));
        EntityArr.items.forEach(g -> {
            if (g.isVisible()) g.render(gc);
        });
        EntityArr.walls.forEach(g -> g.render(gc));
        EntityArr.bricks.forEach(g -> g.render(gc));
        EntityArr.bomberman.bombs.forEach(g -> g.flames.forEach(g1 -> g1.render(gc)));
        EntityArr.bomberman.bombs.forEach(g -> g.render(gc));
        EntityArr.enemies.forEach(g -> {
            if (g.isVisible()) g.render(gc);
        });
        EntityArr.bomberman.render(gc);
    }

}
