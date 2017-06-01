package Classes;

import java.util.List;

public class LIFO extends FIFO {
    private int idProcessoAtivoAtual;
    public LIFO(List<Processo> processos) {
        super(processos);
        this.idProcessoAtivoAtual = -1;
    }

    @Override
    public int start(){
        while(true){
            if (finalizados >= processos.size())
                break;
            verificaSeTemNovoProcesso();

            if (processosAtivos.size() > 0){
                if (firstRun){
                    processosAtivos.get(0).setTempoEntradaProcessador(tempoAtual);
                    if (processosAtivos.get(0).getTempoCriacao() == tempoAtual )
                        processosAtivos.get(0).setTempoFila(0);
                    else
                        processosAtivos.get(0).setTempoFila(tempoAtual - processosAtivos.get(0).getTempoCriacao());

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
        return tempoAtual;
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
                    p.setTempoFila(++tempoSaidaAnterior - p.getTempoCriacao());
                    if (processosAtivos.size() >= 1){
                        for (int i = 0; i < processosAtivos.size() ; i++) {
                            if (processosAtivos.get(i).getId() != idProcessoAtivoAtual){
                                if (p.getId() > processosAtivos.get(i).getId()){
//                                    System.out.println("ID: " + processosAtivos.get(i).getId() + "Substituida por ID: " + p.getId());
                                    p.setTempoFila(++tempoSaidaAnterior - p.getTempoCriacao());
                                    tempoSaidaAnterior = tempoAtual;
                                    processosAtivos.add(i, p);
                                    break;
                                }
                            }

                        }
                    }
                    tempoSaidaAnterior = tempoAtual;
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
