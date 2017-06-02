package Classes;


import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;


public class RoundRobin{
    private int tempoAtual;
    private final int quantum;
    private List<Processo> processos;
    private List<Processo> processosAtivos;
    private int tempoTotalDecorrido;
    private int tempoOcioso;
    private List<ResultadosFinais> resultados;

    public RoundRobin(int quantum, List<Processo> processos) {
        this.tempoAtual = 0;
        this.quantum = quantum;
        this.processos = processos;
        this.processosAtivos = new ArrayList<>();
        this.tempoTotalDecorrido = 0;
        this.tempoOcioso = 0;
        this.resultados = new ArrayList<>();
    }

    public int start(){
        int quantidadeProcessos = this.processos.size();
        boolean ocorreuTrocaDeProcesso = false;
        int last = 0;
        int quantumAtual = 0;
        boolean firstRun = true;
        while(true){
            tempoTotalDecorrido++;
            if (quantidadeProcessos == 0){
                break;
            }
            verificaSeTemNovoProcesso();
            tempoAtual ++;
            if(processosAtivos.size() > 0){
                /*
 Esta parte do código só vai rodar se o numero de processos ativos for maior que 0 (ZERO)

 Aqui esta sendo feita toda a execução dos processos, troca de contexto e remoção dos mesmos
 */
                if (processosAtivos.get(0).getId() != last && processosAtivos.get(0).getTempoEntradaProcessador() >= 0 || ocorreuTrocaDeProcesso){
// System.out.println("");
                    ocorreuTrocaDeProcesso = false;
                    if(firstRun){
                        processosAtivos.get(0).setTempoEntradaProcessador(processosAtivos.get(0).getTempoEntradaProcessador() - 1);
                        processosAtivos.get(0).setTempoFila(processosAtivos.get(0).getTempoEntradaProcessador() - processosAtivos.get(0).getTempoCriacao());
                        firstRun = false;
                    }

// System.out.print(processosAtivos.get(0).getId() + " " + (processosAtivos.get(0).getTempoEntradaProcessador()));
                    if (processosAtivos.get(0).getId() <= last){
                        processosAtivos.get(0).setTempoSaida(tempoAtual);
                        processosAtivos.get(0).setTempoFila(processosAtivos.get(0).getTempoEntradaProcessador() - processosAtivos.get(0).getTempoCriacao());
                    }

// System.out.println("Processo atual: " + processosAtivos.get(0).getId());
                    last = processosAtivos.get(0).getId();

                }
                quantumAtual ++;
// System.out.println(tempoAtual);
                if (processosAtivos.get(0).getTempoAtendimentoRestante() > 0){
                    if (processosAtivos.get(0).getTempoEntradaProcessador() == -1){
                        processosAtivos.get(0).setTempoEntradaProcessador(tempoAtual);
                        processosAtivos.get(0).setTempoFila(processosAtivos.get(0).getTempoEntradaProcessador() - processosAtivos.get(0).getTempoCriacao());
// System.out.println("Tempo de entrada do processo " + processosAtivos.get(0).getId() + " = " + (processosAtivos.get(0).getTempoEntradaProcessador() - 1));
                    }
                    processosAtivos.get(0).diminuiTempoRestante();
                }

// System.out.println("Processo ID: " + processosAtivos.get(0).getId() + " TAR: " + processosAtivos.get(0).getTempoAtendimentoRestante());
                if (processosAtivos.get(0).getTempoAtendimentoRestante() == 0){
                    removeProcessoFimdeExecucao();
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
                        ResultadosFinais rf = new ResultadosFinais(processosAtivos.get(0).getId(), processosAtivos.get(0).getTempoCriacao(), processosAtivos.get(0).getTempoEntradaProcessador(),
                                processosAtivos.get(0).getTempoFila(), processosAtivos.get(0).getTempoSaida(), processosAtivos.get(0).getPrioridade());
                        resultados.add(rf);
                        processosAtivos.get(0).setTempoCriacao(tempoAtual);
                        processosAtivos.add(processosAtivos.get(0));
                        verificaSeTemNovoProcesso();
                    }
//                    System.out.print(" " + tempoAtual);
                    processosAtivos.remove(0);
                    processosAtivos.get(0).setTempoEntradaProcessador(tempoAtual);
                    processosAtivos.get(0).setTempoFila(processosAtivos.get(0).getTempoEntradaProcessador() - processosAtivos.get(0).getTempoCriacao());
                    quantumAtual = 0;
                    ocorreuTrocaDeProcesso = true;
                }
            }
            else {
                tempoOcioso++;
            }
        }
        this.mostraResultados();
        return tempoOcioso;
    }


    private void removeProcessoFimdeExecucao(){
        // Encerra processo se seu atendimento é concluido e adicona a lista de processos terminados
        processosAtivos.get(0).setTempoSaida(tempoAtual);
        ResultadosFinais rf = new ResultadosFinais(processosAtivos.get(0).getId(), processosAtivos.get(0).getTempoCriacao(), processosAtivos.get(0).getTempoEntradaProcessador(),
                processosAtivos.get(0).getTempoFila(), processosAtivos.get(0).getTempoSaida(), processosAtivos.get(0).getPrioridade());
        resultados.add(rf);
        processosAtivos.remove(0);
        if (processosAtivos.size() > 0 && processosAtivos.get(0).getTempoAtendimentoOriginal() > processosAtivos.get(0).getTempoAtendimentoRestante()){
            processosAtivos.get(0).setTempoEntradaProcessador(tempoAtual);
            processosAtivos.get(0).setTempoFila(processosAtivos.get(0).getTempoEntradaProcessador() - processosAtivos.get(0).getTempoCriacao());
        }

    }

    private void verificaSeTemNovoProcesso(){
        boolean flag = true;
        for (Processo p: processos) { // Se o processo chegou ele é adicionado a lista de processos ativos
            // System.out.println("Tempo atual " + tempoAtual );
            for (Processo a: processosAtivos) {
                if (a.getId() == p.getId())
                    flag = false;
            }
            if (flag){
                if (p.getTempoCriacao() == tempoAtual){
                    p.setTempoFila(tempoTotalDecorrido - p.getTempoCriacao());
                    processosAtivos.add(p);
                    // System.out.println("Processo adicionado aos ativos");
                    if (p.getTempoCriacao() > tempoAtual){
                        break;
                    }
                }
            }
            flag = true;

        }

    }

    private void mostraResultados(){
        Formatter fmt = new Formatter();
        fmt.format("%5s %5s %5s %5s %5s %5s\n", "ID", "TC", "TE", "TF", "TS", "PRIO");
        for (ResultadosFinais rf: resultados) {
            fmt.format("%5s %5s %5s %5s %5s %5s\n", rf.getId(), rf.getTempoCriacao(), rf.getTempoEntrada(), rf.getTempoFila(), rf.getTempoSaida(), rf.getPrioridade());
        }
        System.out.println(fmt);
    }
}