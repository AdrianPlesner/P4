package P4.contextualAnalysis;

import P4.contextualAnalysis.Symbol.SubClass;
import P4.contextualAnalysis.Symbol.Symbol;

import java.lang.reflect.Type;
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
        var removeList = new LinkedList<String>();
        for(var key : table.keySet()){
            var current = table.get(key);
            if(current.getLast().getScope() == scope){
                current.removeLast();
                if(current.isEmpty()){
                    removeList.add(key);
                }
            }
        }
        for(var s : removeList){
            table.remove(s);
        }
        scope--;
    }
    // Enter a symbol in the symbol table
    public void enterSymbol(Symbol s){
        LinkedList<Symbol> current = table.get(s.getIdentifier());

        if(current == null){
            current = new LinkedList<>();

            table.put(s.getIdentifier(), current);
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

    public Symbol retrieveSymbol(String name, Class T){
        var current = table.get(name);
        if(current == null){
            return null;
        }
        int i = current.size()-1;
        while( i >= 0){
            if(current.get(i).getClass().equals(T)){
                return current.get(i);
            }
            i--;
        }
        return null;
    }

    public Boolean containsClass(String name){
        var s = table.get(name);
        if(s != null){
            for(Symbol sym : s){
                if(sym instanceof SubClass){
                    return true;
                }
            }
        }
        return false;
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
