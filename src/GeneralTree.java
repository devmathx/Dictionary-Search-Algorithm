import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class GeneralTree {


    // Classe interna Node
    private class Node {
        // Atributos da classe Node
        private Node father;
        private Character element;
        private LinkedList<Node> subtrees;
        private String context;


        // Métodos da classe Node
        public Node(Character element) {
            father = null;
            this.element = element;
            subtrees = new LinkedList<>();
        }
        private void addSubtree(Node n) {
            n.father = this;
            subtrees.add(n);
        }
        private boolean removeSubtree(Node n) {
            n.father = null;
            return subtrees.remove(n);
        }
        public Node getSubtree(int i) {
            if ((i < 0) || (i >= subtrees.size())) {
                throw new IndexOutOfBoundsException();
            }
            return subtrees.get(i);
        }
        public int getSubtreesSize() {
            return subtrees.size();
        }


        public String getContext(){
            return this.context;
        }
    }
   
    // Atributos da classe GeneralTreeOfInteger
    private Node root;
    private int count = 0;
   
    /**
     * Metodo construtor.
     */
    public GeneralTree() {
        root = null;
        count = 0;
    }
   
    /**
     * Metodo para setar o root
     * @param element novo valor de root
     */
    public void setRoot(Character element) {
        Node aux = new Node(element);
        this.root = aux;
    }

    /**
     * Mverifica se o nodo contem um filho com determinado valor,
     * se possui, irá retornar ele, senão retorna null
     * (o Método não verifica os filhos dos filhos...)
     * @param element elemendo a ser usado para busca
     * @param parente nodo em que procuramos o filhos
     */
    public Node containSubtree(Node parent, Character element) {
        int size = parent.getSubtreesSize();


        for(int i = 0;  i < size ; i++){
            if(parent.getSubtree(i).element.equals(element)){
                return parent.getSubtree(i);
            }
        }

        return null;
    }

   

    /**
     * Adiciona um  nodo como subarvore de father
     * retorna o próprio nodo
     * @param elem elemento do novo nodo
     * @param father elemento pai que vai receber o novo nodo na subarvore
     */
    public Node addWithFather(Character elem, Node father) {
        Node aux = new Node(elem);


        father.addSubtree(aux);
        aux.father = father;
         count++;
        return aux;
    }

    /**
     * Adiciona palavras na árvore
     * a partir do root adicionamos os caracteres como nodos filhos um do outro
     * se existe um nodo filho no pai com o mesmo valor a ser adicinado, pulamos ele e vamos para o próximo, e dessa vez o pai faz diferença a esse nodo que já existe
     * @param values[] um array de caracteres da palavra
     */
    public void addWord(Palavra word)
    {
        Node aux = this.root;
        char[] ch = word.getPalavra().toCharArray();
        Node newAux;


        for (int i = 0 ; i < ch.length; i++) {
            Node contains = containSubtree(aux, ch[i]);
            if(contains != null){
                newAux = contains;
            }
            else{
                Node target = this.addWithFather(ch[i], aux);
                newAux = target;
            }


            aux = newAux;
            if((i +1) == ch.length) {
                aux.context = word.getSignificado();
            }
           
        }
    }


    /**
     * Formata entrada do usuário para ficar compativel com palavras do arquivo
     * @param palavra palavra a ser formatada
     */
    public static String formatWord(String palavra){
        if (palavra == null || palavra.isEmpty()) {
          return palavra;  
        }
    
        String primeiraLetraMaiuscula = palavra.substring(0, 1).toUpperCase();
        String restoDaPalavraMinuscula = palavra.substring(1).toLowerCase();
    
        return primeiraLetraMaiuscula + restoDaPalavraMinuscula;
    }
    

     /**
     * Obtém uma lista de palavras que começam com uma determinada sequência.
     * @param word Sequência de caracteres a ser usada como prefixo para buscar palavras.
     * @return Lista de palavras que começam com a sequência fornecida.
     */
    public LinkedList<Palavra> getWords(String word){
        word = formatWord(word);
        char[] values = word.toCharArray();
        return getWords(root, values, 0, word);
    }

    /**
     * Obtém uma lista de palavras que começam com uma determinada sequência, iniciando a busca a partir de um determinado nó.
     * @param startNode Nodo a partir do qual a busca deve ser iniciada.
     * @param values Array de caracteres representando a palavra a ser pesquisada.
     * @param index Índice que indica a posição atual no array de caracteres.
     * @param word Prefixo completo a ser pesquisado.
     * @return Lista de palavras que começam com a sequência fornecida.
     */
    private LinkedList<Palavra> getWords(Node startNode, char[] values, int index, String word){
        LinkedList<Palavra> result = new LinkedList<>();
        if(index == values.length){
            traverseWords(startNode, word, result, 0);
        }
        else{
            Node nextNode = containSubtree(startNode, values[index]);

            if(nextNode != null){
                result = getWords(nextNode, values, (index + 1) , word);
            }
        }
       
        return result;
    }

    /**
     * Percorre os nodos da árvore Trie para obter palavras que começam com uma determinada sequência.
     * @param currentNode Nodo atual durante a travessia.
     * @param currentWord Palavra atual formada durante a travessia.
     * @param result Lista de palavras resultante.
     * @param index Índice usado para definir quando alterar a currentWord.
     */
    private void traverseWords(Node currentNode, String currentWord, LinkedList<Palavra> result, int index) {
        if(index  != 0){
            currentWord += currentNode.element;
        }

        index += 1;

        if (currentNode.context != null) {
            // Se o nó atual representa uma palavra, adiciona à lista de resultados
            result.add(new Palavra(currentWord, currentNode.context));
        }


        for (int i = 0; i < currentNode.getSubtreesSize(); i++) {
            traverseWords(currentNode.getSubtree(i), currentWord, result, index);
        }
    }

   
    // Procura por "elem" a partir de "n" seguindo um
    // caminhamento pre-fixado. Retorna a referencia
    // para o nodo no qual "elem" esta armazenado.
    // Se não encontrar "elem", ele retorna NULL.
    private Node searchNodeRef(Character elem, Node n) {
        if (n == null)
            return null;
       
        if (elem.equals(n.element))
            return n;
   
        Node aux = null;
        for(int i=0; i<n.getSubtreesSize(); i++) {
            aux = searchNodeRef(elem,n.getSubtree(i));
            if (aux != null)
                return aux;
        }
        return aux;
    }
   
    /**
     * Adiciona elem como filho de father
     * @param elem elemento a ser adicionado na arvore.
     * @param father pai do elemento a ser adicionado.
     * @return true se encontrou father e adicionou elem na arvore,
     * false caso contrario.
     */
    public boolean add(Character elem, Character elemFather) {
        Node n = new Node(elem);


        if (elemFather == null) {
            if (root != null) {
                n.addSubtree(root);
                root.father = n;
            }
            root = n;
            count++;
            return true;
        }


        Node aux = searchNodeRef(elemFather,root);
        if (aux != null) {
            aux.addSubtree(n);
            n.father = aux;
            count++;
            return true;
        }
        return false;
    }
   
    /**
     * Retorna uma lista com todos os elementos da árvore numa ordem de
     * caminhamento em largura.
     * @return lista com os elementos da arvore na ordem do caminhamento em largura
     */
    public LinkedList<Character> positionsWidth() {
        LinkedList<Character> lista = new LinkedList<>();
       
        if (root != null) {
            Queue<Node> fila = new Queue<>();
           
            fila.enqueue(root);
           
            while (!fila.isEmpty()) {
                Node aux = fila.dequeue();
                lista.add(aux.element);
                for (int i=0; i<aux.getSubtreesSize(); i++) {
                    fila.enqueue(aux.getSubtree(i));
                }
            }
           
        }
       
        return lista;
    }    

    /**
     * Retorna uma lista com todos os elementos da árvore numa ordem de
     * caminhamento pre-fixado.
     * @return lista com os elementos da arvore na ordem do caminhamento pre-fixado
     */    
    public LinkedList<Character> positionsPre(Node n) {  
        LinkedList<Character> lista = new LinkedList<>();
        positionsPreAux(n,lista);
        return lista;
    }  
    private void positionsPreAux(Node n, LinkedList<Character> lista) {
        if (n!=null) {
            lista.add(n.element);
            for(int i=0; i<n.getSubtreesSize(); i++) {
                positionsPreAux(n.getSubtree(i), lista);
            }            
        }
    }

    /**
     * Retorna uma lista com todos os elementos da árvore numa ordem de
     * caminhamento pos-fixado.
     * @return lista com os elementos da arvore na ordem do caminhamento pos-fixado
     */    
    public LinkedList<Character> positionsPos() {  
        LinkedList<Character> lista = new LinkedList<>();
       
        positionsPosAux(root,lista);
        return lista;
    }  
   
    private void positionsPosAux(Node n, LinkedList<Character> lista) {
        if (n!=null) {
           
            for(int i=0; i<n.getSubtreesSize(); i++) {
                positionsPosAux(n.getSubtree(i), lista);
            }            


            lista.add(n.element);
        }
    }    
   
    /**
     * Remove o galho da arvore que tem element na raiz. A
     * remocao inclui o nodo que contem "element".
     * @param element elemento que sera removido junto com sua
     * subarvore.
     * @return true se achou element e removeu o galho, false
     * caso contrario.
     */
    public boolean removeBranch(Character element) {
        if (root == null)
            return false;
       
        if (element.equals(root.element)) {
            root = null;
            count = 0;
            return true;
        }
       
        Node aux = this.searchNodeRef(element, root);
        if (aux == null)
            return false;
       
        Node pai = aux.father;
        pai.removeSubtree(aux);
        aux.father = null;
        count = count - countNodes(aux);
        return true;
    }

    private int countNodes(Node n) {
        if (n == null)
            return 0;
       
        int c = 1;
       
        for(int i=0; i<n.getSubtreesSize(); i++) {
            c = c + countNodes(n.getSubtree(i));
        }
       
        return c;
    }    
}