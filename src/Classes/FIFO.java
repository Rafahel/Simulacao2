package Classes;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
@SuppressWarnings("ALL")
public class FIFO {
    protected List<Processo> processos;
    protected int tempoOcioso;
    protected int tempoAtual;
    protected List<Processo> processosAtivos;
    protected int tempoTotalDecorrido;
    protected int finalizados;
    protected  boolean firstRun;
    protected List<ResultadosFinais> resultados;
    protected int tempoSaidaAnterior;

    public FIFO(List<Processo> processos) {
        this.processos = processos;
        this.tempoOcioso = 0;
        this.tempoAtual = 0;
        this.processosAtivos = new ArrayList<>();
        this.tempoTotalDecorrido = 0;
        this.finalizados = 0;
        this.firstRun = true;
        this.resultados = new ArrayList<>();
        this.tempoSaidaAnterior = 0;
    }

    public int start(){
        while(true){
            if(finalizados >= processos.size())
                break;
            verificaSeTemNovoProcesso();
            if (processosAtivos.size() > 0){


                if (firstRun){
                    processosAtivos.get(0).setTempoEntradaProcessador(tempoAtual);
                    firstRun = false;
                }
                processosAtivos.get(0).diminuiTempoRestante();
                checaSeProcessoDeveSair();
            }
            else {
                tempoOcioso++;
            }
            tempoAtual ++;
        }
        mostraResultados();

        return tempoAtual;
    }

    protected void checaSeProcessoDeveSair(){
        if (processosAtivos.get(0).getTempoAtendimentoRestante() == 0){
            processosAtivos.get(0).setTempoSaida(tempoAtual);
            ResultadosFinais rf = new ResultadosFinais(processosAtivos.get(0).getId(), processosAtivos.get(0).getTempoCriacao(), processosAtivos.get(0).getTempoEntradaProcessador(),
                    processosAtivos.get(0).getTempoFila(), processosAtivos.get(0).getTempoSaida() + 1, processosAtivos.get(0).getPrioridade());
            resultados.add(rf);
            tempoSaidaAnterior = tempoAtual;
            processosAtivos.remove(0);
            if (processosAtivos.size() > 0){
                processosAtivos.get(0).setTempoFila((tempoAtual - processosAtivos.get(0).getTempoCriacao()) + 1);
                if (processosAtivos.get(0).getTempoCriacao() == tempoAtual) // TODO Verificar no LIfo se fica negativo
                    processosAtivos.get(0).setTempoFila(0);
            }
            firstRun = true;
            finalizados++;
        }
    }
    protected void verificaSeTemNovoProcesso(){
        boolean flag = true;
        for (Processo p: processos) { // Se o processo chegou ele é adicionado a lista de processos ativos
//                System.out.println("Tempo atual " + tempoAtual );
            for (Processo a: processosAtivos) {
                if (a.getId() == p.getId())
                    flag = false;
            }
            if (flag){
                if (p.getTempoCriacao() == tempoAtual){
                    processosAtivos.add(p);
                    if (p.getTempoCriacao() > tempoAtual){
                        break;
                    }
                }
            }
            flag = true;
        }
    }


    protected void mostraResultados(){
        Formatter fmt = new Formatter();
        fmt.format("%5s   %5s   %5s   %5s   %5s   %5s\n", "ID", "TC", "TE", "TF", "TS", "PRIO");
        for (ResultadosFinais rf: resultados) {
            fmt.format("%5s   %5s   %5s   %5s   %5s   %5s\n", rf.getId(), rf.getTempoCriacao(), rf.getTempoEntrada(), rf.getTempoFila(), rf.getTempoSaida(), rf.getPrioridade());

        }
        System.out.println(fmt);
    }

    public int getTempoOcioso() {
        return tempoOcioso;
    }
}
