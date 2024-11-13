import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.util.Objects;

public class MainApp extends Application {
    private FlowerAnimation flowerAnimation;

    @Override
    public void start(Stage primaryStage) {

        // Initialize FlowerAnimation
        flowerAnimation = new FlowerAnimation(primaryStage);
        flowerAnimation.addFlowerImage("/flower1.png");
        flowerAnimation.addFlowerImage("/flower2.png");
        flowerAnimation.addFlowerImage("/flower3.png");
        flowerAnimation.addFlowerImage("/flower4.png");
        flowerAnimation.addFlowerImage("/flower5.png");

        // Cat image
        Image catImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cat.png")));
        ImageView imageView = new ImageView(catImage);
        dropShadow(imageView);

        // Create buttons using CustomButton class
        CustomButton meowButton = new CustomButton("Talk to Hermes", Color.FUCHSIA, Color.PERU, "/meow.mp3", 0.5, () -> {
            // Action for startButton (already handled in CustomButton class)
        });

        CustomButton quitButton = new CustomButton("Quit", Color.DODGERBLUE, Color.CADETBLUE, null, 0.0, primaryStage::close);

        CustomButton newSceneButton = new CustomButton("Visit Suki", Color.GOLD, Color.YELLOWGREEN, null, 0.0, () -> {
            primaryStage.setScene(flowerAnimation.createScene());
        });

        // Layout setup
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));
        buttonBox.getChildren().addAll(quitButton, meowButton, newSceneButton);

        // Vertical box
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(imageView, buttonBox);

        // Layout container for stacking children
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(vbox);
        stackPane.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        // Set scene
        Scene scene = new Scene(stackPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome to Cat Utopia");
        primaryStage.show();
    }

    private void dropShadow(javafx.scene.Node node) {
        Color shadowColor = new Color(0, 0, 0, 0.5); // 50% transparent black
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(shadowColor);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        node.setEffect(dropShadow);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


