/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.parser;

import P4.Sable.node.*;
import P4.Sable.analysis.*;

class TokenIndex extends AnalysisAdapter
{
    int index;

    @Override
    public void caseTId(@SuppressWarnings("unused") TId node)
    {
        this.index = 0;
    }

    @Override
    public void caseTBoolLiteral(@SuppressWarnings("unused") TBoolLiteral node)
    {
        this.index = 1;
    }

    @Override
    public void caseTAddOp(@SuppressWarnings("unused") TAddOp node)
    {
        this.index = 2;
    }

    @Override
    public void caseTMultOp(@SuppressWarnings("unused") TMultOp node)
    {
        this.index = 3;
    }

    @Override
    public void caseTIntLiteral(@SuppressWarnings("unused") TIntLiteral node)
    {
        this.index = 4;
    }

    @Override
    public void caseTFloatLiteral(@SuppressWarnings("unused") TFloatLiteral node)
    {
        this.index = 5;
    }

    @Override
    public void caseTString(@SuppressWarnings("unused") TString node)
    {
        this.index = 6;
    }

    @Override
    public void caseTBoolOp(@SuppressWarnings("unused") TBoolOp node)
    {
        this.index = 7;
    }

    @Override
    public void caseTRelationOp(@SuppressWarnings("unused") TRelationOp node)
    {
        this.index = 8;
    }

    @Override
    public void caseTAssign(@SuppressWarnings("unused") TAssign node)
    {
        this.index = 9;
    }

    @Override
    public void caseTEqualOp(@SuppressWarnings("unused") TEqualOp node)
    {
        this.index = 10;
    }

    @Override
    public void caseTLParen(@SuppressWarnings("unused") TLParen node)
    {
        this.index = 11;
    }

    @Override
    public void caseTRParen(@SuppressWarnings("unused") TRParen node)
    {
        this.index = 12;
    }

    @Override
    public void caseTLBrack(@SuppressWarnings("unused") TLBrack node)
    {
        this.index = 13;
    }

    @Override
    public void caseTRBrack(@SuppressWarnings("unused") TRBrack node)
    {
        this.index = 14;
    }

    @Override
    public void caseTSetup(@SuppressWarnings("unused") TSetup node)
    {
        this.index = 15;
    }

    @Override
    public void caseTPublic(@SuppressWarnings("unused") TPublic node)
    {
        this.index = 16;
    }

    @Override
    public void caseTPrivate(@SuppressWarnings("unused") TPrivate node)
    {
        this.index = 17;
    }

    @Override
    public void caseTSeparator(@SuppressWarnings("unused") TSeparator node)
    {
        this.index = 18;
    }

    @Override
    public void caseTDot(@SuppressWarnings("unused") TDot node)
    {
        this.index = 19;
    }

    @Override
    public void caseTMoves(@SuppressWarnings("unused") TMoves node)
    {
        this.index = 20;
    }

    @Override
    public void caseTLBox(@SuppressWarnings("unused") TLBox node)
    {
        this.index = 21;
    }

    @Override
    public void caseTRBox(@SuppressWarnings("unused") TRBox node)
    {
        this.index = 22;
    }

    @Override
    public void caseTReturn(@SuppressWarnings("unused") TReturn node)
    {
        this.index = 23;
    }

    @Override
    public void caseTSemi(@SuppressWarnings("unused") TSemi node)
    {
        this.index = 24;
    }

    @Override
    public void caseTIf(@SuppressWarnings("unused") TIf node)
    {
        this.index = 25;
    }

    @Override
    public void caseTElse(@SuppressWarnings("unused") TElse node)
    {
        this.index = 26;
    }

    @Override
    public void caseTSwitch(@SuppressWarnings("unused") TSwitch node)
    {
        this.index = 27;
    }

    @Override
    public void caseTCase(@SuppressWarnings("unused") TCase node)
    {
        this.index = 28;
    }

    @Override
    public void caseTDefault(@SuppressWarnings("unused") TDefault node)
    {
        this.index = 29;
    }

    @Override
    public void caseTColon(@SuppressWarnings("unused") TColon node)
    {
        this.index = 30;
    }

    @Override
    public void caseTWhile(@SuppressWarnings("unused") TWhile node)
    {
        this.index = 31;
    }

    @Override
    public void caseTFor(@SuppressWarnings("unused") TFor node)
    {
        this.index = 32;
    }

    @Override
    public void caseTForeach(@SuppressWarnings("unused") TForeach node)
    {
        this.index = 33;
    }

    @Override
    public void caseTTurn(@SuppressWarnings("unused") TTurn node)
    {
        this.index = 34;
    }

    @Override
    public void caseTEndCon(@SuppressWarnings("unused") TEndCon node)
    {
        this.index = 35;
    }

    @Override
    public void caseTIn(@SuppressWarnings("unused") TIn node)
    {
        this.index = 36;
    }

    @Override
    public void caseTType(@SuppressWarnings("unused") TType node)
    {
        this.index = 37;
    }

    @Override
    public void caseTLArr(@SuppressWarnings("unused") TLArr node)
    {
        this.index = 38;
    }

    @Override
    public void caseTRArr(@SuppressWarnings("unused") TRArr node)
    {
        this.index = 39;
    }

    @Override
    public void caseTList(@SuppressWarnings("unused") TList node)
    {
        this.index = 40;
    }

    @Override
    public void caseTTypeof(@SuppressWarnings("unused") TTypeof node)
    {
        this.index = 41;
    }

    @Override
    public void caseTFun(@SuppressWarnings("unused") TFun node)
    {
        this.index = 42;
    }

    @Override
    public void caseEOF(@SuppressWarnings("unused") EOF node)
    {
        this.index = 43;
    }
}