package org.kami.maps;

/**
 * Implementación de {@link ICoinAnimator} que ejecuta una tarea periódica
 * en un hilo separado para animar monedas u otros elementos.
 * <p>
 * Utiliza un {@link Thread} en segundo plano que ejecuta un {@link Runnable}
 * a intervalos definidos en milisegundos.
 * </p>
 */
public class CoinAnimator implements ICoinAnimator{

    /**
     * Intervalo de ejecución de la tarea en milisegundos.
     */
    private final int intervalMs;

    /**
     * Tarea que se ejecutará periódicamente para la animación.
     */
    private final Runnable task;

    /**
     * Indica si el animador está en ejecución.
     * Se declara como {@code volatile} para garantizar visibilidad entre hilos.
     */
    private volatile boolean running;

    /**
     * Hilo encargado de ejecutar la animación.
     */

    private Thread thread;

    /**
     * Constructor del animador.
     *
     * @param intervalMs intervalo de ejecución en milisegundos
     * @param task tarea que se ejecutará periódicamente
     */
    public CoinAnimator(int intervalMs, Runnable task) {
        this.intervalMs = intervalMs;
        this.task = task;
    }

    /**
     * Inicia la ejecución de la animación en un hilo separado.
     * <p>
     * El hilo se ejecuta como daemon, por lo que no bloquea el cierre de la aplicación.
     * </p>
     */
    @Override
    public void start() {
        running = true;

        thread = new Thread(() -> {
            while (running) {
                try {
                    task.run();
                    Thread.sleep(intervalMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Detiene la ejecución de la animación.
     * <p>
     * Interrumpe el hilo en ejecución para finalizar el ciclo de forma controlada.
     * </p>
     */
    @Override
    public void stop() {
        running = false;
        thread.interrupt();
    }
}
