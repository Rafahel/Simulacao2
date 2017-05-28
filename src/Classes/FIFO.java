package Classes;

import java.util.ArrayList;
import java.util.List;

public class FIFO {
    protected List<Processo> processos;
    protected int tempoOcioso;
    protected int tempoAtual;
    protected List<Processo> processosAtivos;
    protected int tempoTotalDecorrido;
    protected List<String> tabelaFinal;
    protected int finalizados;
    protected String tab;
    protected  boolean firstRun;
    public FIFO(List<Processo> processos) {
        this.processos = processos;
        this.tempoOcioso = 0;
        this.tempoAtual = 0;
        this.processosAtivos = new ArrayList<>();
        this.tempoTotalDecorrido = 0;
        this.tabelaFinal = new ArrayList<>();
        this.finalizados = 0;
        this.tab = "";
        this.firstRun = true;
    }

    public void startFIFO(){

        System.out.println("Inciando FIFO");
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
        mostraTabelafinal();


    }

    protected void checaSeProcessoDeveSair(){
        if (processosAtivos.get(0).getTempoAtendimentoRestante() == 0){
            processosAtivos.get(0).setTempoSaida(tempoAtual);
            tab += (processosAtivos.get(0).getId() + "     " + processosAtivos.get(0).getTempoEntradaProcessador() + "     " + (processosAtivos.get(0).getTempoSaida() + 1));
            tabelaFinal.add(tab);
            tab = "";
            processosAtivos.remove(0);
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
                    p.setTempoFila(tempoTotalDecorrido - p.getTempoCriacao());
                    processosAtivos.add(p);
                    if (p.getTempoCriacao() > tempoAtual){
                        break;
                    }
                }
            }
            flag = true;
        }
    }

    protected void mostraTabelafinal(){
        System.out.println("          PID   TE   TS");
        System.out.println("          -------------");
        for (String x: tabelaFinal) {
            System.out.println("          " + x);

        }
        System.out.println("Tempo Ocioso: " + tempoOcioso + "\n");
    }


}
