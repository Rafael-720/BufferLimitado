package produtorconsumidor;

import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;

/**
 *
 * @author FAEL
 */
public class ProdutorConsumidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Semaphore semaforo;
        
        
        // 1. Recuperar os argumentos [0], [1] e [2]
        int dormir = 100;
        int nProd = 1;//defina aqui o numero de produtores
        int nCons = 1;//defina aqui o numero de consumidores
        Semaphore mutex;
        //Item item;

        // 2. Inicializar o buffer
        mutex = new Semaphore(1);
        Buffer b = new Buffer(mutex);

        // 3. Criar thread(s) produtoras
        //Produtor produtor = new Produtor(b, 0);
        Produtor[] produtor = new Produtor[nProd];
        for(int i = 0; i < produtor.length; i++){
            produtor[i] = new Produtor(b, i);
            System.out.println("Criado produtor" + i);
            produtor[i].start();
        }

        // 4. Criar thread(s) consumidoras
//        Consumidor consumidor = new Consumidor(b, 0);
        Consumidor[] consumidor = new Consumidor[nCons];
        for(int i = 0; i < consumidor.length; i++){
            consumidor[i] = new Consumidor(b, i);
            System.out.println("Criado consumidor" + i);
            consumidor[i].start();
        }

        

        // 5. Adormecer
        try{
                sleep(dormir);
            }catch(InterruptedException e){
                //e.printStackTrace();
            }
        
        // 6. Finalizar */

        
    }

    
    
}
