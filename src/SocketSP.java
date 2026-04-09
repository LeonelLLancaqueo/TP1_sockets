import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

class SocketSP extends Thread {
    private Socket socketCli = null;
    private ObjectInputStream oisCli = null;
    private ObjectOutputStream oosCli = null;
    private String pronostico;
    // posible mejora hacer una matriz cantidadSignos X cantidadDeDias

    public SocketSP(Socket socket) {
        this.socketCli = socket;
    }

    public void run(){
        try {
            
            Random r= new Random();
            // instancio objeto entrada y salida
            this.oosCli= new ObjectOutputStream(socketCli.getOutputStream());
            this.oisCli= new ObjectInputStream(socketCli.getInputStream()) ;
            boolean continuar= true;
            int i;
            String msj; 
            while(continuar){
                //leo parametro
                System.out.println("ServidorSP - Esperando mensaje ");
                msj= (String)oisCli.readObject(); 
            
                if (!msj.equals("-1")) {
                    i= Integer.parseInt(msj.substring(0,1));
                    pronostico = ServidorSP.getPronostico( ( r.nextInt(1, 10)+i)%9  );    
                    System.out.println("Servidor SP - Enviando datos");
                    oosCli.writeObject(pronostico);
                }else{
                    continuar= false;
                }
                
            }

        } catch (Exception e) {
        
            e.printStackTrace();
        } finally {
            try {
                /*  cierro conexion cliente*/
                if( oosCli !=null ) oosCli.close();
                if( oisCli !=null ) oisCli.close();
                if( socketCli != null ) socketCli.close();
                System.out.println("Conexion cerrada!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
