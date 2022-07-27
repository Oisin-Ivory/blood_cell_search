package BloodCellSearch;

import BloodCellSearch.ControllerCalculationTestClass;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerCalculationTest {





    @Test
    void testCalculateBloodCells() throws IOException {
        BufferedImage imgFile = ImageIO.read(new File("D:\\Year 2\\Semester 2\\Data Structers & Algorithms 2\\CA1\\src\\testimage.jpg"));
        //11 cells, 9 regular 2 overlapping
        ControllerCalculationTestClass test = new ControllerCalculationTestClass();
//        Image img = SwingFXUtils.toFXImage(imgFile,null);
//        ControllerCalculation.setImage(img,img,32);
        test.setBlueSensitivityInt(2);
        test.setRedSensitivityInt(2); // min pixels per group ( noise filtering )
        test.CalculateBloodCells(null,test.applyPosterize(imgFile));



        assertEquals(11,test.getRedCellCount(),"Miss-counting red cells");
        assertEquals(4,test.getWhiteCellCount(),"Miss-counting white cells");
    }
}