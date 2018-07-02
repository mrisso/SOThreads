package posto;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Carro extends Thread
{
    private int ID;
    private Frentista frentistaResponsavel = null;
    private Posto posto;
    private long tempoDeAbastecimento;

    // Construtor da Classe Carro
    public Carro(int ID, Posto posto)
    {
        this.ID = ID;
        this.posto = posto;
    }

    // Getters e Setters

    @Nullable
    public Frentista getFrentista()
    {
        return frentistaResponsavel;
    }

    public int getID()
    {
        return this.ID;
    }

    public void setFrentistaResponsavel(Frentista frentistaResponsavel)
    {
        this.frentistaResponsavel = frentistaResponsavel;
    }

    public void setTempoDeAbastecimento(long tempoDeAbastecimento)
    {
        this.tempoDeAbastecimento = tempoDeAbastecimento;
    }

    // Método de Execução da Thread
    @Override
    public synchronized void run()
    {
        int contador = 0;

        while(!(posto.solicitarEntrada(this)))
        {
            contador++;

            // Acabou a gasolina do motorista?
            if(contador >= 10)
            {
                // Acabar a execução da Thread caso a gasolina tenha acabado
                System.out.println("Carro " + ID + " está sem gasolina");
                return;
            }

            // Cálculo de um tempo aleatório entre 5 e 10
            long tempo = ThreadLocalRandom.current().nextInt(5,10);

            // Esperar pelo tempo calculado
            System.out.println("Carro " + ID + " iniciou uma volta (" +
            contador + ")");

            System.out.println("Número de carros na fila: " + posto.estadoFila());
            try {
                TimeUnit.SECONDS.sleep(tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(!posto.disponibilidadeFrentista())
        {
            System.out.println("Carro " + ID + " entrou na fila para abastecer");
        }

        while(!(posto.suaVez(this)))
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        frentistaResponsavel.notifyAll();

        // Abastecendo
        System.out.println("Carro " + ID + " está sendo abastecido pelo " +
                "Frentista " + frentistaResponsavel.getID());
        abastecimento(tempoDeAbastecimento);

        System.out.println("Carro " + ID + " terminou o abastecimento");

        notifyAll();
    }

    // Funções de Controle
    private void abastecimento(long tempoDeAbastecimento)
    {
        // Esperar pelo tempo calculado
        try {
            TimeUnit.SECONDS.sleep(tempoDeAbastecimento);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
