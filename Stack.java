import java.util.NoSuchElementException;


public class Stack<T> {
    public Node<T> top;

    public void add(Node<T> node) {
        node.next = this.top;
        this.top = node;
    }

    public T pop() {
        if (top == null) {
            throw new NoSuchElementException("The stack is empty.");
        }
        T auxValue = top.value;
        this.top = this.top.next;
        return auxValue;
    }

    public void show() {
        if (this.top == null) {
            return;
        }
        Node<T> currentNode = this.top;
        System.out.print(currentNode.value + " ");
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            System.out.print(currentNode.value + " ");
        }
    }
}
