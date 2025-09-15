import java.util.NoSuchElementException;

public class Queue {
    Node<Character> first;
    Node<Character> last;

    public void add(Node<Character> node) {
        if (this.first == null) {
            this.first = node;
            this.last = node;
            return;
        } else if (this.first == this.last) {
            this.last = node;
            this.first.next = node;
            return;
        }

        this.last.next = node;
        this.last = node;
    }

    public Character pop() {
        if (this.first == null) {
            throw new NoSuchElementException("The queue is empty.");
        }
        
        Character auxValue = first.value;

        if (this.first.next == this.last) {
            this.first = this.last;
            return auxValue;
        }

        this.first = this.first.next;
        return auxValue;
    }

    public void show() {
        if (this.first == null) {
            return;
        }
        Node<Character> currentNode = this.first;
        System.out.print(currentNode.value + " ");
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            System.out.print(currentNode.value + " ");
        }
    }
}
