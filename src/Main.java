import Classes.*;
import java.util.*;

@SuppressWarnings("ALL")
public class Main {
    public List<Processo> p = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Numero de processos que devem ser simulados: ");
        int quantidade;
        quantidade = scan.nextInt();
        System.out.print("Quantum: ");
        int quantum = scan.nextInt();
        System.out.print("O primeiro processo inicia no tempo zero (1 para sim 0 nao)? ");
        int entrada = scan.nextInt();
        System.out.print("");

        boolean tempoIniciaEmZero;
        if(entrada == 1){
            tempoIniciaEmZero = true;
        }
        else{
            tempoIniciaEmZero = false;
        }
        System.out.print("Tempo uniforme desejado: ");
        int tempoUniforme = scan.nextInt();

        double tempoOcioso = 0;
        ArrayList<Processo> processosO = criaProcessos(quantidade, tempoUniforme, tempoIniciaEmZero);
        List<Processo> processos = new ArrayList<Processo>();
        processos = copia(processosO);

        System.out.println(("--------------------------------------\n" +
                "Iniciando Round Robin\n\t\tTABELA DE PROCESSOS").replaceAll("-","_"));
        mostraProcessos(processos);
        System.out.println("--------------------------------------\n\t\tTABELA FINAL CPU\n");
        RoundRobin roundRobin = new RoundRobin(quantum, processos);
        roundRobin.start();
        System.out.println("Tempo ocioso medio RR: " + tempoOcioso + "\n");
        tempoOcioso = 0;
        processos.clear();

        processos = copia(processosO);

        System.out.println(("--------------------------------------\n\t\t" +
                "  Iniciando FIFO\n\t\tTABELA DE PROCESSOS").replaceAll("-","_"));
        mostraProcessos(processos);
        System.out.println("--------------------------------------\n\t\tTABELA FINAL CPU\n");
        FIFO fifo = new FIFO(processos);
        fifo.start();
        tempoOcioso += fifo.getTempoOcioso();
        System.out.println("Tempo ocioso medio FIFO: " + tempoOcioso + "\n");
        tempoOcioso = 0;

        processos = copia(processosO);

        System.out.println(("--------------------------------------\n\t\t" +
                "  Iniciando LIFO\n\t\tTABELA DE PROCESSOS").replaceAll("-","_"));
        mostraProcessos(processos);
        LIFO lifo = new LIFO(processos);
        System.out.println("--------------------------------------\n\t\tTABELA FINAL CPU\n");
        lifo.start();
        tempoOcioso += lifo.getTempoOcioso();
        System.out.println("Tempo ocioso medio LIFO: " + tempoOcioso + "\n");
        tempoOcioso = 0;

        processos = copia(processosO);

        System.out.println(("--------------------------------------\n\t" +
                "   Iniciando Preemptivo\n\t\tTABELA DE PROCESSOS").replaceAll("-","_"));
        mostraProcessos(processos);
        System.out.println("--------------------------------------\n\t\tTABELA FINAL CPU\n");
        Preemptivo preemptivo = new Preemptivo(processos);
        preemptivo.start();
        tempoOcioso += preemptivo.getTempoOcioso();
        System.out.println("Tempo ocioso medio Preemptivo: " + tempoOcioso + "\n");
        tempoOcioso = 0;

        processos = copia(processosO);

        System.out.println(("--------------------------------------\n\t   " +
                "Iniciando Não Preemptivo\n\t\tTABELA DE PROCESSOS").replaceAll("-","_"));
        mostraProcessos(processos);
        System.out.println("--------------------------------------\n\t\tTABELA FINAL CPU\n");
        NPreemptivo nPreemptivo = new NPreemptivo(processos);
        nPreemptivo.start();
        tempoOcioso += nPreemptivo.getTempoOcioso();
        System.out.println("Tempo ocioso medio Não Preemptivo: " + tempoOcioso + "\n");

    }

    public static ArrayList<Processo> criaProcessos(int quantidade, int tempoUniforme, boolean iniciaEmZero){
        ArrayList<Processo> processos = new ArrayList<>();
        Random rand = new Random();
        int tempoEntradaUniforme = tempoUniforme;

        int tempoChegada = 0;
        int qtdPrioridade1 = 0;
        int qtdPrioridade2 = 0;
        int qtdPrioridade3 = 0;
        int qtdPrioridade4 = 0;
        for (int i = 0; i < quantidade; i++) {
            int prioridade = 0;
            int tempoAtendimentoNecessario = rand.nextInt(30) + 1;

            tempoChegada += tempoEntradaUniforme;
            if(iniciaEmZero){
                tempoChegada = 0;
                iniciaEmZero = false;
            }
            prioridade = prioridade();

            if (prioridade == 1)
                qtdPrioridade1++;
            if (prioridade == 2)
                qtdPrioridade2++;
            if (prioridade == 3)
                qtdPrioridade3++;
            if (prioridade == 4)
                qtdPrioridade4++;

            Processo processo = new Processo((i+ 1), tempoChegada, tempoAtendimentoNecessario, prioridade);
            processos.add(processo);

        }
        System.out.println("Quantidade de prioridades 1: " + qtdPrioridade1 +
                "\n" +"Quantidade de prioridades 2: " + qtdPrioridade2 + "\n" +
                "Quantidade de prioridades 3: " + qtdPrioridade3 + "\n" +
                "Quantidade de prioridades 4: " + qtdPrioridade4 + "\n");
        return processos;
    }

    private static int prioridade(){
        Random rand = new Random();
        boolean val;
        int prob = 0;
        while (prob == 0){
            val = rand.nextInt(2)==0;
            if (val)
                prob = 4;
            val = rand.nextInt(3)==0;
            if (val)
                prob = 3;
            val = rand.nextInt(5)==0;
            if (val)
                prob = 2;
            val = rand.nextInt(10)==0;
            if (val){
                prob = 1;
            }
        }
        return prob;
    }

    private static void mostraProcessos(List<Processo> processos){
        Formatter fmt = new Formatter();
        fmt.format("%5s %7s %7s %10s", "ID","TC", "TA", "PRIO\n");
        for (Processo p: processos) {
            fmt.format("%5d   %5d   %5d   %5d\n" , p.getId(), p.getTempoCriacao(), p.getTempoAtendimentoOriginal(), p.getPrioridade());
        }
        System.out.println(fmt);
    }

    private static List<Processo> copia(List<Processo> processosO){
        List<Processo> processos = new ArrayList<>();
        for (Processo p: processosO) {
            Processo copia = deepCopy(p);
            processos.add(copia);
        }
        return processos;
    }
    public static Processo deepCopy(Processo input){
        Processo copy = new Processo(input.getId(), input.getTempoCriacao(), input.getTempoAtendimentoOriginal(), input.getPrioridade());
        return copy;
    }

}
