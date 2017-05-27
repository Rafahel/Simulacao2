package Classes;


import java.util.ArrayList;
import java.util.List;

public class RoundRobin {
    private int tempoAtual;
    private final int  quantum;
    private List<Processo> processos;
    private List<String> tabelaFinal;
    private List<Processo> processosAtivos;
    private int tempoTotalDecorrido;
    private int tempoOcioso;
    public RoundRobin(int quantum, List<Processo> processos) {
        this.tempoAtual = 0;
        this.quantum = quantum;
        this.processos = processos;
        this.tabelaFinal = new ArrayList<>();
        this.processosAtivos = new ArrayList<>();
        this.tempoTotalDecorrido = 0;
        this.tempoOcioso = 0;
    }

    public void startRoundRobin(){
        System.out.println("Iniciando Round Robin");
        int quantidadeProcessos = this.processos.size();
        boolean ocorreuTrocaDeProcesso = false;
        List<Processo> processosTerminados = new ArrayList<>();
        int last = 0;
        int quantumAtual = 0;
        boolean firstRun = true;
        String tab = "";
//        System.out.println("PID   TE   TS");
        while(true){
            tempoTotalDecorrido++;
            if (quantidadeProcessos == 0){
                tabelaFinal.add(tab);
                System.out.println("\nFim da simulação Round Robin.\n\n\n");
                break;
            }

            processosAtivos = verificaSeTemNovoProcesso();
            tempoAtual ++;

            if (processosAtivos.size() <= 0)
                tempoOcioso++;
            if(processosAtivos.size() > 0){
                /*
                                Esta parte do código só vai rodar se o numero de processos ativos for maior que 0 (ZERO)

                                Aqui esta sendo feita toda a execução dos processos, troca de contexto e remoção dos mesmos
                */

                if (processosAtivos.get(0).getId() != last && processosAtivos.get(0).getTempoEntradaProcessador() >= 0 || ocorreuTrocaDeProcesso){
//                    System.out.println("");
                    ocorreuTrocaDeProcesso = false;
                    if(tab.length() > 0){
                        tabelaFinal.add(tab);
                        tab = "";
                    }
                    if(firstRun){
                        processosAtivos.get(0).setTempoEntradaProcessador(processosAtivos.get(0).getTempoEntradaProcessador() - 1);
                        firstRun = false;
                    }

//                    System.out.print(processosAtivos.get(0).getId() + "     " + (processosAtivos.get(0).getTempoEntradaProcessador()));
                    tab += (processosAtivos.get(0).getId() + "     " + (processosAtivos.get(0).getTempoEntradaProcessador()));
//                    System.out.println("Processo atual: " + processosAtivos.get(0).getId());
                    last = processosAtivos.get(0).getId();

                }
                quantumAtual ++;
//                System.out.println(tempoAtual);
                if (processosAtivos.get(0).getTempoAtendimentoRestante() > 0){
                    if (processosAtivos.get(0).getTempoEntradaProcessador() == -1){
                        processosAtivos.get(0).setTempoEntradaProcessador(tempoAtual);
//                        System.out.println("Tempo de entrada do processo " + processosAtivos.get(0).getId() + " = " + (processosAtivos.get(0).getTempoEntradaProcessador() - 1));
                    }
                    processosAtivos.get(0).diminuiTempoRestante();
                }

//                System.out.println("Processo ID: " + processosAtivos.get(0).getId() + " TAR: " + processosAtivos.get(0).getTempoAtendimentoRestante());
                if (processosAtivos.get(0).getTempoAtendimentoRestante() == 0){

//                    System.out.println("Processo finalizado: " + processosAtivos.get(0).getId());
                    // Encerra processo se seu atendimento é concluido e adicona a lista de processos terminados
                    processosAtivos.get(0).setTempoSaida(tempoAtual);
                    processosTerminados.add(processosAtivos.get(0));
//                    System.out.print("    " + tempoAtual);
                    tab += ("    " + tempoAtual);
                    processosAtivos.remove(0);
                    try{
                        processosAtivos.get(0).setTempoEntradaProcessador(tempoAtual);
                    }catch (IndexOutOfBoundsException e){

                    }
                    quantumAtual = 0;
                    quantidadeProcessos--;
                }

                if (quantumAtual == quantum ){
                /*
                                                       Realiza troca de contexto
                        A troca de contexto é realizada adicionando no final da lista o processo ativo atual e removendo
                        o mesmo do inicio da lista, assim dando espaço para o processo seguinte.

                        O quantumAtual deve ser zerado no final desta troca.

                */
//                    System.out.println("Realizando troca de contexto");

                    if (processosAtivos.get(0).getTempoAtendimentoRestante() > 0){
//                        System.out.println("Processo " + processosAtivos.get(0).getId() + " indo para o final da fila falntando: " + processosAtivos.get(0).getTempoAtendimentoRestante());
                        processosAtivos.get(0).setTempoSaida(tempoAtual);
                        processosAtivos = verificaSeTemNovoProcesso();
                        processosAtivos.add(processosAtivos.get(0));


                    }
//                    System.out.print("    " + tempoAtual);
                    tab+= ("    " + tempoAtual);
                    processosAtivos.remove(0);
                    processosAtivos.get(0).setTempoEntradaProcessador(tempoAtual);
//                    System.out.println("Mostrando processos da fila de processos ativos");
//                    for (Processo x: processosAtivos) {
//                        System.out.println("ID: " + x.getId());
//                    }
                    quantumAtual = 0;
                    ocorreuTrocaDeProcesso = true;

                }


            }

        }
//        mostraStatusProcessosRealizados(processosTerminados);
        mostraTabelafinal();
//        System.out.println("Tempo total: " + tempoAtual);
    }

    private void mostraStatusProcessosRealizados(List<Processo> processos){
        for (int i = 0; i < processos.size() ; i++) {
            System.out.println("ID: " + processos.get(i).getId());
            System.out.println("PRIORIDADE: " + processos.get(i).getPrioridade());
            System.out.println("Tempo de criação: " + processos.get(i).getTempoCriacao());
            System.out.println("Tempo de atendimento: " + processos.get(i).getTempoAtendimentoOriginal());
            System.out.println("Tempo de atendimento restante: " + processos.get(i).getTempoAtendimentoRestante());
            System.out.println("Tempo de Fila: " + processos.get(i).getTempoFila());
            System.out.println("Tempo de saída: " + processos.get(i).getTempoSaida());
            System.out.println("-----------------------------------------------");
        }
        System.out.println("\n");
    }



    private List<Processo> verificaSeTemNovoProcesso(){
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
//                    System.out.println("Processo adicionado aos ativos");
                    if (p.getTempoCriacao() > tempoAtual){
                        break;
                    }
                }
            }
            flag = true;

        }
        return processosAtivos;
    }

    private void mostraTabelafinal(){
        System.out.println("          PID   TE   TS");
        System.out.println("          -------------");
        for (String x: tabelaFinal) {
            System.out.println("          " + x);

        }
        System.out.println("Tempo Ocioso: " + tempoOcioso);
    }


}
