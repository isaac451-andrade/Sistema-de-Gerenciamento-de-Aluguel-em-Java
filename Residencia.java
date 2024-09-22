package desafio2;

public class Residencia {
    private String nomeMorada;
    private double metragem;
    private String posicaoFrente;
    private double valorAluguel;
    private String isDeEsquina;
    private String senhorioNome;
    String inquilinoNome;

    public Residencia(String nomeMorada, double metragem, String posicaoFrente, String isDeEsquina,
            double valorAluguel) {
        this.nomeMorada = nomeMorada;
        this.metragem = metragem;
        this.posicaoFrente = posicaoFrente;
        this.valorAluguel = valorAluguel;
        this.isDeEsquina = isDeEsquina;
        this.nomeMorada = nomeMorada;
    }

    public Residencia(String nomeMorada) {
        this.nomeMorada = nomeMorada;
    }

    public double getMetragem() {
        return metragem;
    }

    public String getPosicaoFrente() {
        return posicaoFrente;
    }

    public String getSenhorio() {
        if (senhorioNome == null) {
            return null;
        }
        return senhorioNome;
    }

    public void setSenhorio(String senhorio) {
        this.senhorioNome = senhorio;
    }

    public String getisDeEsquina() {
        return this.isDeEsquina;
    }

    public String getNomeMorada() {
        return nomeMorada;
    }

    public double getValorAluguel() {
        return valorAluguel;
    }

}
