package Cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Concurrente.MapaConcurrente;
import Concurrente.PuertoConcurrente;
import Concurrente.SocketConcurrencia;
import GUI.VentanaCliente;
import Mensaje.*;
import Mueble.Mueble;
import Usuario.Usuario;

public class Emisor extends Thread {
    
    private ServerSocket ss;
    private SocketConcurrencia server;
    private MapaConcurrente<Mueble,Usuario> mapa;
    private VentanaCliente vc;
    private Mueble muebleAEnviar;
    private int id;
    private PuertoConcurrente pc;
    private int puerto;

    public Emisor(SocketConcurrencia server, ServerSocket ss, MapaConcurrente<Mueble, Usuario> muebles, VentanaCliente vc, int id, PuertoConcurrente pc, int puerto1){
        this.ss = ss;
        this.mapa = muebles;
        this.vc = vc;
        this.server = server;
        this.id = id;
        this.pc = pc;
        this.puerto = puerto1;
    }

    @Override
    public void run() {
        try{
            Socket s = ss.accept();
            SocketConcurrencia canal = new SocketConcurrencia(s);

            boolean continuar = true;
			while (continuar) {
				Mensaje m = (Mensaje) canal.readObject();
				switch (m.getTipo()){
                    case MENSAJE_CLIENTES_CONEXION:
                        canal.writeObject(new MensajeClientesConfConexion());
                        vc.log("Establecida conexion con cliente que solicita.",id);
                        break;
                    case MENSAJE_CLIENTES_PEDIR_PROD:
                        //Aqui puede haber problemas de concurrencia, entre que consultamos que sigue teniendo el producto y lo eliminamos
                        // tener cuidado
                    	this.muebleAEnviar = (Mueble) m.get();
                    	Mueble find;
                    	if((find = mapa.findMuebleByTipoYId(this.muebleAEnviar))!=null) {
                            Usuario u = mapa.get(find);
                    		server.writeObject(new MensajeEliminarProd(u, find));
                    		canal.writeObject(new MensajeClientesConfPedirProd(find, u));
                    		this.mapa.remove(find);
                    		vc.actualizarMuebles(mapa);

                    	}else {
                    		canal.writeObject(new MensajeError("No hay ningun producto con ese tipo y id."));
                    	}
                        break;  
                    case MENSAJE_CLIENTES_CONF_ENVIAR_PROD:
                    	//Podemos mostrar un mensaje
                    	vc.log("El cliente ha recibido el producto solicitado.", id);
                        break;
                    case MENSAJE_CLIENTES_CERRAR_CONEXION:
                        canal.writeObject(new MensajeClientesConfCerrarConexion());
                        vc.log("Conexion con el cliente ha finalizado.",id);
                        ss.close();
                        s.close();
                        pc.aniadirPuerto(puerto);
                        continuar = false;
                        break;
                    case MENSAJE_ERROR:
                        vc.log("Error: " + m.get(),id);
                        break;
                    default:
                        break;

                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
