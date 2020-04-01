/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class AMethodDcl extends PMethodDcl
{
    private TFun _fun_;
    private TId _id_;
    private TLParen _lParen_;
    private PParamDclList _paramDclList_;
    private TRParen _rParen_;
    private TTypeof _typeof_;
    private PType _type_;
    private TLBrack _lBrack_;
    private PStmtList _stmtList_;
    private PReturnStmt _returnStmt_;
    private TRBrack _rBrack_;

    public AMethodDcl()
    {
        // Constructor
    }

    public AMethodDcl(
        @SuppressWarnings("hiding") TFun _fun_,
        @SuppressWarnings("hiding") TId _id_,
        @SuppressWarnings("hiding") TLParen _lParen_,
        @SuppressWarnings("hiding") PParamDclList _paramDclList_,
        @SuppressWarnings("hiding") TRParen _rParen_,
        @SuppressWarnings("hiding") TTypeof _typeof_,
        @SuppressWarnings("hiding") PType _type_,
        @SuppressWarnings("hiding") TLBrack _lBrack_,
        @SuppressWarnings("hiding") PStmtList _stmtList_,
        @SuppressWarnings("hiding") PReturnStmt _returnStmt_,
        @SuppressWarnings("hiding") TRBrack _rBrack_)
    {
        // Constructor
        setFun(_fun_);

        setId(_id_);

        setLParen(_lParen_);

        setParamDclList(_paramDclList_);

        setRParen(_rParen_);

        setTypeof(_typeof_);

        setType(_type_);

        setLBrack(_lBrack_);

        setStmtList(_stmtList_);

        setReturnStmt(_returnStmt_);

        setRBrack(_rBrack_);

    }

    @Override
    public Object clone()
    {
        return new AMethodDcl(
            cloneNode(this._fun_),
            cloneNode(this._id_),
            cloneNode(this._lParen_),
            cloneNode(this._paramDclList_),
            cloneNode(this._rParen_),
            cloneNode(this._typeof_),
            cloneNode(this._type_),
            cloneNode(this._lBrack_),
            cloneNode(this._stmtList_),
            cloneNode(this._returnStmt_),
            cloneNode(this._rBrack_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMethodDcl(this);
    }

    public TFun getFun()
    {
        return this._fun_;
    }

    public void setFun(TFun node)
    {
        if(this._fun_ != null)
        {
            this._fun_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fun_ = node;
    }

    public TId getId()
    {
        return this._id_;
    }

    public void setId(TId node)
    {
        if(this._id_ != null)
        {
            this._id_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._id_ = node;
    }

    public TLParen getLParen()
    {
        return this._lParen_;
    }

    public void setLParen(TLParen node)
    {
        if(this._lParen_ != null)
        {
            this._lParen_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lParen_ = node;
    }

    public PParamDclList getParamDclList()
    {
        return this._paramDclList_;
    }

    public void setParamDclList(PParamDclList node)
    {
        if(this._paramDclList_ != null)
        {
            this._paramDclList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._paramDclList_ = node;
    }

    public TRParen getRParen()
    {
        return this._rParen_;
    }

    public void setRParen(TRParen node)
    {
        if(this._rParen_ != null)
        {
            this._rParen_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rParen_ = node;
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

    public PReturnStmt getReturnStmt()
    {
        return this._returnStmt_;
    }

    public void setReturnStmt(PReturnStmt node)
    {
        if(this._returnStmt_ != null)
        {
            this._returnStmt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._returnStmt_ = node;
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
            + toString(this._fun_)
            + toString(this._id_)
            + toString(this._lParen_)
            + toString(this._paramDclList_)
            + toString(this._rParen_)
            + toString(this._typeof_)
            + toString(this._type_)
            + toString(this._lBrack_)
            + toString(this._stmtList_)
            + toString(this._returnStmt_)
            + toString(this._rBrack_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._fun_ == child)
        {
            this._fun_ = null;
            return;
        }

        if(this._id_ == child)
        {
            this._id_ = null;
            return;
        }

        if(this._lParen_ == child)
        {
            this._lParen_ = null;
            return;
        }

        if(this._paramDclList_ == child)
        {
            this._paramDclList_ = null;
            return;
        }

        if(this._rParen_ == child)
        {
            this._rParen_ = null;
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

        if(this._returnStmt_ == child)
        {
            this._returnStmt_ = null;
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
        if(this._fun_ == oldChild)
        {
            setFun((TFun) newChild);
            return;
        }

        if(this._id_ == oldChild)
        {
            setId((TId) newChild);
            return;
        }

        if(this._lParen_ == oldChild)
        {
            setLParen((TLParen) newChild);
            return;
        }

        if(this._paramDclList_ == oldChild)
        {
            setParamDclList((PParamDclList) newChild);
            return;
        }

        if(this._rParen_ == oldChild)
        {
            setRParen((TRParen) newChild);
            return;
        }

        if(this._typeof_ == oldChild)
        {
            setTypeof((TTypeof) newChild);
            return;
        }

        if(this._type_ == oldChild)
        {
            setType((PType) newChild);
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

        if(this._returnStmt_ == oldChild)
        {
            setReturnStmt((PReturnStmt) newChild);
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