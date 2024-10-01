public class LinkedListİmplementasyon {
    public static void main(String[] args) {
        
    }
}

interface List <E> {
    int size(); // Returns the size of the list
    boolean isEmpty(); // Checks whether the list is empty or not
    E first(); // returns the element/data of first entry in the list
    E last(); // returns te element/data of last entry in the list
    void addFirst(E e); // adds the given element at the start of the list
    void addLast(E e); // adds the given element at the end of the list
    E removeFirst(); // removes the first element in the list
}

interface Circlular {
    void rotate(); // tail becomes next of tail if not empty
}

interface Doubly <E> extends List <E> {
    E removeLast(); //removes the last element from the list
}

interface INode <E> { // storage unit
    E getElement(); // data/element
    Node<E> getPrev(); // returns the previous unit of this unit
    void setPrev(Node<E> prev); // sets the previous of this unit
    Node<E> getNext(); // returns the next unit of this unit
    void setNext(Node<E> next); // sets the next of this unit
}

/*
* According to given above given interfaces, implement:
*       1. A concrete Node class that implements INode generic interface
*       2. A concrete SinglyLinkedList class that implements List generic interface
*       3. A concrete CircularLinkedlist class that implements List generic interface and Circular interface
*       4. A concrete DoublyLinkedList class that implements Doubly generic interface
* For each of the concrete classes, use Node class as the storage unit
* !! SinglyLinkedList class must have head and tail data fields
* !! CircularLİnkedList class must have tail data field
* !! Every class except Node must only have a single constructor without any parameters
* You can implement any additional method you would like
*/


class Node<E> implements INode<E> {
    private E element;
    private Node<E> prev;
    private Node<E> next;

    public Node(E element) {
        this.element = element;
        this.prev = null;
        this.next = null;
    }

    
    public E getElement() {
        return element;
    }

    
    public Node<E> getPrev() {
        return prev;
    }


    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

   
    public Node<E> getNext() {
        return next;
    }

   
    public void setNext(Node<E> next) {
        this.next = next;
    }
}


class SinglyLinkedList<E> implements List<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    
    public int size() {
        return size;
    }

    
    public boolean isEmpty() {
        return size == 0;
    }

    
    public E first() {
        return isEmpty() ? null : head.getElement();
    }

    
    public E last() {
        return isEmpty() ? null : tail.getElement();
    }

    
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        if (isEmpty()) {
            tail = newNode;
        } else {
            newNode.setNext(head);
        }
        head = newNode;
        size++;
    }

    
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }

    
    public E removeFirst() {
        if (isEmpty()) return null;
        E element = head.getElement();
        head = head.getNext();
        size--;
        if (isEmpty()) tail = null;
        return element;
    }
}



class CircularLinkedList<E> implements List<E>, Circlular {
    private Node<E> tail;
    private int size;

    public CircularLinkedList() {
        this.tail = null;
        this.size = 0;
    }

   
    public int size() {
        return size;
    }

    
    public boolean isEmpty() {
        return size == 0;
    }

   
    public E first() {
        return isEmpty() ? null : tail.getNext().getElement();
    }

    
    public E last() {
        return isEmpty() ? null : tail.getElement();
    }

   
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        if (isEmpty()) {
            tail = newNode;
            tail.setNext(tail);
        } else {
            newNode.setNext(tail.getNext());
            tail.setNext(newNode);
        }
        size++;
    }

    
    public void addLast(E e) {
        addFirst(e);
        tail = tail.getNext();
    }

    
    public E removeFirst() {
        if (isEmpty()) return null;
        Node<E> head = tail.getNext();
        E element = head.getElement();
        if (head == tail) {
            tail = null;
        } else {
            tail.setNext(head.getNext());
        }
        size--;
        return element;
    }

    
    public void rotate() {
        if (!isEmpty()) {
            tail = tail.getNext();
        }
    }
}


class DoublyLinkedList<E> implements Doubly<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        return isEmpty() ? null : head.getElement();
    }

    public E last() {
        return isEmpty() ? null : tail.getElement();
    }

    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
        size++;
    }

    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    }

    public E removeLast() {
        if (isEmpty()) return null;
        E element = tail.getElement();
        if (head == tail) {
            head = tail = null; // List becomes empty
        } else {
            tail = tail.getPrev();
            tail.setNext(null);
        }
        size--;
        return element;
    }

    public E removeFirst() {
        if (isEmpty()) return null;
        E element = head.getElement();
        head = head.getNext();
        if (head != null) {
            head.setPrev(null);
        } else {
            tail = null; // List becomes empty
        }
        size--;
        return element;
    }
}