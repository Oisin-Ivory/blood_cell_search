package BloodCellSearch;

public class Coord {
    int xPos;
    int yPos;

    public Coord(int xPos,int yPos){
        this.xPos=xPos;
        this.yPos=yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    @Override
    public String toString() {
        return " X Coord: "+xPos+" YCoord :" +yPos;
    }
}
