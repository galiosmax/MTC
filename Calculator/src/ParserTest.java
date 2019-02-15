import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void calculate() {

        String[] testStrings = {"2 +(3^(4+1+-2)) * 2", "2+1 * 2", "2-(1 + 2)", "2*1", "(2^2)/1", "2^(2 +3)", "2*(2+2)", "111111 + 111111", " 2         +   3"};
        int[] expectedResults = {56, 4, -1, 2, 4, 32, 8, 222222, 5};

        for (int i = 0; i < testStrings.length; i++) {
            Parser parser = new Parser(testStrings[i]);
            int result = parser.calculate();
            assertEquals(expectedResults[i], result);
        }

    }
}