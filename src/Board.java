import java.applet.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Board extends Applet {

    private Tile[][] tiles;
        
    public Board(){
        tiles = new Tile[8][8];
        for ( int i = 0; i < 8; i++ ){
            for ( int j = 0; j < 8; j++ ){
                tiles[i][j] = new Tile( i, j );
            }
        }
    }
    
    public void drawBoard( Graphics g ){
        
        for ( int r = 0; r < 8; r++ ){
            for ( int c = 0; c < 8; c++ ){
                if ( ( r + c ) % 2 != 0 ) g.setColor( new Color( 204, 102, 0 ) );
                else g.setColor( Color.white );
                g.fillRect( 100 + r * 100, 100 + c * 100, 100, 100 );
                if ( tiles[r][c].highlighted() ){
                    if ( tiles[r][c].getPlayer() == null )g.setColor(Color.green);
                    else g.setColor(Color.orange);
                    g.drawLine( 100 + r * 100, 100 + c * 100, 200 + r * 100, 200 + c * 100 );
                    g.drawLine( 100 + r * 100, 200 + c * 100, 200 + r * 100, 100 + c * 100 );
                }
            }
        }
        
        g.setColor( new Color( 204, 102, 0 ) );
        g.drawRect( 100,  100,  800,  800 );
        
        for ( int i = 0; i < 8; i++ ){
            g.drawString( "" + ( 8 - i ), 70, 150 + i * 100 );
        }
        for ( int j = 0 ; j < 8; j++ ){
            String label = getLabel( j );
            g.drawString(label, 150 + j * 100, 920);
        }
        
    }
    
    public Tile getTile( int x, int y ){
        return tiles[x][y];
    }
    
    
    
    public static boolean onBoard( int x, int y ){
        return ( x < 8 && y < 8 && x >= 0 && y >= 0 );
    }
    
    public void noHighlights(){
        for ( int i = 0; i < tiles.length; i++ ){
            for ( int j = 0; j < tiles[0].length; j++ ){
                tiles[i][j].deLight();
            }
        }
    }
    
    public Tile whereAmI( int x, int y ){
        x -= 100;
        y -= 100;
        if ( x > 0 && x < 800 && y > 0 && y < 800 ){
            return this.getTile( x / 100, y / 100 );
        }
        return null;
    }
    
    private String getLabel( int num ){
        if ( num == 0 ) return "a";
        else if ( num == 1 ) return "b";
        else if ( num == 2 ) return "c";
        else if ( num == 3 ) return "d";
        else if ( num == 4 ) return "e";
        else if ( num == 5 ) return "f";
        else if ( num == 6 ) return "g";
        else return "h";
    }
    
}