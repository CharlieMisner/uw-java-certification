package cp120.assignments.assignment002;

import java.math.BigInteger;

import cp120.assignments.assignment002.Numbers;

public class NumbersTest2
{
    public static void main( String[] args )
    {
        lcm();
        newLine();
        factorial();
        newLine();
        factorialP();
    }
    
    private static void newLine()
    {
        System.out.println();
    }
    
    private static void lcm()
    {
        int[][] arr =
        {
            { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 2 }, { 2, 2 }, { 2, 3 },
            { 3, 3 }, { 2, 4 }, { 2, 6 }, { 2, 8 }, { 3, 5 }, { 3, 7 }, 
            { 5, 7 }, { 5, 13 },
            { 210, 16 }, { 125, 55 }, { 70, 34 }, { 21, 18 }
            //  1680         1375        1190        126
        };
        
        for ( int[] pair : arr )
            lcm( pair[0], pair[1] );
    }

    private static void lcm( int num1, int num2 )
    {
        int result  = Numbers.lcm( num1, num2 );
        System.out.printf( "lcm( %3d, %3d ): %4d\n", num1, num2, result );
    }
    
    private static void factorial()
    {
        int[]   nums    =
            {   0,   1,   2,   3,   5,   
           //   1    1    2    6    120
                10,     20,     31 };
           //   3,628,800
           //           2,432,902,008,176,640,000
           //                   8,222,838,654,177,922,817,725,562,880,000,000                                    
          for ( int num : nums )
              factorial( num );
    }
    
    private static void factorial( int num )
    {
        BigInteger  factorial   = Numbers.factorial( num );
        System.out.printf( "factorial( %2d ): %,d\n", num, factorial );
    }
    
    private static void factorialP()
    {
        int[]   nums    =
            {   0,   1,   2,   3,   5,   
           //   1    1    2    6    120
                10,     20,     31 };
           //   3,628,800
           //           2,432,902,008,176,640,000
           //                   8,222,838,654,177,922,817,725,562,880,000,000                                    
          for ( int num : nums )
          {
              BigInteger    bigNum  = BigInteger.valueOf( num );
              factorial( bigNum );
          }
    }
    
    private static void factorial( BigInteger num )
    {
        BigInteger  factorial   = Numbers.factorial( num );
        System.out.printf( "factorial'( %2d ): %,d\n", num, factorial );
    }
}