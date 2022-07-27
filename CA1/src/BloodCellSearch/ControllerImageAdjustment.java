package BloodCellSearch;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ControllerImageAdjustment{
    public CheckBox posterizeGreyScale;
    private int updated = 0;
    public static int openWindow;
    private static int imageSize;

    public ImageView importedImage;
    private BufferedImage image;
    public static Image tempImage;

    public Slider imageSensitivity;
    public ImageView posterizedImage;


    static void setImage(javafx.scene.image.Image image, int imageSizeInput) {
        tempImage = image;
        imageSize = imageSizeInput;

    }


    public void updateImage(MouseEvent mouseEvent) {
        if(updated == 0) {
            image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
            image = SwingFXUtils.fromFXImage(tempImage,null);
            importedImage.setImage(tempImage);
            updated = 1;
        }
    }

    public void applyPosterize(ActionEvent actionEvent) {
        BufferedImage prePosterizedImage;
        prePosterizedImage = SwingFXUtils.fromFXImage(tempImage, null);

        int height = prePosterizedImage.getHeight();
        int width = prePosterizedImage.getWidth();
  //      System.out.println(imageSensitivity.getValue());
        for (int column = 0; column < height; column++) {
            for (int row = 0; row < width; row++) {
                Color color = new Color(prePosterizedImage.getRGB(row, column));
                Color newColor = new Color(255, 255, 255);
                int newRed = posterizeColor(color.getRed());
                int newBlue = posterizeColor(color.getBlue());
                int newGreen = posterizeColor(color.getGreen());
                if(color.getRed() > color.getBlue() && (color.getRed() - color.getBlue()) > imageSensitivity.getValue()) {
                    newColor = new Color(255, 0, 0);
                }else if(color.getRed() < color.getBlue() && (color.getBlue() - color.getRed()) > imageSensitivity.getValue()){
                    newColor = new Color(0, 0, 255);
                }else{
                    newColor = new Color(255,255,255);
                }

                int newColorInt = newColor.getRGB();
                prePosterizedImage.setRGB(row, column, newColorInt);
            }
            posterizedImage.setImage(SwingFXUtils.toFXImage(prePosterizedImage,null));
        }
    }

    public int posterizeColor(int colorVal) {
        return colorVal >= imageSensitivity.getValue() ? 255 : 0;
    }


    public void calculateBloodCells(ActionEvent actionEvent) throws IOException {
        Stage imageControl = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("CalculationMenu.fxml"));
        imageControl.setTitle("BloodCellSearcher");
        imageControl.setScene(new Scene(root, 1250, 750));
        imageControl.show();
        ControllerCalculation.openWindow = 0;
        ControllerCalculation.setImage(posterizedImage.getImage(),importedImage.getImage(),imageSize);
    }
}
