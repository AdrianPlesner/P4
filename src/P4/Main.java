package P4;

/*import P4.Sable.parser.*;
import P4.Sable.lexer.*;
import P4.Sable.node.*;*/

import java.io.*;

public class Main {
    public static void main( String[] args){
        System.out.println("Compiling..");
        long start_time, stop_time; //times compilation
        if (args.length < 1) {
            System.out.println("Usage:");
            System.out.println(" java P4.Main <filename>");
        }
        try {
            start_time = System.currentTimeMillis();

               // create lexer
            /*Lexer lexer = new Lexer (new PushbackReader(new BufferedReader(new FileReader(args[0])), 1024));

               // parser program
            Parser parser = new Parser(lexer);
            // Debug med System.out.println(this.token.getClass().getSimpleName() + ": [" + token.getText() + "]");
            Start ast = parser.parse();*/

            stop_time = System.currentTimeMillis();

            System.out.println("Compilation took " + (stop_time-start_time) + " milliseconds" );
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}

