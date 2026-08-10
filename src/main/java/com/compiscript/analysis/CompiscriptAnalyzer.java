package com.compiscript.analysis;

import com.compiscript.errors.AnalysisError;
import com.compiscript.errors.CollectingErrorListener;
import com.compiscript.errors.TipoError;
import com.compiscript.parser.CompiscriptLexer;
import com.compiscript.parser.CompiscriptParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Corre el lexer y el parser generados por ANTLR sobre un archivo .cps.
 * La recuperación ante errores (seguir después del primer fallo) la da la
 * estrategia por defecto de ANTLR; aquí solo se recolectan los resultados.
 */
public class CompiscriptAnalyzer {

    public AnalysisResult analizar(File archivo) throws IOException {
        CollectingErrorListener lexerListener = new CollectingErrorListener(TipoError.LEXICO);
        CollectingErrorListener parserListener = new CollectingErrorListener(TipoError.SINTACTICO);

        CharStream input = CharStreams.fromPath(archivo.toPath(), StandardCharsets.UTF_8);

        CompiscriptLexer lexer = new CompiscriptLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(lexerListener);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        CompiscriptParser parser = new CompiscriptParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(parserListener);

        parser.program();

        List<AnalysisError> errores = new ArrayList<>();
        errores.addAll(lexerListener.getErrores());
        errores.addAll(parserListener.getErrores());
        errores.sort(Comparator.comparingInt(AnalysisError::linea).thenComparingInt(AnalysisError::columna));

        return AnalysisResult.de(errores);
    }
}
