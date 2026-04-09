

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class Cliente {
    
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private Socket socket = null;
    final String SERVER_CENTRAL_IP=  "127.0.0.1";
    final int SERVER_PORT = 5432;
    
    public void conectarConServidor(String ip, int puerto){
        try {
            // instancio el server con la IP y el PORT
            System.out.println("iniciando conexion con Server Central");
            socket = new Socket(SERVER_CENTRAL_IP,SERVER_PORT);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String consultarServidorcentral(int horoscopo, Date fecha){
        String res= null;

        if(this.socket != null){
            try {
                System.out.println("Envio consulta: horoscopo: "+ horoscopo +" y fecha: " + fecha );
                oos.writeObject((Integer) horoscopo);
                
                res = (String)ois.readObject();
                // muestro la respuesta que envio el server
                System.out.println("Respuesta recibida: "+ res);
            } catch (Exception e) {
                e.printStackTrace();
            }            
        }
        return res;
    }
    public void cerrarConexion(){
        
        try {
            oos.writeObject((Integer)999);

            if( ois != null ) ois.close();
            if( oos != null ) oos.close();
            if( socket != null ) socket.close();            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public String consultarHoroscopo(int horoscopo){
        String res= null;
        if(this.socket != null){
            try {
                System.out.println("Envio consulta: horoscopo: "+ horoscopo);
                oos.writeObject((Integer) horoscopo);
                
                res = (String)ois.readObject();
                // muestro la respuesta que envio el server
                System.out.println("Respuesta recibida: "+ res);
            } catch (Exception e) {
                e.printStackTrace();
            }            
        }
        return res;
    }

}
