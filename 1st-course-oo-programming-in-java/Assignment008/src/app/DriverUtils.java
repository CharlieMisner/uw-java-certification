package app;

import java.awt.Color;

import cp120.assignments.geo_shape.GeoPoint;

public class DriverUtils
{
    public static double   EPSILON = .0001;
    
    public static int getLineNumber()
    {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }
    
    public static boolean assertEqual( double exp, double act, double eps )
    {
        final String    fmt     = 
            "assertEquals @line %d: expected = %.3f, actual = %.3f %s%n";
        boolean result  = Math.abs( exp - act ) < eps;
        if ( !result )
        {
            printf( fmt, getLineNumber(), exp, act, "FAILED" );
            printStackTrace();
        }
        
        return result;
    }
    
    public static boolean assertEqual( int exp, int act )
    {
        final String    fmt     = 
            "assertEquals @line %d: expected = %d, actual = %d %s%n";
        boolean result  = exp == act;
        if ( !result )
        {
            printf( fmt, getLineNumber(), exp, act, "FAILED" );
            printStackTrace();
        }
        
        return result;
    }
    
    public static boolean assertEqual( GeoPoint exp, GeoPoint act )
    {
        final String    fmt     = 
            "assertEquals @line %d: expected = %s, actual = %s %s%n";
        boolean result  =
            (exp.getXco() == act.getXco()) && (exp.getYco() == act.getYco());
        if ( !result )
        {
            printf( fmt, getLineNumber(), exp, act, "FAILED" );
            printStackTrace();
        }
        
        return result;
    }
    
    public static boolean assertEqual( Color exp, Color act )
    {
        final String    fmt     = 
            "assertEquals @line %d: expected = %.3f, actual = %.3f %s%n";
        boolean result  = exp.equals( act );
        if ( !result )
        {
            printf( fmt, getLineNumber(), exp, act, "FAILED" );
            printStackTrace();
        }
        
        return result;
    }
    
    public static void printStackTrace()
    {
        StackTraceElement[] eles    = Thread.currentThread().getStackTrace();
        printf( "********** STACK TRACE%n" );
        for ( StackTraceElement ele : eles )
        {
            String  file    = ele.getFileName();
            String  method  = ele.getMethodName();
            int     num     = ele.getLineNumber();
            printf( "%s, %s at line %d%n", file, method, num );
        }
        printf( "**********%n" );
    }
    
    public static void printf( String fmt, Object... objects  )
    {
        System.out.printf( fmt, objects );
    }
    
    public static String doubleString( double dVal )
    {
        String  strVal  = String.format( "%.4f", dVal );
        return strVal;
    }
}
