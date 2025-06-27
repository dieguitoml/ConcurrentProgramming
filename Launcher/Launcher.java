package Launcher;
import javax.swing.*;

import Cliente.Cliente;
import Servidor.Servidor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Launcher {

    public static void main(String[] args) {
        // Crear la ventana principal
        JFrame frame = new JFrame("Lanzador de aplicacion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null); // Centrar en pantalla

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Texto
        JLabel label = new JLabel("¿Que quieres crear?", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(label, BorderLayout.NORTH);

        // Botones
        JButton clienteBtn = new JButton("Cliente");
        JButton servidorBtn = new JButton("Servidor");

        JPanel botonesPanel = new JPanel();
        botonesPanel.add(clienteBtn);
        botonesPanel.add(servidorBtn);

        panel.add(botonesPanel, BorderLayout.CENTER);

        // Acciï¿½n del botï¿½n Cliente
        clienteBtn.addActionListener((ActionEvent e) -> {
            frame.dispose(); // Cierra el launcher
            Cliente.main(null); // Lanza cliente
        });

        // Acciï¿½n del botï¿½n Servidor
        servidorBtn.addActionListener((ActionEvent e) -> {
            frame.dispose(); // Cierra el launcher
            try {
				Servidor.main(null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // Lanza servidor
        });

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}

