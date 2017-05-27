import Classes.FIFO;
import Classes.LIFO;
import Classes.Processo;
import Classes.RoundRobin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Rafahel on 26/05/2017.
 */

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Numero de processos que devem ser simulados: ");
        String op = "3";
        int rodadas = 1;
        int quantidade = 10;
//        quantidade = scan.nextInt();
        System.out.print("Quantum: ");
        int quantum = 4;
//        int quantum = scan.nextInt();
        //List<Processo> processos = criaProcessos(quantidade);
        List<Processo> processos;
//        mostraProcessos(processos);
        System.out.println("\n\n\n");

        switch (op){
            case "1": // ROUND ROBIN
                for (int i = 0; i < rodadas; i++) {
                    processos = criaProcessos(quantidade);
//                    processos = geradorManual1();
//                    processos = geradorManual2();
//                    processos = geradorManual3();
//                    processos = geradorManual4();
                    RoundRobin roundRobin = new RoundRobin(quantum, processos);
                    roundRobin.startRoundRobin();

                }
                break;
            case "2": // FIFO
                for (int i = 0; i < rodadas; i++) {
//                    processos = criaProcessos(quantidade);
                    processos = geradorManual1();
//                    processos = geradorManual2();
//                    processos = geradorManual3();
//                    processos = geradorManual4();
                    FIFO fifo = new FIFO(processos);
                    fifo.startFIFO();
                }
                break;
            case "3": // LIFO
                for (int i = 0; i < rodadas; i++) {
//                    processos = criaProcessos(quantidade);
//                    processos = geradorManual1();
//                    processos = geradorManual2();
                    processos = geradorManual3();
//                    processos = geradorManual4();
                    LIFO lifo = new LIFO(processos);
                    lifo.startLIFO();
                }
                break;


        }








    }

    public static List<Processo> criaProcessos(int quantidade){

        List<Processo> processos = new ArrayList<>();

        Random rand = new Random();

        int tempoEntradaUniforme = rand.nextInt(20) + 1;
        int tempoChegada = 0;

        int prioridadeAtualAcalcular = 10;
        int qtdPrioridade1 = calcularPrioridadeX(quantidade, prioridadeAtualAcalcular);
        prioridadeAtualAcalcular += 10;
        int qtdPrioridade2 = calcularPrioridadeX(quantidade, prioridadeAtualAcalcular);
        prioridadeAtualAcalcular += 10;
        int qtdPrioridade3 = calcularPrioridadeX(quantidade, prioridadeAtualAcalcular);
        prioridadeAtualAcalcular += 10;
        int qtdPrioridade4 = calcularPrioridadeX(quantidade, prioridadeAtualAcalcular);

        for (int i = 0; i < quantidade; i++) {
            int prioridade = 0;
            int tempoAtendimentoNecessario = rand.nextInt(30) + 1;
            tempoChegada += tempoEntradaUniforme;
            while(prioridade == 0){
                switch (randomPrioridade()){
                    case 1:
                        if(qtdPrioridade1 > 0){
                            prioridade = 1;
                            qtdPrioridade1--;
                            break;
                        }

                    case 2:
                        if(qtdPrioridade2 > 0){
                            prioridade = 2;
                            qtdPrioridade2--;
                            break;
                        }

                    case 3:
                        if(qtdPrioridade3 > 0){
                            prioridade = 3;
                            qtdPrioridade3--;
                            break;
                        }

                    case 4:
                        if(qtdPrioridade4 > 0){
                            prioridade = 4;
                            qtdPrioridade4--;
                            break;
                        }
                }

            }
            Processo processo = new Processo((i+ 1), tempoChegada, tempoAtendimentoNecessario, prioridade);
            processos.add(processo);

        }

        return processos;
    }

    private static int calcularPrioridadeX(int totalDeClientes, int prioridadeAtual){
        int total = 0;
        double porcentagem = (double)prioridadeAtual / 100;
//        System.out.println("Total = "  + porcentagem * totalDeClientes);
        total = (int)Math.round(porcentagem * totalDeClientes);
//        System.out.println(total);
        return total;
    }

    private static int randomPrioridade(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(4) + 1;
//        System.out.println("random prioridade gerado: ");
        return randomNumber;
    }

    private static void mostraProcessos(List<Processo> processos){
        for (int i = 0; i < processos.size(); i++) {
            System.out.println("ID: " + processos.get(i).getId());
            System.out.println("PRIORIDADE: " + processos.get(i).getPrioridade());
            System.out.println("Tempo de chegada: " + processos.get(i).getTempoCriacao());
            System.out.println("Tempo de atendimento: " + processos.get(i).getTempoAtendimentoOriginal());
            System.out.println("Tempo de atendimento restante: " + processos.get(i).getTempoAtendimentoRestante());
            System.out.println("Tempo de saída: " + processos.get(i).getTempoSaida());
            if (processos.get(i).getPrioridade() == 0){ // Teste para saber se deu algum erro e colocou prioridade 0
                System.out.println("ERRO DE PRIORIDADE: PROCESSO ID " + processos.get(i).getId() + " APRESENTA ERRO.");
                break;
            }
            System.out.println("-----------------------------------------------");
        }

    }

    private static List<Processo> geradorManual1(){
        List<Processo> processos = new ArrayList<>();
        Processo a = new Processo(1,0,7, 1);
        processos.add(a);
        Processo b = new Processo(2,3,5, 1);
        processos.add(b);
        Processo c = new Processo(3,5,4, 1);
        processos.add(c);
        Processo d = new Processo(4,6,6, 1);
        processos.add(d);
        Processo e = new Processo(5,8,5, 1);
        processos.add(e);
        Processo f = new Processo(6,10,8, 1);
        processos.add(f);
        Processo g = new Processo(7,12,4, 1);
        processos.add(g);
        return processos;
    }

    private static List<Processo> geradorManual2(){
        List<Processo> processos = new ArrayList<>();
        Processo a = new Processo(1,0,9,2);
        processos.add(a);
        Processo b = new Processo(2,3,7,1);
        processos.add(b);
        Processo c = new Processo(3,5,11,2);
        processos.add(c);
        Processo d = new Processo(4,6,6,2);
        processos.add(d);
        Processo e = new Processo(5,9,8,1);
        processos.add(e);
        return processos;
    }

    private static List<Processo> geradorManual3(){ // Usar quantum 10
        List<Processo> processos = new ArrayList<>();
        Processo a = new Processo(1,0,11,2);
        processos.add(a);
        Processo b = new Processo(2,5,13,1);
        processos.add(b);
        Processo c = new Processo(3,10,9,2);
        processos.add(c);
        Processo d = new Processo(4,15,14,3);
        processos.add(d);
        Processo e = new Processo(5,20,8,2);
        processos.add(e);
        return processos;
    }
    private static List<Processo> geradorManual4(){ // Usar quantum 10
        List<Processo> processos = new ArrayList<>();

        Processo a = new Processo(1,0,11,2);
        processos.add(a);
        Processo b = new Processo(2,15,13,1);
        processos.add(b);
        Processo c = new Processo(3,16,9,2);
        processos.add(c);
        return processos;
    }


}
