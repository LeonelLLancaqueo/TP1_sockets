import java.io.FileInputStream;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class ServidorSP {

    private static int SERVER_PORT_SP;
    
    private static String[] pronostico = new String[] { "Soleado", "Nublado", "lluvia", "Viento","Templado","Inestable","Nieve","Calor","Frio"};

    public static String getPrediccion(int i) {
        return pronostico[i];
    }

    public static void main(String[] args) throws Exception {
        ObjectInput ois = null;
        ObjectOutputStream oos = null;
        Socket socketSP = null;
        
        Properties props = new Properties();
        props.load(new FileInputStream("config.properties"));

        SERVER_PORT_SP= Integer.parseInt(props.getProperty("SERVERS.PORT.SP"));

        ServerSocket ss= new ServerSocket(SERVER_PORT_SP);

        System.out.println("Iniciando ServidorSP esperando en puerto: " + SERVER_PORT_SP);

        while (true) {
            try {
                System.out.println("ServidorSP - iniciando socket");
                socketSP = ss.accept(); // instanciamos un socket
                (new SocketSP(socketSP)).start();

            } catch (Exception e) {
            }
        }

    }
}
