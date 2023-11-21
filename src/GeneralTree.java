import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GeneralTree {

    // Classe interna Node
    private class Node {
        // Atributos da classe Node
        public Node father;
        public Character element;
        public LinkedList<Node> subtrees;
        public String context;

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
    }

    
    
    // Atributos da classe GeneralTreeOfInteger
    private Node root;
    private int count = 0;
    
    // Metodos da classe GeneralTreeOfInteger
    
    /**
     * Metodo construtor.
     */
    public GeneralTree() {
        root = null;
        count = 0;
    }
    
    /**
     * Retorna o numero total de elementos da arvore.
     * @return count
     */
    public int size() {
        return count;
    }

    public void setRoot(Character element) {
        Node aux = new Node(element);
        this.root = aux;
    }

    public void addUnique(Character value) {
        this.addUnique(this.root, value);
    }

  
    public void addUnique(Node parent, Character value) {
        Node aux = new Node(value);

        if (parent.subtrees.size() == 0) {
            this.add(value, parent.element);
            return;
        }

        for (Node n : parent.subtrees) {
            if (n.equals(aux)) {
                this.addUnique(n, value);
                return;
            } else {
                this.add(value, parent.element);
                return;
            }
        }

    }

    public void addWord(Character[] values) {
        Character aux = this.root.element;
        for (Character ch : values) {
            this.add(ch, aux);
            aux = ch;
        }
    }

    
    // Procura por "elem" a partir de "n" seguindo um
    // caminhamento pre-fixado. Retorna a referencia
    // para o nodo no qual "elem" esta armazenado.
    // Se não encontrar "elem", ele retorna NULL.
    private Node searchNodeRef(Character elem, Node n) {
        if (n == null)
            return null;
        
        // Visita a raiz
        if (elem.equals(n.element)) // se elem esta no nodo recebido por parametro
            return n; // retorna a referencia para n
        
        // Visita os filhos
        Node aux = null;
        for(int i=0; i<n.getSubtreesSize(); i++) { 
            aux = searchNodeRef(elem,n.getSubtree(i));
            if (aux != null) // se achou
                return aux; // retorna a referencia para o nodo
        }
//        // "Versao" abaixo com foreach
//        for(Node no : n.subtrees) {
//            aux = searchNodeRef(elem,no);
//            if (aux != null) // se achou
//                return aux; // retorna a referencia para o nodo            
//        }
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
        // Primeiro cria o nodo
        Node n = new Node(elem);
        
        // Verifica se eh para inserir como raiz
        if (elemFather == null) {
            if (root != null) { // se a arvore nao esta vazia
                n.addSubtree(root);
                root.father = n;
            }
            root = n;
            count++;
            return true;
        }
        // Se nao eh para inserir como raiz, procura pelo pai
        Node aux = searchNodeRef(elemFather,root);
        if (aux != null) { // se achou elemFather na arvore
            aux.addSubtree(n); // adiciona como filho
            n.father = aux;
            count++;
            return true;
        }
        return false;
    }
    

    /**
     * Verifica se elem esta ou não na arvore.
     * @param elem a ser procurado.
     * @return true se achar elem, e false caso contrario.
     */
    public boolean contains (Integer elem) {
        // IMPLEMENTE ESTE METODO !!
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
            // Primeiro cria a fila de nodos
            Queue<Node> fila = new Queue<>();
            
            // Coloca a raiz na fila
            fila.enqueue(root);
            
            // Laco "enquanto a fila nao estiver vazia
            while (!fila.isEmpty()) {
                // Retira o nodo da fila
                Node aux = fila.dequeue();
                // Coloca o elemento do nodo na lista
                lista.add(aux.element);
                // Coloca os filhos na fila
                for (int i=0; i<aux.getSubtreesSize(); i++) {
                    fila.enqueue(aux.getSubtree(i));
                }
//                // "Versao" abaixo com foreach
//                for (Node no : aux.subtrees) {
//                    fila.enqueue(no);
//                }
            }
            
        }
        
        return lista;
    }    
    

    /**
     * Retorna uma lista com todos os elementos da árvore numa ordem de 
     * caminhamento pre-fixado.
     * @return lista com os elementos da arvore na ordem do caminhamento pre-fixado
     */    
    public LinkedList<Character> positionsPre() {  
        LinkedList<Character> lista = new LinkedList<>();
        positionsPreAux(root,lista);
        return lista;
    }  
    private void positionsPreAux(Node n, LinkedList<Character> lista) {
        if (n!=null) {
            // Visita a raiz
            lista.add(n.element);
            // Visita os filhos
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
            
            // Visita os filhos
            for(int i=0; i<n.getSubtreesSize(); i++) {
                positionsPosAux(n.getSubtree(i), lista);
            }            

            // Visita a raiz
            lista.add(n.element);
        } 
    }    
    

    /**
     * Retorna em que nivel em que elem esta armazenado. 
     * @param element a ser buscado
     * @return nivel no qual element esta, ou -1 se 
     * nao encontrou element.
     */
    public int level(Integer element) {
        // IMPLEMENTE ESTE METODO !!
        return 0;
       
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
        
        // Se element estiver na raiz
        if (element.equals(root.element)) {
            root = null;
            count = 0;
            return true;
        }
        
        Node aux = this.searchNodeRef(element, root);
        if (aux == null) // se nao encontrou element
            return false;
        
        Node pai = aux.father;
        pai.removeSubtree(aux);
        aux.father = null;
        count = count - countNodes(aux);
        return true;
    }

    // Conta o numero de nodos da subarvore cuja raiz eh passada por parametro
    private int countNodes(Node n) {
        if (n == null)
            return 0;
        
        int c = 1; // conta 1 deste nodo
        
        // soma a quantidade de filhos deste nodo
        for(int i=0; i<n.getSubtreesSize(); i++) {
            c = c + countNodes(n.getSubtree(i));
        }
        
        // retorna a quantidade de filhos desta subarvore
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
