public class FormulaValidator {

    public boolean validar(String formula) {
        formula = formula.replaceAll("\\s+", "");

        if (!validarCaracteres(formula)) return false;
        if (!checarLetrasDuplicadas(formula)) return false;
        if (!validarOperadoresDuplicados(formula)) return false;
        if (!checarParenteses(formula)) return false;
        if (!checarPosicaoOperadores(formula)) return false;

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
            if (operador(formula.charAt(i)) && operador(formula.charAt(i + 1))) {
                return false;
            }
        }
        return true;
    }

    private static boolean operador(char c) {
        return c == '~' || c == '^' || c == 'v' || c == 'V' || c == '>' || c == '-';
    }

    private static boolean checarParenteses(String formula) {
        int balance = 0;
        for (char c : formula.toCharArray()) {
            if (c == '(') balance++;
            else if (c == ')') balance--;
            if (balance < 0) return false;
        }
        return balance == 0;
    }

    private static boolean checarPosicaoOperadores(String formula) {
        if (formula.isEmpty())
            return false;

        if (opBinario(formula.charAt(0)) || opBinario(formula.charAt(formula.length() - 1))) {
            return false;
        }

        for (int i = 0; i < formula.length() - 1; i++) {
            char atual = formula.charAt(i);
            char proximo = formula.charAt(i + 1);

            if (operador(atual) && operador(proximo)) {
                if (atual != '~')
                    return false;
            }

            if (opBinario(atual) && (proximo == ')' || (i > 0 && formula.charAt(i - 1) == '('))) {
                return false;
            }
        }

        return true;
    }

    private static boolean opBinario(char c) {
        return c == '^' || c == 'v' || c == 'V' || c == '>' || c == '-';
    }
}
