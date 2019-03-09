import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class UFSM {

    private Map<Integer, Map<Character, List<Integer>>> transitions = new HashMap<>();
    private List<Integer> endingStates = new ArrayList<>();
    private String symbols;

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                printHelp();
            } else {
                var ufsm = new UFSM();
                ufsm.readDescription(new BufferedReader(new FileReader(args[0])));
                ufsm.calculate(new String(Files.readAllBytes(Paths.get(args[1]))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDescription(BufferedReader descriptionReader) throws IOException {
        var states = descriptionReader.readLine().split(" ");
        for (String state : states) {
            endingStates.add(Integer.parseInt(state));
        }
        String transition;
        while ((transition = descriptionReader.readLine()) != null) {
            var subStrings = transition.split(" ");
            var stateNum = Integer.parseInt(subStrings[0]);
            var event = transitions.computeIfAbsent(stateNum, k -> new HashMap<>());
            var list = event.computeIfAbsent(subStrings[1].charAt(0), k -> new ArrayList<>());
            list.add(Integer.parseInt(subStrings[2]));
        }
    }

    private void calculate(String symbols) {

        this.symbols = symbols;

        if (iterate()) {
            System.out.println("TRUE");
        } else {
            System.out.println("FALSE");
        }
    }

    private boolean iterate() {
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(0 , 0));

        while (!stack.empty()) {
            var point = stack.pop();
            var state = point.x;
            var pos = point.y;

            var states = transitions.get(point.x).get(symbols.charAt(pos));
            if (states != null) {
                for (var i : states) {
                    stack.push(new Point(i, pos + 1));
                }
                if (pos == symbols.length() - 1 && endingStates.contains(state)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void printHelp() {
        System.out.println("Usage: java -jar prog.jar descriptionFile stringFile");
    }
}
