package com.compiscript.gui;

import com.compiscript.errors.AnalysisError;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/** Modelo de tabla Swing que muestra la lista de errores de un análisis. */
public class ErrorTableModel extends AbstractTableModel {

    private static final String[] COLUMNAS = {"Tipo", "Línea", "Columna", "Símbolo", "Descripción"};

    private List<AnalysisError> errores = new ArrayList<>();

    public void setErrores(List<AnalysisError> nuevosErrores) {
        this.errores = new ArrayList<>(nuevosErrores);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return errores.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNAS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNAS[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 1, 2 -> Integer.class;
            default -> String.class;
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AnalysisError error = errores.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> error.tipo().etiqueta();
            case 1 -> error.linea();
            case 2 -> error.columna();
            case 3 -> error.simbolo();
            case 4 -> error.descripcion();
            default -> "";
        };
    }
}
