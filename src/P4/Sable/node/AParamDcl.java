/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class AParamDcl extends PParamDcl
{
    private PType _type_;
    private TId _name_;

    public AParamDcl()
    {
        // Constructor
    }

    public AParamDcl(
        @SuppressWarnings("hiding") PType _type_,
        @SuppressWarnings("hiding") TId _name_)
    {
        // Constructor
        setType(_type_);

        setName(_name_);

    }

    @Override
    public Object clone()
    {
        return new AParamDcl(
            cloneNode(this._type_),
            cloneNode(this._name_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAParamDcl(this);
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

    public TId getName()
    {
        return this._name_;
    }

    public void setName(TId node)
    {
        if(this._name_ != null)
        {
            this._name_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._name_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._type_)
            + toString(this._name_);
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

        if(this._name_ == child)
        {
            this._name_ = null;
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

        if(this._name_ == oldChild)
        {
            setName((TId) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
