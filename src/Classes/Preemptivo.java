package Classes;

import java.util.List;
@SuppressWarnings("ALL")
public class Preemptivo extends FIFO {
    private int idProcessoAtivoAtual;
    public Preemptivo(List<Processo> processos) {
        super(processos);

    }
    @Override
    public int start(){
        while (true){
            if (finalizados >= processos.size()){
                break;
            }
            verificaSeTemNovoProcesso();

            if (processosAtivos.size() > 0){

                if (processosAtivos.get(0).getTempoEntradaProcessador() < 0)
                    processosAtivos.get(0).setTempoEntradaProcessador(tempoAtual);

                processosAtivos.get(0).diminuiTempoRestante();
                idProcessoAtivoAtual = processos.get(0).getId();
//                System.out.println("PRocesso ID: " + processosAtivos.get(0).getId() + " Executando tempo restante: " + processosAtivos.get(0).getTempoAtendimentoRestante() );
                if (processosAtivos.get(0).getTempoAtendimentoRestante() == 0){
//                    System.out.println("PRocesso ID: " + processosAtivos.get(0).getId() + " Finalizado" + " Tempo atual: " + tempoAtual);
                    processosAtivos.get(0).setTempoSaida(tempoAtual);
                    processosAtivos.get(0).setTempoFila(processosAtivos.get(0).getTempoEntradaProcessador() - processosAtivos.get(0).getTempoCriacao());

                    ResultadosFinais rf = new ResultadosFinais(processosAtivos.get(0).getId(), processosAtivos.get(0).getTempoCriacao(), processosAtivos.get(0).getTempoEntradaProcessador(),
                            processosAtivos.get(0).getTempoFila(), processosAtivos.get(0).getTempoSaida() + 1, processosAtivos.get(0).getPrioridade());
                    resultados.add(rf);
                    processosAtivos.remove(0);
                    if (processosAtivos.size() > 0){
                        processosAtivos.get(0).setTempoEntradaProcessador(tempoAtual + 1);
                    }
//                    System.out.println("total restante de processos: " + processosAtivos.size());
                    finalizados++;
                }
            }
            else {
                tempoOcioso++;
            }
            tempoAtual ++;
        }
        mostraTabelafinal();
        return tempoAtual;
    }


    @Override
    protected void verificaSeTemNovoProcesso(){
        boolean flag = true;
        int last = -1;
        for (Processo p: processos) { // Se o processo chegou ele é adicionado a lista de processos ativos
//                System.out.println("Tempo atual " + tempoAtual );
            for (Processo a: processosAtivos) {
                if (a.getId() == p.getId())
                    flag = false;
            }
            if (flag){
                if (p.getTempoCriacao() == tempoAtual){
//                    System.out.println("ID: " + p.getId() + " Prioridade: " + p.getPrioridade());
                    if (processosAtivos.size() > 0){
                        if (p.getPrioridade() < processosAtivos.get(0).getPrioridade()){
                            trabalhaPrioridade(p);
                            return;
                        }
                        else {
                            processosAtivos.add(p);
//                            System.out.println(" --> Processo ID: " + p.getId() + " adicionado aos ativos no tempo: " + tempoAtual);
//                            System.out.println("Verificando se o processo entra antes dos que estao na fila, size de processosAtivos: " + processosAtivos.size());
                            for (int i = 1; i < processosAtivos.size() ; i++) {
                                if (processosAtivos.get(i).getPrioridade() == 1){
                                    continue;
                                }
                                if (processosAtivos.get(processosAtivos.size() - 1).getPrioridade() < processosAtivos.get(i).getPrioridade()){
//                                    processosAtivos.get(processosAtivos.size() - 1).setTempoEntradaProcessador(tempoAtual);  REMOVIDO

                                    processosAtivos.add(i, processosAtivos.get(processosAtivos.size() - 1));
//                                    System.out.println("Removendo processo repetido ID: " + processosAtivos.get(processosAtivos.size() - 1).getId());

                                    processosAtivos.remove(processosAtivos.size() - 1);


                                }
                            }

                        }

                    }
                    if(processosAtivos.size() == 0){
                        p.setTempoEntradaProcessador(tempoAtual);
                        processosAtivos.add(p);
                        return;
                    }

                    if (p.getTempoCriacao() > tempoAtual){
                        break;
                    }
                }
            }
            flag = true;
        }
    }

    protected void trabalhaPrioridade(Processo p){
        //                            System.out.println("ID: " + processosAtivos.get(0).getId() + " Prioridade: " + processosAtivos.get(0).getPrioridade() + " Trocado por " + "ID: " + p.getId() + " Prioridade: " + p.getPrioridade());
        processosAtivos.get(0).setTempoSaida(tempoAtual);
        ResultadosFinais rf = new ResultadosFinais(processosAtivos.get(0).getId(), processosAtivos.get(0).getTempoCriacao(), processosAtivos.get(0).getTempoEntradaProcessador(),
                processosAtivos.get(0).getTempoFila(), processosAtivos.get(0).getTempoSaida(), processosAtivos.get(0).getPrioridade());
        resultados.add(rf);
        Processo processo = processosAtivos.get(0);
//                            System.out.println(" --> Processo ID: " + p.getId() + " adicionado aos ativos no tempo: " + tempoAtual);
        processosAtivos.get(0).setTempoSaida(tempoAtual);
        processosAtivos.get(0).setTempoCriacao(tempoAtual);
        processosAtivos.add(0, p);
    }

    private void ordenaPorPrioridade(Processo p){

        for (int i = 0; i < processosAtivos.size() ; i++) {
            if (p.getPrioridade() < processosAtivos.get(i).getPrioridade()) {
                Processo aux = processosAtivos.get(i);
                processosAtivos.add(i+1, aux);
                aux.setTempoSaida(tempoAtual);
//                aux.setTempoCriacao(tempoAtual);

                processosAtivos.add(aux);

                processosAtivos.remove(i);
            }

        }
            System.out.println("Mostrando fila atual");
            for (Processo z: processosAtivos
                 ) {
                System.out.println("Tempo Ocioso: " + tempoOcioso + "\n");
            }

        }


}
