import grammar.Generator;
import grammar.Parser;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Parser p = new Parser();
        Generator g = new Generator(4);
        String[] s = g.generate(1);
        System.out.println(Arrays.toString(s));
        System.out.println(p.parse(new String[]{"not","id","and","id"}));
    }
}
