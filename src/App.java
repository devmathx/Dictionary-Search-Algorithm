import java.util.ArrayList;
import java.util.Scanner;

public class App {

  private static Scanner kb = new Scanner(System.in);

  /**
   * Inicia o sistema de busca em dicionário.
   */
  public static void execute() {
    System.out.println("----- Dicionário -----");

    String text, word, context = "";
    boolean finalize = false;

    do {
      text = input("Pesquisar: ");
      ArrayList<String> words = search(text);

      word = chooseWord(text, words);
      if (word == null) {
        word = chooseWord(text, words);
      }

      context = getContext(word);
      if (context != null) {
        finalize = true;
      } else {
        System.out.println("Não há resultados para essa palavra, digite novamente.\n");
      }

    } while (!finalize);

    System.out.println("Significado de " + word + ": " + context);
  }

  /**
   * Pega uma entrada de texto pelo terminal.
   * @param question Questão refente ao que está sendo requsitado
   * @return Resposta
   */
  private static String input(String question) {
    System.out.print(">> " + question);
    String text = kb.nextLine();

    return text;
  }

  /**
   * Busca palavras relacionadas com o que foi digitado.
   * @param query Texto pesquisado
   * @return Lista de palavras que contem o trecho  
   */
  private static ArrayList<String> search(String query) {
    ArrayList<String> words = new ArrayList<>();

    // Busca na arvore!

    // Exemplos de teste
    words.add("Teste 2");
    words.add("Teste 1");
    words.add("Alarido");

    return words;
  }

  /**
   * Exibe no terminal as palavras correspondentes para o ususário escolher.
   * @param query Texto pesquisado
   * @param words Lista de palavras
   * @return A palavra selecionada
   */
  private static String chooseWord(String query, ArrayList<String> words) {
    System.out.println("\nResultados para '" + query + "':");

    int index = 0;
    for (String word : words) {
      System.out.println(index + " = " + word);
      index++;
    }

    String wordIndex = input("Buscar significado de: ");
    if (Integer.parseInt(wordIndex) > index) {
      System.out.println("Indice inválido");
      return null;
    }

    String selectedWord = words.get(Integer.parseInt(wordIndex));
    return selectedWord;
  }

  /**
   * Pega o signicado de uma palavra.
   * @param word Palavra para verificar
   * @return Signicado, ou {@code null} se a palavra não possuir
   */
  private static String getContext(String word) {
    String[] contexts = Dictionary.findContexts(word);

    if (contexts == null) {
      return null;
    }

    return contexts[0];
  }
}
