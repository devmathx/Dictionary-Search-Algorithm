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
        this.setRoot('-'); //root da classe é -, representa o inico da árvore
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
   
    public LinkedList<Palavra> getWords(String word){
        char[] values = word.toCharArray();
        return getWords(root, values, 0, word);
    }


    private LinkedList<Palavra> getWords(Node startNode, char[] values, int index, String word){
        LinkedList<Palavra> result = new LinkedList<>();
       
        if(index == values.length){
            traverseWords(startNode, word, result);
        }
        else{
            Node nextNode = containSubtree(startNode, values[index]);


            if(nextNode != null){
                result = getWords(nextNode, values, (index + 1) , word);
            }
        }
       
        return result;
    }


    private void traverseWords(Node currentNode, String currentWord, LinkedList<Palavra> result) {
        currentWord += currentNode.element;


        if (currentNode.context != null) {
            // Se o nó atual representa uma palavra, adiciona à lista de resultados
            result.add(new Palavra(currentWord, currentNode.context));
         
        }


        for (int i = 0; i < currentNode.getSubtreesSize(); i++) {
            traverseWords(currentNode.getSubtree(i), currentWord, result);
        }
    }




    private boolean hasChildren(Node node) {
        return node.getSubtreesSize() > 0;
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
   
   
    ///////////////////////////////////////////
    // Codigos abaixo geram saida para GraphViz
   
   private void geraNodosDOT(Node n) {
        System.out.println("node [shape = circle];\n");


        LinkedList<Character> L = positionsWidth(); // retorna uma lista de elementos (Characters)
        Map<Character, Integer> elementCount = new HashMap<>();


        for (int i = 0; i < L.size(); i++) {
            char currentElement = L.get(i);
            int count = elementCount.getOrDefault(currentElement, 0) + 1;
            elementCount.put(currentElement, count);


            // nodeA1 [label = "A"]
            System.out.println("node" + currentElement + count + " [label = \"" + currentElement + "\"]");
        }
    }


    private void geraConexoesDOT(Node n) {
        Map<String, Integer> connectionCount = new HashMap<>();
        geraConexoesDOTRec(n, connectionCount);
    }


    private void geraConexoesDOTRec(Node n, Map<String, Integer> connectionCount) {
        for (int i = 0; i < n.getSubtreesSize(); i++) {
            Node aux = n.getSubtree(i);
            String connectionKey = "node" + n.element + " -> " + "node" + aux.element + ";";


            int count = connectionCount.getOrDefault(connectionKey, 0) + 1;
            connectionCount.put(connectionKey, count);


            // Adiciona um número à frente do elemento para evitar duplicatas
            System.out.println(connectionKey.replace(";", count + ";"));


            geraConexoesDOTRec(aux, connectionCount);
        }
    }
   
    // Gera uma saida no formato DOT
    // Esta saida pode ser visualizada no GraphViz
    // Versoes online do GraphViz pode ser encontradas em
    // http://www.webgraphviz.com/
    // http://viz-js.com/
    // https://dreampuf.github.io/GraphvizOnline
    public void geraDOT() {
        if (root != null) {
            System.out.println("digraph g { \n");
            // node [style=filled];


            geraNodosDOT(root);


            geraConexoesDOT(root);
            System.out.println("}\n");
        }
    }    
}
