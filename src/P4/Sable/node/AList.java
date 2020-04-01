/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class AList extends PList
{
    private TList _list_;
    private TTypeof _typeof_;
    private TType _type_;

    public AList()
    {
        // Constructor
    }

    public AList(
        @SuppressWarnings("hiding") TList _list_,
        @SuppressWarnings("hiding") TTypeof _typeof_,
        @SuppressWarnings("hiding") TType _type_)
    {
        // Constructor
        setList(_list_);

        setTypeof(_typeof_);

        setType(_type_);

    }

    @Override
    public Object clone()
    {
        return new AList(
            cloneNode(this._list_),
            cloneNode(this._typeof_),
            cloneNode(this._type_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAList(this);
    }

    public TList getList()
    {
        return this._list_;
    }

    public void setList(TList node)
    {
        if(this._list_ != null)
        {
            this._list_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._list_ = node;
    }

    public TTypeof getTypeof()
    {
        return this._typeof_;
    }

    public void setTypeof(TTypeof node)
    {
        if(this._typeof_ != null)
        {
            this._typeof_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._typeof_ = node;
    }

    public TType getType()
    {
        return this._type_;
    }

    public void setType(TType node)
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
            + toString(this._list_)
            + toString(this._typeof_)
            + toString(this._type_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._list_ == child)
        {
            this._list_ = null;
            return;
        }

        if(this._typeof_ == child)
        {
            this._typeof_ = null;
            return;
        }

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
        if(this._list_ == oldChild)
        {
            setList((TList) newChild);
            return;
        }

        if(this._typeof_ == oldChild)
        {
            setTypeof((TTypeof) newChild);
            return;
        }

        if(this._type_ == oldChild)
        {
            setType((TType) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}