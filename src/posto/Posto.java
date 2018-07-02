package posto;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Posto
{
    private boolean caminhaoSolicitado = false;

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
        if(filaDeCarros.size() < capacidadeFila)
        {
            filaDeCarros.addLast(solicitante);
            return true;
        }

        return false;
    }

    public boolean suaVez(Carro solicitante)
    {
        Frentista f;

        if(disponibilidade >= 100)
        {
            if (solicitante.getID() == filaDeCarros.getFirst().getID()
                    && (f = solicitarFrentista()) != null) {
                // Cálculo de um tempo aleatório entre 8 e 10
                long tempo = ThreadLocalRandom.current().nextInt(8,10);
                f.setTempoDeAbastecimento(tempo);
                solicitante.setTempoDeAbastecimento(tempo);
                f.setSolicitante(solicitante);
                solicitante.setFrentistaResponsavel(f);
                f.setLiberado(true);
                return true;
            }
        }

        return false;
    }

    public boolean solicitarCliente(Frentista frentista)
    {
        if(disponibilidade >= 100 && !caminhaoSolicitado && frentista.isLiberado())
            return true;

        return false;
    }

//    public boolean solicitarDescarga()
//   {
//
//   }
}
