import java.util.ArrayList;
import java.util.Scanner;

public class App {
  static Scanner kb = new Scanner(System.in);

  public static void main(String[] args) {
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


  private static String input(String question) {
    System.out.print(">> " + question);
    String text = kb.nextLine();

    return text;
  }


  private static ArrayList<String> search(String query) {
    ArrayList<String> words = new ArrayList<>();

    // Busca na arvore!

    // Exemplos de teste
    words.add("Teste 2");
    words.add("Teste 1");
    words.add("Alarido");

    return words;
  }


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


  private static String getContext(String word) {
    String[] contexts = Dictionary.findContexts(word);

    if (contexts == null) {
      return null;
    }

    return contexts[0];
  }
}
