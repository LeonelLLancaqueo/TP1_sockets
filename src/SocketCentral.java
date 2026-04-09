
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketCentral extends Thread {
    private Socket socketCli = null, socketSH = null, socketSP = null;
    private ObjectInputStream oisCli = null, oisSH = null, oisSP = null;
    private ObjectOutputStream oosCli = null, oosSH = null, oosSP = null;
    private int PORT_SP;
    private int PORT_SH;
    private String LOCAL_IP;

    public SocketCentral(Socket socket, String ip, int spPort, int shPort) {
        this.socketCli = socket;
        this.PORT_SH = shPort;
        this.PORT_SP = spPort;
        this.LOCAL_IP = ip;
    }

    // HACER MATRIZ CON HOROSCOPO
    public void run() {
        try {
            System.out.println("Instanciando objetos I/O con ServerCliente");
            this.oisCli = new ObjectInputStream(socketCli.getInputStream());
            this.oosCli = new ObjectOutputStream(socketCli.getOutputStream());

            System.out.println("Abriendo Socket e Instanciando objetos I/O con ServerSH");
            socketSH = new Socket(LOCAL_IP, PORT_SH);
            this.oosSH = new ObjectOutputStream(socketSH.getOutputStream());
            this.oisSH = new ObjectInputStream(socketSH.getInputStream());


            System.out.println("Abriendo Socket e Instanciando objetos I/O con ServerSP");
            this.socketSP = new Socket(LOCAL_IP, PORT_SP);
            this.oisSP = new ObjectInputStream(socketSP.getInputStream());
            this.oosSP = new ObjectOutputStream(socketSP.getOutputStream());

            boolean continuar = true;

            while (continuar) {

                System.out.println("Servidor Central - Esperando msj cliente");
                int msj = (int) oisCli.readObject();
                if (msj != 999) {

                    System.out.println("Enviando consulta a ServidorSH");
                    oosSH.writeObject(msj);

                    // recibo respuesta

                    String respuestaSH = (String) oisSH.readObject();


                    // me comunico con el ServidorHoroscopo

                    // respondo a cliente
                    System.out.println("Respondiendo a cliente");
                    oosCli.writeObject(respuestaSH);

                } else {
                    // Aviso a los sockets
                    oosSH.writeObject(msj);
                    continuar = false;
                }

            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {

                /* cierro conexion cliente */
                if (oosCli != null)
                    oosCli.close();
                if (oisCli != null)
                    oisCli.close();
                if (socketCli != null)
                    socketCli.close();
                /* cierro conexion con servidores */
                if (oosSH != null)
                    oosSH.close();
                if (oisSH != null)
                    oisSH.close();
                if (socketSH != null)
                    socketSH.close();
                System.out.println("Conexion cerrada!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
