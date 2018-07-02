package posto;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Caminhao extends Thread
{
    Posto posto;

    public Caminhao(Posto posto)
    {
        this.posto = posto;
    }

    @Override
    public synchronized void run()
    {
        // Esperar um tempo aleatório até chegar no posto
        long tempo = ThreadLocalRandom.current().nextInt(10,50);

        try {
            TimeUnit.SECONDS.sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Caminhão chegou no posto!");

        // Tentar abastecer
        while (posto.solicitarDescarga())
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Abastecimento por 5s
        System.out.println("Caminhão começa o reabastecimento!");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        posto.descargaDeCombustivel();
        posto.setCaminhaoSolicitado(false);

        System.out.println("Caminhão terminou o reabastecimento!");

        notifyAll();
    }
}
