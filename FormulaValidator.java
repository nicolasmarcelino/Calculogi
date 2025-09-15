public class FormulaValidator {

    public boolean validar(String formula) {
        formula = formula.replaceAll("\\s+", "");

        if (!validarCaracteres(formula)) return false;
        if (!checarLetrasDuplicadas(formula)) return false;
        if (!validarOperadoresDuplicados(formula)) return false;
        if (!checarParenteses(formula)) return false;

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
            if (isLetter(formula.charAt(i)) && isLetter(formula.charAt(i + 1))) {
               return false;
            }
        }
        return true;
    }

    private static boolean isLetter(char c) {
        return c >= 'A' && c <= 'F';
    }

    private static boolean validarOperadoresDuplicados(String formula) {
        for (int i = 0; i < formula.length() - 1; i++) {
            if (isOperator(formula.charAt(i)) && isOperator(formula.charAt(i + 1))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isOperator(char c) {
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
}
