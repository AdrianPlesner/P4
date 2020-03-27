/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class AInitList extends PInitList
{
    private TLBrack _lBrack_;
    private PElementList _elementList_;
    private TRBrack _rBrack_;

    public AInitList()
    {
        // Constructor
    }

    public AInitList(
        @SuppressWarnings("hiding") TLBrack _lBrack_,
        @SuppressWarnings("hiding") PElementList _elementList_,
        @SuppressWarnings("hiding") TRBrack _rBrack_)
    {
        // Constructor
        setLBrack(_lBrack_);

        setElementList(_elementList_);

        setRBrack(_rBrack_);

    }

    @Override
    public Object clone()
    {
        return new AInitList(
            cloneNode(this._lBrack_),
            cloneNode(this._elementList_),
            cloneNode(this._rBrack_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAInitList(this);
    }

    public TLBrack getLBrack()
    {
        return this._lBrack_;
    }

    public void setLBrack(TLBrack node)
    {
        if(this._lBrack_ != null)
        {
            this._lBrack_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lBrack_ = node;
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

    public TRBrack getRBrack()
    {
        return this._rBrack_;
    }

    public void setRBrack(TRBrack node)
    {
        if(this._rBrack_ != null)
        {
            this._rBrack_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rBrack_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._lBrack_)
            + toString(this._elementList_)
            + toString(this._rBrack_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._lBrack_ == child)
        {
            this._lBrack_ = null;
            return;
        }

        if(this._elementList_ == child)
        {
            this._elementList_ = null;
            return;
        }

        if(this._rBrack_ == child)
        {
            this._rBrack_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._lBrack_ == oldChild)
        {
            setLBrack((TLBrack) newChild);
            return;
        }

        if(this._elementList_ == oldChild)
        {
            setElementList((PElementList) newChild);
            return;
        }

        if(this._rBrack_ == oldChild)
        {
            setRBrack((TRBrack) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
