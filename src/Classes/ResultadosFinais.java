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

    public ResultadosFinais(int id, int tempoCriacao, int tempoEntrada, int tempoFila, int tempoSaida) {
        this.id = id;
        this.tempoCriacao = tempoCriacao;
        this.tempoEntrada = tempoEntrada;
        this.tempoFila = tempoFila;
        this.tempoSaida = tempoSaida;
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
}
