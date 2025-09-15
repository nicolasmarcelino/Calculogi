public class FormulaValidator {

    public boolean validar(String formula) {
        formula = formula.replaceAll("\\s+", "");

        if (!validarCaracteres(formula))
            return false;
        if (!checarLetrasDuplicadas(formula))
            return false;
        if (!validarOperadoresDuplicados(formula))
            return false;
        if (!checarParenteses(formula))
            return false;

        return true;
    }

    private static boolean validarCaracteres(String formula) {
        String validChars = "ABCDEF~^vV>-()";
        for (char c : formula.toCharArray()) {
            if (validChars.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

    private static boolean checarLetrasDuplicadas(String formula) {
        for (int i = 0; i < formula.length() - 1; i++) {
            if (letra(formula.charAt(i)) && letra(formula.charAt(i + 1))) {
                return false;
            }
        }
        return true;
    }

    private static boolean letra(char c) {
        return c >= 'A' && c <= 'F';
    }

    private static boolean validarOperadoresDuplicados(String formula) {
        for (int i = 0; i < formula.length() - 1; i++) {
            char atual = formula.charAt(i);
            char seguinte = formula.charAt(i + 1);

            if (opBin(formula.charAt(0)) || opBin(formula.charAt(formula.length() - 1))) {
                return false;
            }

            // Se ambos são operadores binários (^, v, V, >, -)
            if (opBin(atual) && opBin(seguinte)) {
                return false;
            }

            // Se é negação seguida de operador binário (exceto outra negação)
            if (atual == '~' && opBin(seguinte) && seguinte != '~') {
                return false;
            }

            // Se é operador binário seguido de fechamento de parêntese
            if (opBin(atual) && seguinte == ')') {
                return false;
            }

            // Se é abertura de parêntese seguido de operador binário
            if (atual == '(' && opBin(seguinte)) {
                return false;
            }
        }
        return true;
    }

    private static boolean opBin(char c) {
        return c == '^' || c == 'v' || c == 'V' || c == '>' || c == '-';
    }

    private static boolean checarParenteses(String formula) {
        int balance = 0;
        for (char c : formula.toCharArray()) {
            if (c == '(')
                balance++;
            else if (c == ')')
                balance--;
            if (balance < 0)
                return false;
        }
        return balance == 0;
    }
}
