package P4.symbolTable;

import P4.Sable.node.Node;

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
    public void enterSymbol(String name, Symbol s){
        LinkedList<Symbol> current = table.get(name);

        if(current == null){
            current = new LinkedList<>();

            table.put(name, current);
        }
        s.setScope(scope);
        current.add(s);
    }
    // Retrieve a symbol from the symbol table
    public Symbol retrieveSymbol(String name){
        var current = table.get(name);
        if(current != null){
            return current.getLast();
        }
        return null;

    }

    // Check if a symbol is declared in the most recent scope
    public Boolean declaredLocally(String name){
        var current = table.get(name);
        if(current != null){
            return current.getLast().getScope() == scope;
        }
        return false;
    }

}
