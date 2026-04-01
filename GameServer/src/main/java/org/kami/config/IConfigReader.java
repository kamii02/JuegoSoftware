package org.kami.config;

/**
 * Interfaz que define el contrato para la lectura de configuraciones
 * de la aplicación.
 *
 * <p>
 * Las implementaciones de esta interfaz deben encargarse de obtener
 * valores de configuración desde una fuente específica, como archivos
 * de propiedades, bases de datos u otros medios.
 * </p>
 *
 * <p>
 * Actualmente permite obtener:
 * <ul>
 *     <li>El puerto de comunicación del servidor.</li>
 * </ul>
 * </p>
 *
 * @author Manuel
 * @version 1.0
 * @since 1.0
 */
public interface IConfigReader {
    /**
     * Obtiene el puerto en el que el servidor debe iniciar.
     *
     * @return el número de puerto configurado.
     */
    int getPort();
}