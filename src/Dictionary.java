import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

public class Dictionary {

  /**
   * Busca os significados de uma palavra.
   * @param word Palavra para buscar significados
   * @return Uma lista com os significados ou {@code null}, para palavras inválidas
   */
  public static String[] findContexts(String word) {
    LinkedList<Palavra> lines = readCSV();
    String[] contexts = null;

    for (Palavra line : lines) {
      String w = line.getPalavra();
      if (w.equals(word)) {
        String contextString = line.getSignificado();
        contextString = contextString.replace(".", "");
        contextString = contextString.replace("?", "");
        contexts = contextString.split(",");
      }
    }

    return contexts;
  }
  
  /**
   * Busca as palavras do dicionário.
   * @return Um {@code ArrayList} com as palavras do dicionário 
   */
  public static ArrayList<String> getWords() {
    LinkedList<Palavra> lines = readCSV();
    ArrayList<String> words = new ArrayList<>();

    for (Palavra p : lines) {
      String word = p.getPalavra();
      words.add(word);
    }

    return words;
  }

  /**
   * Verifica se uma palavra está no dicionário.
   * @param word Palavra para buscar
   * @return Se contem ou não
   */
  public static boolean contains(String word) {
    ArrayList<String> words = getWords();

    return words.contains(word);
  }

  /**
   * Lê o arquivo CSV.
   * @return Uma lista com as linhas do arquivo
   */
  private static LinkedList<Palavra> readCSV() {
    LinkedList<Palavra> lista = new LinkedList<>();
    String aux[];

    Path path1 = Paths.get("dicionario.csv");

    try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("UTF-8"))) {
      String line = reader.readLine();
      while (line != null) {
        if (line.length() > 0) {
          aux = line.split(";");
          Palavra p = new Palavra(aux[0], aux[1]);
          lista.add(p);
        }
        line = reader.readLine();
      }
    } catch (IOException e) {
      System.err.format("Erro na leitura do arquivo: ", e);
    }

    return lista;
  }
}
