
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class ServidorSH {

    private static String[] prediccion= new String[]{"Amor", "Dolor", "Dinero", "Salud"};


    private static int SERVER_PORT_SH;


    public static String getPrediccion(int i){
        return  prediccion[i];
    }
public static void main(String[] args) throws Exception{

        Socket socketSH= null;        
        
        Properties props = new Properties();
        props.load(new FileInputStream("config.properties"));

        SERVER_PORT_SH= Integer.parseInt(props.getProperty("SERVERS.PORT.SH"));

        ServerSocket ss= new ServerSocket(SERVER_PORT_SH);
        System.out.println("Iniciando ServidorSH esperando en puerto: " + SERVER_PORT_SH);

        while(true){
            try {
                socketSH= ss.accept(); // instanciamos un socket
                System.out.println("ServidorSH - iniciando socket");
                (new SocketSH(socketSH)).start();

            } catch (Exception e) {
            }
        }
    
    }
}
