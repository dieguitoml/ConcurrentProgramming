package GUI;

import javax.swing.table.AbstractTableModel;

import Concurrente.ListaConcurrente;

import java.util.List;
import Usuario.Usuario;

public class ModeloTablaUsuarios extends AbstractTableModel {
    private final String[] columnas = {"Nombre"};
    private List<Usuario> usuarios;

    public ModeloTablaUsuarios(List<Usuario> usuarios) {
        this.usuarios =usuarios;
    }

    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Usuario u = usuarios.get(fila);
        return switch (columna) {
            case 0 -> u.get();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int col) {
        return columnas[col];
    }
}
