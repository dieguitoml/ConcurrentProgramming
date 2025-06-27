package Cliente;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import GUI.VentanaCliente;

public class Cliente {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String nombre = JOptionPane.showInputDialog(null, "Introduce your username:", "Login", JOptionPane.PLAIN_MESSAGE);
            if (nombre != null && !nombre.isBlank()) {
                new VentanaCliente(nombre);
            } else {
                System.exit(0);
            }
        });
    }
	
}
