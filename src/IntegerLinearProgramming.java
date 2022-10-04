import java.util.Vector;

/* 
 * Integer Linear Programming
 * CSC 414 Midterm Project
 * Tyler Bagwill, Nathan Bonetto, Kaeleb Brandin, Judah Lopez 
*/

public class IntegerLinearProgramming {

    public static void main(String[] args) throws Exception {
        
        // preface
        // init vectors ( one dimensional matrices )

        Vector <Double> b = new Vector <Double> ();
        Vector <Double> a = new Vector <Double> ();
        Vector <Double> x = new Vector <Double> ();
        Vector <Double> s = new Vector <Double> ();

        // populate values
        assign(a, b, s);

        // b = ax + s
        vectorMult(a, b, s, x);

        System.out.println(x.toString());
    }

    public static void assign( Vector <Double> a, Vector <Double> b, Vector <Double> s ){
        b.addElement(5.0);
        b.addElement(12.9);
        b.addElement(2.0);
        a.addElement(4.25);
        a.addElement(1.1);
        a.addElement(0.97);
        s.addElement(3.7);
        s.addElement(3.8);
        s.addElement(1.0);
    }

    public static void vectorMult( Vector <Double> a, Vector <Double> b, Vector <Double> s, Vector <Double> x ){
        for( int i = 0; i < b.size(); i++){
            x.addElement( (b.elementAt(i) - s.elementAt(i) ) / a.elementAt(i) );
        }
    }


}
