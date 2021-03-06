package Classes;

import java.util.List;

public class LIFO extends FIFO {
    private int idProcessoAtivoAtual;
    public LIFO(List<Processo> processos) {
        super(processos);
        this.tab = "";
        this.idProcessoAtivoAtual = -1;
    }

    @Override
    public void start(){
        while(true){
            if (finalizados >= processos.size())
                break;
            verificaSeTemNovoProcesso();

            if (processosAtivos.size() > 0){
                if (firstRun){
                    processosAtivos.get(0).setTempoEntradaProcessador(tempoAtual);
                    firstRun = false;
                }
                processosAtivos.get(0).diminuiTempoRestante();
                idProcessoAtivoAtual = processosAtivos.get(0).getId();
                super.checaSeProcessoDeveSair();
            }
            else {
                tempoOcioso++;
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
                    if (p.getTempoCriacao() > tempoAtual){
                        break;
                    }
                }
            }
            flag = true;
        }
    }


}

// Trocado por checaSeProcessoDeveSair()
//if (processosAtivos.get(0).getTempoAtendimentoRestante() == 0){
//        processosAtivos.get(0).setTempoSaida(tempoAtual);
//        tab += (processosAtivos.get(0).getId() + "     " + processosAtivos.get(0).getTempoEntradaProcessador() + "     " + (processosAtivos.get(0).getTempoSaida() + 1) );
//        tabelaFinal.add(tab);
//        tab = "";
//        processosAtivos.remove(0);
//        firstRun = true;
//        finalizados++;
//        }