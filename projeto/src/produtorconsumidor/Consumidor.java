package produtorconsumidor;

import java.util.Random;
//import java.util.concurrent.Semaphore;

/**
 *
 * @author FAEL
 */
public class Consumidor extends Thread{

    //declaracao de variaveis
    private int tempoAleatorio;//variavel armazena o tempo randomico do sono
    private Buffer b;//buffer compartilhado
    private int id;//id para identificar o consumidor
    
    //construtor, recebe como parametro o buffer compartilhado que e criado na main e
    //o id para identificar o consumidor
    public Consumidor(Buffer b, int id){
        this.b = b;
        this.id = id;
    }
    
    
    //metodo extendido pela classe Thread e sobrescrito aqui
    @Override
    public void run() {
        
        //loop do consumidor
        while(true){
            //gerar tempo aleatorio
            this.tempoAleatorio = new Random().nextInt(1000);
            //mensagem indicando que foi gerado tempo aleatorio para consumidor
            System.out.println("foi gerado tempo aleatorio de "  + this.tempoAleatorio + "ms para o consumidor" + id);
            //adormecer por um periodo aleatorio de tempo
            try{
                Thread.sleep(tempoAleatorio);
                System.out.println("consumidor" + id + " foi dormir");
            }catch(InterruptedException e){
                //e.printStackTrace();
                //colocar a msg de erro aqui
                System.out.println("Deu erro no sono do consumidor" + id);
            }
            
            //remove item no buffer se for possivel
            b.remove_item();
            //mostra a fila do buffer
            b.mostrarFilaCircular();
            
        }
    }
    
}
