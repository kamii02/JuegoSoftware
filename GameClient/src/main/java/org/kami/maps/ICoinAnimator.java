package org.kami.maps;

/**
 * Define el contrato para un animador de monedas u otros elementos animados.
 * <p>
 * Las implementaciones de esta interfaz son responsables de gestionar
 * el ciclo de vida de una animación, incluyendo su inicio y detención.
 * </p>
 */
public interface ICoinAnimator {

    /**
     * Inicia la ejecución de la animación.
     * <p>
     * Generalmente implica la creación o activación de un proceso
     * en segundo plano que actualiza periódicamente el estado visual.
     * </p>
     */
    void start();

    /**
     * Detiene la ejecución de la animación.
     * <p>
     * Libera recursos asociados y detiene cualquier proceso en ejecución.
     * </p>
     */
    void stop();
}
