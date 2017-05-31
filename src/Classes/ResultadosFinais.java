package Classes;

/**
 * Created by Rafahel on 30/05/2017.
 */
public class ResultadosFinais {
    private int id;
    private int tempoCriacao;
    private int tempoEntrada;
    private int tempoFila;
    private int tempoSaida;
    private int prioridade;

    public ResultadosFinais(int id, int tempoCriacao, int tempoEntrada, int tempoFila, int tempoSaida, int prioridade) {
        this.id = id;
        this.tempoCriacao = tempoCriacao;
        this.tempoEntrada = tempoEntrada;
        this.tempoFila = tempoFila;
        this.tempoSaida = tempoSaida;
        this.prioridade = prioridade;
    }

    public int getId() {
        return id;
    }

    public int getTempoCriacao() {
        return tempoCriacao;
    }

    public int getTempoEntrada() {
        return tempoEntrada;
    }

    public int getTempoFila() {
        return tempoFila;
    }

    public int getTempoSaida() {
        return tempoSaida;
    }

    public int getPrioridade() {
        return prioridade;
    }
}
