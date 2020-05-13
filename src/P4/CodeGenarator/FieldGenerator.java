package P4.CodeGenarator;

import P4.Sable.node.*;
import P4.contextualAnalysis.TypeException;

import java.util.HashMap;

public class FieldGenerator extends CodeGenerator {

    // Constructor
    public FieldGenerator(HashMap<String,String> h){
        files = h;
    }

    private String current = "";

    private String value = null;

    @Override
    public void caseAProg(AProg node) throws TypeException {
        for(Start s : node.includes){
            s.getPProg().apply(this);
        }
        node.getSetup().apply(this);
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException {
        current = "card";
        node.getCard().apply(this);
        current = "player";
        node.getPlayer().apply(this);
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException {
        for(PStmt dcl : node.getDcls()){
            dcl.apply(this);
        }
    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException {
        // .field <access-spec> <field-name> <descriptor> [ = <value> ]
        String s = ".field public ";
        String prev = current;
        current = s;
        for(PSingleDcl sdcl : node.getDcls()){
            sdcl.apply(this);
        }
        s = current;
        current = prev;
        String type = node.getType().toString().trim();
        String t;
        switch(type) {
            case "int":
                t = "I";
                break;
            case "float":
                t = "F";
                break;
            case "string":
                t = "Ljava/lang/String";
                break;
            case "bool":
                t = "Z";
                break;
            default:
                t = "L"+type;
                break;
        }
        s = s.concat(t + "\n");
        emit(current,s);
    }

    @Override
    public void caseASingleDcl(ASingleDcl node) throws TypeException {
        current = current.concat(node.getId().getText() + " ");
    }
}

