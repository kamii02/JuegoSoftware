package org.kami.shared;

/**
 * Define el contrato para una conexión de red.
 * Permite establecer comunicación, enviar mensajes y cerrar la conexión,
 * desacoplando la implementación específica del tipo de red utilizada.
 */
public interface INetworkConnection {

    /**
     * Establece la conexión con el destino.
     * @throws Exception si ocurre un error durante la conexión
     */
    void connect() throws Exception;

    /**
     * Envía un mensaje a través de la conexión.
     * @param message mensaje a enviar
     */
    void send(String message);

    /**
     * Cierra la conexión de red.
     */
    void disconnect();
}