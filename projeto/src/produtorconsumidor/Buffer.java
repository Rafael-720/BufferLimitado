package produtorconsumidor;

import java.util.concurrent.Semaphore;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 *
 * @author FAEL
 */
public class Buffer {
    
    //declaracao de variaveis
    public static int NUM_ITEMS = 5;
    private Item [] buffer = new Item[NUM_ITEMS];
    //ponteiros da lista
    private int primeiro;//aponta para o primeiro da fila circular
    private int ultimo;//aponta para o ultimo da fila circular
    private int total;//variavel que informa o total do buffer preenchido
    private boolean disponivel;//variavel para controle de disponibilidade
    private boolean indisponivel;//variavel para controlar buffer cheio
    //private Semaphore bufferVazio;
    //private Semaphore bufferCheio;
    private Semaphore mutex;//semaforo com 1 acesso(exclusao mutua)
    
    //construtor, recebe como parametro o Mutex criado na main
    public Buffer(Semaphore m){
        //inicializar variaveis
        primeiro = 0;//primeiro por padrao aponta pra posicao zero da fila
        ultimo = -1;//o ultimo aponta para -1
        total = 0;//o total preenchido comeca em zero
        disponivel = true;//por padrao o buffer comeca disponivel
        indisponivel = false;//por padrao o buffer comeca disponivel
        this.mutex = m;//mutex local recebe o valor passado no contrutor
        //bufferVazio = new Semaphore(5);
        //bufferCheio = new Semaphore(5);
        
    }

    //insere o item
    boolean insert_item(Item item){
        //mensagem indicando entrada no buffer
        System.out.println("produtor entrando no buffer");
        
        //chama o metodo isVazio
        isVazio();
        
        //verifica se buffer esta disponivel
        if(disponivel){
            try {
                mutex.acquire();//mutex bloqueia o acesso para as demais threads
            } catch (InterruptedException e1) {
                // colocar uma msg de erro aqui
                //e1.printStackTrace();
            }
            //mensagem indicando que o buffer esta disponivel para insercao
            System.out.println("Buffer disponivel para inserção");
            
            //testa se o ponteiro ultimo ja chegou ao limite da fila,
            //caso tenha atingido o limite, o ponteiro e reiniciado para o padrao
            if(ultimo == NUM_ITEMS - 1){
                ultimo = -1;
            }
            
            ultimo++;//incrementa o ponteiro de ultima posicao
            buffer[ultimo] = item;//insere o item no buffer na ultima posicao da fila
            total++;//incrementa o total preenchido
            //mensagem indicando que o item foi inserido com sucesso
            System.out.println("item " + item.value + " inserido com sucesso");
            //libera o mutex
            mutex.release();
            
            return true;//retorna true se for inserido com sucesso
        } else {
            //mensagem indicando falha ao inserir o item, pois buffer esta cheio
            System.out.println("falha ao inserir item " + item.value);
            System.out.println("Buffer cheio");
            return false;//retorna false em caso de fracasso
        }
    }

    //remove o item
    boolean remove_item(){
        
        //mensagem indicando entrada no buffer
        System.out.println("consumidor entrando no buffer");
        
        //verifica se buffer foi preenchido ou esta cheio
        if(total > 0 || indisponivel){
            
            try {
                mutex.acquire();//mutex bloqueia o acesso para as demais threads
            } catch (InterruptedException e1) {
                // colocar uma msg de erro aqui
                //e1.printStackTrace();
            }
            
            //cria variavel temporaria para armazenar o valor do item a ser removido da fila
            //e incrementa o ponteiro primeiro
            Item temp = buffer[primeiro++];
            
            //verifica se o ponteiro primeiro chegou ao fim da fila circular
            //caso chegue ao final, volta a posicao zero.
            if(primeiro == NUM_ITEMS){
                primeiro = 0;
            }

            total--;//decrementa o total preenchido
            disponivel = true;
            indisponivel = false;//indisponivel recebe false
            //mensagem indicando que o item foi consumido com sucesso
            System.out.println("item " + temp.value + " consumido com sucesso");
            
            //libera o mutex
            mutex.release();
            return true;//retorna true se for removido com sucesso
        }
        //mensagem indicando falha ao remover o item, pois buffer esta vazio
        System.out.println("falha ao consumir item ");
        System.out.println("Buffer vazio");
        return false;//retorna false em caso de fracasso
    }

    public static int getNUM_ITEMS() {
        return NUM_ITEMS;
    }

    public static void setNUM_ITEMS(int NUM_ITEMS) {
        Buffer.NUM_ITEMS = NUM_ITEMS;
    }

    public Item[] getBuffer() {
        return buffer;
    }

    public void setBuffer(Item[] buffer) {
        this.buffer = buffer;
    }
        
    public void mostrarFilaCircular(){
        //cria contador
        int cont, i;
        
        //laco for para mostrar a fila do buffer
        for(cont = 0, i = primeiro; cont < total; cont++){
            System.out.println(buffer[i++].value);
            
            if(i == 5){
                i = 0;
            }
        }
    }
    
    public void isVazio(){
        if (total >= NUM_ITEMS) {

            disponivel = false;

        }
    }

    
    
}

class Item {
	public int value;
}

