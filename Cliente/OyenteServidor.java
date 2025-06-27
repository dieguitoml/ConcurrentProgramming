	package Cliente;

	import java.io.IOException;
	import java.io.InputStream;
	import java.io.ObjectInputStream;
	import java.io.ObjectOutputStream;
	import java.io.OutputStream;
	import java.net.ServerSocket;
	import java.net.Socket;
	import java.util.AbstractMap.SimpleEntry;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import Concurrente.ListaConcurrente;
	import Concurrente.MapaConcurrente;
	import Concurrente.PuertoConcurrente;
	import Concurrente.SocketConcurrencia;
	import GUI.VentanaCliente;
	import Mensaje.*;
	import Mueble.Mueble;
	import Usuario.Usuario;

	public class OyenteServidor extends Thread{

		private SocketConcurrencia defaultSocket;
		private List<Usuario> listaUsuarios;
		private MapaConcurrente<Mueble, Usuario> muebles;
		private PuertoConcurrente pc;
		private Usuario u;
		private VentanaCliente vc;
		private int original_id = 1;
		private final int MAX_REQUEST = 10;
		private int current_id = 2;

		public OyenteServidor( VentanaCliente vc, Usuario u, SocketConcurrencia ss) {
			this.defaultSocket = ss;
			this.listaUsuarios = new ArrayList<>();
			this.muebles = new MapaConcurrente<Mueble,Usuario>();
			this.u = u;
			this.vc = vc;
		}

		@SuppressWarnings("unchecked")
		public void run() {
				boolean continuar = true;
				try {
					while (continuar) {
						Mensaje m = (Mensaje) defaultSocket.readObject();
						switch (m.getTipo()) {
							case MENSAJE_CONF_CONEXION:
								this.pc = (PuertoConcurrente) m.get();
								vc.log("Conectado con exito al servidor.",original_id);
								break;
							case MENSAJE_CONF_LISTA_USR:
								listaUsuarios = (List<Usuario>) m.get();
								vc.actualizarTablaUsuarios(listaUsuarios); // â†� necesitas este mÃ©todo en VentanaCliente
								break;
							case MENSAJE_EMITIR:
								// AquÃ­ hacemos productor-consumidor de puertos
								Map<String, Object> mapa = (HashMap<String, Object>) m.get();
								int puerto1 = pc.extraerPuerto();
								ServerSocket ss = new ServerSocket(puerto1); //Crea el ss para que sea usado por el otro cliente
								String ip1 = "127.0.0.1";
								defaultSocket.writeObject(new MensajePreparadoCS(ip1, puerto1, (Usuario) mapa.get("cliente"), (Mueble) mapa.get("muebleSolicitado")));
								Emisor e = new Emisor(defaultSocket, ss, this.muebles, this.vc,current_id,pc,puerto1);
								e.start();
								current_id++;
								break;
							case MENSAJE_PREPARADO_SC:
								Map<String, Object> mapa2 = (HashMap<String, Object>) m.get();
								int puerto2 = (int) mapa2.get("puerto");
								String ip2 = (String) mapa2.get("ip").toString();
								Mueble muebleSolicit = (Mueble) mapa2.get("muebleSolicitado");
								Socket s = new Socket(ip2, puerto2); //Crea el socket para que sea usado por el otro cliente
								Receptor r = new Receptor(defaultSocket, s, this.vc, this.muebles, this.pc, puerto2, muebleSolicit, this.u, this.current_id);
								r.start();
								current_id++;
								break;
							case MENSAJE_CONF_ANIADIR_PROD:
								Mueble mueble = (Mueble) m.get();
								vc.log("Mueble aÃ±adido correctamente.",original_id);
								muebles.put(mueble, u);
								vc.actualizarMuebles(muebles);
								break;
							case MENSAJE_CONF_ELIMINAR_PROD:
								Mueble moble = (Mueble) m.get();
								muebles.remove(moble);
								vc.actualizarMuebles(muebles);
								vc.log("Mueble eliminado correctamente.",original_id);
								break;
							case MENSAJE_CONF_MOD_PROD:
								SimpleEntry<Mueble, Mueble> entr = (SimpleEntry<Mueble,Mueble>)m.get();
								Mueble elimin = muebles.findMuebleByTipoYId((Mueble)entr.getKey());
								muebles.remove(elimin);
								muebles.put(entr.getValue(), u);
								vc.log("Mueble modificado correctamente.",original_id);
								vc.actualizarMuebles(muebles);
								break;
							case MENSAJE_CONF_CERRAR_CONEXION:
								defaultSocket.close();
								vc.log("Cerrando conexion...",original_id);
								continuar = false;
								break;
							case MENSAJE_ERROR:
								vc.log("Error: " + m.get(),original_id);
								break;
							default:
								break;
						}
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	}
		
	}
