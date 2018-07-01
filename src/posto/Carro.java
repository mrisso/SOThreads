package posto;

import org.jetbrains.annotations.Nullable;

public class Carro extends Thread
{
    private Frentista frentistaResponsavel = null;

    @Override
    public void run()
    {

    }

    public void abastecer(Frentista frentistaResponsavel)
    {
        this.frentistaResponsavel = frentistaResponsavel;
        // Processo de abastecer -> wait e notify
    }

    @Nullable
    public Frentista getFrentista()
    {
        return frentistaResponsavel;
    }
}
