/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class ADefaultStmt extends PDefaultStmt
{
    private TDefault _default_;
    private TColon _colon_;
    private PBlock _block_;

    public ADefaultStmt()
    {
        // Constructor
    }

    public ADefaultStmt(
        @SuppressWarnings("hiding") TDefault _default_,
        @SuppressWarnings("hiding") TColon _colon_,
        @SuppressWarnings("hiding") PBlock _block_)
    {
        // Constructor
        setDefault(_default_);

        setColon(_colon_);

        setBlock(_block_);

    }

    @Override
    public Object clone()
    {
        return new ADefaultStmt(
            cloneNode(this._default_),
            cloneNode(this._colon_),
            cloneNode(this._block_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADefaultStmt(this);
    }

    public TDefault getDefault()
    {
        return this._default_;
    }

    public void setDefault(TDefault node)
    {
        if(this._default_ != null)
        {
            this._default_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._default_ = node;
    }

    public TColon getColon()
    {
        return this._colon_;
    }

    public void setColon(TColon node)
    {
        if(this._colon_ != null)
        {
            this._colon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._colon_ = node;
    }

    public PBlock getBlock()
    {
        return this._block_;
    }

    public void setBlock(PBlock node)
    {
        if(this._block_ != null)
        {
            this._block_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._block_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._default_)
            + toString(this._colon_)
            + toString(this._block_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._default_ == child)
        {
            this._default_ = null;
            return;
        }

        if(this._colon_ == child)
        {
            this._colon_ = null;
            return;
        }

        if(this._block_ == child)
        {
            this._block_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._default_ == oldChild)
        {
            setDefault((TDefault) newChild);
            return;
        }

        if(this._colon_ == oldChild)
        {
            setColon((TColon) newChild);
            return;
        }

        if(this._block_ == oldChild)
        {
            setBlock((PBlock) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}