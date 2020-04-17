package P4.symbolTable;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.Start;

public class STBuilder extends DepthFirstAdapter {

    private Start ast;

    public STBuilder(Start ast) {
        this.ast = ast;
    }

    // Construct the given symbol table from the ast
    public SymbolTable BuildST(SymbolTable st){
        ast.apply(this);
        return st;
    }

}
