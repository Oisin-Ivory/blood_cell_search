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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static BloodCellSearch.DisjointSetNode.findHead;
import static BloodCellSearch.DisjointSetNode.union;

public class ControllerCalculation{
    public ImageView calculatedImage;
    public Slider calculationSensitivityRed,calculationSensitivityBlue;
    public Label redResult,blueResult,statusLabel;
    public CheckBox originalView;
    private int updated = 0;
    public static int openWindow;
    private static int imageSize;

    public ImageView importedImage;
    private BufferedImage image;
    private BufferedImage image2;
    public static Image tempImage;
    public static Image tempImage2;


    static void setImage(javafx.scene.image.Image image,javafx.scene.image.Image image2, int imageSizeInput) {
        tempImage = image;
        tempImage2 = image2;
        imageSize = imageSizeInput;

    }

    public void updateImage(MouseEvent mouseEvent) {
        if(updated == 0) {
            image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
            image = SwingFXUtils.fromFXImage(tempImage,null);
            calculatedImage.setImage(tempImage);
            updated = 1;
        }
    }

    public void CalculateBloodCells(ActionEvent actionEvent) {
        statusLabel.setText("Processing...");
        BufferedImage imageToBeEdited = SwingFXUtils.fromFXImage(tempImage,null);
        BufferedImage originalImageToBeEdited = SwingFXUtils.fromFXImage(tempImage2,null);
        int height = imageToBeEdited.getHeight();
        int width = imageToBeEdited.getWidth();

        //Arrays for White and Red Cells
        DisjointSetNode[] nodeArray = new DisjointSetNode[width * height];
        DisjointSetNode[] nodeArrayBlue = new DisjointSetNode[width * height];

        //truncate through pixels
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (imageToBeEdited.getRGB(column,row) != -1) { // make sure color isn't white (white = -1)
                    if (imageToBeEdited.getRGB(column,row) == -65536) { // If color is red (red = -65536)
                        // node position int he array is given by [(row) * width + column]
                        //If the slot in the array doesn't have a node, create one.
                        if (nodeArray[(row) * width + column] == null) {
                            DisjointSetNode<Coord> newNode = new DisjointSetNode<>(new Coord(column, row));
                            nodeArray[(row) * width + column] = newNode;
                        }

                        if (column + 1 < width && nodeArray[(row) * width + column + 1] != null && imageToBeEdited.getRGB(column + 1, row) == -65536) {
                            nodeArray[(row) * width + column].setParent(nodeArray[(row) * width + column+1]);
                        }
                        // if the right node isn't created yet, create it and set the parent as the starting cell.

                        if (column + 1 < width && nodeArray[(row) * width + column + 1] == null && imageToBeEdited.getRGB(column + 1, row) == -65536) {
                            DisjointSetNode<Coord> newNode = new DisjointSetNode<>(new Coord(column + 1, row));
                            newNode.setParent(nodeArray[(row) * width + column]);
                            nodeArray[(row) * width + (column + 1)] = newNode;
                        }
                        // below node node isn't created yet, create it and set the parent as the starting cell.
                        if (row + 1 < height && nodeArray[(row + 1) * width + column] == null && imageToBeEdited.getRGB(column, row + 1) == -65536) {
                            DisjointSetNode<Coord> newNode = new DisjointSetNode<>(new Coord(column, row + 1));
                            newNode.setParent(nodeArray[(row) * width + column]);
                            nodeArray[(row + 1) * width + (column)] = newNode;
                        }
                        //Diag node node isn't created yet, create it and set the parent as the starting cell.

                        if (row + 1 < height && column + 1 < width && nodeArray[(row + 1) * width + column + 1] == null && imageToBeEdited.getRGB(column + 1, row + 1) == -65536) {
                            DisjointSetNode<Coord> newNode = new DisjointSetNode<>(new Coord(column + 1, row + 1));
                            newNode.setParent(nodeArray[(row) * width + column]);
                            nodeArray[(row + 1) * width + (column + 1)] = newNode;
                        }


//                        if(!(row-1 < 0) && !(column-1 <0)) {
//
//
//                            if (nodeArray[(row - 1) * width + column] != null && nodeArray[row * width + column].getParent() == null && imageToBeEdited.getRGB(column, row) == -65536) {
//                                union(findHead(nodeArray[row * width + column]), findHead(nodeArray[(row + 1) * width + column]));
//
//                            }
//                        }
                           }// else if (nodeArray[(row-1)*width + column-1] != null && nodeArray[row * width + column].getParent() == null) {
//                                union( nodeArray[row * width + column],nodeArray[(row - 1) * width + column - 1]);
//                                System.out.print("BEEP1");
//                            } else if (nodeArray[(row - 1) * width + column] != null && nodeArray[row * width + column].getParent() == null) {
//                                union( nodeArray[row * width + column],nodeArray[(row - 1) * width + column]);
//                                System.out.print("BEEP2");
//                            }
//                        }

                    }
                // If color is blue (blue = -16776961)
                    if (imageToBeEdited.getRGB(column,row) == -16776961) {
                        //Same as red
                        if (nodeArrayBlue[(row) * width + column] == null) {
                            DisjointSetNode<Coord> newNode = new DisjointSetNode<>(new Coord(column, row));
                            nodeArrayBlue[(row) * width + column] = newNode;

                        }

                        if (column + 1 < width && nodeArrayBlue[(row) * width + column + 1] != null && imageToBeEdited.getRGB(column + 1, row) == -16776961) {
                            nodeArrayBlue[(row) * width + column].setParent(nodeArrayBlue[(row) * width + column+1]);
                        }

//                        if (column + 1 < width && nodeArrayBlue[(row) * width + column + 1] != null && imageToBeEdited.getRGB(column + 1, row) == -16776961) {
//                            nodeArrayBlue[(row) * width + column].setParent(nodeArrayBlue[(row) * width + column+1]);
//                        }else if (column + 1 < width && nodeArrayBlue[(row - 1) * width + (column)] != null && imageToBeEdited.getRGB(column + 1, row-1) == -16776961) {
//                            nodeArrayBlue[(row) * width + column].setParent(nodeArrayBlue[(row - 1) * width + (column)]);
//                        }/* else if (column - 1 < width && nodeArrayBlue[(row) * width + column - 1] != null && imageToBeEdited.getRGB(column - 1, row) == -16776961) {
//                            nodeArrayBlue[(row) * width + column].setParent(nodeArrayBlue[(row) * width + column-1]);
//                        }*/
                        // right node

                        if (column + 1 < width && nodeArrayBlue[(row) * width + column + 1] == null && imageToBeEdited.getRGB(column + 1, row) == -16776961) {
                            DisjointSetNode<Coord> newNode = new DisjointSetNode<>(new Coord(column + 1, row));
                            newNode.setParent(nodeArrayBlue[(row) * width + column]);
                            nodeArrayBlue[(row) * width + (column + 1)] = newNode;
                        }
                        // below node
                        if (row + 1 < height && nodeArrayBlue[(row + 1) * width + column + 1] == null && imageToBeEdited.getRGB(column, row + 1) == -16776961) {
                            DisjointSetNode<Coord> newNode = new DisjointSetNode<>(new Coord(column, row + 1));
                            newNode.setParent(nodeArrayBlue[(row) * width + column]);
                            nodeArrayBlue[(row + 1) * width + column + 1] = newNode;
                        }
                        //Diag node

                        if (row + 1 < height && column + 1 < width && nodeArrayBlue[(row + 1) * width + column + 1] == null && imageToBeEdited.getRGB(column + 1, row + 1) == -16776961) {
                            DisjointSetNode<Coord> newNode = new DisjointSetNode<>(new Coord(column + 1, row + 1));
                            newNode.setParent(nodeArrayBlue[(row) * width + column]);
                            nodeArrayBlue[(row + 1) * width + (column + 1)] = newNode;
                        }
                    }
                }




//                    if(column - 1 > 0 && imageToBeEdited.getRGB(row, column - 1) != -1) {
//                        newNode = new DisjointSetNode<>(new Coord(row, column));
//                        nodeArray.add(newNode);
//
//                    if(row+1<width && column + 1 < height) {
//                        if (imageToBeEdited.getRGB(row + 1, column) != -1) {
//                            DisjointSetNode<Coord> newNodeRight = new DisjointSetNode<>(new Coord(row + 1, column));
//                            newNodeRight.setParent(newNode);
//                            nodeArray.add(newNodeRight);
//                        }
//
//                        if (imageToBeEdited.getRGB(row + 1, column + 1) != -1) {
//                            DisjointSetNode<Coord> newNodeBottom = new DisjointSetNode<>(new Coord(row, column + 1));
//                            newNodeBottom.setParent(newNode);
//                            nodeArray.add(newNodeBottom);
//                        }
//
//                        if (imageToBeEdited.getRGB(row, column + 1) != -1) {
//                            DisjointSetNode<Coord> newNodeDiag = new DisjointSetNode<>(new Coord(row + 1, column + 1));
//                            newNodeDiag.setParent(newNode);
//                            nodeArray.add(newNodeDiag);
//                        }
//                    }
                //for each of the nodes around it that are black, create nodes and set parent to node.
            }
        // goes back through the image, finding adjacent nodes that may have not linked yet due to recursive looping, then union nodes that are beside each other
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (imageToBeEdited.getRGB(column, row) == -65536) {
                    if (column + 2 < width && nodeArray[(row) * width + column + 2] != null && imageToBeEdited.getRGB(column + 2, row) == -65536) {
                        if(findHead(nodeArray[(row) * width + column + 2]) != findHead(nodeArray[(row) * width + column])){
                            findHead(nodeArray[(row) * width + column + 2]).setParent(findHead(nodeArray[(row) * width + column]));
                        }
                    }

                    if (row + 1 < height && nodeArray[(row+1) * width + column] != null && imageToBeEdited.getRGB(column, row+1) == -65536) {
                        if(findHead(nodeArray[(row+1) * width + column]) != findHead(nodeArray[(row) * width + column])){
                            findHead(nodeArray[(row+1) * width + column]).setParent(findHead(nodeArray[(row) * width + column]));
                        }
                    }
                }
                if (imageToBeEdited.getRGB(column, row) == -16776961) {
                    if (column + 2 < width && nodeArrayBlue[(row) * width + column + 2] != null && imageToBeEdited.getRGB(column + 2, row) == -16776961) {
                        if(findHead(nodeArrayBlue[(row) * width + column + 2]) != findHead(nodeArrayBlue[(row) * width + column])){
                            findHead(nodeArrayBlue[(row) * width + column + 2]).setParent(findHead(nodeArrayBlue[(row) * width + column]));
                        }
                    }
                    if (row + 2 < height && nodeArrayBlue[(row+2) * width + column] != null && imageToBeEdited.getRGB(column, row+2) == -16776961) {
                        if(findHead(nodeArrayBlue[(row+2) * width + column]) != findHead(nodeArrayBlue[(row) * width + column])){
                            findHead(nodeArrayBlue[(row+2) * width + column]).setParent(findHead(nodeArrayBlue[(row) * width + column]));
                        }
                    }
                }
            }
        }
















        int cellcount = 0;
        int totalCellSizeRed = 0;
        int LRed = width;
        int RRed = 0;
        int BRed = 0;
        int TRed = height;
        for (DisjointSetNode disjointSetNode : nodeArray) { // goes through nodes
            int size = 0; // size of a cell in pixels
            if(disjointSetNode!=null) {
                if(disjointSetNode.getParent()==null){ // if it is a head node
                    for (DisjointSetNode disjointSetNode2 : nodeArray){ // go through nodes again
                        if(disjointSetNode2!=null) {    //make sure slot has node
                            if (findHead(disjointSetNode2) == disjointSetNode) {// if the node belongs to the head group
                                size++; // add size up
                                Coord Node = (Coord)disjointSetNode2.getData(); // get the info out of node
                                if(Node.getxPos()<LRed){
                                    LRed = Node.getxPos();
                                }
                                if(Node.getxPos()>RRed){ // compare all min-max values for x y
                                    RRed = Node.getxPos();
                                }
                                if(Node.getyPos()>BRed){
                                    BRed = Node.getyPos();
                                }
                                if(Node.getyPos()<TRed){
                                    TRed = Node.getyPos();
                                }
                            }
                        }
                    }
                    int averageCellsize;
                    if(size > calculationSensitivityRed.getValue()){ // noise filtering, nodes must be above a certain size
                        cellcount++; // if it is seen as a cell add to cell count
                        totalCellSizeRed=totalCellSizeRed+size;
                        averageCellsize = totalCellSizeRed/cellcount; // get average size of cells
                        Color color;
                        if(size > averageCellsize * 2 + (averageCellsize * 0.46)){ // if cell is 2.46 times larger than the average it is counted as 3 cells
                            cellcount++;
                            cellcount++;
                            color = new Color(176, 255, 0);
                        }
                        else
                        if(size > averageCellsize + averageCellsize*0.6) { // if cell is 1.6 times larger than the average it is counted as 2 cells
                            color = new Color(0, 177, 76);
                            cellcount++;
                        }else{
                            color = new Color(0, 0, 255); // else it is a single cell
                        }

//                        Label label = new Label();
//                        label.setText(""+cellcount);
//                        label.setTranslateX(LRed);
//                        label.resize(50,50);
//                        label.setTranslateY(TRed);
                        // choose between posterized view and regular
                        if(originalView.isSelected()){
                            for(int i = 0; i < RRed-LRed;i++){

                                originalImageToBeEdited.setRGB(LRed+(i),TRed,color.getRGB());
                                originalImageToBeEdited.setRGB(LRed+(i),BRed,color.getRGB());
                            }
                            for(int i = 0; i <= BRed-TRed;i++){

                                originalImageToBeEdited.setRGB(LRed,TRed+i,color.getRGB());
                                originalImageToBeEdited.setRGB(RRed,TRed+i,color.getRGB());
                            }
                        }else {

                            for (int i = 0; i < RRed - LRed; i++) {

                                imageToBeEdited.setRGB(LRed + (i), TRed, color.getRGB());
                                imageToBeEdited.setRGB(LRed + (i), BRed, color.getRGB());
                            }
                            for (int i = 0; i <= BRed - TRed; i++) {

                                imageToBeEdited.setRGB(LRed, TRed + i, color.getRGB());
                                imageToBeEdited.setRGB(RRed, TRed + i, color.getRGB());
                            }
                        }
//406+25
                    }
                    LRed = width;
                    RRed = 0;
                    BRed = 0;
                    TRed = height;
                }
            }
        }

        int cellcountblue = 0;
        int LBlue = width;
        int RBlue = 0;
        int BBlue = 0;
        int TBlue = height;

    //    int bottom /// sort out the boxes for( farRBlue-farlblue
        //Same as red except for blue cells
        int totalCellSize = 0;
        for (DisjointSetNode disjointSetNode : nodeArrayBlue) {
            int size = 0;

            if(disjointSetNode!=null) {
                if(disjointSetNode.getParent()==null){
                    for (DisjointSetNode disjointSetNode2 : nodeArrayBlue){

                        if(disjointSetNode2!=null) {
                            if (findHead(disjointSetNode2) == disjointSetNode) {
                                Coord Node = (Coord)disjointSetNode2.getData();
                                if(Node.getxPos()<LBlue){
                                    LBlue = Node.getxPos();
                                }
                                if(Node.getxPos()>RBlue){
                                    RBlue = Node.getxPos();
                                }
                                if(Node.getyPos()>BBlue){
                                    BBlue = Node.getyPos();
                                }
                                if(Node.getyPos()<TBlue){
                                    TBlue = Node.getyPos();
                                }
                                size++;
                            }
                        }

                    }
                }

                int averageCellsize;
                    if(size > calculationSensitivityBlue.getValue()){
                        cellcountblue++;
                        totalCellSize=totalCellSize+size;
                        averageCellsize = totalCellSize/cellcount;
                        Color color;
                        if(size > averageCellsize + averageCellsize*0.5) {
                             color = new Color(255, 127, 0);
                        }else{
                             color = new Color(255, 0, 0);
                        }
                        if(originalView.isSelected()){
                            for(int i = 0; i < RBlue-LBlue;i++){
                                originalImageToBeEdited.setRGB(LBlue+(i),TBlue,color.getRGB());
                                originalImageToBeEdited.setRGB(LBlue+(i),BBlue,color.getRGB());
                            }
                            for(int i = 0; i <= BBlue-TBlue;i++){
                                originalImageToBeEdited.setRGB(LBlue,TBlue+i,color.getRGB());
                                originalImageToBeEdited.setRGB(RBlue,TBlue+i,color.getRGB());
                            }
                        }else {
                            for (int i = 0; i < RBlue - LBlue; i++) {
                                imageToBeEdited.setRGB(LBlue + (i), TBlue, color.getRGB());
                                imageToBeEdited.setRGB(LBlue + (i), BBlue, color.getRGB());
                            }
                            for (int i = 0; i <= BBlue - TBlue; i++) {
                                imageToBeEdited.setRGB(LBlue, TBlue + i, color.getRGB());
                                imageToBeEdited.setRGB(RBlue, TBlue + i, color.getRGB());
                            }
                        }

                    }
                LBlue = width;
                RBlue = 0;
                BBlue = 0;
                TBlue = height;
                }

            }
        // update results.
        redResult.setText(" "+cellcount);
        blueResult.setText(" "+cellcountblue);
        statusLabel.setText("Complete!");
        //System.out.println("Red: "+cellcount+"\nBlue: "+cellcountblue);
        if(originalView.isSelected()) {
            calculatedImage.setImage(SwingFXUtils.toFXImage(originalImageToBeEdited, null));
        }else {
            calculatedImage.setImage(SwingFXUtils.toFXImage(imageToBeEdited, null));
        }
    }
        //for (DisjointSetNode<Coord> integerDisjointSetNode : nodeArray) {
     //       System.out.println(integerDisjointSetNode.data.toString()+" (connected to: "+(integerDisjointSetNode.parent==null ? "null" : integerDisjointSetNode.parent.data)+")");
}


