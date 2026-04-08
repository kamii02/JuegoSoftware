package org.kami.view.maps.elements;

public class CoinAnimator implements ICoinAnimator{
    private final int intervalMs;
    private final Runnable task;
    private volatile boolean running;
    private Thread thread;

    public CoinAnimator(int intervalMs, Runnable task) {
        this.intervalMs = intervalMs;
        this.task = task;
    }

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

    @Override
    public void stop() {
        running = false;
        thread.interrupt();
    }
}
