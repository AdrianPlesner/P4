/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class AExpandElementList extends PElementList
{
    private PElement _element_;
    private TSemi _semi_;
    private PElementList _elementList_;

    public AExpandElementList()
    {
        // Constructor
    }

    public AExpandElementList(
        @SuppressWarnings("hiding") PElement _element_,
        @SuppressWarnings("hiding") TSemi _semi_,
        @SuppressWarnings("hiding") PElementList _elementList_)
    {
        // Constructor
        setElement(_element_);

        setSemi(_semi_);

        setElementList(_elementList_);

    }

    @Override
    public Object clone()
    {
        return new AExpandElementList(
            cloneNode(this._element_),
            cloneNode(this._semi_),
            cloneNode(this._elementList_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAExpandElementList(this);
    }

    public PElement getElement()
    {
        return this._element_;
    }

    public void setElement(PElement node)
    {
        if(this._element_ != null)
        {
            this._element_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._element_ = node;
    }

    public TSemi getSemi()
    {
        return this._semi_;
    }

    public void setSemi(TSemi node)
    {
        if(this._semi_ != null)
        {
            this._semi_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semi_ = node;
    }

    public PElementList getElementList()
    {
        return this._elementList_;
    }

    public void setElementList(PElementList node)
    {
        if(this._elementList_ != null)
        {
            this._elementList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._elementList_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._element_)
            + toString(this._semi_)
            + toString(this._elementList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._element_ == child)
        {
            this._element_ = null;
            return;
        }

        if(this._semi_ == child)
        {
            this._semi_ = null;
            return;
        }

        if(this._elementList_ == child)
        {
            this._elementList_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._element_ == oldChild)
        {
            setElement((PElement) newChild);
            return;
        }

        if(this._semi_ == oldChild)
        {
            setSemi((TSemi) newChild);
            return;
        }

        if(this._elementList_ == oldChild)
        {
            setElementList((PElementList) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
