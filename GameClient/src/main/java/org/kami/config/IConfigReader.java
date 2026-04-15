package org.kami.config;

/**
 * Proporciona un contrato para la lectura de configuraciones del sistema.
 *
 * <p>
 * Esta interfaz define métodos para obtener valores de configuración
 * a partir de una clave, permitiendo desacoplar la fuente de datos
 * (archivo, base de datos, variables de entorno, etc.) de su uso.
 * </p>
 *
 * <p>
 * Las implementaciones concretas pueden leer desde distintos orígenes,
 * pero deben respetar los tipos de retorno definidos.
 * </p>
 *
 * @author  manuel
 * @version 1.0
 * @since   1.0
 */
public interface IConfigReader {
    /**
     * Obtiene un valor entero asociado a la clave especificada.
     *
     * @param key la clave de configuración
     * @return el valor entero correspondiente
     * @throws RuntimeException si la clave no existe o no es un entero válido
     */
    int getInt(String key);

    /**
     * Obtiene un valor de tipo cadena asociado a la clave especificada.
     *
     * @param key la clave de configuración
     * @return el valor en formato String
     * @throws RuntimeException si la clave no existe
     */
    String getString(String key);
}

