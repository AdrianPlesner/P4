/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class AListType extends PType
{
    private TId _type_;

    public AListType()
    {
        // Constructor
    }

    public AListType(
        @SuppressWarnings("hiding") TId _type_)
    {
        // Constructor
        setType(_type_);

    }

    @Override
    public Object clone()
    {
        return new AListType(
            cloneNode(this._type_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAListType(this);
    }

    public TId getType()
    {
        return this._type_;
    }

    public void setType(TId node)
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

    @Override
    public String toString()
    {
        return ""
            + toString(this._type_);
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

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._type_ == oldChild)
        {
            setType((TId) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
