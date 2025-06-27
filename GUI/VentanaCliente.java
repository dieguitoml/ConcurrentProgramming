package GUI;

import javax.swing.*;
import javax.swing.table.TableModel;

import Cliente.OyenteServidor;
import Concurrente.ListaConcurrente;
import Concurrente.MapaConcurrente;
import Concurrente.PuertoConcurrente;
import Concurrente.SocketConcurrencia;
import Concurrente.Locks.RompeEmpate;
import Mensaje.*;
import Mueble.Mueble;
import Servidor.OyenteCliente;
import Usuario.Usuario;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class VentanaCliente extends JFrame {
    private String nombreUsuario;
    private static JTable UserTable;
    private JTable FurnitureTable;
    private SocketConcurrencia s;
	private List<Usuario> listaUsuarios;
	private MapaConcurrente<Mueble, Usuario> muebles;
    private Usuario usuario;
    private JTextArea terminalArea;
    private RompeEmpate lock;
    private final int MAX_REQUEST = 10;
    public VentanaCliente(String nombreUsuario) {
    	this.lock = new RompeEmpate(MAX_REQUEST);
        this.nombreUsuario = nombreUsuario;
        setTitle("User - " + nombreUsuario);
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Barra superior
        JToolBar toolbar = new JToolBar();
        ImageIcon originalIcon1 = new ImageIcon("iconos/pngConect.png");
        Image imagenEscalada1 = originalIcon1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton btnConectar = new JButton("Connect",new ImageIcon(imagenEscalada1));
        ImageIcon originalIcon2 = new ImageIcon("iconos/furnit.png");
        Image imagenEscalada2 = originalIcon2.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon originalIcon3 = new ImageIcon("iconos/usr.png");
        Image imagenEscalada3 = originalIcon3.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        JButton btnUsr = new JButton("Users",new ImageIcon(imagenEscalada3));
        ImageIcon originalIcon4 = new ImageIcon("iconos/add.png");
        Image imagenEscalada4 = originalIcon4.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        JButton btnAnadir = new JButton("Add furniture",new ImageIcon(imagenEscalada4));
        ImageIcon originalIcon5 = new ImageIcon("iconos/delete.png");
        Image imagenEscalada5 = originalIcon5.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        JButton btnEliminar = new JButton("Erase furniture", new ImageIcon(imagenEscalada5));
        ImageIcon originalIcon6 = new ImageIcon("iconos/modify.png");
        Image imagenEscalada6 = originalIcon6.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        JButton btnModificar = new JButton("Modify furniture", new ImageIcon(imagenEscalada6));
        ImageIcon originalIcon7 = new ImageIcon("iconos/order.png");
        Image imagenEscalada7 = originalIcon7.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        JButton btnPedir = new JButton("Order furniture", new ImageIcon(imagenEscalada7));
        toolbar.add(btnConectar);
        toolbar.add(btnUsr);
        toolbar.add(btnAnadir);
        toolbar.add(btnEliminar);
        toolbar.add(btnModificar);
        toolbar.add(btnPedir);
        add(toolbar, BorderLayout.NORTH);

        UserTable = new JTable();
        FurnitureTable = new JTable();

        JScrollPane scrollUsuarios = new JScrollPane(UserTable);
        scrollUsuarios.setBorder(BorderFactory.createTitledBorder("User table"));

        JScrollPane scrollMuebles = new JScrollPane(FurnitureTable);
        scrollMuebles.setBorder(BorderFactory.createTitledBorder("Furniture info"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollUsuarios, scrollMuebles);
        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);

        terminalArea = new JTextArea(6, 50);
        terminalArea.setEditable(false);
        JScrollPane terminalScroll = new JScrollPane(terminalArea);
        add(terminalScroll, BorderLayout.SOUTH);

        setVisible(true);

        // Aquí puedes conectar listeners a los botones

        //Conecta con el servidor
        btnConectar.addActionListener(e -> {
			try {
				mostrarDialogoConexion();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        
        //Muestra la tabla de usuarios
        btnUsr.addActionListener(e -> {
			try {
				s.writeObject(new MensajeListaUsr());
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        //Añade el producto que le introducimos
        btnAnadir.addActionListener(e -> {
            Mueble m = pedirMueble("Datos del mueble a añadir",true);
            if (m != null) {
                try {
                    if(s != null){
                        s.writeObject(new MensajeAniadirProd(this.usuario, m));
                    } else {
                        JOptionPane.showMessageDialog(this, "No se ha podido añadir el mueble", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } 
            else {
            JOptionPane.showMessageDialog(this, "No se ha podido añadir el mueble", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Elimina el producto que le introducimos (debe existir en la tabla) 
         btnEliminar.addActionListener(e -> {
            Mueble m = pedirMueble("Datos del mueble a eliminar", false);
            if (m != null) {
                try {
                    s.writeObject(new MensajeEliminarProd(this.usuario, m));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } 
            else {
                JOptionPane.showMessageDialog(this, "No se ha podido eliminar el mueble", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Se le pide un mueble a modificar y un nuevo mueble, se eliminar el originar y se añade el nuevo
        btnModificar.addActionListener(e -> {
            Mueble viejo = pedirMueble("Datos del mueble a modificar",false);
            Mueble nuevo = pedirMueble("Datos del mueble que lo sustituye",true);
            if (viejo != null && nuevo != null) {
                try {
                    s.writeObject(new MensajeModProd(this.usuario, viejo, nuevo));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } 
            else {
                JOptionPane.showMessageDialog(this, "No se ha podido modificar el mueble", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPedir.addActionListener(e -> {
            Mueble mueblePedir = pedirMueble("Datos del mueble a pedir", false);
            if (mueblePedir != null) {
                try {
                    s.writeObject(new MensajePedir(mueblePedir, this.usuario));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Introduzca un mueble válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    if (s != null && usuario != null) {
                        s.writeObject(new MensajeCerrarConexion(usuario));
                    }
                } catch (IOException ex) {
                }
            }
        });
    }

    private void mostrarTablaUsuarios() {
		// TODO Auto-generated method stub
		
	}

	private void mostrarDialogoConexion() throws UnknownHostException, IOException {
        JTextField campoIP = new JTextField("127.0.0.1");
        JTextField campoPuerto = new JTextField("99");
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("IP:"));
        panel.add(campoIP);
        panel.add(new JLabel("Port:"));
        panel.add(campoPuerto);

        int res = JOptionPane.showConfirmDialog(this, panel, "Connect to server", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            String ip = campoIP.getText();
            int puerto = Integer.parseInt(campoPuerto.getText());
            // aquí llamas al método que conecta con el servidor
            Socket ss = new Socket(ip,puerto);
            this.usuario = new Usuario(nombreUsuario);
            ObjectInputStream fin = new ObjectInputStream(ss.getInputStream());
			ObjectOutputStream  fout = new ObjectOutputStream (ss.getOutputStream());
			this.s = new SocketConcurrencia(ss, fin, fout);
			s.writeObject(new MensajeConexion(this.usuario));
            OyenteServidor oyente = new OyenteServidor(this, this.usuario, this.s);
            oyente.start(); //ejecuta en segundo plano
	   }
        }

	public void actualizarTablaUsuarios(List<Usuario> listaUsuarios2) {
	    // setear nuevo TableModel o actualizarlo
	    UserTable.setModel(new ModeloTablaUsuarios(listaUsuarios2));
	}
	
    public void actualizarMuebles(MapaConcurrente<Mueble, Usuario> mapa) {
		FurnitureTable.setModel(new ModeloTablaMuebles(mapa.read())); // usamos read() para obtener copia segura
    }

	private Mueble pedirMueble(String mensaje, boolean requireDescripcion) {
	    JTextField huecoTipo = new JTextField();
	    JTextField huecoId = new JTextField();
	    JTextField huecoDescripcion = new JTextField();

	    JPanel panel = new JPanel(new GridLayout(requireDescripcion ? 3 : 2, 2));
	    panel.add(new JLabel("Tipo de mueble:"));
	    panel.add(huecoTipo);
	    panel.add(new JLabel("ID del mueble:"));
	    panel.add(huecoId);

	    if (requireDescripcion) {
	        panel.add(new JLabel("Descripción:"));
	        panel.add(huecoDescripcion);
	    }

	    int res = JOptionPane.showConfirmDialog(this, panel, mensaje, JOptionPane.OK_CANCEL_OPTION);
	    if (res == JOptionPane.OK_OPTION) {
	        try {
	            String tipo = huecoTipo.getText().trim();
	            int id = Integer.parseInt(huecoId.getText().trim());
	            String descripcion = requireDescripcion ? huecoDescripcion.getText().trim() : "N/A";

	            if (tipo.isEmpty() || (requireDescripcion && descripcion.isEmpty())) {
	                JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.", "Error", JOptionPane.ERROR_MESSAGE);
	                return null;
	            }

	            return new Mueble(tipo, id, descripcion);
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	    return null;
	}
    
    public void log(String mensaje, int id) {//Usamos rompeEmpate para acceder a escribir en pantalla 
    	lock.takeLock(id);
        terminalArea.append(mensaje + "\n");
        terminalArea.setCaretPosition(terminalArea.getDocument().getLength()); // autoscroll
        lock.releaseLock(id);
    }
    
}

