# laboratorio-1-compis

Analizador léxico y sintáctico del lenguaje **Compiscript** (`.cps`), construido con
**Java 17 + ANTLR4 + Swing**. Laboratorio 01 — Construcción de Compiladores, UVG.

## Link del video
https://youtu.be/I9vZxcpHLpE

**Estado:** Laboratorio completado y entregado ✓



## Requisitos

- JDK 17 o superior.
- Maven (o un IDE que lo integre, p. ej. IntelliJ IDEA, que resuelve todo automáticamente
  al abrir el `pom.xml`).

## Compilar y ejecutar

```bash
mvn clean package
java -jar target/compiscript-analyzer-1.0-SNAPSHOT.jar
```

Esto abre la interfaz gráfica: no requiere argumentos de línea de comandos, el archivo
`.cps` se selecciona desde la propia ventana.

## Estructura del proyecto

```
src/main/antlr4/com/compiscript/parser/Compiscript.g4   gramática ANTLR
src/main/java/com/compiscript/errors/                    modelo y traducción de errores
src/main/java/com/compiscript/analysis/                  orquestación del lexer/parser
src/main/java/com/compiscript/gui/                        interfaz gráfica (Swing)
ejemplos/                                                 archivos .cps de prueba
```

## Archivos de prueba

Los ocho archivos en `ejemplos/` cubren cada fila de la rúbrica de evaluación
(complejidad baja/media, sin errores / solo léxicos / solo sintácticos / mixtos):

| Archivo | Complejidad | Errores esperados |
|---|---|---|
| `01_baja_sin_errores.cps` | Baja | 0 |
| `02_baja_errores_lexicos.cps` | Baja | 3 léxicos |
| `03_baja_errores_sintacticos.cps` | Baja | 3 sintácticos |
| `04_baja_errores_mixtos.cps` | Baja | 2 léxicos + 2 sintácticos |
| `05_media_sin_errores.cps` | Media | 0 |
| `06_media_errores_lexicos.cps` | Media | 3 léxicos |
| `07_media_errores_sintacticos.cps` | Media | 3 sintácticos |
| `08_media_errores_mixtos.cps` | Media | 2 léxicos + 2 sintácticos |

"Complejidad baja" = 3 tipos de variables + constante + 2 operadores aritméticos
distintos + if/else + while + switch. "Complejidad media" = todo lo anterior +
arreglo declarado y usado + 2 clases + 2 objetos instanciados + 2 funciones
declaradas y llamadas.
