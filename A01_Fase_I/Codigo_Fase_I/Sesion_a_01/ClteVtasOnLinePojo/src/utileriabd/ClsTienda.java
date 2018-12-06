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
 * @author sdist
 */
public class ClsTienda 
{
    ClsConexion conexion;
 
    ClsTienda()
    {
        
    }
    
    boolean conectate( String unUsr, String pwd )
    {
        conexion = new ClsConexion("OnLineSales");
        return conexion.conectate(unUsr, pwd);
    }
    
    boolean desconectate()
    {
        conexion.cierraCon();
        return true;
    }
    
    boolean conectado()
    {
        return conexion.conectado();
    }
    
    Customer[] CatalogoDeClientes()
    {
    	int k = -1;
    	Customer[] catCltes = null;
    	ResultSet r=null;
    	try 
        {
    	    r = conexion.obtenRS("Customers");
            if( r != null )
            { 
	          catCltes=new Customer[conexion.cuentaRegs("Customers")];
	          while(r.next())
	          {
	            catCltes[++k] = new Customer(r.getString("CustomerID"),r.getString("Country"));                   
	          }
            }
            else
              System.out.println("No se pudo recuperar el ResultSet...");  

		} 
    	catch (SQLException e)
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}
        return catCltes;
    }
    
    Prod[] CatalogoDeProductos()
    {
     	int k = -1;
    	Prod[] catProds = null;
    	ResultSet r=null;
    	try 
        {
    	    r = conexion.obtenRS("Products");
            if( r != null )
            { 
	          catProds=new Prod[conexion.cuentaRegs("Products")];
	          while(r.next())
	          {
	            catProds[++k] = new Prod(r.getString("StockCode"),r.getString("ProdDescription"), r.getString("UnitPr"));                   
	          }
            }
            else
              System.out.println("No se pudo recuperar el ResultSet...");  

		} 
    	catch (SQLException e)
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}
        return catProds;
    }
    
    public String impArrCltes(Customer[] cltes)
    {
    	int n = cltes.length;
        StringBuffer sb = new StringBuffer("Catalogo de Clientes\n");
    	for( int k = 0; k < n; k++)
    		sb.append(k).append(" ... ").append(cltes[k]).append('\n');
        return new String( sb );
    }

    public String impArrProds(Prod[] prods)
    {
    	int n = prods.length;
        StringBuffer sb = new StringBuffer("Catalogo de productos\n");
    	for( int k = 0; k < n; k++)
    		sb.append(k).append(" ... ").append(prods[k]).append('\n');
        return new String( sb );
    }
 
    public static void main(String args[])
    {
        
        Customer arr_Clientes[]  = null;
        Prod     arr_Productos[] = null;
        
        ClsTienda tienda = new ClsTienda();
        tienda.conectate("rafa", "rafa");
        if( tienda.conectado())
        {
          System.out.println("Conectado");
          
          // aquí va la funcionalidad de prueba
          // solicitar el catálogo de clientes
          arr_Clientes = tienda.CatalogoDeClientes();
          System.out.println(tienda.impArrCltes(arr_Clientes));
          // solicitar el catalogo de productos
          arr_Productos = tienda.CatalogoDeProductos();
          System.out.println(tienda.impArrProds(arr_Productos));
          tienda.desconectate();
          System.out.println("Desconectado...");
        }
        else
            System.out.println("NO se pudo conectar, llamar a los bomberos...");
    }
}
