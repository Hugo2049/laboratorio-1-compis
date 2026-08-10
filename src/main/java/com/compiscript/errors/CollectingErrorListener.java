package com.compiscript.errors;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Junta los errores que reporta ANTLR (lexer o parser) en vez de imprimirlos por consola. */
public class CollectingErrorListener extends BaseErrorListener {

    private final TipoError tipo;
    private final List<AnalysisError> errores = new ArrayList<>();

    public CollectingErrorListener(TipoError tipo) {
        this.tipo = tipo;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                             int charPositionInLine, String msg, RecognitionException e) {
        String simbolo = obtenerSimbolo(offendingSymbol, msg);
        String descripcion = ErrorMessageTranslator.traducir(msg, simbolo);
        AnalysisError nuevo = new AnalysisError(tipo, line, charPositionInLine + 1, simbolo, descripcion);
        if (esRepetidoInmediato(nuevo)) {
            return;
        }
        errores.add(nuevo);
    }

    /** Evita agregar el mismo error dos veces seguidas (p. ej. durante la resincronización). */
    private boolean esRepetidoInmediato(AnalysisError candidato) {
        if (errores.isEmpty()) {
            return false;
        }
        AnalysisError ultimo = errores.get(errores.size() - 1);
        return ultimo.linea() == candidato.linea()
                && ultimo.columna() == candidato.columna()
                && ultimo.descripcion().equals(candidato.descripcion());
    }

    private String obtenerSimbolo(Object offendingSymbol, String msg) {
        if (offendingSymbol instanceof Token token) {
            return sanear(token.getText());
        }
        int inicio = msg.indexOf('\'');
        int fin = msg.lastIndexOf('\'');
        if (inicio >= 0 && fin > inicio) {
            return sanear(msg.substring(inicio + 1, fin));
        }
        return "?";
    }

    /** Deja el símbolo en una sola línea y con un largo razonable para mostrarlo en la tabla. */
    private String sanear(String texto) {
        String limpio = texto.replace("\r", "").replace("\n", "\\n");
        int limite = 30;
        return limpio.length() > limite ? limpio.substring(0, limite) + "…" : limpio;
    }

    public List<AnalysisError> getErrores() {
        return Collections.unmodifiableList(errores);
    }
}
