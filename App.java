import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        //String formula = "~~B"; // B ~ ~, precisa ser ~(~B)
        // String formula = "~(~A)"; // A ~ ~
        // String formula = "A ^ B"; // A B ^
        // String formula = "A v B"; // A B v
        // String formula = "A > B"; // A B >
        //String formula = "A - B"; // A B -
        // String formula = "A V B"; // A B V
        // String formula = "A ^ B ^ C ^ D"; // A B ^ C ^ D ^
        // String formula = "A ^ (B ^ C) ^ D"; // A B C ^ ^ D ^
        // String formula = "A ^ (B v C) ^ D"; // A B C v ^ D ^
        // String formula = "A ^ (B v C) V D"; // A B C v ^ D V 0 0 0 1 0 1 0 1 1 1 1 0 1 0 1 0 Contingência
        // String formula = "(A v ~A) v (B v ~B)"; // A B C v ^ D V 1 1 1 1 Tautologia
        // String formula = "(A v (~A v B)) ^ ~(B ^ ~C)"; // A A ~ B v v B C ~ ^ ~ ^ 1 1 0 0 1 1 1 1 Contingência.
        // String formula = "(D > (A ^ ~C)) ^ ((A > (C v B)) ^ D)"; // D A C ~ ^ > A C B v > D ^ ^ 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0  Contingência.
        // String formula = "(A ^ B ^ ~C ^ D) v ~(A v D)"; // A B ^ C ~ D ^ ^ A D v ~ v 1 0 1 0 1 0 1 0 0 0 0 1 0 0 0 0 Contingência.
        // String formula = "A ^ ~A"; // A A ~ ^ 0 0 Contradicão.

        FormulaValidator validator = new FormulaValidator();

        String formula = "A ^ ~A";

        if (validator.validar(formula)) {
            System.out.println("Fórmula bem formada.");
        } else {
            System.out.println("Fórmula malformada. Não é possível prosseguir.");
            System.exit(0);
        }
        
        RpnParser parser = new RpnParser();

        // Converte a formula para Reverse Polish Notation (RPN). 
        // Validacão deve vir acima.
        Queue rpnQueue = parser.toReversePolishNotation(formula, true);
        
        System.out.println();

        // Instancia os objetos necessários pro cálculo.
        // Pilha de Booleanos que armazena os cálculos a medida que vão sendo resolvidos
        Stack<Boolean> valueStack = new Stack<>();

        // ArrayList que armazena os Characters gerados pela conversão para RPN
        // Necessário para que seja possível ler os tokens mais de uma vez
        ArrayList<Character> tokenArray = new ArrayList<>();

        // Número de proposicões. Usado para definir o tamanho da tabela-verdade.
        int n = 0;

        // Vetor de Characteres usado para definir se uma proposicão 
        // já foi vista antes.
        Character[] uniquePropositions = new Character[6];
        
        // Lê a fila resultado da conversão para Rpn, avalia quantas 
        // proposicões existem e armazena os tokens no ArrayList.
        while (rpnQueue.first != null) {
            Character token = rpnQueue.pop();
            
            // Se token é preposicão avalia se já foi visto. Se não, 
            // adiciona em uniquePropositions.
            if (token == 'A' | token == 'B' | token == 'C' | token == 'D' | token == 'E' | token == 'F') {
                if (n == 0) {
                    uniquePropositions[n] = token;
                    n++;
                }

                for (int i = 0; i < n; i++) {
                    if (token.equals(uniquePropositions[i])) {
                        break;
                    }

                    if (i == n - 1) {
                        uniquePropositions[n] = token;
                        n++;
                    }
                }
            }

            tokenArray.add(token);
        }

        System.out.println();
        
        // Printa o cabecalho da tabela-verdade usando as proposicões unicas
        // e a fórmula.
        for (Character proposition : uniquePropositions) {
            if (proposition != null) { System.out.print(proposition + " | "); }
        }

        System.out.println(formula);

        // Define os seis estados possíveis das 6 proposicões aceitas.
        Boolean[] state = {true, true, true, true, true, true};
        ArrayList<Boolean> result = new ArrayList<>();
        
        // Itera 2 ** n vezes para formar a tabela-verdade 
        // sendo n o número de proposicões únicas.
        for (int i = 0; i < Math.pow(2, n); i++) {
            // Para cada iteracão muda o estado das proposicões
            // para resolver a formula para todos as possibilidades.
            state = switchState(state, i, n);

            // Vetor que armazena se o estado de uma proposicão já foi printado
            // na tabela-verdade.
            Boolean[] alreadyShown = {false, false, false, false, false, false};

            // Printa o valor da proposicão na tabela-verdade na primeira
            // vez que ela surge.
            for (Character token : tokenArray) {
                if (token.equals(uniquePropositions[0])) {
                    valueStack.add(new Node<>(state[0]));
                    if (!alreadyShown[0]) { 
                        System.out.print((state[0] ? 1 : 0) + " | ");
                        alreadyShown[0] = true;
                    }
                } else if (token.equals(uniquePropositions[1])) {
                    valueStack.add(new Node<>(state[1]));
                    if (!alreadyShown[1]) { 
                        System.out.print((state[1] ? 1 : 0) + " | ");
                        alreadyShown[1] = true;
                    }
                } else if (token.equals(uniquePropositions[2])) {
                    valueStack.add(new Node<>(state[2]));
                    if (!alreadyShown[2]) { 
                        System.out.print((state[2] ? 1 : 0) + " | ");
                        alreadyShown[2] = true;
                    }
                } else if (token.equals(uniquePropositions[3])) {
                    valueStack.add(new Node<>(state[3]));
                    if (!alreadyShown[3]) { 
                        System.out.print((state[3] ? 1 : 0) + " | ");
                        alreadyShown[3] = true;
                    }
                } else if (token.equals(uniquePropositions[4])) {
                    valueStack.add(new Node<>(state[4]));
                    if (!alreadyShown[4]) { 
                        System.out.print((state[4] ? 1 : 0) + " | ");
                        alreadyShown[4] = true;
                    }
                } else if (token.equals(uniquePropositions[5])) {
                    valueStack.add(new Node<>(state[5]));
                    if (!alreadyShown[5]) { 
                        System.out.print((state[5] ? 1 : 0) + " | ");
                        alreadyShown[5] = true;
                    }
                }
                
                // Se token for uma operacão, realiza a solucão dela de acordo.
                if (token == '~') {
                    Boolean left = valueStack.pop();
                    valueStack.add(new Node<>(!left));
                }

                if (token == '^') {
                    Boolean left = valueStack.pop();
                    Boolean right = valueStack.pop();
                    valueStack.add(new Node<>(left & right));
                }

                if (token == 'v') {
                    Boolean left = valueStack.pop();
                    Boolean right = valueStack.pop();
                    valueStack.add(new Node<>(left | right));
                }

                if (token == '>') {
                    Boolean right = valueStack.pop();
                    Boolean left = valueStack.pop();
                    if (left & !right) {
                        valueStack.add(new Node<>(false));
                    } else {
                        valueStack.add(new Node<>(true));
                    }
                }

                if (token == '-') {
                    Boolean right = valueStack.pop();
                    Boolean left = valueStack.pop();
                    valueStack.add(new Node<>(!((left & !right) | (!left & right))));
                }

                if (token == 'V') {
                    Boolean right = valueStack.pop();
                    Boolean left = valueStack.pop();
                    valueStack.add(new Node<>((left & !right) | (!left & right)));
                }
            }

            Boolean auxResult = valueStack.pop();
            result.add(auxResult);
            System.out.println(auxResult ? 1 : 0);
        }

        // Avalia quantos true existem e define se tautologia,
        // contingência ou contradicão.
        int nTrue = 0;
        for (Boolean r : result) {
            if (r) {nTrue++;}
        }

        System.out.println();

        if (nTrue == 0) {
            System.out.println("Contradição.");
        } else if (nTrue == result.size()) {
            System.out.println("Tautologia.");
        } else {
            System.out.println("Contingência.");
        }        
    }

    private static Boolean[] switchState (Boolean[] state, int iteration, int neededStates) {
        state[0] = !state[0];

        if (neededStates > 0 & iteration % 2 == 0) {
            state[1] = !state[1];
        }

        if (neededStates > 0 & iteration % 4 == 0) {
            state[2] = !state[2];
        }

        if (neededStates > 0 & iteration % 8 == 0) {
            state[3] = !state[3];
        }

        if (neededStates > 0 & iteration % 16 == 0) {
            state[4] = !state[4];
        }

        if (neededStates > 0 & iteration % 32 == 0) {
            state[5] = !state[5];
        }
        
        return state;
    }
}