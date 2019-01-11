package cp120.assignments.assignment005;

import cp120.assignments.assignment005.Crypto;

public class CryptoDriver
{
    public static void main(String[] args)
    {
        String[]    tests   =
        {
            "abcdefghi",
            "abcdefgh",
            "abcdefg",
            "abcdef",
            "abcde",
            "abcd",
            "abc",
            "ab",
            "a",
            "",
        };
        for ( String str : tests )
        {
            String  encrStr = Crypto.encrypt( str );
            String  decrStr = Crypto.decrypt( encrStr );
            printUnicode( encrStr );
            print( str );
            print( decrStr );
            String  msg = "PASS";
            if ( !str.equals( decrStr ) )
                msg = "FAIL";
           System.out.printf( "%s%n%n", msg );
        }
        print( "done" );
    }

    private static void print( String str )
    {
        String  temp = '\"' + str + '\"';
        System.out.println( temp );
    }
    
    private static void printUnicode( String str )
    {
        char[]  arr     = str.toCharArray();
        for ( char ccc : arr )
        {
            System.out.printf( "%3d ", (int)ccc );
        }
        System.out.println();
    }
}
