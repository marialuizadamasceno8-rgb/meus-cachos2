
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class MeuCachos {

    static Scanner sc = new Scanner(System.in);

    // Cadastro dos produtos
    static String[] nome = {
        "Creme de Pentear Salon Line",
        "Ativador Elseve",
        "Finalizador Apice",
        "Touca de Cetim",
        "Pente Garfo"
    };

    static String[] categoria = {
        "CREME",
        "CREME",
        "CREME",
        "ACESSORIO",
        "ACESSORIO"
    };

    static double[] preco = {
        35.90, 42.00, 39.50, 25.00, 12.00
    };

    // Controle do carrinho e das avaliações
    static int[] qtd = new int[5];
    static int[] somaAval = new int[5];
    static int[] numAval = new int[5];

    // Histórico de avaliações da sessão
    static List<String> historicoAvaliacoes = new ArrayList<>();

    // Calcula o subtotal
    public static double subtotal(double preco, int quantidade) {
        return preco * quantidade;
    }

    // Calcula o desconto
    public static double calcularDesconto(double valor) {
        double perc;

        if (valor >= 100) {
            perc = 10;
        } else {
            perc = 5;
        }

        return valor * perc / 100;
    }

    // Calcula a média das avaliações
    public static double media(double soma, int num) {
        if (num == 0) {
            return 0;
        }

        return soma / num;
    }

    // Entrada de números inteiros com tratamento de erro
    public static int lerInteiro(String mensagem, int minimo, int maximo) {
        int valor;

        while (true) {
            System.out.println(mensagem);

            try {
                valor = Integer.parseInt(sc.nextLine());

                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }

                System.out.println("Valor fora do intervalo permitido.");

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
            }
        }
    }

    // Entrada de números decimais
    public static double lerDouble(String mensagem) {
        double valor;

        while (true) {
            System.out.println(mensagem);

            try {
                valor = Double.parseDouble(
                    sc.nextLine().replace(",", ".")
                );

                if (valor >= 0) {
                    return valor;
                }

                System.out.println("Digite um valor positivo.");

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número.");
            }
        }
    }

    // Menu principal
    public static void mostrarMenu() {
        System.out.println("\n========== MEU CACHOS ==========");
        System.out.println("1 - Ver todos os produtos");
        System.out.println("2 - Filtrar por categoria");
        System.out.println("3 - Filtrar por faixa de preço");
        System.out.println("4 - Buscar produto pelo nome");
        System.out.println("5 - Adicionar ao carrinho");
        System.out.println("6 - Remover do carrinho");
        System.out.println("7 - Ver carrinho e total");
        System.out.println("8 - Avaliar produto");
        System.out.println("9 - Ver histórico de avaliações");
        System.out.println("10 - Produtos em ordem alfabética");
        System.out.println("0 - Sair");
        System.out.println("================================");
    }

    // Exibe todos os produtos
    public static void listarProdutos() {
        System.out.println("\n===== PRODUTOS DISPONÍVEIS =====");

        for (int i = 0; i < nome.length; i++) {
            System.out.printf(
                "%d - %s (%s) - R$ %.2f%n",
                i + 1, nome[i], categoria[i], preco[i]
            );
        }
    }

    // Filtra os produtos por categoria
    public static void filtrarCategoria() {
        System.out.println("\n===== CATEGORIAS =====");
        System.out.println("1 - Creme");
        System.out.println("2 - Acessório");

        int opcaoCat = lerInteiro("Escolha uma categoria:", 1, 2);

        String nomeCat;

        if (opcaoCat == 1) {
            nomeCat = "CREME";
        } else {
            nomeCat = "ACESSORIO";
        }

        boolean achou = false;

        System.out.println("\nProdutos encontrados:");

        for (int i = 0; i < nome.length; i++) {
            if (categoria[i].equals(nomeCat)) {
                System.out.printf(
                    "%d - %s - R$ %.2f%n",
                    i + 1, nome[i], preco[i]
                );

                achou = true;
            }
        }

        if (!achou) {
            System.out.println("Nenhum produto encontrado.");
        }
    }

    // Filtra por faixa de preço
    public static void filtrarPreco() {
        double pMin = lerDouble("Digite o preço mínimo:");
        double pMax = lerDouble("Digite o preço máximo:");

        if (pMin > pMax) {
            System.out.println("Faixa inválida! O mínimo é maior que o máximo.");
            return;
        }

        boolean achou = false;

        for (int i = 0; i < nome.length; i++) {
            if (preco[i] >= pMin && preco[i] <= pMax) {
                System.out.printf(
                    "%d - %s - R$ %.2f%n",
                    i + 1, nome[i], preco[i]
                );

                achou = true;
            }
        }

        if (!achou) {
            System.out.println("Nenhum produto nessa faixa de preço.");
        }
    }

    // Busca produto pelo nome
    public static void buscarProduto() {
        System.out.println("\nDigite o nome ou parte do nome do produto:");
        String termo = sc.nextLine().trim();

        boolean achou = false;

        for (int i = 0; i < nome.length; i++) {
            if (nome[i].toLowerCase().contains(termo.toLowerCase())) {
                System.out.printf(
                    "%d - %s - R$ %.2f%n",
                    i + 1, nome[i], preco[i]
                );

                achou = true;
            }
        }

        if (!achou) {
            System.out.println("Produto não encontrado.");
        }
    }

    // Adiciona produto ao carrinho
    public static void adicionarCarrinho() {
        int codigo = lerInteiro("Digite o código do produto (1 a 5):", 1, 5);

        int quantidade = lerInteiro("Quantas unidades deseja adicionar?", 1, 1000);

        qtd[codigo - 1] += quantidade;

        System.out.println(
            quantidade + "x " + nome[codigo - 1] +
            " adicionado ao carrinho!"
        );
    }

    // Remove produto do carrinho
    public static void removerCarrinho() {
        int codigo = lerInteiro("Digite o código do produto (1 a 5):", 1, 5);

        if (qtd[codigo - 1] == 0) {
            System.out.println("Esse produto não está no carrinho.");
            return;
        }

        System.out.println("Quantidade atual: " + qtd[codigo - 1]);

        int quantidade = lerInteiro(
            "Quantas unidades deseja remover?",
            1,
            qtd[codigo - 1]
        );

        qtd[codigo - 1] -= quantidade;

        System.out.println("Produto removido com sucesso!");
    }

    // Exibe o carrinho e calcula os valores
    public static void mostrarCarrinho() {
        double total = 0;
        int itens = 0;

        System.out.println("\n========== SEU CARRINHO ==========");

        for (int i = 0; i < nome.length; i++) {
            if (qtd[i] > 0) {
                double sub = subtotal(preco[i], qtd[i]);

                System.out.printf(
                    "%s x%d = R$ %.2f%n",
                    nome[i], qtd[i], sub
                );

                total += sub;
                itens += qtd[i];
            }
        }

        if (itens == 0) {
            System.out.println("Seu carrinho está vazio.");
            return;
        }

        double desconto = calcularDesconto(total);
        double valorFinal = total - desconto;

        System.out.println("----------------------------------");
        System.out.printf("Quantidade de produtos: %d%n", itens);
        System.out.printf("Total sem desconto: R$ %.2f%n", total);
        System.out.printf("Desconto: R$ %.2f%n", desconto);
        System.out.printf("Total final: R$ %.2f%n", valorFinal);
        System.out.println("==================================");
    }

    // Registra a avaliação em arquivo
    public static void salvarAvaliacao(String avaliacao) {
        try (
            BufferedWriter escritor = new BufferedWriter(
                new FileWriter("dados/avaliacoes_meu_cachos.txt", true)
            )
        ) {
            escritor.write(avaliacao);
            escritor.newLine();

        } catch (IOException e) {
            System.out.println("Erro ao salvar a avaliação.");
            e.printStackTrace();
        }
    }

    // Avalia um produto
    public static void avaliarProduto() {
        int codigo = lerInteiro("Digite o código do produto (1 a 5):", 1, 5);

        int nota = lerInteiro("Digite sua nota (1 a 5):", 1, 5);

        somaAval[codigo - 1] += nota;
        numAval[codigo - 1]++;

        double mediaProduto = media(
            somaAval[codigo - 1],
            numAval[codigo - 1]
        );

        String avaliacao = nome[codigo - 1] +
            " - Nota: " + nota +
            " - Média atual: " + String.format("%.2f", mediaProduto);

        historicoAvaliacoes.add(avaliacao);

        salvarAvaliacao(avaliacao);

        System.out.println("Obrigada pela avaliação!");
        System.out.printf("Média atual do produto: %.2f%n", mediaProduto);
    }

    // Exibe avaliações realizadas na sessão atual
    public static void mostrarAvaliacoes() {
        System.out.println("\n===== HISTÓRICO DE AVALIAÇÕES =====");

        if (historicoAvaliacoes.isEmpty()) {
            System.out.println("Nenhuma avaliação realizada nesta sessão.");
            return;
        }

        for (int i = 0; i < historicoAvaliacoes.size(); i++) {
            System.out.println(
                historicoAvaliacoes.get(i)
            );
        }
    }

    // Organiza os nomes em ordem alfabética
    public static void ordenarProdutos() {
        String[] nomesOrdenados = Arrays.copyOf(nome, nome.length);

        Arrays.sort(nomesOrdenados);

        System.out.println("\n===== PRODUTOS EM ORDEM ALFABÉTICA =====");

        System.out.println(Arrays.toString(nomesOrdenados));
    }

    // Programa principal
    public static void main(String[] args) {

        int opcao;

        while (true) {
            mostrarMenu();

            opcao = lerInteiro("Digite a opção desejada:", 0, 10);

            switch (opcao) {

                case 1:
                    listarProdutos();
                    break;

                case 2:
                    filtrarCategoria();
                    break;

                case 3:
                    filtrarPreco();
                    break;

                case 4:
                    buscarProduto();
                    break;

                case 5:
                    adicionarCarrinho();
                    break;

                case 6:
                    removerCarrinho();
                    break;

                case 7:
                    mostrarCarrinho();
                    break;

                case 8:
                    avaliarProduto();
                    break;

                case 9:
                    mostrarAvaliacoes();
                    break;

                case 10:
                    ordenarProdutos();
                    break;

                case 0:
                    System.out.println("Obrigada pela visita ao Meu Cachos!");
                    sc.close();
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
