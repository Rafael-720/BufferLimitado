package produtorconsumidor;

import java.util.Random;
//import java.util.concurrent.Semaphore;

/**
 *
 * @author FAEL
 */
public class Produtor extends Thread{
    
    //declaracao de variaveis
    private Buffer b;//buffer compartilhado
    private int tempoAleatorio;//variavel armazena o tempo randomico do sono
    private Item item;//Item para inserir no buffer
    private int id;//id para identificar o produtor
    
    //construtor, recebe como parametro o buffer compartilhado que e criado na main e
    //o id para identificar o produtor
    public Produtor(Buffer b, int id){//, Semaphore m){//, Semaphore semaforo){
        this.b = b;
        this.id = id;
    }

    //metodo extendido pela classe Thread e sobrescrito aqui
    @Override
    public void run() {
        
        //loop do produtor
        while(true){
            //gerar tempo aleatorio
            this.tempoAleatorio = new Random().nextInt(1000);
            //mensagem indicando que foi gerado tempo aleatorio para produtor
            System.out.println("foi gerado tempo aleatorio de " + this.tempoAleatorio + "ms para o produtor" + id);
            //adormecer por um periodo aleatorio de tempo
            try{
                Thread.sleep(tempoAleatorio);
                System.out.println("produtor" + id + " foi dormir");
            }catch(InterruptedException e){
                //e.printStackTrace();
                //colocar a msg de erro aqui
                System.out.println("Deu erro no sono do produtor" + id);
            }
            
            //gerar um elemento aleatorio
            item = new Item();//inicializa item
            item.value = new Random().nextInt(100);//atribui valor randomico
            //mensagem indicando que foi produzido
            System.out.println("foi produzido o item " + item.value);
            //mensagem indicando tentativa do produtor em inserir item no buffer
            System.out.println("Produtor" + id + " tentara inserir o item " + item.value + " no buffer");
            //insere item no buffer se for possivel
            b.insert_item(item);
            //mostrando conteudo no buffer
            System.out.println("itens no buffer");
            b.mostrarFilaCircular(); //mostra a fila do buffer
            System.out.println("");//da um espaco
            
            
        }
        
    }
    
    
}
