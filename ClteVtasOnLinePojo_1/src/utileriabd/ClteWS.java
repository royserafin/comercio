/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utileriabd;

import java.sql.*;
import java.util.*;

/**
 *
 * @author rafael
 */
public class ClteWS
{ 
  public static void main( String args[])
  {
    ClsConexion c  = null;
    String[] arr_CvesCustomers = null;
    String[] arr_CvesProducts = null; 
    int numPedidos     =  5;
    int numMaxItems    = 10;
    int numMaxUnidades = 20;
    
    int numCltes   = 0;  // cantidad total de clientes en la BD
    int numProds   = 0;  // cantidad total de productos en la BD
    
    int kPedido    = 0;  // qué pedido es <= numPedidos
    int numItems   = 0;  // cantidad de items de un pedido <= numMaxItems
    int jItem      = 0;  // que item es
    int jProd      = 0;  // lugar de la clave del producto
    int jClte      = 0;  // lugar del cliente en el arreglo de claves
    int unidades   = 0;  // cantidad de unidades de cada item
    
    try
    {
      c = new ClsConexion("OnLineSales");
      c.conectate("rafa","rafa");
      if( c.conectado() )    
      {
        arr_CvesCustomers = c.cargaDatos(1, "Customers");
        numCltes = arr_CvesCustomers.length;
        for(int k = 0; k < numCltes; k++ )
          System.out.println("CveCustomers[" + k + "] = " + arr_CvesCustomers[k]);
                       
        arr_CvesProducts = c.cargaDatos(1, "Products");
        numProds = arr_CvesProducts.length;
        for(int k = 0; k < numProds; k++ )
          System.out.println("CveProducts[" + k + "] = " + arr_CvesProducts[k]);
        
        c.cierraCon();
      }
      else
      {
          System.out.println("No se pudo abrir la BD");
      }
    }
    catch( Exception e)
    {
        e.printStackTrace();
        System.exit(1);
    }
    
    // se generan numPedidos pedidos:
    
    for( kPedido = 1; kPedido <= numPedidos; kPedido++ )
    {
        // selecciono el cliente
        jClte      = (int) ( Math.random() * numCltes + 0.5);
        System.out.println("pedido num." + kPedido + " clte: " + arr_CvesCustomers[jClte]);
        numItems = (int) ( Math.random() * numMaxItems + 0.5);
        for( jItem = 1; jItem <= numItems; jItem++)
        {
            unidades = (int)(1.0 + Math.random() * numMaxUnidades );
            System.out.println("Item:" + jItem + "   CveProd:" + 
                                arr_CvesProducts[(int) ( Math.random() * numProds + 0.5)] +
                                " con " + unidades + " unidades" );
        }
    }
    
    
    
    
    
    
 
      
    
      
      
      
                       

      
      
      
  }
    
}
