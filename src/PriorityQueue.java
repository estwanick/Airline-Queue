public class PriorityQueue<Key> {

    private Key[] queue;
    private int numItems = 0;

    public PriorityQueue(int initCapacity) {
        queue = (Key[]) new Object[initCapacity + 1];
        numItems = 0;
    }

    public boolean isEmpty() {
        return numItems == 0;
    }

    public int size() {
        return numItems;
    }

    private void resize(int capacity) {
        assert capacity > numItems;
        Key[] temp = (Key[]) new Object[capacity];
        for(int i = 1; i <= numItems; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
    }

    public void insert(Key key) {
        if(numItems == queue.length - 1) {
            resize(2 * queue.length);
        }
        queue[++numItems] = key;
        heapUp(numItems);
    }

    private void heapUp(int key) {
        while(key > 1 && lessThan(key/2, key)) {
            swap(key, key/2);
            key = key/2;
        }
    }

    private void heapDown(int key) {
        while(2*key <= numItems) {
            int x = 2 * key;
            if(x < numItems && lessThan(x, x+1)) {
                x++;
            }
            if(!lessThan(key, x)) {
                break;
            }
            swap(key, x);
            key = x;
        }
    }

    private boolean lessThan(int x, int y) {
        return ((Comparable<Key>) queue[x]).compareTo(queue[y]) < 0;
    }

    private void swap(int x, int y) {
        Key swap = queue[x];
        queue[x] = queue[y];
        queue[y] = swap;
    }

}
