public class Calculogi {

     public static boolean validar(String expr) {
          // 1. Checagem de caracteres
          if (!expr.matches("[A-Z∧∨→¬()]*")) {
               return false;
          }

          // 2. Checagem de parênteses
          int balance = 0;
          for (char c : expr.toCharArray()) {
               if (c == '(')
                    balance++;
               if (c == ')') {
                    balance--;
                    if (balance < 0)
                         return false;
               }
          }
          if (balance != 0)
               return false;

          // 3. Checagem da forma
          String tokenPattern = "(¬?(?:[A-Z]|\\([^()]+\\)))";
          String fullPattern = tokenPattern + "([∧∨→]" + tokenPattern + ")*";

          return expr.matches(fullPattern);
     }

     public static void main(String[] args) {
          String e1 = "(A→B)∧(B→A)";       // Exemplo de uma FBF
          String e2 = "∧∧→BC";             // Exemplo de uma não FBF

          System.out.println(validar(e1)); // true, como esperado
          System.out.println(validar(e2)); // false, como esperado
     }
}