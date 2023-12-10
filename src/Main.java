import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Criando uma lista para armazenar os funcionários cadastrados
        List<Funcionario> funcionarios = new ArrayList<>();


        cadastrarFuncionario(funcionarios, "Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        cadastrarFuncionario(funcionarios, "João", LocalDate.of(1990, 05, 12), new BigDecimal("2284.38"), "Operador");
        cadastrarFuncionario(funcionarios, "Caio", LocalDate.of(1961, 05, 02), new BigDecimal("9836.14"), "Coordenador");
        cadastrarFuncionario(funcionarios, "Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor");
        cadastrarFuncionario(funcionarios, "Alice", LocalDate.of(1995, 01, 05), new BigDecimal("2234.68"), "Recepcionista");
        cadastrarFuncionario(funcionarios, "Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador");
        cadastrarFuncionario(funcionarios, "Arthur", LocalDate.of(1993, 11, 19), new BigDecimal("4071.84"), "Contador");
        cadastrarFuncionario(funcionarios, "Laura", LocalDate.of(1994, 07, 8), new BigDecimal("3017.45"), "Gerente");
        cadastrarFuncionario(funcionarios, "Heloísa", LocalDate.of(2003, 05, 24), new BigDecimal("1606.85"), "Eletricista");
        cadastrarFuncionario(funcionarios, "Helena", LocalDate.of(1996, 9, 02), new BigDecimal("2799.93"), "Gerente");

        // Imprimindo informações dos funcionários antes do aumento
        System.out.println("Antes do aumento:");
        imprimirFuncionarios(funcionarios);
        System.out.println("######################################");

        // Aplicando aumento de 10% nos salários
        aplicarAumentoSalario(funcionarios, new BigDecimal("0.10"));

        // Imprimindo informações dos funcionários após o aumento
        System.out.println("\nApós o aumento:");
        imprimirFuncionarios(funcionarios);
        System.out.println("######################################");

        deletarFuncionario(funcionarios,"João");

        // Agrupando funcionários por função em um Map
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparFuncionariosPorFuncao(funcionarios);

        // Imprimindo os funcionários agrupados por função
        System.out.println("\nFuncionários agrupados por função:");
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);
        System.out.println("######################################");

        // Imprimindo os funcionários que fazem aniversário nos meses 10 e 12
        System.out.println("\nFuncionários que fazem aniversário nos meses 10 e 12:");
        imprimirAniversariantes(funcionarios, 10, 12);
        System.out.println("######################################");

        // Imprimindo o funcionário com a maior idade
        System.out.println("\nFuncionário com a maior idade:");
        imprimirFuncionarioMaiorIdade(funcionarios);
        System.out.println("######################################");

        // Imprimindo o total dos salários dos funcionários
        System.out.println("\nTotal dos salários dos funcionários: " + calcularTotalSalarios(funcionarios));
        System.out.println("######################################");

        System.out.println("\nQuantidade de salários mínimos que cada funcionário recebe:");
        imprimirSalariosMinimos(funcionarios, new BigDecimal("1212.00"));
        System.out.println("######################################");

    }

    private static void cadastrarFuncionario(List<Funcionario> funcionarios, String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        Funcionario funcionario = new Funcionario(nome, dataNascimento, salario, funcao);
        funcionarios.add(funcionario);
    }

    // Função para imprimir um funcionário
    private static void imprimirFuncionario(Funcionario funcionario, DateTimeFormatter formatoData, DecimalFormat decimalFormat) {
        System.out.println("Nome: " + funcionario.getNome());
        System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(formatoData));
        System.out.println("Salário: " + decimalFormat.format(funcionario.getSalario()));
        System.out.println("Função: " + funcionario.getFuncao());
        System.out.println("--------------------");
    }

    // Função para imprimir um funcionários
    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        // Formatador para datas no formato dd/mm/aaaa
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Formatador para valores numéricos com ponto como separador de milhar e vírgula como decimal
        NumberFormat formatoNumerico = DecimalFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat decimalFormat = (DecimalFormat) formatoNumerico;
        decimalFormat.applyPattern("#,##0.00");

        for (Funcionario funcionario : funcionarios) {
            imprimirFuncionario(funcionario, formatoData, decimalFormat);
        }
    }
    // Função para deletar um funcionário pelo nome
    private static void deletarFuncionario(List<Funcionario> funcionarios, String nome) {
        Iterator<Funcionario> iterator = funcionarios.iterator();

        while (iterator.hasNext()) {
            Funcionario funcionario = iterator.next();

            if (funcionario.getNome().equals(nome)) {
                iterator.remove();
                System.out.println("Funcionário " + nome + " removido com sucesso.");
                return;
            }
        }

        System.out.println("Funcionário " + nome + " não encontrado.");
    }

    // Função para agrupar funcionários por função em um Map
    private static Map<String, List<Funcionario>> agruparFuncionariosPorFuncao(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();

        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();
            funcionariosPorFuncao.computeIfAbsent(funcao, k -> new ArrayList<>()).add(funcionario);
        }

        return funcionariosPorFuncao;
    }

    // Função para imprimir os funcionários agrupados por função
    private static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat formatoNumerico = DecimalFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat decimalFormat = (DecimalFormat) formatoNumerico;
        decimalFormat.applyPattern("#,##0.00");

        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario funcionario : entry.getValue()) {
                imprimirFuncionario(funcionario, formatoData, decimalFormat);
            }
        }
    }

    // Função para aplicar aumento de salário a todos os funcionários
    private static void aplicarAumentoSalario(List<Funcionario> funcionarios, BigDecimal percentualAumento) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal aumento = funcionario.getSalario().multiply(percentualAumento);
            BigDecimal novoSalario = funcionario.getSalario().add(aumento);
            funcionario.setSalario(novoSalario);
        }
    }

    // Função para imprimir os funcionários que fazem aniversário nos meses especificados
    private static void imprimirAniversariantes(List<Funcionario> funcionarios, int... meses) {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat formatoNumerico = DecimalFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat decimalFormat = (DecimalFormat) formatoNumerico;
        decimalFormat.applyPattern("#,##0.00");

        for (Funcionario funcionario : funcionarios) {
            int mesAniversario = funcionario.getDataNascimento().getMonthValue();
            for (int mes : meses) {
                if (mesAniversario == mes) {
                    imprimirFuncionario(funcionario, formatoData, decimalFormat);
                    break;
                }
            }
        }
    }

    // Função para imprimir o funcionário com a maior idade
    private static void imprimirFuncionarioMaiorIdade(List<Funcionario> funcionarios) {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat formatoNumerico = DecimalFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat decimalFormat = (DecimalFormat) formatoNumerico;
        decimalFormat.applyPattern("#,##0.00");

        Funcionario funcionarioMaiorIdade = encontrarFuncionarioMaiorIdade(funcionarios);

        if (funcionarioMaiorIdade != null) {
            System.out.println("Nome: " + funcionarioMaiorIdade.getNome());
            System.out.println("Idade: " + calcularIdade(funcionarioMaiorIdade.getDataNascimento()));
        } else {
            System.out.println("Nenhum funcionário encontrado.");
        }
    }

    // Função para encontrar o funcionário com a maior idade
    private static Funcionario encontrarFuncionarioMaiorIdade(List<Funcionario> funcionarios) {
        Funcionario funcionarioMaiorIdade = null;
        int idadeMaxima = Integer.MIN_VALUE;

        for (Funcionario funcionario : funcionarios) {
            int idade = calcularIdade(funcionario.getDataNascimento());
            if (idade > idadeMaxima) {
                idadeMaxima = idade;
                funcionarioMaiorIdade = funcionario;
            }
        }

        return funcionarioMaiorIdade;
    }

    // Função para calcular a idade com base na data de nascimento
    private static int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    // Função para imprimir o total dos salários dos funcionários
    private static void imprimirTotalSalarios(List<Funcionario> funcionarios) {
        BigDecimal totalSalarios = calcularTotalSalarios(funcionarios);
        NumberFormat formatoNumerico = DecimalFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat decimalFormat = (DecimalFormat) formatoNumerico;
        decimalFormat.applyPattern("#,##0.00");

        System.out.println("Total dos salários dos funcionários: " + decimalFormat.format(totalSalarios));
    }

    // Função para calcular o total dos salários dos funcionários
    private static BigDecimal calcularTotalSalarios(List<Funcionario> funcionarios) {
        BigDecimal totalSalarios = BigDecimal.ZERO;
        for (Funcionario funcionario : funcionarios) {
            totalSalarios = totalSalarios.add(funcionario.getSalario());
        }
        return totalSalarios;
    }

    // Função para imprimir quantos salários mínimos cada funcionário recebe
    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios, BigDecimal salarioMinimo) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        for (Funcionario funcionario : funcionarios) {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(funcionario.getNome() + ": " + decimalFormat.format(salariosMinimos) + " salários mínimos");
        }
    }
}
