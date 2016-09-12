import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.applet.*;

@SuppressWarnings("serial")
public class Game extends Applet implements MouseListener {
    
    private ArrayList<Piece> pieces;
    private ArrayList<Piece> deadPieces;
    private int currentPlayer;
    private Board gameBoard;
    private Player[] players;
    private int mouseX, mouseY;
    private Piece currentPiece;
    private boolean AIOn;
    private int victor;
        
    public void init(){
        currentPlayer = 1;
        pieces = new ArrayList<Piece>();
        deadPieces = new ArrayList<Piece>();
        gameBoard = new Board();
        players = new Player[2];
        players[0] = new Player( new Color( 0, 0, 204 ), 1 );
        players[1] = new Player( new Color( 255, 0, 127 ), 2 );
        addMouseListener(this);
        currentPiece = null;
        victor = 0;
        
        //Player 1
        pieces.add( new Piece( "king", players[0], gameBoard.getTile( 0, 3 ) ) );
        pieces.add( new Piece( "queen", players[0], gameBoard.getTile( 0, 4 ) ) );
        pieces.add( new Piece( "bishop", players[0], gameBoard.getTile( 0, 2 ) ) );
        pieces.add( new Piece( "bishop", players[0], gameBoard.getTile( 0, 5 ) ) );
        pieces.add( new Piece( "knight", players[0], gameBoard.getTile( 0, 1 ) ) );
        pieces.add( new Piece( "knight", players[0], gameBoard.getTile( 0, 6 ) ) );
        pieces.add( new Piece( "rook", players[0], gameBoard.getTile( 0, 0 ) ) );
        pieces.add( new Piece( "rook", players[0], gameBoard.getTile( 0, 7 ) ) );
        pieces.add( new Piece( "pawn", players[0], gameBoard.getTile( 1, 0 ) ) );
        pieces.add( new Piece( "pawn", players[0], gameBoard.getTile( 1, 1 ) ) );
        pieces.add( new Piece( "pawn", players[0], gameBoard.getTile( 1, 2 ) ) );
        pieces.add( new Piece( "pawn", players[0], gameBoard.getTile( 1, 3 ) ) );
        pieces.add( new Piece( "pawn", players[0], gameBoard.getTile( 1, 4 ) ) );
        pieces.add( new Piece( "pawn", players[0], gameBoard.getTile( 1, 5 ) ) );
        pieces.add( new Piece( "pawn", players[0], gameBoard.getTile( 1, 6 ) ) );
        pieces.add( new Piece( "pawn", players[0], gameBoard.getTile( 1, 7 ) ) );
        
        //Player 2
        pieces.add( new Piece( "king", players[1], gameBoard.getTile( 7, 3 ) ) );
        pieces.add( new Piece( "queen", players[1], gameBoard.getTile( 7, 4 ) ) );
        pieces.add( new Piece( "bishop", players[1], gameBoard.getTile( 7, 2 ) ) );
        pieces.add( new Piece( "bishop", players[1], gameBoard.getTile( 7, 5 ) ) );
        pieces.add( new Piece( "knight", players[1], gameBoard.getTile( 7, 1 ) ) );
        pieces.add( new Piece( "knight", players[1], gameBoard.getTile( 7, 6 ) ) );
        pieces.add( new Piece( "rook", players[1], gameBoard.getTile( 7, 0 ) ) );
        pieces.add( new Piece( "rook", players[1], gameBoard.getTile( 7, 7 ) ) );
        pieces.add( new Piece( "pawn", players[1], gameBoard.getTile( 6, 0 ) ) );
        pieces.add( new Piece( "pawn", players[1], gameBoard.getTile( 6, 1 ) ) );
        pieces.add( new Piece( "pawn", players[1], gameBoard.getTile( 6, 2 ) ) );
        pieces.add( new Piece( "pawn", players[1], gameBoard.getTile( 6, 3 ) ) );
        pieces.add( new Piece( "pawn", players[1], gameBoard.getTile( 6, 4 ) ) );
        pieces.add( new Piece( "pawn", players[1], gameBoard.getTile( 6, 5 ) ) );
        pieces.add( new Piece( "pawn", players[1], gameBoard.getTile( 6, 6 ) ) );
        pieces.add( new Piece( "pawn", players[1], gameBoard.getTile( 6, 7 ) ) );
    }
    
    public void paint( Graphics g ){
        drawStatus( g );
        paintScore( g );
        drawAIButton( g );
        gameBoard.drawBoard( g );
        for ( int i = 0; i < pieces.size(); i++ ){
            if ( pieces.get(i).isAlive() ){
                pieces.get( i ).drawPiece( g );
            }
        }
    }
    
    public ArrayList<Piece> getPieces(){
        return pieces;
    }
    
    private void react( int mouseX, int mouseY ){
        Tile selected = gameBoard.whereAmI( mouseX, mouseY );
        if ( mouseX > 1000 && mouseX < 1100 && mouseY > 100 && mouseY < 140 ) toggleAI();
        else if ( ( selected != null && selected.getPlayer() != null ) 
                && ( selected.getPlayer().getNumber() == currentPlayer ) ){
            gameBoard.noHighlights();
            currentPiece = selected.getPiece();
            ArrayList<Tile> highlights = selected.getPiece().moveChoices( gameBoard );
            for ( Tile a : highlights ) a.light();
        }
        else {
            if ( selected != null && selected.highlighted() ){
                currentPiece.move(selected);
                currentPiece = null;
                advanceTurn();
            }
            gameBoard.noHighlights();
            currentPiece = null;
        }
        
    }
    
    private void drawStatus( Graphics g ){
        g.setColor(Color.black);
        g.setFont( new Font("TimesRoman",Font.BOLD, 20 ) );
        if ( victor != 0 ){
            g.drawString( "Player " + victor + " wins!", 450, 50 );
                try {
                    Thread.sleep(5000);
                    System.exit(0);
                } catch ( InterruptedException e ) {
                    System.out.println( "Interrupted Exception" );
                }
        }
        g.drawString( "Player " + currentPlayer + "'s turn", 450, 50 );
    }
    
    private void drawAIButton( Graphics g ){
        g.setColor( Color.white );
        g.fillRect( 1000, 100, 100, 40 );
        g.setColor( Color.black );
        g.setFont( new Font("TimesRoman",Font.BOLD, 20 ) );
        g.drawRect( 1000, 100, 100, 40 );
        if ( AIOn ) g.drawString( "AI On", 1020, 125 );
        else g.drawString( "AI Off", 1020, 125 );
    }
    
    private void advanceTurn(){
        if ( currentPlayer == 1 ) currentPlayer++;
        else currentPlayer = 1;
        for ( int a = pieces.size() - 1; a >= 0; a-- ) if ( !pieces.get( a ).isAlive() )
            deadPieces.add( pieces.remove( a ) );
        victor = checkVictory();
    }
    
    private void toggleAI(){
        if ( AIOn ) AIOn = false;
        else AIOn = true;
    }
    
   private void AIMove(){
       Move aiMove = new Move( pieces, 1 );
       Move best = aiMove.getBestMove();
       gameBoard.getTile( best.getStart()[0], best.getStart()[1] ).getPiece()
           .move( gameBoard.getTile( best.getEnd()[0], best.getEnd()[1] ) );
       advanceTurn();
   }
    
    private void paintScore( Graphics g ){
        int player1 = 0, player2 = 0;
        for ( Piece a : deadPieces ){
            if ( a.getPlayer().getNumber() == 1 ) player2 += a.getValue();
            else player1 += a.getValue();
        }
        g.setColor(Color.black);
        g.setFont( new Font("TimesRoman",Font.BOLD, 24 ) );
        g.drawString( "Score", 950, 350 );
        g.setFont( new Font("TimesRoman", Font.PLAIN, 18 ) );
        g.drawString( "Player 1:    " + player1, 950, 380 );
        g.drawString( "Player 2:    " + player2, 950, 405 );
    }
    
    private int checkVictory(){
        boolean player1King = false, player2King = false;
        for ( Piece a : pieces ){
            if ( a.getType().equals("king") ){
                if ( a.getPlayer().getNumber() == 1 ) player1King = true;
                else player2King = true;
            }
        }
        if ( player1King && player2King ) return 0;
        if ( player1King ) return 1;
        return 2;
    }
    
    public void mouseClicked( MouseEvent e ){
        mouseX = e.getX();
        mouseY = e.getY();
        react( mouseX, mouseY );
        repaint();
        if ( currentPlayer == 2 && AIOn ) AIMove();
        repaint();
    }
    
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
