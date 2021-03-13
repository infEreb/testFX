package Engine;


import java.util.*;

public class Graph<T> {
    private HashMap<Node<T>, ArrayList<Node<T>>> graph;

    Comparator<Node<T>> comparator = new Comparator<Node<T>>() {
        public int compare(Node<T> n1, Node<T> n2)
        {
            return n1.getValue().toString().compareTo(n2.getValue().toString());
        }
    };

    public Graph() {
        graph = new HashMap<>();
    }

    public HashMap<Node<T>, ArrayList<Node<T>>> getGraph() {
        return graph;
    }
    private ArrayList<Node<T>> getSortedKeys() {
        ArrayList<Node<T>> sortedKeys = new ArrayList<>(graph.keySet());
        Collections.sort(sortedKeys, comparator);
        return sortedKeys;
    }

    public void addNode(T nodeValue) {
        graph.putIfAbsent(new Node<>(nodeValue), new ArrayList<>());
    }
    public void removeNode(T nodeValue) {
        Node<T> n = new Node<>(nodeValue);
        graph.values().forEach(e -> e.remove(n));
        graph.remove(new Node<>(nodeValue));
    }

    public void addEdge(T nodeValue1, T nodeValue2) {
        Node<T> n1 = new Node<>(nodeValue1);
        Node<T> n2 = new Node<>(nodeValue2);
        if (!hasNode(n1)) addNode(nodeValue1);
        if (!hasNode(n2)) addNode(nodeValue2);
        graph.get(n1).add(n2);
        graph.get(n2).add(n1);
        Collections.sort(graph.get(n1), comparator);
        Collections.sort(graph.get(n2), comparator);
    }
    public void removeEdge(T nodeValue1, T nodeValue2) {
        Node<T> n1 = new Node<>(nodeValue1);
        Node<T> n2 = new Node<>(nodeValue2);
        List<Node<T>> eN1 = graph.get(n1);
        List<Node<T>> eN2 = graph.get(n2);
        if (eN1 != null)
            eN1.remove(n2);
        if (eN2 != null)
            eN2.remove(n1);
    }

    public boolean hasNode(Node<T> node) {
        return graph.containsKey(node);
    }
    public boolean hasEdge(Node<T> node1, Node<T> node2) {
        if (!hasNode(node1)) return false;
        List<Node<T>> edges = graph.get(node1);
        return Collections.binarySearch(edges, node2, comparator) != -1;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        getSortedKeys().forEach(k -> {
            sb.append(k.getValue().toString() + " --> ");
            graph.get(k).forEach(lv -> {
                sb.append(lv.getValue().toString() + ", ");
            });
            sb.append("\n");
        });
        return sb.toString();
    }

}
