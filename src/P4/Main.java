package P4;

import P4.Sable.parser.*;
import P4.Sable.lexer.*;
import P4.Sable.node.*;
import P4.symbolTable.*;

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
            Lexer lexer = new Lexer (new PushbackReader(new BufferedReader(new FileReader(args[0])), 1024));

               // parser program
            Parser parser = new Parser(lexer);
            // Debug med System.out.println(this.token.getClass().getSimpleName() + ": [" + token.getText() + "]");
            Start ast = parser.parse();

            // Construct symbol table and type check
            STBuilder stBuilder = new STBuilder(ast);
            SymbolTable st = stBuilder.BuildST(new SymbolTable());

            stop_time = System.currentTimeMillis();
            // Compute and print compilation time
            System.out.println("Compilation took " + (stop_time-start_time) + " milliseconds" );

            if(stBuilder.hasTypeErrors()){
                System.out.println(stBuilder.getErrorList());
            }
            else{
                //TODO: code generation
            }

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}

