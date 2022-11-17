import java.util.Random;
import java.util.Vector;

/* 
 * Integer Linear Programming
 * CSC 414 Midterm Project
 * Tyler Bagwill, Nathan Bonetto, Kaeleb Brandin
*/

public class IntegerLinearProgramming {

    public static final Integer n = 4;

    public static Vector < Vector < Double > > x = new Vector < Vector < Double > > ();

    public static void main(String[] args) throws Exception {
        
        // Given b = ax + s, where a = n*n matrix and b, x, s are n x 1 matrices

        /* STEP 1: Create system of equations using ax cross product ( Ex: )
        **
        ** | a00 * x0 , a10 * x1 , a20 * x2 |
        ** | a01 * x0 , a11 * x1 , a21 * x2 |
        ** | a02 * x0 , a12 * x1 , a22 * x2 |
        */

        Vector < Vector < Double > > b = new Vector < Vector < Double > > ();
        Vector < Vector < Double > > a = new Vector < Vector < Double > > ();
        Vector < Vector < Double > > s = new Vector < Vector < Double > > ();

        // Populate Matrices ( System of Equations )
        pop(a, n, n);
        pop(b, 1, n);
        pop(s, 1, n);

        // check matrices
        printMatrix(a, "a");
        printMatrix(b, "b");
        printMatrix(s, "s");

        /* STEP 2: b = ax + s -> (b - s) = Ax 
        **
        */

        b = subtractMatrix(b, s);

        /* STEP 3: A^-1 = ( 1 / det(A) ) * adj(a)
        ** 
        */

        Vector < Vector < Double > > inv = new Vector < Vector < Double > > ();

        inv = inverseMatrix( a );

        /* STEP 4: (b - s) = Ax -> (b - s)A^-1 = x
        ** 
        */

        // larger matrix first
        x = multMatrix( inv, b );

        printMatrix(x, "x");
    }

    public static Vector < Vector < Double > > multMatrix( Vector < Vector < Double > > a, Vector < Vector < Double > > b ){
        if( a.size() != b.elementAt(0).size() ){
            throw new IllegalArgumentException( "Invalid Matrices Dimensions." );
        }
        
        Vector < Vector < Double > > res = new Vector < Vector < Double > > ();
        double sum = 0.0;

        for( int i = 0; i < b.size(); i++ ){
            res.add( new Vector < Double > () );
            for( int j = 0; j < a.elementAt(i).size(); j++ ){
                for( int k = 0; k < b.elementAt(i).size(); k++ ){
                    sum += a.elementAt(k).elementAt(j) * b.elementAt(i).elementAt(k);
                }
                res.elementAt(i).add( sum );
                sum = 0;
            }
        }

        return res;
    }

    public static Vector < Vector < Double > > subtractMatrix ( Vector < Vector < Double > > b, Vector < Vector < Double > > s ){
        Vector < Vector < Double > > bs = new Vector < Vector < Double > > ();

        for( int i = 0; i < b.size(); i++ ){
            bs.add( new Vector < Double > () );
            for( int j = 0; j < b.elementAt(i).size(); j++ ){
                bs.elementAt(i).add( b.elementAt(i).elementAt(j) - s.elementAt(i).elementAt(j) );
            }
        }

        return bs; 
    }

    public static Vector < Vector < Double > > inverseMatrix( Vector < Vector < Double > > v ){
        Vector < Vector < Double > > inv = new Vector < Vector < Double > > ();
        Vector < Vector < Double > > adj = new Vector < Vector < Double > > ();

        adj = adjucate( v );

        for( int i = 0; i < v.size(); i++ ){
            inv.add( new Vector < Double > () );
            for( int j = 0; j < v.elementAt(i).size(); j++ ){
                inv.elementAt(i).add( ( 1 / determinant( v ) ) * adj.elementAt( i ).elementAt( j ) );
            }
        }

        printMatrix(inv, "inv of a");

        return inv;
    }

    public static Vector < Vector < Double > > adjucate( Vector < Vector < Double > > v ){
        Vector < Vector < Double > > v2 = new Vector < Vector < Double > > ();

        for( int i = 0; i < v.size(); i++){
            v2.add( new Vector < Double > () );
            for( int j = 0; j < v.elementAt(i).size(); j++ ){
                v2.elementAt(i).add( cofactor( v, i, j ) );
                if ( (i + j) % 2 == 1 ){
                    v2.elementAt(i).set(j, v2.elementAt(i).elementAt(j) * -1.0 );
                }
            }
        }

        v2 = transpose( v2 );

        return v2;
    }

    public static Double cofactor( Vector < Vector < Double > > v, Integer i, Integer j ){
        Vector < Vector < Double > > v2 = new Vector < Vector < Double > > ();

        // copy matrix 
        v2 = copyMatrix( v );

        // remove row
        for( int x = 0; x < v.size(); x++ ){
            v2.elementAt( x ).removeElementAt( j );
        }

        // remove n
        v2.removeElementAt(i);

        // debug
        // printMatrix(v2, "null");

        Double deter = determinant( v2 );

        return deter;
    }

    public static Double determinant( Vector < Vector < Double > > v ){
        // Check for square matrix
        if( v.size() != v.elementAt(0).size() ){
            throw new IllegalArgumentException("Determinant must come from square matrix.");
        }

        // Create matrix of n + n-1*n + n-1 
        Vector < Vector < Double > > v2 = new Vector < Vector < Double > > ();
        
        // Duplicate matrix
        v2 = copyMatrix( v );

        // Expand if n > 2
        if( v.size() > 2 ){
            int k = 0;
            for( int i = v.size(); i < v.size() * 2 - 1; i++ ){
                v2.add( new Vector < Double > () );
                for( int j = 0; j < v.elementAt(k).size(); j++ ){
                    v2.elementAt(i).add( v.elementAt(k).elementAt(j) );
                }
                k++;
            }

            // init temp variables
        Double sum1 = 0.0, temp1 = 0.0;
        int xoffset1 = 0;

        // parse matrix diagonally: top left to bottom right
        while( xoffset1 < v2.elementAt(0).size() ){
            temp1 = 1.0;
            for( int i = 0; i < v2.size(); i++){
                for( int j = 0; j < v2.elementAt(i).size(); j++ ){
                    if( i == j + xoffset1 ){
                        temp1 *= v2.elementAt(i).elementAt(j);
                    }
                }
            }
            sum1 += temp1;
            xoffset1++;
        }

        // init temp variables
        Double sum2 = 0.0, temp2 = 0.0;
        int xoffset2 = 0;
        // parse matrix diagonally: bottom left to top right
        while( xoffset2 < v2.elementAt(0).size() ){
            temp2 = 1.0;
            for( int i = 0; i < v2.size(); i++){
                for( int j = v2.elementAt(i).size() - 1; j >= 0; j-- ){
                    if( i + j == ( v2.elementAt(i).size() - 1 ) + xoffset2 ){
                        temp2 *= v2.elementAt(i).elementAt(j);
                    }
                }
            }
            sum2 += temp2;
            xoffset2++;
        }

        return sum1 - sum2;

        } else {
            // brute force
            return v2.elementAt(0).elementAt(0) * v2.elementAt(1).elementAt(1) - v2.elementAt(1).elementAt(0) * v2.elementAt(0).elementAt(1);
        }
    }

    public static Vector < Vector < Double > > transpose( Vector < Vector < Double > > v ){
        // flip matrix
        Vector < Vector < Double > > v2 = new Vector < Vector < Double > > ();

        for( int i = 0; i < v.elementAt(0).size(); i++ ){
            v2.add( new Vector < Double > () );
            for( int j = 0; j < v.size(); j++ ){
                v2.elementAt(i).add( v.elementAt(j).elementAt(i) );
            }
        }

        return v2;
    }

    public static Vector < Vector < Double > > copyMatrix( Vector < Vector < Double > > v ){
        // duplicate
        Vector < Vector < Double > > copy = new Vector < Vector < Double > > ();

        for( int i = 0; i < v.size(); i++ ){
            copy.add( new Vector < Double > () );
            for( int j = 0; j < v.elementAt(i).size(); j++ ){
                copy.elementAt(i).add( v.elementAt(i).elementAt(j) );
            }
        }
        
        return copy;
    }

    public static void pop( Vector < Vector < Double > > a, int xsize, int ysize ) {
        for( int i = 0; i < xsize; i++ ){
            a.add( new Vector < Double > () );
            for( int j = 0; j < ysize; j++ ){
                a.elementAt(i).add( randNum() );
            }
        }
        
        // // populate a
        // a.addElement( new Vector < Double > () );
        // a.addElement( new Vector < Double > () );
        // a.addElement( new Vector < Double > () );
        // a.elementAt(0).addElement(3.2);
        // a.elementAt(1).addElement(8.7);
        // a.elementAt(2).addElement(5.9);
        // a.elementAt(0).addElement(2.4);
        // a.elementAt(1).addElement(3.1);
        // a.elementAt(2).addElement(1.1);
        // a.elementAt(0).addElement(9.7);
        // a.elementAt(1).addElement(6.1);
        // a.elementAt(2).addElement(0.3);
        // // populate b
        // b.addElement( new Vector < Double > () );
        // b.elementAt(0).addElement(5.0);
        // b.elementAt(0).addElement(12.9);
        // b.elementAt(0).addElement(2.0);
        // // populate s
        // s.addElement( new Vector < Double > () );
        // s.elementAt(0).addElement(3.7);
        // s.elementAt(0).addElement(3.8);
        // s.elementAt(0).addElement(1.0);
    }

    public static void printMatrix( Vector< Vector < Double > > m, String n ) {
        System.out.println( "--" + " Matrix " + n );
        // row
        for ( int i = 0; i < m.elementAt(0).size(); i++ ){
            System.out.println();
            // n
            for (int j = 0; j < m.size(); j++ ){

                System.out.print( m.elementAt(j).elementAt(i) + "\t" );

            }
            System.out.println();
        }
        System.out.println();
    }    

    public static Double randNum(){
        Random r = new Random( System.currentTimeMillis() + System.nanoTime() );
        Integer i = r.nextInt( 10 );
        Double d = r.nextDouble();
        return i + d;
    }

}
