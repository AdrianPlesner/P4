/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class ASetup extends PSetup
{
    private TSetup _setup_;
    private TLBrack _lBrack_;
    private PStmtList _stmtList_;
    private PPublic _public_;
    private PPrivate _private_;
    private TRBrack _rBrack_;

    public ASetup()
    {
        // Constructor
    }

    public ASetup(
        @SuppressWarnings("hiding") TSetup _setup_,
        @SuppressWarnings("hiding") TLBrack _lBrack_,
        @SuppressWarnings("hiding") PStmtList _stmtList_,
        @SuppressWarnings("hiding") PPublic _public_,
        @SuppressWarnings("hiding") PPrivate _private_,
        @SuppressWarnings("hiding") TRBrack _rBrack_)
    {
        // Constructor
        setSetup(_setup_);

        setLBrack(_lBrack_);

        setStmtList(_stmtList_);

        setPublic(_public_);

        setPrivate(_private_);

        setRBrack(_rBrack_);

    }

    @Override
    public Object clone()
    {
        return new ASetup(
            cloneNode(this._setup_),
            cloneNode(this._lBrack_),
            cloneNode(this._stmtList_),
            cloneNode(this._public_),
            cloneNode(this._private_),
            cloneNode(this._rBrack_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASetup(this);
    }

    public TSetup getSetup()
    {
        return this._setup_;
    }

    public void setSetup(TSetup node)
    {
        if(this._setup_ != null)
        {
            this._setup_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._setup_ = node;
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

    public PStmtList getStmtList()
    {
        return this._stmtList_;
    }

    public void setStmtList(PStmtList node)
    {
        if(this._stmtList_ != null)
        {
            this._stmtList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._stmtList_ = node;
    }

    public PPublic getPublic()
    {
        return this._public_;
    }

    public void setPublic(PPublic node)
    {
        if(this._public_ != null)
        {
            this._public_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._public_ = node;
    }

    public PPrivate getPrivate()
    {
        return this._private_;
    }

    public void setPrivate(PPrivate node)
    {
        if(this._private_ != null)
        {
            this._private_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._private_ = node;
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
            + toString(this._setup_)
            + toString(this._lBrack_)
            + toString(this._stmtList_)
            + toString(this._public_)
            + toString(this._private_)
            + toString(this._rBrack_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._setup_ == child)
        {
            this._setup_ = null;
            return;
        }

        if(this._lBrack_ == child)
        {
            this._lBrack_ = null;
            return;
        }

        if(this._stmtList_ == child)
        {
            this._stmtList_ = null;
            return;
        }

        if(this._public_ == child)
        {
            this._public_ = null;
            return;
        }

        if(this._private_ == child)
        {
            this._private_ = null;
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
        if(this._setup_ == oldChild)
        {
            setSetup((TSetup) newChild);
            return;
        }

        if(this._lBrack_ == oldChild)
        {
            setLBrack((TLBrack) newChild);
            return;
        }

        if(this._stmtList_ == oldChild)
        {
            setStmtList((PStmtList) newChild);
            return;
        }

        if(this._public_ == oldChild)
        {
            setPublic((PPublic) newChild);
            return;
        }

        if(this._private_ == oldChild)
        {
            setPrivate((PPrivate) newChild);
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