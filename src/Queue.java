import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<T>  implements  Iterable<T>{

    private int total;
    private Node<T> first, last;

    public Queue() {
        first = null;
        last = null;
        total = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public Node peek() {
        return first;
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

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator<T>(first);
    }

    private class QueueIterator<T> implements Iterator<T> {
        private Node<T> current;

        public QueueIterator(Node <T> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = current.getData();
            current = current.getNext();
            return item;
        }

    }
}
