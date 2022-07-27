package BloodCellSearch;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


    public class Controller {
        private Window stage;
        private int imageSize = 16;

    public void submitImage(ActionEvent actionEvent) throws IOException {
        FileChooser input_file = new FileChooser();
        input_file.setTitle("Choose an Image");
        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        image = ImageIO.read(input_file.showOpenDialog(stage));
        BufferedImage imageToBeEdited = image;
        javafx.scene.image.Image imageToBeExported = SwingFXUtils.toFXImage(image, null);

        Stage imageControl = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("imageControlMenu.fxml"));
        imageControl.setTitle("BloodCellSearcher");
        imageControl.setScene(new Scene(root, 1250, 750));
        imageControl.show();
        ControllerImageAdjustment.openWindow = 0;
        ControllerImageAdjustment.setImage(imageToBeExported,imageSize);
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(1);
    }
}
