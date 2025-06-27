package Cliente;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.AbstractMap.SimpleEntry;

import Concurrente.MapaConcurrente;
import Concurrente.PuertoConcurrente;
import Concurrente.SocketConcurrencia;
import GUI.VentanaCliente;
import Mensaje.*;
import Mueble.Mueble;
import Usuario.Usuario;

public class Receptor extends Thread {

    private SocketConcurrencia canal;
    private SocketConcurrencia defaultSocket;
    private VentanaCliente vc;
    private MapaConcurrente<Mueble, Usuario> muebles;
    private PuertoConcurrente pc;
    private int puerto;
    private Mueble muebleSolicitado;
    private Usuario usuario;
    private int id;

    public Receptor(SocketConcurrencia defaultSocket, Socket s, VentanaCliente vc, MapaConcurrente<Mueble, Usuario> muebles, PuertoConcurrente pc, int puerto2, Mueble m, Usuario u, int id) throws IOException {
        ObjectInputStream fin = new ObjectInputStream(s.getInputStream());
        ObjectOutputStream  fout = new ObjectOutputStream (s.getOutputStream());
        this.canal = new SocketConcurrencia(s, fin, fout);
        this.vc = vc;
        this.puerto = puerto2;
        this.muebles = muebles;
        this.pc = pc;
        this.muebleSolicitado = m;
        this.usuario = u;
        this.defaultSocket = defaultSocket;
        this.id = id;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void run() {
        try {
            canal.writeObject(new MensajeClientesConexion());

            boolean continuar = true;
            while (continuar) {
                Mensaje m = (Mensaje) canal.readObject();
                switch (m.getTipo()) {
                    case MENSAJE_CLIENTES_CONF_CONEXION:
                        //Mensaje de conexion
                        vc.log("Establecida la conexion con el cliente productor.",id);
                        canal.writeObject(new MensajeClientesPedirProd(muebleSolicitado));
                        break;

                    case MENSAJE_CLIENTES_CONF_PEDIR_PROD:
                    	SimpleEntry<Mueble,Usuario> solit = (SimpleEntry<Mueble, Usuario>) m.get();
                    	defaultSocket.writeObject(new MensajeAniadirProd(this.usuario, solit.getKey()));
                    	muebles.put(solit.getKey(), this.usuario);
                        vc.actualizarMuebles(muebles);
                        vc.log("Producto Solicitado Recibido",id);
                        canal.writeObject(new MensajeClientesConfEnviarProd());
                        canal.writeObject(new MensajeClientesCerrarConexion());
                        break;
                    case MENSAJE_CLIENTES_CONF_CERRAR_CONEXION:
                        //Mensaje de cierre de conexion
                        vc.log("Se ha cerrado la conexion con el cliente productor.",id);
                    	canal.close();
                        continuar = false;
                        break;
                    case MENSAJE_ERROR:
                        vc.log("Error: " + m.get(),id);
                        break;
                    default:
                        break;
                }
            }

            canal.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
