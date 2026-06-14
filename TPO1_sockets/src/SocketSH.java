import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

class SocketSH extends Thread {
    private Socket socketCli = null;
    private ObjectInputStream oisCli = null;
    private ObjectOutputStream oosCli = null;
    private String prediccion;
    // posible mejora hacer una matriz cantidadSignos X cantidadDeDias

    public SocketSH(Socket socket) {
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
            while(continuar){
                //leo parametro
                System.out.println("ServidorSH - Esperando mensaje ");
                String msj= (String)oisCli.readObject(); 
            
                if (!msj.equals("-1")) {

                    i= Integer.parseInt(msj);
                    prediccion = ServidorSH.getPrediccion( ( r.nextInt(1, 10)+i)%4  );    
                    //respondo  a cliente
                    System.out.println("Servidor SH - Enviando datos");
                    oosCli.writeObject(prediccion);
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
