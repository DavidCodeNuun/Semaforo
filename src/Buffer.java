import java.util.concurrent.Semaphore;

class Buffer {
    private final int[] contenedor;   // Arreglo compartido
    private int entrada = 0;          // Índice donde el productor coloca datos
    private int salida = 0;           // Índice donde el consumidor extrae datos
    private final int tamaño;

    // Semáforos
    private final Semaphore espaciosDisponibles; // Controla cuántos huecos vacíos hay
    private final Semaphore elementosDisponibles; // Controla cuántos elementos hay listos
    private final Semaphore accesoExclusivo;     // Exclusión mutua para proteger el contenedor

    public Buffer(int tamaño) {
        this.tamaño = tamaño;
        contenedor = new int[tamaño];
        espaciosDisponibles = new Semaphore(tamaño);          // Al inicio todos los espacios están vacíos
        elementosDisponibles = new Semaphore(0);     // Al inicio no hay elementos
        accesoExclusivo = new Semaphore(1);          // Solo un hilo puede entrar a la sección crítica
    }

    // Método para producir un elemento
    public void producir(int dato) throws InterruptedException {
        espaciosDisponibles.acquire();   // Espera a que haya espacio vacío
        accesoExclusivo.acquire();       // Entra a sección crítica

        contenedor[entrada] = dato;
        System.out.println("Productor produjo: " + dato);
        entrada = (entrada + 1) % tamaño;

        accesoExclusivo.release();       // Sale de sección crítica
        elementosDisponibles.release();  // Señala que hay un nuevo elemento
    }

    // Método para consumir un elemento
    public int consumir() throws InterruptedException {
        elementosDisponibles.acquire();  // Espera a que haya elementos disponibles
        accesoExclusivo.acquire();       // Entra a sección crítica

        int dato = contenedor[salida];
        System.out.println("Consumidor consumió: " + dato);
        salida = (salida + 1) % tamaño;

        accesoExclusivo.release();       // Sale de sección crítica
        espaciosDisponibles.release();   // Señala que hay un espacio libre

        return dato;
    }
}
