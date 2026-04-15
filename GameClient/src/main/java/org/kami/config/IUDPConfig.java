package org.kami.config;
/**
 * Define el contrato para la configuración de comunicación UDP del sistema.
 *
 * <p>
 * Esta interfaz proporciona los parámetros necesarios para establecer
 * conexiones de red mediante el protocolo UDP, como la dirección IP,
 * el puerto y el identificador del jugador.
 * </p>
 *
 * <p>
 * Permite desacoplar la fuente de configuración de red de su uso,
 * facilitando cambios en la implementación sin afectar el resto del sistema.
 * </p>
 *
 * @author  [tu nombre]
 * @version 1.0
 * @since   1.0
 */
public interface IUDPConfig {

    /**
     * Obtiene el puerto utilizado para la comunicación UDP.
     */
    int getPort();

    /**
     * Obtiene la dirección IP utilizada para la comunicación UDP.
     */
    String getIp();

    /**
     * Obtiene la id del jugador utilizada para la comunicación UDP.
     */
    String getPlayerId();
}
