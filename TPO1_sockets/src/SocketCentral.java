
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketCentral extends Thread {
    private Socket socketCli = null, socketSH = null, socketSP = null;
    private ObjectInputStream oisCli = null, oisSH = null, oisSP = null;
    private ObjectOutputStream oosCli = null, oosSH = null, oosSP = null;
    private int PORT_SP;
    private int PORT_SH;
    private String IP_SH;
    private String IP_SP;
    private String msj_SH, msj_SP, msj;
    

    public SocketCentral(Socket socket, String ip_sp, String ip_sh, int spPort, int shPort) {
        this.socketCli = socket;
        this.PORT_SH = shPort;
        this.PORT_SP = spPort;
        this.IP_SH = ip_sh;
        this.IP_SP = ip_sp;
    }
    private void conectarConSH() throws Exception{

            socketSH = new Socket(IP_SH, PORT_SH);
            this.oosSH = new ObjectOutputStream(socketSH.getOutputStream());
            this.oisSH = new ObjectInputStream(socketSH.getInputStream());
 
    }
    private void cerrarConexionSH() throws Exception{
 
                /* cierro conexion con servidor SH*/
                if (oosSH != null)
                    oosSH.close();
                if (oisSH != null)
                    oisSH.close();
                if (socketSH != null)
                    socketSH.close();
                System.out.println("Conexion con SH cerrada!");

    }
    private void conectarConSP() throws Exception{
       
        this.socketSP = new Socket(IP_SP, PORT_SP);
        this.oisSP = new ObjectInputStream(socketSP.getInputStream());
        this.oosSP = new ObjectOutputStream(socketSP.getOutputStream());

    }
    private void cerrarConexionSP() throws Exception{

                /* cierro conexion con servidorSP */
                if (oosSP != null)
                    oosSP.close();
                if (oisSP != null)
                    oisSP.close();
                if (socketSP != null)
                    socketSP.close();
                System.out.println("Conexion con SP cerrada!");

    }
    private void cerrarConexionCliente() throws Exception{

                /* cierro conexion cliente */
                if (oosCli != null)
                    oosCli.close();
                if (oisCli != null)
                    oisCli.close();
                if (socketCli != null)
                    socketCli.close();

    }
    private String enviarMsjSH() throws Exception{
        this.msj_SH= msj.substring(0, msj.indexOf(';'));
        System.out.println("Enviando consulta a ServidorSH");
        oosSH.writeObject(msj_SH);
        return (String) oisSH.readObject();
    }
    private String enviarMsjSP() throws Exception{
        this.msj_SP= msj.substring(msj.indexOf(';')+1, msj.length());
        System.out.println("Enviando consulta a ServidorSP");
        oosSP.writeObject(msj_SP);
        return (String) oisSP.readObject();
    }


    // HACER MATRIZ CON HOROSCOPO
    public void run() {
        try {
            String respuestaSH;
            String respuestaSP;
            boolean continuar = true;

            System.out.println("Socket Central - Abriendo Socket e Instanciando objetos I/O con ServerSH");
            conectarConSH();

            System.out.println("Socket Central - Abriendo Socket e Instanciando objetos I/O con ServerSP");
            conectarConSP();
            
            System.out.println("Socket Central - Instanciando objetos I/O con ServerCliente");
            this.oisCli = new ObjectInputStream(socketCli.getInputStream());
            this.oosCli = new ObjectOutputStream(socketCli.getOutputStream());



            while (continuar) {

                System.out.println("Servidor Central - Esperando msj cliente");
                this.msj = (String) oisCli.readObject();

                if (!msj.equals("-1") ) {

                    // dividir mensaje
                    System.out.println("Mensaje recibido: " + msj );

                    respuestaSH= enviarMsjSH();

                    respuestaSP= enviarMsjSP();

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
                cerrarConexionCliente();
                cerrarConexionSH();
                cerrarConexionSP();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
