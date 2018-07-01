package posto;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

public class Posto
{
    private int capacidade = 2000;
    private int disponibilidade = 2000;
    private int capacidadeFila = 10;

    private Caminhao caminhao;
    private LinkedList<Frentista> frentistas;
    private LinkedList<Carro> filaDeCarros;

    @Nullable
    private Frentista solicitarFrentista()
    {
        for (Frentista f : frentistas)
        {
            if(f.isLivre())
            {
                f.setLivre(false);
                return f;
            }
        }

        return null;
    }

    public boolean solicitarEntrada(Carro solicitante)
    {
        Frentista f;

        if(filaDeCarros.size() == 0)
        {
            if((f = solicitarFrentista()) != null)
            {
                f.abastecer(solicitante);
                solicitante.abastecer(f);
                return true;
            }
        }

        else if(filaDeCarros.size() < 10)
        {
            filaDeCarros.addLast(solicitante);
            return true;
        }

        return false;
    }

    public boolean solicitarCliente(Frentista trabalhador)
    {

    }

    public boolean solicitarDescarga()
    {

    }
}
