import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class App {

  private static Scanner kb = new Scanner(System.in); //Scanner para input
  private static LinkedList<Palavra> words;  //lista de palavras lidas na árvore de acordo com pequisa
  private static GeneralTree tree = new GeneralTree();  //ávore genérica

  /**
   * Inicia o sistema de busca em dicionário.
   */
  public static void execute() {
    LinkedList<Palavra> dicionario = Dictionary.readCSV(); //lendo CSV e adiccionando em dicinario
    tree.setRoot('0'); //setar um root como 0
    for(int i = 0; i < dicionario.size(); i++) {
      tree.addWord(dicionario.get(i)); //Preenchendo a árvore com as palavras
    }
    


    menu();
  }

  /**
   * Execução do menu principal com looping em recursão
   */
  private static void menu(){
      String text;
      Palavra word;
      boolean finalize = false;

      System.out.println("\n----- Dicionário -----");
      text = input("Pesquisar: ");
      words = search(text);

      word = chooseWord(text);

      if (word == null) {
        System.out.println("Não há resultados para essa palavra.\n");
      }
      else{
        System.out.println("Significado de " + word.getPalavra() + ": " + word.getSignificado() + "\n");
      }

      String verify = input("Deseja pesquisar outra palavra? (0 - NÃO | 1 - SIM)  >> ");
      switch(verify){
        case "1": menu(); break;
        case "0": return;
        default: System.out.println("Opção inválida...");
      }

      System.out.println("\nPrograma encerrado...");
      return;
  }

  /**
   * Pega uma entrada de texto pelo terminal.
   * @param question Questão refente ao que está sendo requisitado
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
  private static LinkedList<Palavra> search(String query) {
    query = query.trim();
    LinkedList<Palavra> response = tree.getWords(query);
   
    return response;
  }

  /**
   * Exibe no terminal as palavras correspondentes para o ususário escolher.
   * @param query Texto pesquisado
   * @return A palavra selecionada
   */
  private static Palavra chooseWord(String query) {
    String wordIndex;
    System.out.println("\nResultados para '" + query + "':");

    int index = 0;
    for (Palavra word : words) {
      System.out.println(index + " = " + word.getPalavra());
      index++;
    }

    if(words.size() > 0 && words != null){
        wordIndex = input("Buscar significado de: ");
        if (Integer.parseInt(wordIndex) > index) {
          System.out.println("Indice inválido");
          return null;
        }
    }
    else{
      return null;
    }

    Palavra selectedWord = words.get(Integer.parseInt(wordIndex));
    return selectedWord;
  }

  
}
