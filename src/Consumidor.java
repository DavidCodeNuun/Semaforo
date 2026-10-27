class Consumidor extends Thread {
    private final Buffer buffer;

    public Consumidor(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            while (true) {
                buffer.consumir();
                Thread.sleep(1500); // simula tiempo de consumo
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
