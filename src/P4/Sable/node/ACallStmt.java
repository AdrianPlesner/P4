/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;
import P4.contextualAnalysis.TypeException;

@SuppressWarnings("nls")
public final class ACallStmt extends PStmt
{
    private PVal _val_;

    public ACallStmt()
    {
        // Constructor
    }

    public ACallStmt(
        @SuppressWarnings("hiding") PVal _val_)
    {
        // Constructor
        setVal(_val_);

    }

    @Override
    public Object clone()
    {
        return new ACallStmt(
            cloneNode(this._val_));
    }

    @Override
    public void apply(Switch sw) throws TypeException {
        ((Analysis) sw).caseACallStmt(this);
    }

    public PVal getVal()
    {
        return this._val_;
    }

    public void setVal(PVal node)
    {
        if(this._val_ != null)
        {
            this._val_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._val_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._val_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._val_ == child)
        {
            this._val_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._val_ == oldChild)
        {
            setVal((PVal) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
