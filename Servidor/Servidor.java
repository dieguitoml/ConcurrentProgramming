package Servidor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import Concurrente.ListaConcurrente;
import Concurrente.MapaConcurrente;
import Concurrente.PuertoConcurrente;
import Concurrente.SocketConcurrencia;
import Concurrente.Locks.Bakery;
import GUI.VentanaServidor;
import Mueble.Mueble;
import Usuario.Usuario;

public class Servidor {
		private static final int puerto = 99;
		//Aquï¿½ van las tablas compartidas, para las cuales hay que usar concurrencia OJO	
		private static ListaConcurrente<Usuario> tablaUsuarios;
		private static MapaConcurrente<Usuario, SocketConcurrencia> canales;
		private static MapaConcurrente<Mueble, Usuario> muebles;
		private static VentanaServidor ventana;
		private static PuertoConcurrente pc = new PuertoConcurrente();
		private static final int MAX_REQUEST = 10;

		public Servidor(){
			/*
			Servidor.tablaUsuarios = new ListaConcurrente<>();
			Servidor.canales = new MapaConcurrente<>();
			Servidor.muebles = new MapaConcurrente<>();*/
		}
		
		private static void iniciarServidor() {
			Thread servidorThread = new Thread(() -> {
				try (ServerSocket ss = new ServerSocket(puerto)) {
					while (true) {
						Socket s = ss.accept();
						Bakery lock = new Bakery(MAX_REQUEST);
						OyenteCliente oyente = new OyenteCliente(pc,
							ventana, s, tablaUsuarios, canales, muebles,lock);
						new Thread(oyente).start();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			servidorThread.start();
		}
		

		public static void main(String[] args) throws IOException {
			// Crear la GUI en el hilo de eventos de Swing
			SwingUtilities.invokeLater(() -> {
				Servidor.ventana = new VentanaServidor();
			});
		
			// Inicializa estructuras concurrentes
			Servidor.tablaUsuarios = new ListaConcurrente<>();
			Servidor.canales = new MapaConcurrente<>();
			Servidor.muebles = new MapaConcurrente<>();
		   iniciarServidor();
	    }
}
