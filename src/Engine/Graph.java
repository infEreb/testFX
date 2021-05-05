package Engine;


import javafx.geometry.Point2D;

import java.util.*;

public class Graph<T> {
    private HashMap<Node<T>, ArrayList<Node<T>>> graph;

    Comparator<Node<T>> toStringComparator = new Comparator<Node<T>>() {
        public int compare(Node<T> n1, Node<T> n2)
        {
            return n1.getValue().toString().compareTo(n2.getValue().toString());
        }
    };
    Comparator<Node<T>> priorityComparator = new Comparator<Node<T>>() {
        public int compare(Node<T> n1, Node<T> n2)
        {
            return n1.getPriority() > n2.getPriority() ? 1 : -1;
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
        Collections.sort(sortedKeys, toStringComparator);
        return sortedKeys;
    }

    public Node<T> getNode(T value) {
        Node<T> node = new Node<>(value);
        if(graph.containsKey(node))
            return node;
        return null;
    }

    //MUTABLE METHODS
    public void addNode(T nodeValue) {
        graph.putIfAbsent(new Node<>(nodeValue), new ArrayList<>());
    }
    public void addNode(T nodeValue, int nodePriority) {
        graph.putIfAbsent(new Node<>(nodeValue, nodePriority), new ArrayList<>());
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
        Collections.sort(graph.get(n1), toStringComparator);
        Collections.sort(graph.get(n2), toStringComparator);
    }
    public void addEdge(T nodeValue1, int nodePriority1, T nodeValue2, int nodePriority2) {
        Node<T> n1 = new Node<>(nodeValue1, nodePriority1);
        Node<T> n2 = new Node<>(nodeValue2, nodePriority2);
        if (!hasNode(n1)) addNode(nodeValue1);
        if (!hasNode(n2)) addNode(nodeValue2);
        graph.get(n1).add(n2);
        graph.get(n2).add(n1);
        Collections.sort(graph.get(n1), toStringComparator);
        Collections.sort(graph.get(n2), toStringComparator);
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
        return Collections.binarySearch(edges, node2, toStringComparator) != -1;
    }

    public int cost(Node<T> node1, Node<T> node2) {
        return Math.abs(node1.getPriority() - node2.getPriority());
    }
    private int heuristic(Point2D point1, Point2D point2) {
        return Math.abs((int)point1.getX() - (int)point2.getX()) + Math.abs((int)point1.getY() - (int)point2.getY());
    }

    public ArrayList<T> nodeListToValueList(ArrayList<Node<T>> nodes) {
        ArrayList<T> values = new ArrayList<>();
        nodes.forEach(n -> {
            values.add(n.getValue());
        });
        return values;
    }
    public boolean setNodesPriority(T startNodeValue) { // false if has error
        ArrayDeque<Node<T>> frontier = new ArrayDeque<>();

        Node<T> startNode = getNode(startNodeValue);
        if(startNode == null)
            return false;

        int priority = 0;

        frontier.add(startNode);
        ArrayList<Node<T>> hasPriority = new ArrayList<>();
        startNode.setPriority(priority);
        priority++;
        hasPriority.add(startNode);


        while(!frontier.isEmpty()) {
            Node<T> current = frontier.poll();

            for (Node<T> next: graph.get(current)) {
                if(!hasPriority.contains(next)) {
                    frontier.add(next);
                    next.setPriority(priority);
                    hasPriority.add(next);
                }
            }
            priority++;
        }
        return true;
    }
    public boolean setNodesAntiPriority(T startNodeValue) { // false if has error
        ArrayDeque<Node<T>> frontier = new ArrayDeque<>();

        Node<T> startNode = getNode(startNodeValue);
        if(startNode == null)
            return false;

        int priority = 0;

        frontier.add(startNode);
        ArrayList<Node<T>> hasPriority = new ArrayList<>();
        startNode.setPriority(priority);
        priority--;
        hasPriority.add(startNode);


        while(!frontier.isEmpty()) {
            Node<T> current = frontier.poll();

            for (Node<T> next: graph.get(current)) {
                if(!hasPriority.contains(next)) {
                    frontier.add(next);
                    next.setPriority(priority);
                    hasPriority.add(next);
                }
            }
            priority--;
        }
        return true;
    }

    //ALGORITHMS
    public ArrayList<Node<T>> breadthFirstSearching(Node<T> start, Node<T> destination) { //return path for destination
        ArrayDeque<Node<T>> frontier = new ArrayDeque<>();
        frontier.add(start);
        HashMap<Node<T>, Node<T>> came_from = new HashMap<>();
        came_from.put(start, null);


        while(!frontier.isEmpty()) {
            Node<T> current = frontier.poll();

            if(current == destination) { //early exit
                break;
            }

            graph.get(current).forEach(next -> {
                if(!came_from.containsKey(next)) {
                    frontier.add(next);
                    came_from.put(next, current);
                }
            });
        }

        Node<T> current = destination;
        ArrayList<Node<T>> reverse_path = new ArrayList<>();

        while (current != start) {
            if(current == null)
                break;
            //System.out.println("Current: " + current.toString() + "CameFrom " + came_from.get(current).toString());
            reverse_path.add(current);
            current = came_from.get(current);
        }

        ArrayList<Node<T>> path = new ArrayList<>();
        for(int i = reverse_path.size()-1; i >= 0; i--) {
            path.add(reverse_path.get(i));
        }
        System.out.println(pathToString(path));
        return path;
    }
    public ArrayList<Node<T>> longBreadthFirstSearching(Node<T> start, Node<T> destination) { //return path for destination
        ArrayDeque<Node<T>> frontier = new ArrayDeque<>();
        frontier.add(start);
        HashMap<Node<T>, Node<T>> came_from = new HashMap<>();
        came_from.put(start, null);


        while(!frontier.isEmpty()) {
            Node<T> current = frontier.poll();

            if(current == destination) { //early exit
                break;
            }

            graph.get(current).forEach(next -> {
                if(!came_from.containsKey(next)) {
                    frontier.add(next);
                    came_from.put(next, current);
                }
            });
        }

        Node<T> current = destination;
        ArrayList<Node<T>> reverse_path = new ArrayList<>();

        while (current != start) {
            if(current == null)
                break;
            //System.out.println("Current: " + current.toString() + "CameFrom " + came_from.get(current).toString());
            reverse_path.add(current);
            current = came_from.get(current);
        }

        ArrayList<Node<T>> path = new ArrayList<>();
        for(int i = reverse_path.size()-1; i >= 0; i--) {
            path.add(reverse_path.get(i));
        }
        //System.out.println(pathToString(path));
        return path;
    }
    public ArrayList<Node<T>> DijkstraSearching(Node<T> start, Node<T> destination) {
        if(!setNodesPriority(start.getValue())) // optional in this func
            return null;

        PriorityQueue<Node<T>> frontier = new PriorityQueue<>(priorityComparator);
        //start.setPriority(0);
        frontier.add(start);
        HashMap<Node<T>, Node<T>> came_from = new HashMap<>();
        HashMap<Node<T>, Integer> cost_so_far = new HashMap<>();
        came_from.put(start, null);
        cost_so_far.put(start, 0);

        while(!frontier.isEmpty()) {
            Node<T> current = frontier.poll();

            if(current == destination) {
                break;
            }

            graph.get(current).forEach(next -> {
                int new_cost = cost_so_far.get(current) + cost(current, next);
                if(!cost_so_far.containsKey(next) || new_cost < cost_so_far.get(next)) {
                    cost_so_far.put(next, new_cost);
                    next.setPriority(new_cost);
                    frontier.add(next);
                    came_from.put(next, current);
                }
            });
        }

        Node<T> current = destination;
        ArrayList<Node<T>> reverse_path = new ArrayList<>();

        while (current != start) {
            reverse_path.add(current);
            current = came_from.get(current);
        }

        ArrayList<Node<T>> path = new ArrayList<>();
        for(int i = reverse_path.size()-1; i >= 0; i--) {
            path.add(reverse_path.get(i));
        }
        return path;
    }
    public ArrayList<Node<T>> longDijkstraSearching(Node<T> start, Node<T> destination) {
        if(!setNodesPriority(start.getValue())) // optional in this func
            return null;

        PriorityQueue<Node<T>> frontier = new PriorityQueue<>(priorityComparator);
        //start.setPriority(0);
        frontier.add(start);
        HashMap<Node<T>, Node<T>> came_from = new HashMap<>();
        HashMap<Node<T>, Integer> cost_so_far = new HashMap<>();
        came_from.put(start, null);
        cost_so_far.put(start, 0);

        while(!frontier.isEmpty()) {
            Node<T> current = frontier.poll();

//            if(current == destination) {
//                break;
//            }

            graph.get(current).forEach(next -> {
                int new_cost = cost_so_far.get(current) + cost(current, next);
                System.out.println("New cost: " + new_cost);
                if(!cost_so_far.containsKey(next) || new_cost < cost_so_far.get(next)) {
                    cost_so_far.put(next, new_cost);
                    next.setPriority(new_cost);
                    System.out.println("Next priority: " + next.getPriority());
                    frontier.add(next);
                    came_from.put(next, current);
                }
            });
        }

        Node<T> current = destination;
        System.out.println(current.toString());
        ArrayList<Node<T>> reverse_path = new ArrayList<>();

        while (current != start) {
            reverse_path.add(current);
            current = came_from.get(current);
        }

        ArrayList<Node<T>> path = new ArrayList<>();
        for(int i = reverse_path.size()-1; i >= 0; i--) {
            path.add(reverse_path.get(i));
        }
        return path;
    }
    public ArrayList<Node<T>> heuristicSearching(Node<T> start, Node<T> destination) { //for the points
        PriorityQueue<Node<T>> frontier = new PriorityQueue<>(priorityComparator);
        frontier.add(start);
        HashMap<Node<T>, Node<T>> came_from = new HashMap<>();
        came_from.put(start, null);

        while(!frontier.isEmpty()) {
            Node<T> current = frontier.poll();

            if(current == destination) {
                break;
            }

            graph.get(current).forEach(next -> {
                if(!came_from.containsKey(next)) {
                    int priority = heuristic((Point2D)destination.getValue(), (Point2D)next.getValue());
                    next.setPriority(priority);
                    frontier.add(next);
                    came_from.put(next, current);
                }
            });
        }

        Node<T> current = destination;
        ArrayList<Node<T>> reverse_path = new ArrayList<>();

        while (current != start) {
            reverse_path.add(current);
            current = came_from.get(current);
        }

        ArrayList<Node<T>> path = new ArrayList<>();
        for(int i = reverse_path.size()-1; i >= 0; i--) {
            path.add(reverse_path.get(i));
        }
        return path;
    }
    public ArrayList<Node<T>> longHeuristicSearching(Node<T> start, Node<T> destination) { //for the points
        PriorityQueue<Node<T>> frontier = new PriorityQueue<>(priorityComparator);
        frontier.add(start);
        HashMap<Node<T>, Node<T>> came_from = new HashMap<>();
        came_from.put(start, null);

        while(!frontier.isEmpty()) {
            Node<T> current = frontier.poll();

            if(current == destination) {
                break;
            }

            graph.get(current).forEach(next -> {
                if(!came_from.containsKey(next)) {
                    int priority = heuristic((Point2D)destination.getValue(), (Point2D)next.getValue()) * -1;
                    next.setPriority(priority);
                    frontier.add(next);
                    came_from.put(next, current);
                }
            });
        }

        Node<T> current = destination;
        ArrayList<Node<T>> reverse_path = new ArrayList<>();

        while (current != start) {
            System.out.println("Current: " + current.toString());
            System.out.println("Start: " + start.toString());
            reverse_path.add(current);
            current = came_from.get(current);
        }

        ArrayList<Node<T>> path = new ArrayList<>();
        for(int i = reverse_path.size()-1; i >= 0; i--) {
            path.add(reverse_path.get(i));
        }
        return path;
    }

    public String pathToString(ArrayList<Node<T>> path) {
        StringBuffer sb = new StringBuffer();
        path.forEach(e -> {
            sb.append("(" + e.getValue().toString() + ") - ");
        });
        return sb.toString();
    }

    @Override
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
