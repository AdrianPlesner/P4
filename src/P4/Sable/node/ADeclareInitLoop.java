/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class ADeclareInitLoop extends PInitLoop
{
    private PDcl _dcl_;

    public ADeclareInitLoop()
    {
        // Constructor
    }

    public ADeclareInitLoop(
        @SuppressWarnings("hiding") PDcl _dcl_)
    {
        // Constructor
        setDcl(_dcl_);

    }

    @Override
    public Object clone()
    {
        return new ADeclareInitLoop(
            cloneNode(this._dcl_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADeclareInitLoop(this);
    }

    public PDcl getDcl()
    {
        return this._dcl_;
    }

    public void setDcl(PDcl node)
    {
        if(this._dcl_ != null)
        {
            this._dcl_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._dcl_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._dcl_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._dcl_ == child)
        {
            this._dcl_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._dcl_ == oldChild)
        {
            setDcl((PDcl) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}