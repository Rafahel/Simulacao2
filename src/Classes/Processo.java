package Classes;

@SuppressWarnings("ALL")
public class Processo {
    private int id;
    private int tempoCriacao;
    private int tempoSaida;
    private int tempoAtendimentoRestante;
    private int tempoAtendimentoOriginal;
    private int prioridade;
    private int tempoFila;
    private int tempoEntradaProcessador;

    public Processo(int id, int tempoCriacao, int tempoAtendimentoOriginal, int prioridade) {
        this.id = id;
        this.tempoCriacao = tempoCriacao;
        this.tempoSaida = 0;
        this.tempoFila = 0;
        this.tempoAtendimentoOriginal = tempoAtendimentoOriginal;
        this.tempoAtendimentoRestante = this.tempoAtendimentoOriginal;
        this.prioridade = prioridade;
        this.tempoEntradaProcessador = -1;
    }

    public int getId() {
        return id;
    }

    public int getTempoCriacao() {
        return tempoCriacao;
    }

    public int getTempoSaida() {
        return tempoSaida;
    }

    public int getTempoAtendimentoRestante() {
        return tempoAtendimentoRestante;
    }

    public int getTempoAtendimentoOriginal() {
        return tempoAtendimentoOriginal;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setTempoSaida(int tempoSaida) {
        this.tempoSaida = tempoSaida;
    }

    public void setTempoAtendimentoRestante(int tempoAtendimentoRestante) {
        this.tempoAtendimentoRestante = tempoAtendimentoRestante;
    }
    public void diminuiTempoRestante(){
        this.tempoAtendimentoRestante --;
    }

    public int getTempoFila() {
        return tempoFila;
    }

    public void setTempoFila(int tempoFila) {
        this.tempoFila = tempoFila;
    }

    public void setTempoCriacao(int tempoCriacao) {
        this.tempoCriacao = tempoCriacao;
    }

    public int getTempoEntradaProcessador() {
        return tempoEntradaProcessador;
    }

    public void setTempoEntradaProcessador(int tempoEntradaProcessador) {
        this.tempoEntradaProcessador = tempoEntradaProcessador;
    }
}
