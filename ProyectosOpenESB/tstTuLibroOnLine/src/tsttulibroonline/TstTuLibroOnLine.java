/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsttulibroonline;

/**
 *
 * @author sdist
 */
public class TstTuLibroOnLine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        long t0,t1,dt,sdt = 0,sdt2 = 0;
        double prom, desvStd;
        
        String strISBN, strIDClte, strRes;
        int intUnidades, intPU;
        tsttulibroonline.VentaLibrosWSDLService service = new tsttulibroonline.VentaLibrosWSDLService();
        tsttulibroonline.VentaLibrosWSDLPortType port = service.getVentaLibrosWSDLPort();
        long N = 10;
        N = args.length > 0 ? Long.parseLong(args[0]): N;
            
        for(int i = 1; i <= N; i++ )
        {
          strIDClte   = "Clte_" + i;
          strISBN     = "" + (long)( 1000000.0 * Math.random());
          intUnidades = 1;//(int)(100.0 * Math.random() + 1 );
          intPU       = (int)(50.0 + 150.0 * Math.random()); 
          t0 = System.nanoTime();
          strRes = port.ventaLibrosWSDLOperation(strISBN,strIDClte,intUnidades,intPU);
          t1    = System.nanoTime();
          dt    = t1 - t0;
          sdt  += dt;
          sdt2 += dt * dt; 
          System.out.println("Requisicion " + i + 
                             " ISBN:"   + strISBN +
                             " IDClte:" + strIDClte + 
                             " cant. "  + intUnidades + 
                             " PU:" + intPU + " respuesta:" + strRes);
        }
        prom    = ((double)sdt) / N;
        desvStd = Math.sqrt((sdt2 - N * prom * prom ) / N);
        
        prom    *= 1.0e-9; // en segundos
        desvStd *= 1.0e-9; // en segundos
        
        System.out.println(" Para " + N + " solicitudes,\npromedio de tiempo de ciclo: " + prom + " seg.\n" +
                           " desvStd de tiempo de ciclo: " + desvStd + " seg.");
    }

    private static String ventaLibrosWSDLOperation(java.lang.String isbn, java.lang.String idCliente, int unidades, int precioUnidad) {
        tsttulibroonline.VentaLibrosWSDLService service = new tsttulibroonline.VentaLibrosWSDLService();
        tsttulibroonline.VentaLibrosWSDLPortType port = service.getVentaLibrosWSDLPort();
        return port.ventaLibrosWSDLOperation(isbn, idCliente, unidades, precioUnidad);
    }
    
}
