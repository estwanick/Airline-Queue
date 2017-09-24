import java.util.Arrays;

/*
    Min heap implementation
 */

public class PriorityQueue<T> {

    private T[] heap;
    private int size = 0;

    public PriorityQueue(int capacity) {
        heap = (T[]) new Object[capacity + 1];
        size = 0;
    }

    public boolean isEmpty() { return size == 0; }
    public int getSize() { return size; }

    private void upHeap() {
        int index = size - 1;
        while(hasParent(index) && greaterThan(parent(index), heap[index])) {
            swap(getParentIndex(index), index);
            index = getParentIndex(index);
        }
    }

    private void downHeap() {
        int index = 0;
        while(hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            if(hasRightChild(index) && lessThan(rightChild(index), leftChild(index))) {
                smallerChildIndex = getRightChildIndex(index);
            }

            if(lessThan(heap[index], heap[smallerChildIndex])) {
                break;
            } else {
                swap(index, smallerChildIndex);
            }
            index = smallerChildIndex;
        }
    }

    public void printHeap() {
        for(T p: heap) {
            System.out.println(fetchMin());
        }
    }

    public void add(T item) {
        ensureCapacity();
        heap[size++] = item;
        upHeap();
    }

    public T fetchMin() {
        if(size == 0) return null;
        T minItem = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        downHeap();
        return minItem;
    }

    public void emptyHeap() {
        while(size > 0) {
            fetchMin();
        }
    }

    public T peek() {
        if(size == 0) return null;
        return heap[0];
    }

    private boolean greaterThan(T x, T y) {
        return ((Comparable<T>) x).compareTo(y) > 0;
    }

    private boolean lessThan(T x, T y) {
        return ((Comparable<T>) x).compareTo(y) < 0;
    }

    private void swap(int x, int y) {
        T swap = heap[x];
        heap[x] = heap[y];
        heap[y] = swap;
    }

    private void ensureCapacity() {
        if(size == heap.length - 1) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
    }

    private int getLeftChildIndex(int parentIndex) { return 2 * parentIndex + 1; }
    private int getRightChildIndex(int parentIndex) { return 2 * parentIndex + 2; }
    private int getParentIndex(int childIndex) { return (childIndex - 1) / 2; }

    private boolean hasLeftChild(int index) { return getLeftChildIndex(index) < size; }
    private boolean hasRightChild(int index) { return getRightChildIndex(index) < size; }
    private boolean hasParent(int index) { return getParentIndex(index) >= 0; }

    private T leftChild(int index) { return heap[getLeftChildIndex(index)]; }
    private T rightChild(int index) { return heap[getRightChildIndex(index)]; }
    private T parent(int index) { return heap[getParentIndex(index)]; }

}
