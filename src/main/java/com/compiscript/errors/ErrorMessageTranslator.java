package com.compiscript.errors;

/** Convierte los mensajes crudos en inglés que produce ANTLR en descripciones en español. */
public final class ErrorMessageTranslator {

    private ErrorMessageTranslator() {
    }

    public static String traducir(String msgAntlr, String simbolo) {
        if (msgAntlr == null) {
            return "Se encontró un error no especificado cerca de '" + simbolo + "'.";
        }
        if (msgAntlr.startsWith("token recognition error")) {
            return "El carácter o secuencia '" + simbolo + "' no es válido en Compiscript.";
        }
        if (msgAntlr.startsWith("mismatched input")) {
            return "Se encontró '" + simbolo + "', que no corresponde con lo esperado en ese punto.";
        }
        if (msgAntlr.startsWith("missing")) {
            return "Falta un símbolo requerido antes de '" + simbolo + "'.";
        }
        if (msgAntlr.startsWith("extraneous input")) {
            return "El símbolo '" + simbolo + "' sobra en ese punto de la sentencia o expresión.";
        }
        if (msgAntlr.startsWith("no viable alternative")) {
            return "El símbolo '" + simbolo + "' no encaja con ninguna estructura válida de Compiscript en ese punto.";
        }
        return "Error de sintaxis cerca de '" + simbolo + "'.";
    }
}
