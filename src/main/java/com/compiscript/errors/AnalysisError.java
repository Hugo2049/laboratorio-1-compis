package com.compiscript.errors;

/** Un hallazgo individual del análisis: qué tipo de error es, dónde y por qué. */
public record AnalysisError(TipoError tipo, int linea, int columna, String simbolo, String descripcion) {
}
