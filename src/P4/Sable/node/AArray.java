/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class AArray extends PArray
{
    private TType _type_;
    private TLBox _lBox_;
    private TRBox _rBox_;

    public AArray()
    {
        // Constructor
    }

    public AArray(
        @SuppressWarnings("hiding") TType _type_,
        @SuppressWarnings("hiding") TLBox _lBox_,
        @SuppressWarnings("hiding") TRBox _rBox_)
    {
        // Constructor
        setType(_type_);

        setLBox(_lBox_);

        setRBox(_rBox_);

    }

    @Override
    public Object clone()
    {
        return new AArray(
            cloneNode(this._type_),
            cloneNode(this._lBox_),
            cloneNode(this._rBox_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAArray(this);
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

    public TLBox getLBox()
    {
        return this._lBox_;
    }

    public void setLBox(TLBox node)
    {
        if(this._lBox_ != null)
        {
            this._lBox_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lBox_ = node;
    }

    public TRBox getRBox()
    {
        return this._rBox_;
    }

    public void setRBox(TRBox node)
    {
        if(this._rBox_ != null)
        {
            this._rBox_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rBox_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._type_)
            + toString(this._lBox_)
            + toString(this._rBox_);
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

        if(this._lBox_ == child)
        {
            this._lBox_ = null;
            return;
        }

        if(this._rBox_ == child)
        {
            this._rBox_ = null;
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
            setType((TType) newChild);
            return;
        }

        if(this._lBox_ == oldChild)
        {
            setLBox((TLBox) newChild);
            return;
        }

        if(this._rBox_ == oldChild)
        {
            setRBox((TRBox) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}