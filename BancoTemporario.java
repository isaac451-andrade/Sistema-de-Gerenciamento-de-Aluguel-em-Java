package desafio2;

import java.util.ArrayList;

public class BancoTemporario {

    ArrayList<Pessoa> pessoas = new ArrayList<>();
    ArrayList<Proprietario> proprietarios = new ArrayList<>();
    ArrayList<Residencia> residencias = new ArrayList<>();
    ArrayList<String> CPFs = new ArrayList<>();

    private boolean existeTitular(Pessoa pessoa) {
        for (Pessoa p : pessoas) {
            if (p.CPF.equals(pessoa.CPF)) {
                return true;
            }
        }
        return false;
    }

    private boolean existeTitular(Proprietario proprietario) {

        for (Proprietario pr : proprietarios) {
            if (pr.CPF.equals(proprietario.CPF)) {
                return true;
            }
        }
        return false;
    }

    public void listaPessoas() {
        System.out.println();

        for (int i = 0; i < pessoas.size(); i++) {
            System.out.printf("Indice: %d\n\n", i);
            System.out.printf("Nome: %s\n\n", pessoas.get(i).nome);
            System.out.printf("Idade: %d\n\n", pessoas.get(i).idade);
            System.out.printf("Já é proprietário: %b\n\n", pessoas.get(i).isProprietario);
            System.out.printf("Salário: R$%.2f\n\n", pessoas.get(i).salario);
            System.out.println("-----------------");

        }
    }

    public ArrayList<Residencia> listaResidenciasPorDisponibilidadeDeAluguel() {
        ArrayList<Residencia> filtrada = new ArrayList<>();

        for (Residencia r : residencias) {
            if (r.inquilinoNome == null) {
                filtrada.add(r);
            }
        }
        return filtrada;
    }

    public ArrayList<Residencia> listaResidenciasPorDisponibilidadeDeCompra() {
        ArrayList<Residencia> filtrada = new ArrayList<>();

        for (Residencia r : residencias) {
            if (r.getSenhorio() == null) {
                filtrada.add(r);
            }
        }
        return filtrada;
    }

    public String listaResidenciasForAluguelIfNotEmpty() {
        ArrayList<Residencia> filtradaResidencias = listaResidenciasPorDisponibilidadeDeAluguel();
        if (filtradaResidencias.isEmpty()) {
            System.out.println("NÃO TEM NENHUMA CASA DISPONÍVEL!");
            return "n";
        }
        for (int index = 0; index < filtradaResidencias.size(); index++) {
            System.out.println();
            System.out.println("---------------------------");
            System.out.printf("Indice: %d\n", index);
            System.out.printf("Moradia: %s\n", filtradaResidencias.get(index).getNomeMorada());
            System.out.printf("Metragem: %.2f m²\n", filtradaResidencias.get(index).getMetragem());
            System.out.printf("Posicão da Frente: %s\n", filtradaResidencias.get(index).getPosicaoFrente());
            System.out.printf("É de esquina? => %s\n", filtradaResidencias.get(index).getisDeEsquina());
            System.out.printf("Valor do Aluguel: R$%.2f\n", filtradaResidencias.get(index).getValorAluguel());
            System.out.printf("Valor do Aluguel: R$%.2f\n", filtradaResidencias.get(index).getValorAluguel());
            if (filtradaResidencias.get(index).getSenhorio() == null) {
                System.out.println("Tem senhorio? => Não");
            } else {
                System.out.println("Tem senhorio? => Sim");
            }
            if (filtradaResidencias.get(index).inquilinoNome == null) {
                System.out.println("Alugada? => Não");
            } else {
                System.out.println("Alugada? => Sim");
            }
            System.out.println("---------------------------");
        }
        return "s";

    }

    public void listaResidenciasForCompraIfNotEmpty(){
        ArrayList<Residencia> filtradaResidencias = listaResidenciasPorDisponibilidadeDeCompra();
        if (filtradaResidencias.isEmpty()) {
            System.out.println("NÃO TEM NENHUMA CASA DISPONÍVEL!");
            return;
        }
        for (int index = 0; index < filtradaResidencias.size(); index++) {
            System.out.println();
            System.out.println("---------------------------");
            System.out.printf("Indice: %d\n", index);
            System.out.printf("Moradia: %s\n", filtradaResidencias.get(index).getNomeMorada());
            System.out.printf("Metragem: %.2f m²\n", filtradaResidencias.get(index).getMetragem());
            System.out.printf("Posicão da Frente: %s\n", filtradaResidencias.get(index).getPosicaoFrente());
            System.out.printf("É de esquina? => %s\n", filtradaResidencias.get(index).getisDeEsquina());
            System.out.printf("Valor do Aluguel: R$%.2f\n", filtradaResidencias.get(index).getValorAluguel());
            System.out.println("---------------------------");
        }
    }

    

    public Residencia getResidenciaByIndexForAluguel(int indice) {
        ArrayList<Residencia> casas = listaResidenciasPorDisponibilidadeDeAluguel();


        for (Residencia casa : casas) {
            if (casa == casas.get(indice)) {
                return casa;
            }
        }
        return null;
    }

    public Residencia getResidenciaByIndexForCompra(int indice) {
        ArrayList<Residencia> casas = listaResidenciasPorDisponibilidadeDeCompra();


        for (Residencia casa : casas) {
            if (casa == casas.get(indice)) {
                return casa;
            }
        }
        return null;
    }

    public Pessoa getPessoaByCPF(String CPF) {
        for (Pessoa p : pessoas) {
            if (p.CPF.equals(CPF)) {
                return p;
            }
        }
        return null;
    }

    public Proprietario getProprietarioByCPF(String CPF) {
        for (Proprietario pr : proprietarios) {
            if (pr.CPF.equals(CPF)) {
                return pr;
            }
        }
        return null;
    }

    public Pessoa getPessoaByIndex(int indice) {
        return pessoas.get(indice);
    }

    public Proprietario getProprietario(int indice) {
        return proprietarios.get(indice);

    }

    public void saveTitular(Pessoa pessoa) {
        boolean pessoaExiste = existeTitular(pessoa);
        System.out.println();
        if (pessoaExiste) {
            System.out.println("Esta pessoa já existe!!! Crie com outro CPF");
        } else {
            pessoas.add(pessoa);
            CPFs.add(pessoa.CPF);
            System.out.println("Pessoa cadastrada com sucesso!");
        }
    }

    public void saveTitular(Proprietario proprietario) {
        boolean proprietarioExiste = existeTitular(proprietario);
        System.out.println();

        if (proprietarioExiste) {
            System.out.println("Este proprietário já existe!!");
        } else {

            proprietarios.add(proprietario);
            CPFs.add(proprietario.CPF);
        }
    }

    public void saveTitularFromPessoa(Proprietario proprietario, Pessoa pessoa) {
        System.out.println();
        pessoa.isProprietario = true;
        proprietarios.add(proprietario);
    }

    public void saveResidencia(Residencia residencia) {
        residencias.add(residencia);
    }
    public boolean senhorioOk(Residencia residencia){
        if(residencia.getSenhorio() == null){
            return false;
        }
        else{
            return true;
        }
    }

}
