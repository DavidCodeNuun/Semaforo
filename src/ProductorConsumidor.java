public class ProductorConsumidor {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);
        new Productor(buffer).start();
        new Consumidor(buffer).start();
    }
}
