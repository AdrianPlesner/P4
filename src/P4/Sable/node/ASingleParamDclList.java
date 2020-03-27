/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class ASingleParamDclList extends PParamDclList
{
    private PParamDcl _paramDcl_;

    public ASingleParamDclList()
    {
        // Constructor
    }

    public ASingleParamDclList(
        @SuppressWarnings("hiding") PParamDcl _paramDcl_)
    {
        // Constructor
        setParamDcl(_paramDcl_);

    }

    @Override
    public Object clone()
    {
        return new ASingleParamDclList(
            cloneNode(this._paramDcl_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASingleParamDclList(this);
    }

    public PParamDcl getParamDcl()
    {
        return this._paramDcl_;
    }

    public void setParamDcl(PParamDcl node)
    {
        if(this._paramDcl_ != null)
        {
            this._paramDcl_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._paramDcl_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._paramDcl_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._paramDcl_ == child)
        {
            this._paramDcl_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._paramDcl_ == oldChild)
        {
            setParamDcl((PParamDcl) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
