public class Queue<T> {

    private int total;
    private Node first, last;

    public Queue() {
        first = null;
        last = null;
        total = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public Queue<T> enqueue(T ele)
    {
        Node current = last;
        last = new Node();
        last.setData(ele);

        if (total++ == 0) first = last;
        else current.setNext(last);

        return this;
    }

    public Node dequeue()
    {
        if (total == 0) throw new java.util.NoSuchElementException();
        Node node = first;
        first = first.getNext();
        if (--total == 0) last = null;
        return node;
    }

}
