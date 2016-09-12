import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Piece extends Applet{

    private String type;
    private Player owner;
    private Tile location;
    private boolean alive;
    
    public Piece( String type, Player owner, Tile loc ){
        this.type = type;
        this.owner = owner;
        location = loc;
        loc.addPiece(this);
        alive = true;
    }
    
    public String getType(){
        return type;
    }
    
    public Player getOwner(){
        return owner;
    }
    
    public Tile getTile(){
        return this.location;
    }
    
    public void move(Tile tile){
        location.removePiece(false);
        location = tile;
        tile.removePiece(true);
        tile.addPiece(this);
    }
    
    public void removeTile(boolean kill){
        location = null;
        if (kill) alive = false;
    }
    
    public boolean isAlive(){
        return alive;
    }
    
    public int getValue(){
        if ( type.equals( "pawn" ) ) return 1;
        else if ( type.equals( "rook" ) ) return 5;
        else if ( type.equals( "queen" ) ) return 9;
        else if ( type.equals( "king" ) ) return 1000;
        else return 3;
    }
    
    public ArrayList<Tile> moveChoices(Board board){
        ArrayList<Tile> moves =  new ArrayList<Tile>();
        if ( type.equals( "rook" ) ){
            moves.addAll(straightMove( false, board ));
        }
        else if ( type.equals( "bishop" ) ){
            moves.addAll(diagonalMove( false, board ));
        }
        else if ( type.equals( "knight" ) ){
            moves.addAll(knightMove( board ));
        }
        else if ( type.equals( "queen" ) ){
            moves.addAll(straightMove( false, board ));
            moves.addAll(diagonalMove( false, board ));
        }
        else if ( type.equals( "king" ) ){
            moves.addAll(straightMove( true, board ));
            moves.addAll(diagonalMove( true, board ));
        }
        else {
            moves.addAll(pawnMove( board ));
        }
        return moves;
    }
    
    private ArrayList<Tile> diagonalMove( boolean king, Board board ){
        ArrayList<Tile> moves =  new ArrayList<Tile>();
        
        for ( int NE = 1; NE < 8; NE++ ){//Moves to the NE
            if ( Board.onBoard( location.getX() + NE, location.getY() + NE ) ){//If on the board
                if ( board.getTile( location.getX() + NE,  location.getY() + NE).getPlayer() == this.getOwner() ) break;//If tile owned by this player
                else if ( board.getTile( location.getX() + NE, location.getY() + NE ).getPlayer() != null ){//if tile owned by other player
                    moves.add( board.getTile( location.getX() + NE, location.getY() + NE ) );
                    break;
                }
                moves.add( board.getTile( location.getX() + NE, location.getY() + NE ) );
                if ( king ) break;
            }
        }
        
        for ( int NW = 1; NW < 8; NW++ ){//Moves to the NW
            if ( Board.onBoard(location.getX() - NW, location.getY() + NW ) ){//If on the board
                if ( board.getTile( location.getX() - NW,  location.getY() + NW).getPlayer() == this.getOwner() ) break;//If tile owned by this player
                else if ( board.getTile( location.getX() - NW, location.getY() + NW ).getPlayer() != null ){//if tile owned by other player
                    moves.add( board.getTile( location.getX() - NW, location.getY() + NW ) );
                    break;
                }
                moves.add( board.getTile( location.getX() - NW, location.getY() + NW ) );
                if ( king ) break;
            }
        }
        
        for ( int SE = 1; SE < 8; SE++ ){//Moves to the SE
            if ( Board.onBoard(location.getX() + SE, location.getY() - SE ) ){//If on the board
                if ( board.getTile( location.getX() + SE,  location.getY() - SE).getPlayer() == this.getOwner() ) break;//If tile owned by this player
                else if ( board.getTile( location.getX() + SE, location.getY() - SE ).getPlayer() != null ){//if tile owned by other player
                    moves.add( board.getTile( location.getX() + SE, location.getY() - SE ) );
                    break;
                }
                moves.add( board.getTile( location.getX() + SE, location.getY() - SE ) );
                if ( king ) break;
            }
        }
        
        for ( int SW = 1; SW < 8; SW++ ){//Moves to the SW
            if ( Board.onBoard(location.getX() - SW, location.getY() - SW ) ){//If on the board
                if ( board.getTile( location.getX() - SW,  location.getY() - SW).getPlayer() == this.getOwner() ) break;//If tile owned by this player
                else if ( board.getTile( location.getX() - SW, location.getY() - SW ).getPlayer() != null ){//if tile owned by other player
                    moves.add( board.getTile( location.getX() - SW, location.getY() - SW ) );
                    break;
                }
                moves.add( board.getTile( location.getX() - SW, location.getY() - SW ) );
                if ( king ) break;
            }
        }
        
        return moves;
    }
        
    private ArrayList<Tile> straightMove( boolean king, Board board ){
        ArrayList<Tile> moves =  new ArrayList<Tile>();
        
        for ( int xPlus = 1; xPlus < 8; xPlus++ ){//Moves to the right
            if ( Board.onBoard( location.getX() + xPlus, location.getY() ) ){
                if ( board.getTile( location.getX() + xPlus, location.getY() ).getPlayer() == this.getOwner() ) break;
                else if ( board.getTile( location.getX() + xPlus, location.getY() ).getPlayer() != null ){
                    moves.add( board.getTile( location.getX() + xPlus, location.getY() ) );
                    break;
                }
                moves.add( board.getTile( location.getX() + xPlus, location.getY() ) );
                if ( king ) break;
            }
        }
        
        for ( int yPlus = 1; yPlus < 8; yPlus++ ){//Moves up
            if ( Board.onBoard( location.getX(), location.getY() + yPlus ) ){
                if ( board.getTile( location.getX(), location.getY()+ yPlus ).getPlayer() == this.getOwner() ) break;
                else if ( board.getTile( location.getX(), location.getY() + yPlus ).getPlayer() != null ){
                    moves.add( board.getTile( location.getX(), location.getY()+ yPlus ) );
                    break;
                }
                moves.add( board.getTile( location.getX(), location.getY()+ yPlus ) );
                if ( king ) break;
            }
        }
        
        for ( int xMinus = 1; xMinus < 8; xMinus++ ){//Moves left
            if ( Board.onBoard( location.getX() - xMinus, location.getY() ) ){
                if ( board.getTile( location.getX() - xMinus, location.getY() ).getPlayer() == this.getOwner() ) break;
                else if ( board.getTile( location.getX() - xMinus, location.getY() ).getPlayer() != null ){
                    moves.add( board.getTile( location.getX() - xMinus, location.getY() ) );
                    break;
                }
                moves.add( board.getTile( location.getX() - xMinus, location.getY() ) );
                if ( king ) break;
            }
        }
        
        for ( int yMinus = 1; yMinus < 8; yMinus++ ){//Moves down
            if ( Board.onBoard( location.getX(), location.getY() - yMinus ) ){
                if ( board.getTile( location.getX(), location.getY() - yMinus ).getPlayer() == this.getOwner() ) break;
                else if ( board.getTile( location.getX(), location.getY() - yMinus ).getPlayer() != null ){
                    moves.add( board.getTile( location.getX(), location.getY() - yMinus ) );
                    break;
                }
                moves.add( board.getTile( location.getX(), location.getY() - yMinus ) );
                if ( king ) break;
            }
        }
        
        return moves;
    }
    
    private ArrayList<Tile> knightMove( Board board ){
        ArrayList<Tile> moves =  new ArrayList<Tile>();
                
        if ( Board.onBoard( location.getX() + 2, location.getY() + 1 ) && //1
                board.getTile( location.getX() + 2, location.getY() + 1 ).getPlayer() != this.getOwner() )
                moves.add( board.getTile( location.getX() + 2, location.getY() + 1 ) );
        if ( Board.onBoard( location.getX() + 2, location.getY() - 1 ) && //2
                board.getTile( location.getX() + 2, location.getY() - 1 ).getPlayer() != this.getOwner() )
                moves.add( board.getTile( location.getX() + 2, location.getY() - 1 ) );
        if ( Board.onBoard( location.getX() - 2, location.getY() + 1 ) && //3
                board.getTile( location.getX() - 2, location.getY() + 1 ).getPlayer() != this.getOwner() )
                moves.add( board.getTile( location.getX() - 2, location.getY() + 1 ) );
        if ( Board.onBoard( location.getX() - 2, location.getY() - 1 ) && //4
                board.getTile( location.getX() - 2, location.getY() - 1 ).getPlayer() != this.getOwner() )
                moves.add( board.getTile( location.getX() - 2, location.getY() - 1 ) );
        if ( Board.onBoard( location.getX() + 1, location.getY() + 2 ) && //5
                board.getTile( location.getX() + 1, location.getY() + 2 ).getPlayer() != this.getOwner() )
                moves.add( board.getTile( location.getX() + 1, location.getY() + 2 ) );
        if ( Board.onBoard( location.getX() + 1, location.getY() - 2 ) && //6
                board.getTile( location.getX() + 1, location.getY() - 2 ).getPlayer() != this.getOwner() )
                moves.add( board.getTile( location.getX() + 1, location.getY() - 2 ) );
        if ( Board.onBoard( location.getX() - 1, location.getY() + 2 ) && //7
                board.getTile( location.getX() - 1, location.getY() + 2 ).getPlayer() != this.getOwner() )
                moves.add( board.getTile( location.getX() - 1, location.getY() + 2 ) );
        if ( Board.onBoard( location.getX() - 1, location.getY() - 2 ) && //8
                board.getTile( location.getX() - 1, location.getY() - 2 ).getPlayer() != this.getOwner() )
                moves.add( board.getTile( location.getX() - 1, location.getY() - 2 ) );
        
        return moves;
    }
    
    private ArrayList<Tile> pawnMove( Board board ){
        ArrayList<Tile> moves =  new ArrayList<Tile>();
        int n;
        if ( this.owner.getNumber() == 1 ) n = 1;
        else n = -1;
        
        if ( Board.onBoard( location.getX() + n, location.getY() ) &&
                board.getTile( location.getX() + n, location.getY() ).getPlayer() == null ){
                moves.add( board.getTile( location.getX() + n, location.getY() ) );
                if ( ( n == 1 && location.getX() == 1 ) || ( n == -1 && location.getX() == 6 ) && 
                        ( Board.onBoard( location.getX() + 2 * n, location.getY() ) &&
                        board.getTile( location.getX() + 2*  n, location.getY() ).getPlayer() == null ) ){
                    moves.add( board.getTile( location.getX() + 2 * n, location.getY() ) );
                }
        }
        if ( Board.onBoard( location.getX() + n, location.getY() + 1 ) &&
                board.getTile( location.getX() + n, location.getY() + 1 ).getPlayer() != null &&
                board.getTile( location.getX() + n, location.getY() + 1 ).getPlayer() != this.getOwner() )
                moves.add( board.getTile( location.getX() + n, location.getY() + 1 ) );
        if ( Board.onBoard( location.getX() + n, location.getY() - 1 ) &&
                board.getTile( location.getX() + n, location.getY() - 1 ).getPlayer() != null &&
                board.getTile( location.getX() + n, location.getY() - 1 ).getPlayer() != this.getOwner() )
                moves.add( board.getTile( location.getX() + n, location.getY() - 1 ) );
        
        return moves;
    }

    public Player getPlayer(){
        return owner;
    }
    
    public void drawPiece( Graphics g ){
        
        if ( type.equals("pawn") ) drawPawn( g );
        else if ( type.equals( "rook" ) ) drawRook( g );
        else if ( type.equals( "knight" ) ) drawKnight( g );
        else if ( type.equals( "bishop" ) ) drawBishop( g );
        else if ( type.equals( "queen" ) ) drawQueen( g );
        else drawKing( g );
    }
    
    private void drawPawn( Graphics g ){
        int x = 100 + 100 * location.getX();
        int y = 100 + 100 * location.getY();
        g.setColor( this.getOwner().getColor() );
        int[] xPoints = { x + 50, x + 65, x + 35 };
        int[] yPoints = { y + 50, y + 70, y + 70 };
        g.fillPolygon( xPoints, yPoints, 3 );
        g.fillRect( x + 45, y + 40, 10, 20 );
        g.fillOval( x + 40, y + 30, 20, 20 );
    }
    
    private void drawRook( Graphics g ){
        int x = 100 + 100 * location.getX();
        int y = 100 + 100 * location.getY();
        g.setColor( this.getOwner().getColor() );
        g.fillRect(x + 30, y + 30, 40, 50);
        g.fillRect(x + 20, y + 80, 60, 5);
        g.fillRect(x + 30, y + 25, 4, 5);
        g.fillRect(x + 39, y + 25, 4, 5);
        g.fillRect(x + 48, y + 25, 5, 5);
        g.fillRect(x + 57, y + 25, 4, 5);
        g.fillRect(x + 66, y + 25, 4, 5);
        g.setColor( Color.yellow );
        g.fillRect(x + 46, y + 55, 8, 15);
    }
    
    private void drawKnight( Graphics g ){
        int x = 100 + 100 * location.getX();
        int y = 100 + 100 * location.getY();
        g.setColor( this.getOwner().getColor() );
        int[] earX = { x + 32, x + 45, x + 35 };
        int[] earY = { y + 21, y + 23, y + 41 };
        g.fillOval(x + 45, y + 29, 30, 18);
        g.fillOval(x + 40, y + 22, 20, 20);
        g.fillOval(x + 30, y + 30, 25, 25);
        g.fillRect(x + 30, y + 45, 20, 25);
        g.fillRect(x + 20, y + 70, 40, 5);
        g.fillPolygon( earX, earY, 3);
        g.setColor( Color.yellow );
        g.fillOval(x + 50, y + 30, 5, 2);        
    }
    
    private void drawBishop( Graphics g ){
        int x = 100 + 100 * location.getX();
        int y = 100 + 100 * location.getY();
        g.setColor( this.getOwner().getColor() );
        int[] xPoints1 = { x + 50, x + 65, x + 35 };
        int[] yPoints1 = { y + 10, y + 30, y + 30 };
        int[] xPoints2 = { x + 50, x + 70, x + 30 };
        int[] yPoints2 = { y + 60, y + 70, y + 70 };
        g.fillPolygon( xPoints1, yPoints1, 3 );
        g.fillPolygon( xPoints2,  yPoints2, 3 );
        g.fillOval( x + 35, y + 25, 30, 20 );
        g.fillRect( x + 45, y + 40, 10, 30 );
        g.fillRect( x + 20, y + 70, 60, 5 );
        g.setColor( Color.yellow );
        g.drawLine( x + 42, y + 20, x + 47, y + 30 );
    }
    
    private void drawQueen( Graphics g ){
        g.setColor( this.getOwner().getColor() );
        int x = 100 + 100 * location.getX();
        int y = 100 + 100 * location.getY();
        g.setColor( this.getOwner().getColor() );
        int[] crown1x = { x + 35, x + 43, x + 35 };
        int[] crown2x = { x + 42, x + 48, x + 42 };
        int[] crown3x = { x + 50, x + 53, x + 47 };
        int[] crown4x = { x + 58, x + 58, x + 52 };
        int[] crown5x = { x + 65, x + 65, x + 57 };
        int[] crown1y = { y + 15, y + 40, y + 40 };
        int[] crown2y = { y + 15, y + 35, y + 35 };
        int[] crown3y = { y + 15, y + 30, y + 30 };
        int[] crown4y = { y + 15, y + 35, y + 35 };
        int[] crown5y = { y + 15, y + 40, y + 40 };
        int[] xPoints = { x + 50, x + 30, x + 70 };
        int[] yPoints = { y + 60, y + 80, y + 80 };
        g.fillPolygon( crown1x, crown1y, 3 );
        g.fillPolygon( crown2x, crown2y, 3 );
        g.fillPolygon( crown3x, crown3y, 3 );
        g.fillPolygon( crown4x, crown4y, 3 );
        g.fillPolygon( crown5x, crown5y, 3 );
        g.fillPolygon( xPoints,  yPoints, 3 );
        g.fillOval( x + 35, y + 25, 30, 30 );
        g.fillRect( x + 40, y + 40, 20, 30 );
        g.fillRect( x + 30, y + 80, 40, 10 );
        g.fillRect( x + 32, y + 30, 36, 10);
    }
    
    private void drawKing( Graphics g ){
        g.setColor( this.getOwner().getColor() );
        int x = 100 + 100 * location.getX();
        int y = 100 + 100 * location.getY();
        g.setColor( this.getOwner().getColor() );
        int[] xPoints = { x + 50, x + 70, x + 30 };
        int[] yPoints = { y + 60, y + 80, y + 80 };
        g.fillPolygon( xPoints,  yPoints, 3 );
        g.fillOval( x + 35, y + 25, 30, 30 );
        g.fillRect( x + 40, y + 40, 20, 30 );
        g.fillRect( x + 30, y + 80, 40, 10 );
        g.fillRect( x + 47, y + 10, 6, 40 );
        g.fillRect( x + 40, y + 15, 20, 6 );
    }
    
    public String toString(){
        return type + " at " + location.getX() + ", " + location.getY();
    }
}