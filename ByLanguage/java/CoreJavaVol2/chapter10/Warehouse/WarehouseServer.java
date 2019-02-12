
import java.rmi.*;
import javax.naming.*;

public class WarehouseServer {
    public static void main(String[] args) throws RemoteException, NamingException {
        System.out.println("Constructing server implementation...");
        WarehouseImpl centralWarehouse = new WarehouseImpl();

        System.out.println("Binding server implementation to registry...");;
        Context namingContext =new InitialContext();
        namingContext.bind("rmi://localhost/central_warehouse", centralWarehouse);

        System.out.println("Waiting for invocations from clients...");
    }
}
