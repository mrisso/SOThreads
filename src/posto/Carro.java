package posto;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Carro extends Thread
{
    private int ID;
    private Frentista frentistaResponsavel = null;
    private Posto posto;

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

    // Método de Execução da Thread
    @Override
    public void run()
    {
        int contador = 0;

        while(!(posto.solicitarEntrada(this)))
        {
            contador++;

            // Acabou a gasolina do motorista?
            if(contador >= 10)
            {
                // Acabar a execução da Thread caso a gasolina tenha acabado
                return;
            }

            // Cálculo de um tempo aleatório entre 5 e 10
            long tempo = ThreadLocalRandom.current().nextInt(5,10);

            // Esperar pelo tempo calculado
            try {
                TimeUnit.SECONDS.sleep(tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while(!(posto.suaVez(this)))
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // Funções de Controle

    public void abastecer(Frentista frentistaResponsavel)
    {
        this.frentistaResponsavel = frentistaResponsavel;
        // Processo de abastecer -> wait e notify
    }

}
