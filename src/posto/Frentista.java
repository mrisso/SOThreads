package posto;

import java.util.concurrent.TimeUnit;

public class Frentista extends Thread
{
    private boolean livre = true;
    private boolean liberado = false;
    private Carro solicitante;
    private Posto posto;
    private int ID;
    private long tempoDeAbastecimento;

    public Frentista(int ID, Posto posto)
    {
        this.ID = ID;
        this.posto = posto;
    }

    private void abastecimento(long tempoDeAbastecimento)
    {
        // Esperar pelo tempo calculado
        try {
            TimeUnit.SECONDS.sleep(tempoDeAbastecimento);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        while(true)
        {
            while (!(posto.solicitarCliente(this))) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            abastecimento(tempoDeAbastecimento);

            notifyAll();
        }
    }

    public boolean isLivre()
    {
        return livre;
    }

    public void setLivre(boolean livre)
    {
        this.livre = livre;
    }

    public int getID()
    {
        return this.ID;
    }

    public void setSolicitante(Carro solicitante)
    {
        this.solicitante = solicitante;
    }

    public void setLiberado(boolean liberado)
    {
        this.liberado = liberado;
    }

    public boolean isLiberado()
    {
        return liberado;
    }

    public void setTempoDeAbastecimento(long tempoDeAbastecimento)
    {
        this.tempoDeAbastecimento = tempoDeAbastecimento;
    }
}
