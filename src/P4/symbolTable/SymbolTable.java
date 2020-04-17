package P4.symbolTable;

import java.util.*;

//Symbol table
public class SymbolTable {

    private HashMap<String, LinkedList<Symbol>> table = new HashMap<>();
    private int scope = 0;

    public SymbolTable() {

    }
    // Open a new scope
    public void openScope(){
        scope++;
    }
    // Close the most recent scope
    public void closeScope(){
        scope--;
    }
    // Enter a symbol in the symbol table
    public void enterSymbol(String name, String type ){
        LinkedList<Symbol> current = table.get(name);

        if(current == null){
            table.put(name, new LinkedList<Symbol>((Collection<? extends Symbol>) new Symbol()));
        }
    }
    // Retrieve a symbol from the symbol table
    public String retrieveSymbol(String name){
        return "";
    }
    // Check if a symbol is declared in the most recent scope
    public Boolean declaredLocally(String name){
        return true;
    }

}
