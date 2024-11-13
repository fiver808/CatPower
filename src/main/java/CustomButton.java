import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

public class CustomButton extends Button {

    private MediaPlayer mediaPlayer;

    public CustomButton(String text, Color baseColor, Color rolloverColor, String soundFilePath, double volume, Runnable action) {
        super(text);
        styleButton(this, baseColor, rolloverColor);
        dropShadow(this);

        if (soundFilePath != null && !soundFilePath.isEmpty()) {
            Media sound = new Media(getClass().getResource(soundFilePath).toExternalForm());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setVolume(volume); // Set the volume
        }

        setOnAction(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(javafx.util.Duration.ZERO); // Reset to the beginning
                mediaPlayer.play();
            }
            if (action != null) {
                action.run();
            }
        });
    }

    private void styleButton(Button button, Color baseColor, Color rolloverColor) {
        String baseStyle = "-fx-font-size: 16px; " +
                "-fx-padding: 10px; " +
                "-fx-border-width: 0px; " + // Remove border
                "-fx-background-radius: 5px; "; // Rounded corners

        // Apply Initial state
        button.setStyle(baseStyle +
                "-fx-background-color: " + toRgbString(baseColor) + "; " +
                "-fx-text-fill: #000000;"); // Black text color

        // Set rollover effect
        button.setOnMouseEntered(event -> button.setStyle(baseStyle +
                "-fx-background-color: " + toRgbString(rolloverColor) + "; " +
                "-fx-text-fill: #FFFFFF;")); // White text color on rollover

        // Set default style on mouse exit
        button.setOnMouseExited(event -> button.setStyle(baseStyle +
                "-fx-background-color: " + toRgbString(baseColor) + "; " +
                "-fx-text-fill: #000000;")); // Black text color

        // Set preferred min and max button size
        button.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        button.setPrefSize(200, 50); // Or any size you prefer
        button.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
    }

    private void dropShadow(javafx.scene.Node node) {
        Color shadowColor = new Color(0, 0, 0, 0.5); // 50% transparent black
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(shadowColor);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        node.setEffect(dropShadow);
    }

    private String toRgbString(Color color) {
        return String.format("#%02x%02x%02x", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }
}