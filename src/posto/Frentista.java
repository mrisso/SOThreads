package posto;

import java.util.concurrent.TimeUnit;

public class Frentista extends Thread
{
    private int motivo;
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

       posto.abastecimento();
    }

    @Override
    public synchronized void run()
    {
        while(true)
        {
            while (!(posto.solicitarCliente(this)))
            {
                if(motivo == 1)
                {
                    System.out.println("Frentista " +
                            ID + " dormiu por falta de combustível no posto");
                }

                else if(motivo == 2)
                {
                    System.out.println("Frentista " +
                            ID + " dormiu porque há um caminhão pronto para abastecer");
                }

                else if(motivo == 3)
                {
                    System.out.println("Frentista " +
                            ID + " dormiu por falta de clientes");
                }

                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Frentista " +
                    + ID + " acordou!");

            abastecimento(tempoDeAbastecimento);
            liberado = false;
            livre = true;

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

    public void setMotivo(int motivo)
    {
        this.motivo = motivo;
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
