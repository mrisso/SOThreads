package posto;

public class Frentista extends Thread
{
    private boolean livre = true;
    private Carro solicitante;

    @Override
    public void run()
    {

    }

    public boolean isLivre()
    {
        return livre;
    }

    public void setLivre(boolean livre)
    {
        this.livre = livre;
    }

    public void abastecer(Carro solicitante)
    {
        this.solicitante = solicitante;
        // Processo de abastecer -> wait e notify
    }
}
