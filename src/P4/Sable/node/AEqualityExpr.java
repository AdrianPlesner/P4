/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.CodeGenarator.SemanticException;
import P4.Sable.analysis.*;
import P4.contextualAnalysis.TypeException;

@SuppressWarnings("nls")
public final class AEqualityExpr extends PExpr
{
    private PExpr _l_;
    private TEqualOp _operator_;
    private PExpr _r_;

    public AEqualityExpr()
    {
        // Constructor
    }

    public AEqualityExpr(
        @SuppressWarnings("hiding") PExpr _l_,
        @SuppressWarnings("hiding") TEqualOp _operator_,
        @SuppressWarnings("hiding") PExpr _r_)
    {
        // Constructor
        setL(_l_);

        setOperator(_operator_);

        setR(_r_);

    }

    @Override
    public Object clone()
    {
        return new AEqualityExpr(
            cloneNode(this._l_),
            cloneNode(this._operator_),
            cloneNode(this._r_));
    }

    @Override
    public void apply(Switch sw) throws TypeException, SemanticException {
        ((Analysis) sw).caseAEqualityExpr(this);
    }

    public PExpr getL()
    {
        return this._l_;
    }

    public void setL(PExpr node)
    {
        if(this._l_ != null)
        {
            this._l_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._l_ = node;
    }

    public TEqualOp getOperator()
    {
        return this._operator_;
    }

    public void setOperator(TEqualOp node)
    {
        if(this._operator_ != null)
        {
            this._operator_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._operator_ = node;
    }

    public PExpr getR()
    {
        return this._r_;
    }

    public void setR(PExpr node)
    {
        if(this._r_ != null)
        {
            this._r_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._r_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._l_)
            + toString(this._operator_)
            + toString(this._r_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._l_ == child)
        {
            this._l_ = null;
            return;
        }

        if(this._operator_ == child)
        {
            this._operator_ = null;
            return;
        }

        if(this._r_ == child)
        {
            this._r_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._l_ == oldChild)
        {
            setL((PExpr) newChild);
            return;
        }

        if(this._operator_ == oldChild)
        {
            setOperator((TEqualOp) newChild);
            return;
        }

        if(this._r_ == oldChild)
        {
            setR((PExpr) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
