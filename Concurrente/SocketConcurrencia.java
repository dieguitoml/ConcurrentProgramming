package Concurrente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import Concurrente.Locks.Lock;
import Concurrente.Locks.Ticket;
import Mensaje.Mensaje;
//Clase que controla el acceso a cada uno de los sockets
//Para controlar la cocurrencia utilizamos los locks implementados
public class SocketConcurrencia {
	private Socket s;
	private ObjectOutputStream fout;
	private ObjectInputStream fin;
	private Lock lock;
	
	public SocketConcurrencia(String ip, int port) throws UnknownHostException, IOException {
		this.s = new Socket(ip, port);
		this.fin = new ObjectInputStream(s.getInputStream());
        this.fout = new ObjectOutputStream (s.getOutputStream()); // true = autoFlush activado
		this.lock = new Ticket();
	}
	
	public SocketConcurrencia (Socket enter) throws IOException {
		this.s = enter;
		this.fout = new ObjectOutputStream(s.getOutputStream());
		this.fin = new ObjectInputStream(s.getInputStream());
		this.lock = new Ticket();
	}
	
	public SocketConcurrencia(Socket ss, ObjectInputStream fin2, ObjectOutputStream fout2) {
		// TODO Auto-generated constructor stub
		this.s = ss;
		this.fin = fin2;
		this.fout = fout2;
		this.lock = new Ticket();
	}

	public void writeObject(Mensaje m) throws IOException {
		this.lock.takeLock();
		fout.writeObject(m);
		fout.flush();
		this.lock.releaseLock();
	}
	
	public void close() throws IOException {
		fout.close();
		fin.close();
		s.close();
	}
	
	public Object readObject() throws ClassNotFoundException, IOException {
		return fin.readObject();
	}

	public void reset() throws IOException {
		this.fout.reset();
	}
	
}
