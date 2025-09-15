public class RpnParser {
    public Queue toReversePolishNotation (String formula, Boolean demonstrate) {
        Stack<Character> rpnStack = new Stack<>();
        Queue rpnQueue = new Queue();

        for (char c : formula.toCharArray()) {
            if (c == ' ') {
                continue;
            }

            if (demonstrate) {
                System.out.print(c + ": ");
                rpnStack.show();
                System.out.print(" | ");
                rpnQueue.show();
                System.out.println("");
            }            

            Node<Character> newNode = new Node<>(c);

            if (c == 'A' | c == 'B' | c == 'C' | c == 'D' | c == 'E' | c == 'F') {
                rpnQueue.add(newNode);
                continue;
            }

            if (c == '(' | c == '~' | rpnStack.top == null) {
                rpnStack.add(newNode);
                continue;
            }

            if (c == '>' | c == '-' | c == 'V') {
                if (rpnStack.top.value == '^' | rpnStack.top.value == 'v' | rpnStack.top.value == '>' | rpnStack.top.value == '-' | rpnStack.top.value == 'V') {
                    Node<Character> toQueue = new Node<>(rpnStack.pop());
                    rpnQueue.add(toQueue);
                }
                rpnStack.add(newNode);
                continue;
            }

            if (c == '^' | c == 'v') {
                if (rpnStack.top.value == '^' | rpnStack.top.value == 'v' | rpnStack.top.value == '~') {
                    Node<Character> toQueue = new Node<>(rpnStack.pop());
                    rpnQueue.add(toQueue);
                    rpnStack.add(newNode);
                } else {
                    rpnStack.add(newNode);
                }
                continue;
            }

            if (c == ')') {
                Character currentChar = rpnStack.pop();
                while (currentChar != '(') {
                    Node<Character> toQueue = new Node<>(currentChar);
                    rpnQueue.add(toQueue);
                    currentChar = rpnStack.pop();
                }
            }
        }

        while (rpnStack.top != null) {
            Node<Character> toQueue = new Node<>(rpnStack.pop());
            rpnQueue.add(toQueue);
        }


        if (demonstrate) {
            rpnQueue.show();
        }

        return rpnQueue;
    }

    public Queue toReversePolishNotation (String formula) {
        Boolean demonstrate = false;
        return toReversePolishNotation(formula, demonstrate);
    }
}
