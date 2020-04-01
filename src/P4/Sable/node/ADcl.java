/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class ADcl extends PDcl
{
    private PType _type_;
    private PDclList _dclList_;

    public ADcl()
    {
        // Constructor
    }

    public ADcl(
        @SuppressWarnings("hiding") PType _type_,
        @SuppressWarnings("hiding") PDclList _dclList_)
    {
        // Constructor
        setType(_type_);

        setDclList(_dclList_);

    }

    @Override
    public Object clone()
    {
        return new ADcl(
            cloneNode(this._type_),
            cloneNode(this._dclList_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADcl(this);
    }

    public PType getType()
    {
        return this._type_;
    }

    public void setType(PType node)
    {
        if(this._type_ != null)
        {
            this._type_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._type_ = node;
    }

    public PDclList getDclList()
    {
        return this._dclList_;
    }

    public void setDclList(PDclList node)
    {
        if(this._dclList_ != null)
        {
            this._dclList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._dclList_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._type_)
            + toString(this._dclList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._type_ == child)
        {
            this._type_ = null;
            return;
        }

        if(this._dclList_ == child)
        {
            this._dclList_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._type_ == oldChild)
        {
            setType((PType) newChild);
            return;
        }

        if(this._dclList_ == oldChild)
        {
            setDclList((PDclList) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
