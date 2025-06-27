package Servidor;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

import Concurrente.ListaConcurrente;
import Concurrente.MapaConcurrente;
import Concurrente.PuertoConcurrente;
import Concurrente.SocketConcurrencia;
import Concurrente.Locks.Bakery;
import GUI.VentanaServidor;
import Mensaje.*;
import Mueble.Mueble;
import Usuario.Usuario;

public class OyenteCliente extends Thread{
	
	private SocketConcurrencia sc;
	private ListaConcurrente<Usuario> listaUsuarios;
	private MapaConcurrente<Usuario, SocketConcurrencia> canales;
	private MapaConcurrente<Mueble, Usuario> muebles;
	private VentanaServidor v;
	private PuertoConcurrente pc;
	private Bakery lock;
	
	public OyenteCliente(PuertoConcurrente pc, VentanaServidor v, Socket s, ListaConcurrente<Usuario> listaUsuarios, MapaConcurrente<Usuario, SocketConcurrencia> canales, MapaConcurrente<Mueble, Usuario> muebles, Bakery lock) throws IOException {
		this.sc = new SocketConcurrencia(s);
		this.listaUsuarios = listaUsuarios;
		this.canales = canales;
		this.muebles = muebles;
		this.v = v;
		this.pc = pc;
		this.lock = lock;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			boolean continuar = true;
			while (continuar) {
				Mensaje m = (Mensaje) sc.readObject();
				switch (m.getTipo()) {
					case MENSAJE_CONEXION:
						Usuario u = (Usuario) m.get();
						listaUsuarios.add(u);
						canales.put(u, sc);
						v.actualizarUsuarios(listaUsuarios.read());
						sc.writeObject(new MensajeConfConexion(pc));
						break;
					case MENSAJE_LISTA_USR:
						sc.writeObject(new MensajeConfListaUsr(listaUsuarios.read()));
						break;
					case MENSAJE_CERRAR_CONEXION:
						Usuario usr = (Usuario) m.get();
						listaUsuarios.erase(usr);
						muebles.removeByValue(usr);
						v.actualizarUsuarios(listaUsuarios.read());
						v.actualizarMuebles(muebles);
						sc.writeObject(new MensajeConfCerrarConexion());
						continuar = false;
						break;
					case MENSAJE_PEDIR:
						SimpleEntry<Mueble, Usuario> entry1 = (SimpleEntry<Mueble, Usuario>) m.get();
						Mueble mueblePedir = (Mueble) entry1.getKey();
						Usuario uProductor = null;
						//Buscamos el usuario asociado al mueble con dicho ID
						for(Mueble mueble : muebles.keySet()){
							if(mueble.equals(mueblePedir)){
								uProductor = muebles.get(mueble);
								break;
							}
						}
						//Si hemos encontrado el usuario asociado al mueble, se lo enviamos
						if(uProductor != null){
							//Buscamos el socket del usuario asociado al mueble
							SocketConcurrencia canal = canales.get(uProductor);
							if(canal != null){
								canal.writeObject(new MensajeEmitir(entry1.getValue(), mueblePedir));
							}
							else{
								//ERROR: No se ha encontrado el socket del usuario productor
								sc.writeObject(new MensajeError("El usuario productor no está conectado."));
							}
						}
						else{
							sc.writeObject(new MensajeError("El mueble que quieres comprar no existe."));
						}
						break;
					case MENSAJE_PREPARADO_CS:
						Map<String, Object> mapaaux = (Map<String, Object>) m.get();
						String ip = (String) mapaaux.get("ip");
						int puerto = (int) mapaaux.get("puerto");
						Mueble muebleSoli = (Mueble) mapaaux.get("muebleSolicitado");
						Usuario cliente = (Usuario) mapaaux.get("Cliente");
						SocketConcurrencia canal2 = canales.get(cliente);
						if(canal2 != null){
							canal2.writeObject(new MensajePreparadoSC(ip, puerto, muebleSoli, cliente));
						}
						else{
							//ERROR: No se ha encontrado el socket del usuario que solicita
							sc.writeObject(new MensajeError("El usuario que solicita no está conectado."));
						}
						break;
					case MENSAJE_ANIADIR_PROD:
						SimpleEntry<Usuario, Mueble> entry2 = (SimpleEntry<Usuario, Mueble>) m.get();
						//El put devuelve null si no existía previamente una entrada con dicha clave y el valor anterior
						//si ya existía
						Usuario estaba = muebles.put(entry2.getValue(), entry2.getKey());
						if(estaba != null){
							sc.writeObject(new MensajeError("El mueble que quieres añadir ya existe."));
							break;
						}
						v.actualizarMuebles(muebles);
						sc.writeObject(new MensajeConfAniadirProd(entry2.getValue()));
						break;
					case MENSAJE_ELIMINAR_PROD:
						//Debemos revisar que quién elimina el producto es también su propietario
						SimpleEntry<Usuario, Mueble> entry3 = (SimpleEntry<Usuario, Mueble>) m.get();
						Mueble muebl = (Mueble) muebles.findMuebleByTipoYId(entry3.getValue());
						if(muebl == null){
							sc.writeObject(new MensajeError("El mueble que quieres eliminar no existe."));
							break;
						}
						Usuario aux = (Usuario) muebles.get((Mueble) muebl);
						if(aux.get().equals(entry3.getKey().get())){
							muebles.remove((Mueble) muebl);
							sc.writeObject(new MensajeConfEliminarProd(muebl));
							v.actualizarMuebles(muebles);
						}
						else{
							sc.writeObject(new MensajeError("No tienes permisos para modificar este producto."));
						}
						break;
					case MENSAJE_MOD_PROD:
						//Debemos revisar que quién modifica un producto es también su propietario
						SimpleEntry<Usuario, List<Mueble>> entry4 = (SimpleEntry<Usuario, List<Mueble>>) m.get();
						Mueble mueblito = (Mueble) muebles.findMuebleByTipoYId(entry4.getValue().get(0));
						if(mueblito == null){
							sc.writeObject(new MensajeError("El mueble que quieres modificar no existe."));
							break;
						}
						Usuario u1 = (Usuario) muebles.get((Mueble) mueblito);
						if(entry4.getKey().get().equals(u1.get())){
							muebles.remove(mueblito);
							muebles.put(entry4.getValue().get(1), entry4.getKey());
							sc.writeObject(new MensajeConfModProd(mueblito,entry4.getValue().get(1)));
							v.actualizarMuebles(muebles);
						}
						else{
							sc.writeObject(new MensajeError("No tienes permisos para modificar este producto."));
						}
						break;
					default:
						break;
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

