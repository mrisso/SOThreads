package posto;

import java.util.LinkedList;

public class Main
{
   public static void main(String [] args)
   {
      Posto p = new Posto();
      LinkedList<Carro> carros = new LinkedList<Carro>();
      Caminhao caminhoneiro = new Caminhao(p);


       // Criar 3 Frentistas
       for (int i = 0; i < 3; i++)
       {
           p.addFrentista(new Frentista((i+1), p));
       }

       // Criar 50 Carros
       for (int i = 0; i < 50; i++)
       {
           Carro c = new Carro((i+1), p);
           carros.addLast(c);
           c.start();
       }

      p.setCaminhao(caminhoneiro);
      caminhoneiro.start();
   }
}
