package Engine;

public class Node<T> {
    private int priority;
    private T value;



    public Node(T value) {
        this.value = value;
        this.priority = 0;
    }
    public Node(T value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public T getValue() {
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }


    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Node<T> other = (Node<T>) obj;
        if (value == null) {
            if (other.getValue() != null)
                return false;
        } else if (!value.equals(other.getValue()))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "Node{" +
                "priority=" + priority +
                ", value=" + value.toString() +
                '}';
    }
}
