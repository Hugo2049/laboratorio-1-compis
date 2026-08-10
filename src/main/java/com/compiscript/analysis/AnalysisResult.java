package com.compiscript.analysis;

import com.compiscript.errors.AnalysisError;

import java.util.List;

/** Resultado de analizar un archivo: si fue exitoso y la lista de errores encontrados. */
public record AnalysisResult(boolean exitoso, List<AnalysisError> errores) {

    public static AnalysisResult de(List<AnalysisError> errores) {
        return new AnalysisResult(errores.isEmpty(), errores);
    }
}
