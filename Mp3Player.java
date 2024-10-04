import java.util.NoSuchElementException;

public class HW02_20220808074 {
    public static void main(String[] args) {

        MusicPlayer mp = new MusicPlayer("./Musics");
                
    }
}

interface INode <T> { // storage unit
    // Constructor (T data, Node<T> prev, Node<T> next)
    T getData(); // returns the data
    Node<T> getNext(); // returns the next of this storage unit
    Node<T> getPrev(); // returns the previous storage unit of this unit
    void setNext(Node<T> next); // sets next pointer of this node
    void setPrev(Node<T> prev); // sets the prev pointer of this node
    String toString(); // string representation
}

interface IDoublyCircularLinkedList <T> {
    // must have the data field current
    // Constructor ()
    void addFirst(T data); // adds an element to the head of the list. If first element in list, must also be last element
    // if only element in the list its next and prev should point to itself
    void addLast(T data); // adds an element to the tail of the list. If first element in list, must also be last element
    // if only element in the list its next and prev should point to itself
    T removeFirst() throws NoSuchElementException; // removes the first element in the list,
    // throw exception if list is empty, if only element remaining it should be first and last and its next and prev,
    // should be itself
    T removeLast() throws NoSuchElementException; // removes the last element in the list,
    // throw exception if list is empty, if only element remaining it should be first and last and its next and prev,
    // should be itself
    T get(int index) throws IndexOutOfBoundsException; // gets the ith element in the list,
    // should throw exception if out of bounds
    T first() throws NoSuchElementException; // should set current, returns the first data
    T last() throws NoSuchElementException; // should set current, returns the last data
    boolean remove(T data); // should return false if data doesnt exists, returns true and removes if exists
    boolean isEmpty();
    int size();
    T next() throws NoSuchElementException; // if empty, throws exception, should change current correctly
    // if current is null should return head and set it to head
    T previous() throws NoSuchElementException; // if empty, throws exception, should change current correctly
    // if current is null should return tail data and set it
    T getCurrent() throws NoSuchElementException; // Retruns the current pointer, if no element exits throws exception
    // if current is null returns heads data
    Node<T> getHead(); // returns the head of the list, if is empty returns null
    // any other method needed

}

class Node<T> implements INode<T> {
    private T data;
    private Node<T> prev;
    private Node<T> next;

    public Node(T data, Node<T> prev, Node<T> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public String toString() {
        return data.toString();
    }
}

class DoublyCircularLinkedList<T> implements IDoublyCircularLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private Node<T> current;
    private int size;

    public DoublyCircularLinkedList() {
        this.head = null;
        this.tail = null;
        this.current = null;
        this.size = 0;
    }

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data, null, null);
        if (isEmpty()) {
            head = tail = newNode;
            head.setNext(head);
            head.setPrev(head);
        } else {
            newNode.setNext(head);
            newNode.setPrev(tail);
            head.setPrev(newNode);
            tail.setNext(newNode);
            head = newNode;
        }
        size++;
    }

    public void addLast(T data) {
        Node<T> newNode = new Node<>(data, null, null);
        if (isEmpty()) {
            head = tail = newNode;
            head.setNext(head);
            head.setPrev(head);
        } else {
            newNode.setNext(head);
            newNode.setPrev(tail);
            tail.setNext(newNode);
            head.setPrev(newNode);
            tail = newNode;
        }
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        T data = head.getData();
        if (size == 1) {
            head = tail = null;
        } else {
            tail.setNext(head.getNext());
            head.getNext().setPrev(tail);
            head = head.getNext();
        }
        size--;
        return data;
    }

    public T removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        T data = tail.getData();
        if (size == 1) {
            head = tail = null;
        } else {
            head.setPrev(tail.getPrev());
            tail.getPrev().setNext(head);
            tail = tail.getPrev();
        }
        size--;
        return data;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }
        return currentNode.getData();
    }

    public T first() {
        if (isEmpty()) throw new NoSuchElementException();
        current = head;
        return current.getData();
    }

    public T last() {
        if (isEmpty()) throw new NoSuchElementException();
        current = tail;
        return current.getData();
    }

    public boolean remove(T data) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.getData().equals(data)) {
                if (currentNode == head) {
                    removeFirst();
                } else if (currentNode == tail) {
                    removeLast();
                } else {
                    currentNode.getPrev().setNext(currentNode.getNext());
                    currentNode.getNext().setPrev(currentNode.getPrev());
                    size--;
                }
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T next() {
        if (isEmpty()) throw new NoSuchElementException();
        if (current == null) {
            current = head;
        } else {
            current = current.getNext();
        }
        return current.getData();
    }

    public T previous() {
        if (isEmpty()) throw new NoSuchElementException();
        if (current == null) {
            current = tail;
        } else {
            current = current.getPrev();
        }
        return current.getData();
    }

    public T getCurrent() {
        if (current == null) return head.getData();
        return current.getData();
    }

    public Node<T> getHead() {
        return head;
    }
}