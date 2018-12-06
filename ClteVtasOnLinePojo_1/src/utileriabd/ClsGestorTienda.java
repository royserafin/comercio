/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utileriabd;

/**
 *
 * @author sdist
 */
public class ClsGestorTienda 
{
    ClsConexion conexion;
 
    ClsGestorTienda()
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
    
    Clte[] CatalogoDeClientes()
    {
        return null;
    }
    
    Prod[] CatalogoDeProductos()
    {
        return null;
    }
    
    
    
    public static void main(String args[])
    {
        Clte arr_Clientes[]   = null;
        Prod  arr_Productos[] = null;
        
        ClsGestorTienda gestor = new ClsGestorTienda();
        gestor.conectate("rafa", "rafa");
        if( gestor.conectado())
        {
          System.out.println("Conectado");
          
          // aquí va la funcionalidad de prueba
          // solicitar el catálogo de clientes
          arr_Clientes = gestor.CatalogoDeClientes();
          // solicitar el catalogo de productos
          arr_Productos = gestor.CatalogoDeProductos();
          
          gestor.desconectate();
          System.out.println("Desconectado...");
        }
        else
            System.out.println("NO se pudo conectar, llamar a los bomberos...");
    }
}
