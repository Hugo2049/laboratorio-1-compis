package com.compiscript.gui;

import javax.swing.*;

/** Punto de entrada de la aplicación: aplica el look and feel y abre la ventana principal. */
public final class MainApp {

    private MainApp() {
    }

    public static void main(String[] args) {
        aplicarLookAndFeelNimbus();
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }

    private static void aplicarLookAndFeelNimbus() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception ignored) {
                    // si Nimbus no está disponible se conserva el look and feel por defecto
                }
                return;
            }
        }
    }
}
