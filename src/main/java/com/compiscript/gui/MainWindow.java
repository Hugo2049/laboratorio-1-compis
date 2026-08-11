package com.compiscript.gui;

import com.compiscript.analysis.AnalysisResult;
import com.compiscript.analysis.CompiscriptAnalyzer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/** Ventana principal: elegir un archivo .cps, analizarlo y mostrar los resultados. */
public class MainWindow extends JFrame {

    private static final Color COLOR_EXITO = new Color(46, 125, 50);
    private static final Color COLOR_ERROR = new Color(198, 40, 40);

    private final CompiscriptAnalyzer analyzer = new CompiscriptAnalyzer();
    private final ErrorTableModel tableModel = new ErrorTableModel();

    private File archivoActual;
    private JLabel archivoLabel;
    private JTextArea codigoArea;
    private JButton analizarBoton;
    private JLabel bannerLabel;

    public MainWindow() {
        super("Analizador Léxico y Sintáctico de Compiscript");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(construirBarraSuperior(), BorderLayout.NORTH);
        add(construirCentro(), BorderLayout.CENTER);
        add(construirBanner(), BorderLayout.SOUTH);
    }

    private JComponent construirBarraSuperior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(new EmptyBorder(5, 10, 5, 10));

        JButton seleccionarBoton = new JButton("Seleccionar archivo .cps");
        seleccionarBoton.addActionListener(e -> seleccionarArchivo());

        analizarBoton = new JButton("Analizar");
        analizarBoton.setEnabled(false);
        analizarBoton.addActionListener(e -> analizar());

        archivoLabel = new JLabel("Ningún archivo seleccionado");

        panel.add(seleccionarBoton);
        panel.add(analizarBoton);
        panel.add(archivoLabel);
        return panel;
    }

    private JComponent construirCentro() {
        codigoArea = new JTextArea();
        codigoArea.setEditable(false);
        codigoArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane codigoScroll = new JScrollPane(codigoArea);
        codigoScroll.setBorder(BorderFactory.createTitledBorder("Código fuente"));

        JTable tabla = new JTable(tableModel);
        tabla.setAutoCreateRowSorter(true);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(24);
        JScrollPane tablaScroll = new JScrollPane(tabla);
        tablaScroll.setBorder(BorderFactory.createTitledBorder("Errores encontrados"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, codigoScroll, tablaScroll);
        splitPane.setResizeWeight(0.5);
        splitPane.setBorder(new EmptyBorder(0, 10, 0, 10));
        return splitPane;
    }

    private JComponent construirBanner() {
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        bannerLabel = new JLabel("Seleccione un archivo .cps para comenzar.");
        bannerLabel.setFont(bannerLabel.getFont().deriveFont(Font.BOLD, 14f));
        bannerPanel.add(bannerLabel, BorderLayout.CENTER);
        return bannerPanel;
    }

    private void seleccionarArchivo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos Compiscript (*.cps)", "cps"));
        chooser.setAcceptAllFileFilterUsed(false);
        int seleccion = chooser.showOpenDialog(this);
        if (seleccion != JFileChooser.APPROVE_OPTION) {
            return;
        }

        archivoActual = chooser.getSelectedFile();
        archivoLabel.setText(archivoActual.getName());
        analizarBoton.setEnabled(true);
        tableModel.setErrores(List.of());
        bannerLabel.setText("Archivo cargado. Presione \"Analizar\" para continuar.");
        bannerLabel.setForeground(Color.DARK_GRAY);

        try {
            codigoArea.setText(Files.readString(archivoActual.toPath()));
            codigoArea.setCaretPosition(0);
        } catch (IOException ex) {
            mostrarError("No se pudo leer el archivo: " + ex.getMessage());
        }
    }

    private void analizar() {
        try {
            AnalysisResult resultado = analyzer.analizar(archivoActual);
            tableModel.setErrores(resultado.errores());
            actualizarBanner(resultado);
        } catch (IOException ex) {
            mostrarError("No se pudo analizar el archivo: " + ex.getMessage());
        }
    }

    private void actualizarBanner(AnalysisResult resultado) {
        if (resultado.exitoso()) {
            bannerLabel.setText("✔ Análisis exitoso: no se encontraron errores léxicos ni sintácticos.");
            bannerLabel.setForeground(COLOR_EXITO);
        } else {
            int cantidad = resultado.errores().size();
            bannerLabel.setText("✖ Se encontraron " + cantidad + " error(es) léxico(s) y/o sintáctico(s).");
            bannerLabel.setForeground(COLOR_ERROR);
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
