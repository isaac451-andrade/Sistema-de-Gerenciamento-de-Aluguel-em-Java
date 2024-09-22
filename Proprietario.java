package desafio2;

import java.util.ArrayList;

public class Proprietario extends Pessoa {
    private ArrayList<Residencia> unidades = new ArrayList<>();

    public Proprietario(String nome, int idade, double salario, String CPF) {
        super(nome, idade, CPF, salario);
    }

    public Proprietario(Pessoa pessoa) {
        super(pessoa.nome, pessoa.idade, pessoa.CPF, pessoa.salario);
        pessoa.isProprietario = true;
    }

    /**
     * 
     * @param residencia
     * 
     *                   verifica se o número de residencias máximo tá "querendo"
     *                   ser ultrapassado
     *                   E também verifica se há alguma residencia repetida
     */
    private void verificaResidencia(Residencia residencia) {
        if (unidades.size() == 4) {
            throw new IllegalArgumentException("NÚMERO LIMITE DE UNIDADES ATINGIDA POR " + nome);
        } else {
            for (Residencia casa : unidades) {
                if (residencia == casa) {
                    throw new IllegalArgumentException("CASA JÁ CADASTRADA PARA UM PROPRIETÁRIO!");
                }
            }
        }
    }

    public void listaUnidades(){
        for (int i = 0; i < unidades.size(); i++) {
            System.out.printf("Unidade %d: %s\n", i+1, unidades.get(i).getNomeMorada());
        }
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

        if(unidades.isEmpty()){
            System.out.println("Não possui unidades!");
        }
        else {
            listaUnidades();
        }
        System.out.println("---------------------------");
    }

    public void addResidencia(Residencia residencia) {
        verificaResidencia(residencia);
        residencia.setSenhorio(nome);
        unidades.add(residencia);
    }

    public int getUnidades() {
        return unidades.size();
    }

}
