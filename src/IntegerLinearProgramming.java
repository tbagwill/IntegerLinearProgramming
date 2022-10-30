import java.util.Vector;

/* 
 * Integer Linear Programming
 * CSC 414 Midterm Project
 * Tyler Bagwill, Nathan Bonetto, Kaeleb Brandin, Judah Lopez 
*/

public class IntegerLinearProgramming {

    public static final Integer col = 3;
    public static final Integer row = 3;
    public static Vector < Vector < Double > > x = new Vector < Vector < Double > > ();

    public static void main(String[] args) throws Exception {
        
        // preface
        // INIT MATRICES

        Vector < Vector < Double > > b = new Vector < Vector < Double > > ();
        Vector < Vector < Double > > a = new Vector < Vector < Double > > ();
        Vector < Vector < Double > > s = new Vector < Vector < Double > > ();

        // POPULATE VALUES
        // TODO: populate with random values 
        pop(a, b, s);

        // check matrices
        printMatrix(a, "a");
        printMatrix(b, "b");
        printMatrix(s, "s");

        // Given b = ax + s, where a = n*n matrix and b, x, s are n x 1 matrices

        /* STEP 1: Create system of equations using ax cross product ( Ex: )
        **
        ** | a00 * x0 , a10 * x1 , a20 * x2 |
        ** | a01 * x0 , a11 * x1 , a21 * x2 |
        ** | a02 * x0 , a12 * x1 , a22 * x2 |
        */

        /* STEP 2: b = ax + s -> // (b - s) = Ax || (b - s)A^-1 = x
         * 
         * 
         */

        // TODO: shortcut method ( determinant of a matrix )

        // TODO: adjucate of matrix

        // TODO: inverse of the matrix using A^-1 = adjucate( A ) * ( 1 / determinate(A) )


        // System.out.println(x.toString());
    }

    public static void pop( Vector < Vector < Double > > a, Vector < Vector < Double > > b, Vector < Vector < Double > > s ) {
        // populate a
        a.addElement( new Vector < Double > () );
        a.addElement( new Vector < Double > () );
        a.addElement( new Vector < Double > () );
        a.elementAt(0).addElement(3.2);
        a.elementAt(0).addElement(8.7);
        a.elementAt(0).addElement(5.9);
        a.elementAt(1).addElement(2.4);
        a.elementAt(1).addElement(3.1);
        a.elementAt(1).addElement(1.1);
        a.elementAt(2).addElement(9.7);
        a.elementAt(2).addElement(6.1);
        a.elementAt(2).addElement(0.3);
        
        // populate b
        b.addElement( new Vector < Double > () );
        b.elementAt(0).addElement(5.0);
        b.elementAt(0).addElement(12.9);
        b.elementAt(0).addElement(2.0);
        
        // populate s
        s.addElement( new Vector < Double > () );
        s.elementAt(0).addElement(3.7);
        s.elementAt(0).addElement(3.8);
        s.elementAt(0).addElement(1.0);
    }

        

    public static Vector < Vector < Double > > inverseMatrix( Vector < Vector < Double > > m ){
        Vector < Vector < Double > > inv = new Vector < Vector < Double > > ();
        

        return inv;
    }

    public static void printMatrix( Vector< Vector< Double >> m, String n ) {
        System.out.println( "--" + " Matrix " + n );
        for ( int i = 0; i < m.size(); i++ ){
            for (int j = 0; j < m.elementAt(i).size(); j++ ){
                System.out.print( m.elementAt(i).elementAt(j) + "\t" );
            }
            System.out.println();
        }
        System.out.println();
    }    

}
