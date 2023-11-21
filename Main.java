public class Main {
  
  public static void main(String[] args) {
    
    GeneralTree tree = new GeneralTree();

    tree.setRoot('X');

    Character[] chars1 = { 'B', 'A', 'N', 'A', 'N', 'A' };
    // Character[] chars2 = { 'B', 'A', 'N', 'H', '0' };
    
    tree.addWord(chars1);

    tree.geraDOT();
  }
}


