/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sce.estresador;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
/**
 *
 * @author mgb
 */
public class Client {
    
    /*
    args:
    0: num solicitudes, default 10
    1: rmihost, defaul localhost
    */
    public static void main(String[] args) {
        String rmihost = "localhost";
        long numRequests = 10;
        long id, delay, milis, nanos, sum, sqsum, min, max, t0, t1, responseTime;
        sum = 0;
        sqsum = 0;
        min = Long.parseLong("7FFFFFFFFFFFFFFF",16);
        System.out.println(min);
        max = -1;
        
        //Lee argumentos
        if(args.length == 1){
            
            numRequests = Long.parseLong(args[0]);
            
        }else if (args.length > 1){
            
            numRequests = Long.parseLong(args[0]);
            rmihost = args[1];
            
        }
        
        try{
            //Consigue el scheduler desde el rmi en rmihost
            Registry registry = LocateRegistry.getRegistry(rmihost);
            SchedulerInterface sch = (SchedulerInterface) registry.lookup("Scheduler");
            
            //Consigue tu id + tiempo de espera para mandar solicitudes
            id = sch.clientID();
            delay = sch.nanoDelay();
            milis =(long) Math.floor(delay * 1e-6);
            nanos = (long) (delay-milis*1e6);
            System.out.println("Cliente numero: " + id);
            System.out.println("Delay de: "+milis+" ms, "+nanos+" ns");
            Thread.sleep(milis, (int) nanos);
            
            //Crea los objetos para la llamada al webservice en 23.96.10.86:8080
            sce.dummy.LoopbackWS_Service service = new sce.dummy.LoopbackWS_Service();
            sce.dummy.LoopbackWS port = service.getLoopbackWSPort();
            int a, b, response;
            Random rng = new Random();
            
            //Manda numRequests solicitudes
            for(int i = 0; i < numRequests; ++i){
                a = rng.nextInt(1000);
                b = rng.nextInt(1000);
                
                t0 = System.nanoTime();
                response = port.suma(a, b);
                t1 = System.nanoTime();
                
                responseTime = t1-t0;
                
                if(responseTime > max){
                    max = responseTime;
                }else if(responseTime < min){
                    min = responseTime;
                }
                
                sum += responseTime;
                sqsum += responseTime*responseTime;
                
                if(i % 100 == 0){
                    System.out.println("Solicitud "+i+": " + a + " + " + b + " = " + response);
                }
            }
            
            
            sch.addStats(numRequests, sum, sqsum, min, max);
            
            
        }catch(Exception e){
            System.err.println("Exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
