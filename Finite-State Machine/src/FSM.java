import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FSM {

    private Map<Integer, Map<Character, Integer>> transitions = new HashMap<>();
    private int[] endingStates;

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                printHelp();
            } else {
                var fsm = new FSM();
                fsm.readDescription(new BufferedReader(new FileReader(args[0])));
                fsm.calculate(new String(Files.readAllBytes(Paths.get(args[1]))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDescription(BufferedReader descriptionReader) throws IOException {
        var states = descriptionReader.readLine().split(" ");
        endingStates = new int[states.length];
        for (var i = 0; i < states.length; i++) {
            endingStates[i] = Integer.parseInt(states[i]);
        }
        String transition;
        while ((transition = descriptionReader.readLine()) != null) {
            var subStrings = transition.split(" ");
            var stateNum = Integer.parseInt(subStrings[0]);
            var event = transitions.computeIfAbsent(stateNum, k -> new HashMap<>());
            event.putIfAbsent(subStrings[1].charAt(0), Integer.parseInt(subStrings[2]));
        }
    }

    private void calculate(String symbols) {
        var state = 0;
        for (var i = 0; i < symbols.length(); i++) {
            state = transitions.get(state).get(symbols.charAt(i));
        }

        if (isFinal(state)) {
            System.out.println("TRUE");
        } else {
            System.out.println("FALSE");
        }
    }

    private boolean isFinal(int state) {
        for (var endingState : endingStates) {
            if (state == endingState) return true;
        }
        return false;
    }

    private static void printHelp() {
        System.out.println("Usage: java -jar prog.jar descriptionFile stringFile");
    }
}
