import java.awt.*;

public class Player{
    
    private Color myColor;
    private int playerNumber;
    
    public Player( Color color, int number ){
        myColor = color;
        playerNumber = number;
    }
    
    public Color getColor(){
        return myColor;
    }
    
    public int getNumber(){
        return playerNumber;
    }
}
