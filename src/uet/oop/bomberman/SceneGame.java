package uet.oop.bomberman;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uet.oop.bomberman.graphics.Sprite;


public class SceneGame {
    public static Scene getSceneNextLevel(int level) {
        Label label = new Label();
        label.setText("LEVEL " + level);



        label.setPrefSize(BombermanGame.WIDTH, BombermanGame.HEIGHT);
        Scene scene = new Scene(label);
        return scene;

    }

    public static Scene getSceneEndGame(int level, int point, int time) {
        VBox vBox = new VBox();
        vBox.setPrefSize(Sprite.SCALED_SIZE * BombermanGame.WIDTH / 2, BombermanGame.HEIGHT * Sprite.SCALED_SIZE);

        Label gameOver = new Label("GAME OVER");
        Label lv = new Label("LEVEL : " + level);
        Label p = new Label("POINT : " + point);
        Label t = new Label("TIME :" + time);

        gameOver.setPrefSize(Sprite.SCALED_SIZE * BombermanGame.WIDTH / 2, BombermanGame.HEIGHT * Sprite.SCALED_SIZE / 4);
        gameOver.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 48px; -fx-font-weight: bold; -fx-border-color: white;");

        lv.setPrefSize(Sprite.SCALED_SIZE * BombermanGame.WIDTH / 2, BombermanGame.HEIGHT * Sprite.SCALED_SIZE / 4);
        lv.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold; -fx-border-color: white;");

        p.setPrefSize(Sprite.SCALED_SIZE * BombermanGame.WIDTH / 2, BombermanGame.HEIGHT * Sprite.SCALED_SIZE / 4);
        p.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold; -fx-border-color: white;");

        t.setPrefSize(Sprite.SCALED_SIZE * BombermanGame.WIDTH / 2, BombermanGame.HEIGHT * Sprite.SCALED_SIZE / 4);
        t.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold; -fx-border-color: white;");

        vBox.getChildren().addAll(gameOver, lv, p, t);
        Scene scene = new Scene(vBox);
        return scene;
    }
}
