import java.util.ArrayList;
import java.util.Scanner;

public class App {
    
    static Scanner kb = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("----- Dicionário -----");

        String text = "";
        boolean finalize = false;

        do {
            text = input("Pesquisar: ");

            ArrayList<String> words = search(text);

            String word = chooseWord(text, words);



        } while (!finalize);
    }

    private static String input(String question) {
        System.out.print(question);
        String text = kb.nextLine();

        return text;
    }

    private static ArrayList<String> search(String query) {
        ArrayList<String> words = new ArrayList<>();
        
        // Busca na arvore os resultados dado a "query"
        
        return words;
    }

    private static String chooseWord(String query, ArrayList<String> words) {
        System.out.println("Resultados para " + query);

        int index = 0;
        for (String word : words) {
            System.out.println(index + " = " + word);
            index++;
        }

        String wordIndex = input("Buscar significado de: ");
        String selectedWord = words.get(Integer.parseInt(wordIndex)); 
        return selectedWord;
    }

    private static String getContext()
}
