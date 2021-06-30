
public class Player {
    private double positionX;
    private double positionY;
    private int energyLevel;

    public Player(double X, double Y, int e){
        this.positionX = X;
        this.positionY = Y;
        this.energyLevel = e;

    }

    public double getX() {
        return positionX;
    }

    public double getY(){
        return positionY;
    }

    public int getE(){
        return energyLevel;
    }

    public void setX(double x){
        positionX = x;
    }

    public void setY(double y){
        positionY = y;
    }

    public void setE(int e){
        energyLevel = e;
    }


}

