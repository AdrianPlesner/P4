package P4.CodeGenarator;

import P4.Sable.node.*;
import P4.contextualAnalysis.TypeException;

import java.util.HashMap;

public class FieldGenerator extends CodeGenerator {

    // Constructor
    public FieldGenerator(HashMap<String,String> h){
        files = h;
    }

    @Override
    public void caseAProg(AProg node) throws TypeException, SemanticException {
        // Go through all includes
        for(Start s : node.includes){
            s.getPProg().apply(this);
        }
        // Go through this
        // Fields are only found in setup
        node.getSetup().apply(this);
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException, SemanticException {
        // Card fields
        current = "Game/card";
        node.getCard().apply(this);
        // player fields
        current = "Game/player";
        node.getPlayer().apply(this);
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException, SemanticException {
        for(PStmt dcl : node.getDcls()){
            dcl.apply(this);
        }
    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException, SemanticException {
        // .field <access-spec> <field-name> <descriptor> [ = <value> ]

        for(PSingleDcl sdcl : node.getDcls()){
            // field head
            String s = ".field public ";
            if(Static){
                s += "static ";
            }
            String prev = current;
            current = s;
            sdcl.apply(this);
            s = current;
            current = prev;
            emit(s);
            node.getType().apply(this);
            emit("\n");

        }

    }

    @Override
    public void caseASingleDcl(ASingleDcl node) throws TypeException {
        current = current.concat(node.getId().getText() + " ");
    }

    @Override
    public void caseAVarType(AVarType node) throws TypeException {
        super.caseAVarType(node);
    }
}

