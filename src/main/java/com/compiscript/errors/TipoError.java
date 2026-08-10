package com.compiscript.errors;

/** Distingue si un error fue detectado por el lexer o por el parser. */
public enum TipoError {
    LEXICO("Léxico"),
    SINTACTICO("Sintáctico");

    private final String etiqueta;

    TipoError(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String etiqueta() {
        return etiqueta;
    }
}
