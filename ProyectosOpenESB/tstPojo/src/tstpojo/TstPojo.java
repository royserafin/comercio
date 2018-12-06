/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tstpojo;

/**
 *
 * @author rafael
 */
public class TstPojo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        tstpojo.InputComplexType p = new tstpojo.InputComplexType();
        tstpojo.OutputComplexType r;
        
        tstpojo.MyCalculatorCAService1 service = new tstpojo.MyCalculatorCAService1();
        tstpojo.MyCalculatorPortType port = service.getCasaPort1();
       
        long N = 1000;
        for( int i = 1; i <= 1000; i++)
        {
          p.setParam01((int)(1000.0 * Math.random()));
          p.setParam02((int)(1000.0 * Math.random()));
        
          r =  port.additionOperation(p);
         
          System.out.println(i + " ... resultado = " + r.getResponse01());
        }
    }

    private static OutputComplexType additionOperation(tstpojo.InputComplexType part1) {
        tstpojo.MyCalculatorCAService1 service = new tstpojo.MyCalculatorCAService1();
        tstpojo.MyCalculatorPortType port = service.getCasaPort1();
        return port.additionOperation(part1);
    }
    
}
