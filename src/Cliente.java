

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private Socket socket = null;


    public void conectarConServidor(String ip, int puerto){
        try {
            
            
            // instancio el server con la IP y el PORT
            System.out.println("iniciando conexion con Server Central");
            socket = new Socket(ip,puerto);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());   
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String consultarServidorcentral(String horoscopo, String fecha){
        String res= null;
        String msj;
        if(this.socket != null){
            try {
                String msj_SP, msj_SH;
                System.out.println("Envio consulta: horoscopo: "+ horoscopo +" y fecha: " + fecha );
                msj= horoscopo+";"+fecha;
                oos.writeObject(msj);
                res = (String)ois.readObject();
                msj_SH= "Horoscopo: " + res.substring(0, res.indexOf(';'));
                msj_SP= "Pronostico Tiempo: "+res.substring(res.indexOf(';')+1,msj.length());
                // procesar mensaje
                res= msj_SH+"/n"+msj_SP; 

            } catch (Exception e) {
                e.printStackTrace();
            }            
        }
        return res;
    }
    public void cerrarConexion(){
        
        try {
            oos.writeObject("-1");

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
