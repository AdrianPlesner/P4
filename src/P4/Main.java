package P4;

import P4.Sable.parser.*;
import P4.Sable.lexer.*;
import P4.Sable.node.*;
import P4.contextualAnalysis.*;

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

            // Construct symbol table
            STBuilder stBuilder = new STBuilder(ast);
            SymbolTable st = stBuilder.BuildST(new SymbolTable());

            TypeChecker tc = new TypeChecker(ast);

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
        catch(InvalidTypeException e){
            String message = e.getMessage();
            var token = e.getToken();
            if(token != null){
                message += token.getText() + "is not a valid type.";
                message = patchMessage(message,token);
            }
            System.out.println(message);
        }
        catch (IdentifierAlreadyExistsException e){
            String message = e.getMessage();
            var token = e.getToken();
            if(token != null){
                message = patchMessage(message,token);
                message += "identifier already excists locally";
            }
            System.out.println(message);
        }
        catch (TypeException e){
            String message = e.getMessage();
            var token = e.getToken();
            if(token != null){
                    message = patchMessage(message,token);
            }
            System.out.println(message);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String patchMessage(String m, Token t){
        return "Problem at line " + t.getLine() + ":" + t.getPos() + "\n" + m;
    }
}

