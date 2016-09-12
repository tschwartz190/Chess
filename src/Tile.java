public class Tile {

    private int x, y;
    private Piece contains;
    private boolean lighted;
    
    public Tile( int x, int y ){
        this.x = x;
        this.y = y;
        contains = null;
        lighted = false;
    }
    
    public void light(){
        lighted = true;
    }
    
    public void deLight(){
        lighted = false;
    }
    
    public boolean highlighted(){
        return lighted;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public Piece getPiece(){
        return contains;
    }
    
    public Player getPlayer(){
        if ( contains != null )
            return contains.getPlayer();
        return null;
    }

    public void addPiece(Piece piece) {
        contains = piece;
    }
    
    public void removePiece(boolean kill){
        if (contains != null ){
            contains.removeTile(kill);
            contains = null;
        }
    }
    
}
