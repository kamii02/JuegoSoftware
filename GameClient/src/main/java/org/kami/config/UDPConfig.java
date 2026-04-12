package org.kami.config;
/**
 * Implementación de {@link IUDPConfig} que proporciona los parámetros
 * necesarios para la comunicación mediante UDP.
 *
 * <p>
 * Esta clase utiliza un {@link IConfigReader} para obtener los valores
 * de configuración como la dirección IP, el puerto y el identificador
 * del jugador.
 * </p>
 *
 * <p>
 * El identificador del jugador se inicializa desde la configuración,
 * pero puede modificarse en tiempo de ejecución mediante
 * {@link #setPlayerId(String)}.
 * </p>
 *
 * @author  manuel
 * @version 1.0
 * @since   1.0
 */
public class UDPConfig implements IUDPConfig {
    /**
     * Lector de configuración utilizado como fuente de datos.
     */
    private final IConfigReader configReader;

    /**
     * Identificador único del jugador en la red.
     */
    private String playerId;

    /**
     * Crea una nueva instancia de {@code UDPConfig}.
     *
     * @param configReader el lector de configuración utilizado para obtener los valores
     */
    public UDPConfig(IConfigReader configReader) {
        this.configReader = configReader;
        this.playerId = configReader.getString("id");
    }

    /**
     * Establece el identificador del jugador en tiempo de ejecución.
     *
     * @param playerId el nuevo identificador del jugador
     */
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPort() {
        return configReader.getInt("port");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIp() {
        return configReader.getString("ip");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayerId() {
        return playerId;
    }


}