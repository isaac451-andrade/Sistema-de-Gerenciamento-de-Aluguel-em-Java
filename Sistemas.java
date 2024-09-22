package desafio2;

import java.util.ArrayList;
import java.util.Scanner;

public class Sistemas {
    private static Scanner entrada = new Scanner(System.in);
    private static BancoTemporario bancoTemporario = new BancoTemporario();

    public void telaControlador() {
        int opcaoMenu;
        while (true) {
            System.out.println("|SISTEMA DE PESSOAS E RESIDÊNCIA|");

            System.out.println();
            opcaoMenu = obterValorMenu(
                    "Escolha a opção: \n[ 1 ] Entrar como Pessoa\n[ 2 ] Entrar como Proprietário\n[ 3 ] Cadastre-se se não tiver um titular(Pessoa ou Proprietário)\n[ 4 ] Cadastrar residências\n[ 0 ] Sair\nOpção:",
                    new int[] { 0, 1, 2, 3, 4 });
            System.out.println();

            if (opcaoMenu == 1) {
                telaLoginPessoa();

            } else if (opcaoMenu == 2) {
                telaLoginProprietario();
            }

            else if (opcaoMenu == 3) {
                telaCadastroTitular();
            } else if (opcaoMenu == 4) {
                telaCadastroResidencia();
            }

            else if (opcaoMenu == 0) {
                entrada.close();
                System.out.println();
                System.out.println("Fim do Programa! Dados não foram persistidos!");
                break;

            }
        }

    }

    private void telaCadastroTitular() {
        int opcaoCadastroTitular;

        opcaoCadastroTitular = obterValorMenu("Cadastrar: \n[ 1 ] Pessoa\n[ 2 ] Proprietário\n[ 0 ] Sair\nOPÇÃO: ",
                new int[] { 0, 1, 2 });

        System.out.println();

        if (opcaoCadastroTitular == 1) {
            String continuar;
            while (true) {

                System.out.println("|CADASTRO DE PESSOAS|: ");
                System.out.println();

                String nome = obterValorEntrada("Digite seu nome: ");

                int idade = (int) obterValorPositivo("Digite sua idade: ");

                String cpf = obterCpfValidado("Digite seu CPF SEM PONTO E VÍRGULA: ");

                double salario = obterValorPositivo("Digite seu salário: R$");

                Pessoa pessoa = new Pessoa(nome, idade, cpf, salario);

                bancoTemporario.saveTitular(pessoa);

                continuar = obterValorEntrada("Quer cadastrar novamente? [s/n]: ");

                if (continuar.toLowerCase().equals("n")) {
                    break;
                }

            }

        } else if (opcaoCadastroTitular == 2) {
            String continuar;
            while (true) {
                System.out.println("|CADASTRO DE PROPRIETÁRIO NORMAL|");
                System.out.println();

                String nome = obterValorEntrada("Digite seu nome: ");

                int idade = (int) obterValorPositivo("Digite sua idade: ");

                String cpf = obterCpfValidado("Digite seu CPF SEM PONTO E VÍRGULA: ");

                double salario = obterValorPositivo("Digite seu salário: R$");

                Proprietario proprietario = new Proprietario(nome, idade, salario, cpf);

                Pessoa pessoa = bancoTemporario.getPessoaByCPF(proprietario.CPF);

                if (pessoa != null) {

                    if (!pessoa.isProprietario) {
                        String promoveOpcao = obterValorEntrada(
                                "Existe uma pessoa com esse cpf que não é um proprietário!\nQuer promovê-lo para Proprietário? [s/n]");

                        if (promoveOpcao.equals("s")) {
                            Proprietario proprietarioPessoa = new Proprietario(pessoa);
                            bancoTemporario.saveTitularFromPessoa(proprietarioPessoa, pessoa);
                            System.out.println(
                                    "Promovido com sucesso! Todos os dados da pessoa correspondem a seu proprietário!");
                        }
                    }

                } else {
                    Pessoa pessoaCorrespondente = new Pessoa(nome, idade, cpf, salario);
                    pessoaCorrespondente.isProprietario = true;
                    bancoTemporario.saveTitular(pessoaCorrespondente);
                    bancoTemporario.saveTitular(proprietario);
                    System.out.println(
                            "Proprietário cadastrado com sucesso! Pode entrar tanto como Pessoa, como Proprietário!");
                }

                continuar = obterValorEntrada("Quer cadastrar novamente? [s/n]: ");

                if (continuar.toLowerCase().equals("n")) {
                    break;

                }
            }
        }

    }

    private void telaCadastroResidencia() {
        String continuar;
        while (true) {
            System.out.println();
            System.out.println("|CADASTRO DE RESIDÊNCIAS|: ");
            System.out.println();

            String nomeMorada = obterValorEntrada("Digite o nome da morada: ");

            double metragem = obterValorPositivo("Digite a metragem da residência: ");

            String posicaoFrente = obterValorEntrada(
                    "Digite a posição da frente da residência (Norte, Sul, Leste, Oeste): ").toLowerCase();

            while (!verificaPosicaoFrente(posicaoFrente)) {
                posicaoFrente = obterValorEntrada("VALOR INVÁLIDO! Digite (Norte, Sul, Leste, Oeste): ").toLowerCase();
            }

            double valorAluguel = obterValorPositivo("Digite o valor do Aluguel esperado: ");

            String isDeEsquina = obterValorEntrada("É uma residência de esquina? [s/n]: ");
            while (!isDeEsquina.equals("n") && !isDeEsquina.equals("s")) {
                System.out.print("VALOR INVÁLIDO! Digite [s/n]: ");
                isDeEsquina = entrada.nextLine();
            }
            isDeEsquina = converteValorIsEsquina(isDeEsquina);

            Residencia residencia = new Residencia(nomeMorada, metragem, posicaoFrente, isDeEsquina, valorAluguel);

            bancoTemporario.saveResidencia(residencia);
            System.out.println("Residência salva com sucesso!");
            System.out.println();

            System.out.print("Quer cadastrar novamente? [s/n]: ");
            continuar = entrada.nextLine();

            if (continuar.toLowerCase().equals("n")) {
                break;

            }

        }

    }

    private void telaLoginPessoa() {
        if (bancoTemporario.pessoas.isEmpty()) {
            System.out.println("NÃO HÁ NENHUM CPF CADASTRADO AINDA!\n");

        } else {
            System.out.println("|LOGIN COM CPF|: ");
            System.out.println();

            String cpf = obterCpfValidado("Digite seu cpf sem pontos e hífen: ");

            Pessoa user = encontraLoginPessoa(cpf);
            System.out.println();
            System.out.println("Login feito com sucesso! Bem vindo " + user.nome + "\n");
            int opcao;

            while (true) {
                opcao = obterValorMenu("[ 1 ] Listar info\n [ 2 ] Ver casas disponíveis\n [ 0 ] Sair\n Opção: ",
                        new int[] { 0, 1, 2 });
                System.out.println();

                if (opcao == 1) {
                    user.listaInformacoes();
                    System.out.println();
                }

                else if (opcao == 2) {
                    System.out.println();
                    String permitirAlugar = bancoTemporario.listaResidenciasForAluguelIfNotEmpty();
                    if (permitirAlugar.equals("s")) {
                        if (user.residencia == null) {
                            int opcaoCasa = obterValorIndiceResidencia("Alugar casa pelo indice: ");

                            while (true) {

                                Residencia residencia = bancoTemporario.getResidenciaByIndexForAluguel(opcaoCasa);

                                if (!bancoTemporario.senhorioOk(residencia)) {
                                    System.out.println("NÃO PODE ALUGAR CASA SEM SENHORIO!");
                                    break;
                                } else {
                                    user.alugaResidencia(residencia);
                                    System.out.println();
                                    System.out.println("Casa alugada com sucesso!");
                                    break;
                                }

                            }

                        } else {
                            System.out.printf("%s, você já alugou uma casa para morar!\n", user.nome);
                            System.out.println();
                        }

                    }

                } else if (opcao == 0) {
                    break;
                }
            }
        }

    }

    private void telaLoginProprietario() {
        if (bancoTemporario.proprietarios.isEmpty()) {
            System.out.println("NÃO NENHUM PROPRIETÁRIO CADASTRADO!\n");
        } else {
            int opcao;
            System.out.println("|LOGIN COM CPF|: ");
            System.out.println();

            String cpf = obterCpfValidado("Digite seu cpf sem pontos e hífen: ");

            Proprietario user = encontraLoginProprietario(cpf);

            System.out.println();
            System.out.println("Login feito com sucesso! Bem vindo " + user.nome + "\n");

            while (true) {
                opcao = obterValorMenu("[ 1 ] Listar info\n [ 2 ] Ver casas disponíveis\n [ 0 ] Sair\n Opção: ",
                        new int[] { 0, 1, 2 });
                System.out.println();

                if (opcao == 1) {
                    user.listaInformacoes();
                } else if (opcao == 2) {
                    System.out.println();

                    bancoTemporario.listaResidenciasForCompraIfNotEmpty();
                    int escolha = obterValorIndiceResidencia("Compre a casa pelo Indice: ");

                    Residencia casaComprada = bancoTemporario.getResidenciaByIndexForCompra(escolha);

                    user.addResidencia(casaComprada);
                    System.out.println("\nCasa comprada com sucesso!");

                }

                else if (opcao == 0) {
                    break;
                }

            }
        }

    }

    // UTILITÁRIOS:
    private boolean verificaPosicaoFrente(String posicaoFrente) {
        String[] posicoes = new String[] { "norte", "sul", "leste", "oeste" };

        for (String posicao : posicoes) {
            if (posicaoFrente.equals(posicao)) {
                return true;
            }

        }
        return false;
    }

    private boolean isMenorIgualZero(double valor) {
        return (valor <= 0);
    }

    private String converteValorIsEsquina(String isDeEsquina) {
        if (isDeEsquina.equals("s")) {
            return "É de esquina";
        } else {
            return "Não é de esquina";
        }

    }

    private String obterValorEntrada(String mensagem) {

        System.out.print(mensagem);
        return entrada.nextLine();
    }

    private double obterValorPositivo(String mensagem) {
        double valor;
        do {
            System.out.print(mensagem);
            valor = Double.parseDouble(entrada.nextLine());
        } while (isMenorIgualZero(valor));

        return valor;
    }

    private boolean isNumerico(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            return false;
        }

        for (int pos = 0; pos < cpf.length(); pos++) {
            if (!Character.isDigit(cpf.charAt(pos))) {
                return false;
            }
        }
        return true;
    }

    private String obterCpfValidado(String mensagemInicial) {
        System.out.println(mensagemInicial);
        String cpf = entrada.nextLine();
        while (cpf.length() != 11 || !isNumerico(cpf)) {
            cpf = obterValorEntrada(
                    "VALOR INVÁLIDO! Seu cpf é menor do que 11 dígitos ou possui caracteres especiais! CPF: ");
        }
        return cpf;
    }

    private boolean validarIndiceExistePessoa(int indice) {
        try {
            bancoTemporario.pessoas.get(indice);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }


    private boolean validarIndiceExisteResidencia(int index) {
        try {
            bancoTemporario.residencias.get(index);
            return true;
        } catch (Exception e) {
            return false;
            // TODO: handle exception
        }

    }

    private int obterValorIndiceResidencia(String mensagem) {
        System.out.print(mensagem);
        int index;
        while (true){
            try {
                index = Integer.parseInt(entrada.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("VALOR INVÁLIDO! Digite novamente o indice: ");
            }

        }
        while (index < 0 || !validarIndiceExisteResidencia(index)) {
            System.out.print("Não existe este indice! Digite novamente: ");
            index = Integer.parseInt(entrada.nextLine());
        }
        return index;

    }

    private boolean checarOpcaoMenu(int indice, int[] listaOpcoes) {
        for (int i : listaOpcoes) {
            if (indice == i) {
                return true;
            }
        }
        return false;

    }

    private int obterValorMenu(String mensagemMenu, int[] listaOpcoes) {
        int indice;
        while (true) {
            try {
                System.out.print(mensagemMenu);
                indice = Integer.parseInt(entrada.nextLine());

                if (listaOpcoes != null) {
                    if (!checarOpcaoMenu(indice, listaOpcoes)) {
                        System.out.print("Valor fora do intervalo das opções!");
                        System.out.println();
                    } else {
                        break;
                    }
                }
                break;

            } catch (NumberFormatException z) {
                System.out.println("VALOR INVÁLIDO!");
            }
        }

        return indice;

    }

    public Pessoa encontraLoginPessoa(String cpf) {
        Pessoa pessoa = bancoTemporario.getPessoaByCPF(cpf);
        while (pessoa == null) {
            cpf = obterCpfValidado("USUÁRIO NÃO ENCONTRADO. DIGITE NOVAMENTE O CPF: ");
            pessoa = bancoTemporario.getPessoaByCPF(cpf);
        }
        return pessoa;
    }

    public Proprietario encontraLoginProprietario(String cpf) {
        Proprietario proprietario = bancoTemporario.getProprietarioByCPF(cpf);
        while (proprietario == null) {
            cpf = obterCpfValidado("USUÁRIO NÃO ENCONTRADO. DIGITE NOVAMENTE O CPF: ");
            proprietario = bancoTemporario.getProprietarioByCPF(cpf);
        }
        return proprietario;
    }

}
