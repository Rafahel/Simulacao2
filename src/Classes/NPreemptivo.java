package Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafahel on 01/06/2017.
 */
public class NPreemptivo extends Preemptivo {

    public NPreemptivo(List<Processo> processos) {
        super(processos);
    }

    @Override
    protected void trabalhaPrioridade(Processo p){
        processosAtivos.add(1, p);
    }
}
