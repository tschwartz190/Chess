import java.util.ArrayList;

public class Move {
    
    private Board myBoard; //a board with the orientation that it is passed
    private ArrayList<Piece> pieces;
    private ArrayList<Move> nextMoves;
    private int steps;
    private Tile start, end;
    
    //myBoard is the orientation that is passed in, steps is how many moves to look ahead
    //steps must be odd
    public Move( ArrayList<Piece> pieces, int steps ){
        this.steps = steps;
        this.pieces = pieces;
        copyBoard( this.pieces );
        buildNextMoves(); 
        start = null;
        end = null;
    }
    
    public Move( ArrayList<Piece> pieces, int steps, Tile start, Tile end ){
        this.steps = steps;
        this.pieces = pieces;
        copyBoard( this.pieces );
        myBoard.getTile( start.getX(), start.getY() ).getPiece().move( myBoard.getTile( end.getX(), end.getY() ) );
        if ( steps > 0 ) buildNextMoves();
        this.start = start;
        this.end = end;
    }
    
    public Move getBestMove(){
        if ( steps == 0 ) return this;
        Move bestMove = nextMoves.get( 0 );
        double bestScore = -1040;
        for ( Move m : nextMoves ){
            double score = m.getMoveScore();
            if ( score > bestScore ){
                bestScore = score;
                bestMove = m;
            }
        }
        return bestMove;
    }
    
    public double getMoveScore(){
        if ( steps == 0 ) return generateScore();
        double sum = 0;
        for ( Move m : nextMoves ){
            sum += m.getMoveScore();
        }
        return sum / nextMoves.size();
    }
    
    public int[] getStart(){
        int[] temp = { start.getX(), start.getY() };
        return temp;
    }
    
    public int[] getEnd(){
        int[] temp = { end.getX(), end.getY() };
        return temp;
    }
        
    private void copyBoard ( ArrayList<Piece> pieces ){
        myBoard = new Board();
        for( Piece p : pieces ){
            myBoard.getTile( p.getTile().getX(), p.getTile().getY() ).addPiece( 
                    new Piece( p.getType(), p.getOwner(), myBoard.getTile( p.getTile().getX(), p.getTile().getY() ) ) );
        }
        
    }
    
    private void buildNextMoves(){
        nextMoves = new ArrayList<Move>();
        for ( int r = 0; r < 8; r++ ){
            for ( int c = 0; c < 8; c++ ){
                if ( ( myBoard.getTile( r,  c ).getPlayer() != null ) && 
                        ( myBoard.getTile( r, c ).getPlayer().getNumber() == steps % 2 + 1 ) ){
                    ArrayList<Tile> destinations = myBoard.getTile( r,  c ).getPiece().moveChoices( myBoard );
                    for ( Tile t : destinations ){
                        nextMoves.add( new Move( pieces, steps - 1, myBoard.getTile( r, c ), t ) );
                    }
                }
            }
        }
    }
    
    private int generateScore(){
        int score = 0;
        for ( int r = 0; r < 8; r++ ){
            for ( int c = 0; c < 8; c++ ){
                if ( myBoard.getTile( r,  c ).getPiece() != null ){
                    if ( myBoard.getTile( r, c ).getPiece().getPlayer().getNumber() == 2 )
                        score += myBoard.getTile( r,  c ).getPiece().getValue();
                    else score -= myBoard.getTile( r, c ).getPiece().getValue();
                }
            }
        }
        for ( Piece p : pieces ){
            if ( p.getOwner().getNumber() == 2 ){
                if ( p.getType().equals( "king" ) && isThreatened( p.getX(), p.getY() ) ) score -= Integer.MAX_VALUE / 2;
                else if ( isThreatened( p.getX(), p.getY() ) && !isGuarded( p.getX(), p.getY() ) ) score -= p.getValue();
            }
        }
        return score;
    }
    
    public boolean isThreatened( int r, int c ){
        for ( Piece p : pieces ){
            if ( p.getOwner().getNumber() == 1 ){
                if ( p.moveChoices( myBoard ).contains( myBoard.getTile( r, c ) ) ) return true;
            }
        }
        return false;
    }
    
    private boolean isGuarded( int r, int c ){
        for ( Piece p : pieces ){
            if ( p.getOwner().getNumber() == 2 ){
                if ( p.moveChoices( myBoard ).contains( myBoard.getTile( r, c ) ) ) return true;
            }
        }
        return false;
    }
}
