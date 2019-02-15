import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-f")) {
                if (args.length > 1) {
                    File file = new File(args[1]);
                    Parser parser = new Parser(file);
                    int result = parser.calculate();
                    System.out.println("Result of the expression is " + result);
                } else {
                    printHelp();
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (String arg : args) {
                    stringBuilder.append(arg);
                }
                Parser parser = new Parser(stringBuilder.toString());
                int result = parser.calculate();
                System.out.println("Result of the expression is " + result);
            }
        } else {
            parseCommandLine();
        }
    }

    private static void parseCommandLine() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write your expression:");
        String expression = scanner.nextLine();
        Parser parser = new Parser(expression);
        int result = parser.calculate();
        System.out.println("Result of the expression is " + result);
        scanner.close();
    }

    private static void printHelp() {
        System.out.println("Usage: java -jar Calculator.jar [-f fileName] [expressionString]");
    }
}
