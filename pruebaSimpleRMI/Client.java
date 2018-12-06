package example.hello;

    import java.rmi.registry.LocateRegistry;
    import java.rmi.registry.Registry;

    public class Client {

        private Client() {}

        public static void main(String[] args) {
            long i;
            String response;
            String host = (args.length < 1) ? null : args[0];
            try 
            {
                Registry registry = LocateRegistry.getRegistry(host);
                Hola stub = (Hola) registry.lookup("Hola");
                for(i=0;i<20000;i++)
                {
                  response = stub.sayHello();
                  System.out.println("response: " + response);
                } 
            } 
            catch (Exception e) 
            {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
            }
        }
    }

