/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class AMoves extends PMoves
{
    private TMoves _moves_;
    private PMoveDclList _moveDclList_;

    public AMoves()
    {
        // Constructor
    }

    public AMoves(
        @SuppressWarnings("hiding") TMoves _moves_,
        @SuppressWarnings("hiding") PMoveDclList _moveDclList_)
    {
        // Constructor
        setMoves(_moves_);

        setMoveDclList(_moveDclList_);

    }

    @Override
    public Object clone()
    {
        return new AMoves(
            cloneNode(this._moves_),
            cloneNode(this._moveDclList_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMoves(this);
    }

    public TMoves getMoves()
    {
        return this._moves_;
    }

    public void setMoves(TMoves node)
    {
        if(this._moves_ != null)
        {
            this._moves_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._moves_ = node;
    }

    public PMoveDclList getMoveDclList()
    {
        return this._moveDclList_;
    }

    public void setMoveDclList(PMoveDclList node)
    {
        if(this._moveDclList_ != null)
        {
            this._moveDclList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._moveDclList_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._moves_)
            + toString(this._moveDclList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._moves_ == child)
        {
            this._moves_ = null;
            return;
        }

        if(this._moveDclList_ == child)
        {
            this._moveDclList_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._moves_ == oldChild)
        {
            setMoves((TMoves) newChild);
            return;
        }

        if(this._moveDclList_ == oldChild)
        {
            setMoveDclList((PMoveDclList) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}