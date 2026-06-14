

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;



// ip 127.0.0.2
public class ServidorCentral {

    private static String SERVER_IP_SP;
    private static String SERVER_IP_SH;
    private static int SERVER_PORT_SP;
    private static int SERVER_PORT_SH;
    private static int SERVER_PORT_CENTRAL;

    public static void main(String[] args) throws Exception{
        

        Socket socketCliente= null;

                // LEO PARAMETROS DE ARCHIVO CONFIGURACION
        Properties props = new Properties();
        props.load(new FileInputStream("config.properties"));
        SERVER_IP_SH= props.getProperty("SERVER.IP.SH");
        SERVER_IP_SP= props.getProperty("SERVER.IP.SP");

        SERVER_PORT_CENTRAL= Integer.parseInt(props.getProperty("SERVERS.PORT.CENTRAL"));
        SERVER_PORT_SP= Integer.parseInt(props.getProperty("SERVERS.PORT.SP"));
        SERVER_PORT_SH= Integer.parseInt(props.getProperty("SERVERS.PORT.SH"));
        
        ServerSocket ss= new ServerSocket(SERVER_PORT_CENTRAL);        
        System.out.println("Iniciando Servidor Central esperando en puerto: " + SERVER_PORT_CENTRAL);
        while(true){
            try {
                socketCliente= ss.accept(); // instanciamos un socket
                System.out.println("Servidor Central - iniciando socket ");
                (new SocketCentral(socketCliente, SERVER_IP_SP, SERVER_IP_SH, SERVER_PORT_SP, SERVER_PORT_SH)).start();

            } catch (Exception e) {
            }
        }
    
    }
}
