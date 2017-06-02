package Classes;

import java.util.List;

public class NPreemptivo extends Preemptivo {

    public NPreemptivo(List<Processo> processos) {
        super(processos);
    }

    @Override
    protected void trabalhaPrioridade(Processo p){
        processosAtivos.add(1, p);
    }
}
