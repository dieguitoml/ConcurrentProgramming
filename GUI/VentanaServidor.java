package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

import Concurrente.MapaConcurrente;
import Mueble.Mueble;
import Usuario.Usuario;

public class VentanaServidor extends JFrame {
    private JTable tablaUsuarios;
    private JTable tablaMuebles;

    public VentanaServidor() {
        setTitle("Servidor - Monitor de estado");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tabla de usuarios
        tablaUsuarios = new JTable();
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        scrollUsuarios.setBorder(BorderFactory.createTitledBorder("Usuarios conectados"));

        // Tabla de muebles
        tablaMuebles = new JTable();
        JScrollPane scrollMuebles = new JScrollPane(tablaMuebles);
        scrollMuebles.setBorder(BorderFactory.createTitledBorder("Muebles registrados"));

        // SplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollUsuarios, scrollMuebles);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actualizarUsuarios(List<Usuario> lista) {
        tablaUsuarios.setModel(new ModeloTablaUsuarios(lista));
    }

    public void actualizarMuebles(MapaConcurrente<Mueble, Usuario> mapa) {
        tablaMuebles.setModel(new ModeloTablaMuebles(mapa.read())); // usamos read() para obtener copia segura
    }
}
