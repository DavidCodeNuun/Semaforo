class Productor extends Thread {
    private final Buffer buffer;

    public Productor(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        int item = 1;
        try {
            while (true) {
                buffer.producir(item++);
                Thread.sleep(1000); // simula tiempo de producción
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

