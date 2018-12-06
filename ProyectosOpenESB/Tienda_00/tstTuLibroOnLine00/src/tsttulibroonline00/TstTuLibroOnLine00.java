/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsttulibroonline00;

/**
 *
 * @author RGAMBOAH
 */
public class TstTuLibroOnLine00 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        String strISBN;
        int intCantidad, intPU;
        String strRes;
        org.netbeans.j2ee.wsdl.serviciosventa00.descriptorbpel.ventalibroswsdl.VentaLibrosWSDLService service = new org.netbeans.j2ee.wsdl.serviciosventa00.descriptorbpel.ventalibroswsdl.VentaLibrosWSDLService();
        org.netbeans.j2ee.wsdl.serviciosventa00.descriptorbpel.ventalibroswsdl.VentaLibrosWSDLPortType port = service.getVentaLibrosWSDLPort();
    
        long N = 1000;
        for( int i = 1; i <= N; i++)
        {
          strISBN = "01234" + i ;
          intCantidad = (int)(10.0 * Math.random() + 1.0 );
          intPU = (int)(200.0 * Math.random() + 1.0 );
          strRes = ventaLibrosWSDLOperation("1234567890","rgamboah",intCantidad,intPU);
          System.out.println(i + " ... Respuesta:" + strRes);
        }
    }

    private static String ventaLibrosWSDLOperation(java.lang.String isbn, java.lang.String idCliente, int unidades, int precioUnidad) {
        org.netbeans.j2ee.wsdl.serviciosventa00.descriptorbpel.ventalibroswsdl.VentaLibrosWSDLService service = new org.netbeans.j2ee.wsdl.serviciosventa00.descriptorbpel.ventalibroswsdl.VentaLibrosWSDLService();
        org.netbeans.j2ee.wsdl.serviciosventa00.descriptorbpel.ventalibroswsdl.VentaLibrosWSDLPortType port = service.getVentaLibrosWSDLPort();
        return port.ventaLibrosWSDLOperation(isbn, idCliente, unidades, precioUnidad);
    }
    
}
