package org.kami.view.maps.elements;

public class CoinAnimator implements ICoinAnimator{
    private volatile boolean visible = true;
    private volatile boolean running = false;
    private Thread thread;
    private final int intervalMs; // cada cuánto parpadea

    public CoinAnimator(int intervalMs) {
        this.intervalMs = intervalMs;
    }

    @Override
    public void start() {
        running = true;
        thread = new Thread(() -> {
            while (running) {
                try {
                    visible = !visible;
                    Thread.sleep(intervalMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.setDaemon(true); // muere cuando cierra la app
        thread.start();
    }

    @Override
    public void stop() {
        running = false;
        thread.interrupt();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }
}
