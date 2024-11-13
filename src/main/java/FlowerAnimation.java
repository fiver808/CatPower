import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class FlowerAnimation {

    private final Map<String, Image> flowerImages;
    private final Random random = new Random();
    private final Stage primaryStage;

    public FlowerAnimation(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.flowerImages = new HashMap<>();
    }

    public void preloadImage(String imagePath) {
        // Load image into memory
        flowerImages.put(imagePath, new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
    }

    public Scene createScene() {
        StackPane newStackPane = new StackPane();

        // Apply a solid dark green background
        newStackPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        // Add link image to the StackPane
        Image linkImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/suki.png")));
        ImageView linkImageView = new ImageView(linkImage);
        linkImageView.setTranslateY(320);
        newStackPane.getChildren().add(linkImageView);

        // Set the background image
        Image gardenImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/garden1.png")));
        ImageView backgroundImageView = new ImageView(gardenImage);
        backgroundImageView.setFitWidth(800); // Set to the width of the scene
        backgroundImageView.setPreserveRatio(true); // Maintain aspect ratio
        backgroundImageView.setSmooth(true);
        backgroundImageView.setCache(true);

        // Move the background image downwards by 40 pixels
        backgroundImageView.setTranslateY(40);

        // Add the background image to the StackPane
        newStackPane.getChildren().add(backgroundImageView);

        // Load and resize the daisies image
        Image daisiesImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/daisies.png")));
        ImageView daisiesImageView = new ImageView(daisiesImage);

        // Set X and Y position of daisies
        daisiesImageView.setTranslateX(-280);
        daisiesImageView.setTranslateY(150);

        // Add the daisies image to the StackPane
        newStackPane.getChildren().add(daisiesImageView);

        // Create buttons and add them to the bottom
        CustomButton quitButton = new CustomButton("Quit", Color.DODGERBLUE, Color.CADETBLUE, null, 0.0, () -> {
            primaryStage.close();
        });

        CustomButton meowButton = new CustomButton("Talk to Suki", Color.FUCHSIA, Color.PERU, "/purr.mp3", 1.0, () -> {

        });

        CustomButton goBackButton = new CustomButton("Visit Link", Color.GOLD, Color.YELLOWGREEN, null, 0.0, () -> {
            // Action for goBackButton
        });

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));
        buttonBox.getChildren().addAll(quitButton, meowButton, goBackButton);

        newStackPane.getChildren().add(buttonBox); // Add buttons to the StackPane

        // Adjust vertical position of buttonBox
        buttonBox.setTranslateY(205);

        Scene newScene = new Scene(newStackPane, 800, 600);

        // Set the title of the new scene
        primaryStage.setTitle("Suki's Flower Garden");

        // Create Timeline for duration between frames
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            addFlowerToScene(newStackPane, newScene);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        return newScene;
    }

    private void addFlowerToScene(StackPane stackPane, Scene scene) {
        if (flowerImages.isEmpty()) return;

        // Pick a random flower image
        String[] imagePaths = flowerImages.keySet().toArray(new String[0]);
        String randomImagePath = imagePaths[random.nextInt(imagePaths.length)];  // Randomly pick array index
        Image flowerImage = flowerImages.get(randomImagePath);

        // Create a new flower
        ImageView flower = new ImageView(flowerImage);

        // Set random width and maintain aspect ratio
        double minFlowerWidth = 30;
        double maxFlowerWidth = 70;
        double flowerWidth = minFlowerWidth + (Math.random() * (maxFlowerWidth - minFlowerWidth));
        double originalWidth = flowerImage.getWidth();
        double originalHeight = flowerImage.getHeight();
        double aspectRatio = originalWidth / originalHeight;
        double flowerHeight = flowerWidth / aspectRatio;

        flower.setFitWidth(flowerWidth); // Resizes flower image to fit within random flowerWidth
        flower.setFitHeight(flowerHeight);

        // Set random starting position for X and offset Y by setting translate
        double startX = Math.random() * (scene.getWidth() - flower.getFitWidth());  // Random start point in x direction
        double startY = -360; // Off-screen above

        flower.setTranslateX(startX - (scene.getWidth() / 2)); // Translate (move) to random X position
        flower.setTranslateY(startY); // Start above the screen

        // Add flower to the scene
        stackPane.getChildren().add(flower);

        // Random falling speeds
        double minAnimationSpeed = 3.0;
        double maxAnimationSpeed = 15.0;
        double speed = minAnimationSpeed + (random.nextDouble() * (maxAnimationSpeed - minAnimationSpeed));
        TranslateTransition transition = new TranslateTransition(Duration.seconds(speed), flower);
        transition.setFromY(startY);
        transition.setToY(scene.getHeight());
        transition.setCycleCount(1);
        transition.setOnFinished(event -> stackPane.getChildren().remove(flower));

        // Start the animation
        transition.play();
    }


    public void addFlowerImage(String path) {
        preloadImage(path);
    }
}