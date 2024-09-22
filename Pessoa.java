package desafio2;

public class Pessoa {
    String nome;
    int idade;
    String CPF;
    double salario;
    Residencia residencia;
    boolean isProprietario;

    public Pessoa(String nome, int idade, String CPF, double salario) {
        this.nome = nome;
        this.idade = idade;
        this.CPF = CPF;
        this.salario = salario;
    }

    public void alugaResidencia(Residencia residencia) {
            residencia.inquilinoNome = nome;
            this.residencia = residencia;
    }

    public void listaInformacoes() {
        System.out.println();
        System.out.println("---------------------------");
        System.out.printf("Nome: %s\n\n", nome);
        System.out.printf("Idade: %d anos\n\n", idade);
        System.out.printf("Salario: R$%.2f\n\n", salario);
        System.out.printf("CPF: %s\n\n", CPF);
        if (residencia == null) {
            System.out.println("Residência: não tem moradia\n");
        } else {
            System.out.printf("Residência: %s\n\n", residencia.getNomeMorada());
        }

        if(isProprietario == false){
            System.out.println("É proprietário? => Não");
        }
        else {
            System.out.println("É proprietário? => Sim");
        }
        System.out.println("---------------------------");
    }

    public String getNome() {
        return this.nome;
    }

    public int getIdade() {
        return idade;
    }

    public double getSalario() {
        return salario;
    }

    public Residencia getResidencia() {
        return residencia;
    }

}
