
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;



public class App {
    
    
    public static void main(String[] args) throws Exception {

        Scanner s= new Scanner(System.in);
        Cliente cli= new Cliente();
        String valor_signo, valor_fecha;
        int continuar= 1;
        String res;
        // LEO PARAMETROS DE ARCHIVO CONFIGURACION
        Properties props = new Properties();
        props.load(new FileInputStream("config.properties"));
        String SERVER_IP= props.getProperty("SERVERS.IP");
        int SERVER_PORT= Integer.parseInt(props.getProperty("SERVERS.PORT.CENTRAL")) ;
        //conecto con el servidor
        cli.conectarConServidor(SERVER_IP, SERVER_PORT);


        while(continuar == 1){
            System.out.println("Consulta un horoscopo: ");
            System.out.println("0 - Aries");
            System.out.println("1 - Tauro");
            System.out.println("2 - Geminis");
            System.out.println("3 - Cancer");
            System.out.println("4 - Leo");
            System.out.println("5 - Virgo");
            System.out.println("6 - Libra");
            System.out.println("7 - Escorpio");
            System.out.println("8 - Sagitario");
            System.out.println("9 - Capricornio");
            System.out.println("10 - Acuario");
            System.out.println("11 - Picis");
            
            System.out.print("Ingrese un Valor: ");
            valor_signo= s.nextLine();
            
            s.nextLine();
            System.out.println("Ingrese una fecha (dd/mm/yyyy)");
            valor_fecha= s.nextLine();
            
             
            //DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/mm/yyyy");
            
            res= cli.consultarServidorcentral(valor_signo, valor_fecha);

            System.out.println("Respuesta: "+res);

            System.out.println("Continuar programa?");
            System.out.println("1 - Si");
            System.out.println("2 - No");
            continuar= s.nextInt();
            
            /*
Aries, Tauro, Géminis, Cáncer, Leo, Virgo, Libra, Escorpio, Sagitario, Capricornio, Acuario y Piscis

*/
        }        
        cli.cerrarConexion();      

        s.close();


    }
}
