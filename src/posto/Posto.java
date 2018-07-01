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
        if(filaDeCarros.size() < 10)
        {
            filaDeCarros.addLast(solicitante);
            return true;
        }

        return false;
    }

    public boolean suaVez(Carro solicitante)
    {
       if(solicitante.getID() == filaDeCarros.getFirst().getID()
           && solicitarFrentista() != null)
           return true;

       return false;
    }

    public boolean solicitarDescarga()
    {

    }
}
