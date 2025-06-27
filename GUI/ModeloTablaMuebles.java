package GUI;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Mueble.Mueble;
import Usuario.Usuario;

public class ModeloTablaMuebles extends AbstractTableModel {
    private final List<Mueble> muebles;
    private final List<Usuario> propietarios;
    private final String[] columnas = {"Nombre", "Id", "Usuario", "Descripcion"};

    public ModeloTablaMuebles(Map<Mueble, Usuario> mapa) {
        muebles = new ArrayList<>(mapa.keySet());
        propietarios = muebles.stream().map(mapa::get).toList();
    }

    @Override
    public int getRowCount() {
        return muebles.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mueble m = muebles.get(rowIndex);
        Usuario u = propietarios.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> m.getTipo();
            case 1 -> m.getId();
            case 2 -> u.get();
            case 3 -> m.getDescripcion();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }
}
