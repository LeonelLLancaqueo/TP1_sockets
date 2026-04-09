
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
    private String msj_SH, msj_SP, msj;
    

    public SocketCentral(Socket socket, String ip, int spPort, int shPort) {
        this.socketCli = socket;
        this.PORT_SH = shPort;
        this.PORT_SP = spPort;
        this.LOCAL_IP = ip;
    }

    // HACER MATRIZ CON HOROSCOPO
    public void run() {
        try {
            String respuestaSH;
            String respuestaSP;

            System.out.println("Socket Central - Instanciando objetos I/O con ServerCliente");
            this.oisCli = new ObjectInputStream(socketCli.getInputStream());
            this.oosCli = new ObjectOutputStream(socketCli.getOutputStream());

            System.out.println("Socket Central - Abriendo Socket e Instanciando objetos I/O con ServerSH");
            socketSH = new Socket(LOCAL_IP, PORT_SH);
            this.oosSH = new ObjectOutputStream(socketSH.getOutputStream());
            this.oisSH = new ObjectInputStream(socketSH.getInputStream());


            System.out.println("Socket Central - Abriendo Socket e Instanciando objetos I/O con ServerSP");
            this.socketSP = new Socket(LOCAL_IP, PORT_SP);
            this.oisSP = new ObjectInputStream(socketSP.getInputStream());
            this.oosSP = new ObjectOutputStream(socketSP.getOutputStream());

            boolean continuar = true;

            while (continuar) {

                System.out.println("Servidor Central - Esperando msj cliente");
                String msj = (String) oisCli.readObject();
                if (!msj.equals("-1") ) {

                    // dividir mensaje
                    System.out.println("Mensaje recibido: " + msj );
                    msj_SH= msj.substring(0, msj.indexOf(';'));
                    msj_SP= msj.substring(msj.indexOf(';')+1, msj.length());
                    

                    System.out.println("Enviando consulta a ServidorSH");
                    oosSH.writeObject(msj_SH);
                    respuestaSH = (String) oisSH.readObject();
                    
                    System.out.println("Enviando consulta a ServidorSP");
                    oosSP.writeObject(msj_SP);
                    respuestaSP = (String) oisSP.readObject();
                    // me comunico con el ServidorSP

                    // respondo a cliente
                    System.out.println("Respondiendo a cliente");
                    msj= respuestaSH+";"+respuestaSP;
                    oosCli.writeObject(msj);

                }else{
                    // Aviso a los sockets
                    oosSH.writeObject(msj);
                    oosSP.writeObject(msj);
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
                /* cierro conexion con servidor SH*/
                if (oosSH != null)
                    oosSH.close();
                if (oisSH != null)
                    oisSH.close();
                if (socketSH != null)
                    socketSH.close();
                /* cierro conexion con servidorSP */
                if (oosSP != null)
                    oosSP.close();
                if (oisSP != null)
                    oisSP.close();
                if (socketSP != null)
                    socketSP.close();
                System.out.println("Conexion cerrada!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
