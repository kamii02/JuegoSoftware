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
 *     <li>El estado inicial de la bola (activa o no).</li>
 * </ul>
 * </p>
 *
 * @author Camila
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

    /**
     * Obtiene la dirección IP del servidor al que el cliente debe conectarse.
     *
     * @return IP del servidor en formato {@code "x.x.x.x"}
     */
    String getIpServer();

    String getIdPlayer();
}

