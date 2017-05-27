package Classes;

import java.util.List;

/**
 * Created by Rafahel on 27/05/2017.
 */
public class LIFO extends FIFO {
    boolean novoProcesso;
    String tab;
    int last;
    int idProcessoAtivoAtual;
    public LIFO(List<Processo> processos) {
        super(processos);
        this.tab = "";
        this.last = -1;
        this.idProcessoAtivoAtual = -1;
    }

    public void startLIFO(){
        novoProcesso = false;
        boolean firstRun = true;

        while(true){
            if (finalizados >= processos.size())
                break;
            verificaSeTemNovoProcesso();

            if (processosAtivos.size() > 0){
                last = processosAtivos.get(0).getId();
                if (firstRun){
                    processosAtivos.get(0).setTempoEntradaProcessador(tempoAtual);
                    firstRun = false;
                }

                processosAtivos.get(0).diminuiTempoRestante();
                idProcessoAtivoAtual = processosAtivos.get(0).getId();

                if (processosAtivos.get(0).getTempoAtendimentoRestante() == 0){
                    processosAtivos.get(0).setTempoSaida(tempoAtual);
                    tab += (processosAtivos.get(0).getId() + "     " + processosAtivos.get(0).getTempoEntradaProcessador() + "     " + (processosAtivos.get(0).getTempoSaida() + 1) );
                    tabelaFinal.add(tab);
                    tab = "";
                    processosAtivos.remove(0);
                    firstRun = true;
                    finalizados++;
                }
            }




            tempoAtual ++;


        }

        super.mostraTabelafinal();
    }


   @Override
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
                    if (processosAtivos.size() >= 1){
                        for (int i = 0; i < processosAtivos.size() ; i++) {
                            if (processosAtivos.get(i).getId() != idProcessoAtivoAtual){
                                if (p.getId() > processosAtivos.get(i).getId()){
//                                    System.out.println("ID: " + processosAtivos.get(i).getId() + "Substituida por ID: " + p.getId());
                                    processosAtivos.add(i, p);
                                    break;
                                }
                            }

                        }
                    }
                    processosAtivos.add(p);
                    novoProcesso = true;
                    if (p.getTempoCriacao() > tempoAtual){
                        break;
                    }
                }
            }
            flag = true;
        }
    }


}
