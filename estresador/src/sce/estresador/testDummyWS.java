/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sce.estresador;

/**
 *
 * @author Mike
 */
public class testDummyWS {
    public static void main(String[] args) {
        System.out.println("2 + 5 = "+ suma(2,5));
    }

    private static int suma(int a, int b) {
        sce.dummy.LoopbackWS_Service service = new sce.dummy.LoopbackWS_Service();
        sce.dummy.LoopbackWS port = service.getLoopbackWSPort();
        return port.suma(a, b);
    }
}
