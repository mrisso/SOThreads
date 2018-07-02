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
    private LinkedList<Frentista> frentistas = new LinkedList<Frentista>();
    private LinkedList<Carro> filaDeCarros = new LinkedList<Carro>();

    @Nullable
    private Frentista solicitarFrentista()
    {
        for (Frentista f : frentistas)
        {
            if(f.isLivre())
            {
                f.setLivre(false);
                f.notifyAll();
                return f;
            }
        }

        return null;
    }

    public void addFrentista(Frentista f)
    {
        frentistas.addLast(f);
        f.start();
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

        if(disponibilidade >= 100 && !caminhaoSolicitado)
        {
            solicitante.notifyAll();
            Integer ID = solicitante.getID();
            Integer IDc = filaDeCarros.getFirst().getID();
            if (ID.equals(IDc)
                    && (f = solicitarFrentista()) != null) {
                // Cálculo de um tempo aleatório entre 8 e 10
                long tempo = ThreadLocalRandom.current().nextInt(8,10);
                f.setTempoDeAbastecimento(tempo);
                solicitante.setTempoDeAbastecimento(tempo);
                f.setSolicitante(solicitante);
                solicitante.setFrentistaResponsavel(f);
                f.setLiberado(true);
                f.notifyAll();
                return true;
            }
        }

        return false;
    }

    public boolean solicitarCliente(Frentista frentista)
    {
        frentista.notifyAll();
        if(disponibilidade < 100)
        {
            frentista.setMotivo(1);
            return false;
        }

        else if(caminhaoSolicitado)
        {
            frentista.setMotivo(2);
            return false;
        }

        else if(!frentista.isLiberado())
        {
            frentista.setMotivo(3);
            return false;
        }

        return true;
    }

    public boolean postoLivre()
    {
        int contador = 0;
        for (Frentista f : frentistas)
        {
            if(f.isLivre())
            {
                contador++;
            }
        }

        if(contador == frentistas.size())
            return true;

        return false;
    }

    public boolean solicitarDescarga()
    {
        caminhaoSolicitado = true;

        if(postoLivre())
            return true;

        return false;
    }

    public void descargaDeCombustivel()
    {
        if(disponibilidade <= (capacidade - 500))
            disponibilidade += 500;

        else
            disponibilidade = capacidade;
    }

    public void setCaminhaoSolicitado(boolean caminhaoSolicitado)
    {
        this.caminhaoSolicitado = caminhaoSolicitado;
    }

    public void abastecimento()
    {
        disponibilidade -= 100;
    }

    public boolean disponibilidadeFrentista()
    {
        for (Frentista f : frentistas)
        {
            if(f.isLivre())
            {
                return true;
            }
        }

        return false;
    }

    public int estadoFila()
    {
        return filaDeCarros.size();
    }

    public void setCaminhao(Caminhao caminhao)
    {
        this.caminhao = caminhao;
    }
}
