/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class AExpandRelation extends PRelation
{
    private PRelation _relation_;
    private TRelationOp _relationOp_;
    private PAddExpr _addExpr_;

    public AExpandRelation()
    {
        // Constructor
    }

    public AExpandRelation(
        @SuppressWarnings("hiding") PRelation _relation_,
        @SuppressWarnings("hiding") TRelationOp _relationOp_,
        @SuppressWarnings("hiding") PAddExpr _addExpr_)
    {
        // Constructor
        setRelation(_relation_);

        setRelationOp(_relationOp_);

        setAddExpr(_addExpr_);

    }

    @Override
    public Object clone()
    {
        return new AExpandRelation(
            cloneNode(this._relation_),
            cloneNode(this._relationOp_),
            cloneNode(this._addExpr_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAExpandRelation(this);
    }

    public PRelation getRelation()
    {
        return this._relation_;
    }

    public void setRelation(PRelation node)
    {
        if(this._relation_ != null)
        {
            this._relation_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._relation_ = node;
    }

    public TRelationOp getRelationOp()
    {
        return this._relationOp_;
    }

    public void setRelationOp(TRelationOp node)
    {
        if(this._relationOp_ != null)
        {
            this._relationOp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._relationOp_ = node;
    }

    public PAddExpr getAddExpr()
    {
        return this._addExpr_;
    }

    public void setAddExpr(PAddExpr node)
    {
        if(this._addExpr_ != null)
        {
            this._addExpr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._addExpr_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._relation_)
            + toString(this._relationOp_)
            + toString(this._addExpr_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._relation_ == child)
        {
            this._relation_ = null;
            return;
        }

        if(this._relationOp_ == child)
        {
            this._relationOp_ = null;
            return;
        }

        if(this._addExpr_ == child)
        {
            this._addExpr_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._relation_ == oldChild)
        {
            setRelation((PRelation) newChild);
            return;
        }

        if(this._relationOp_ == oldChild)
        {
            setRelationOp((TRelationOp) newChild);
            return;
        }

        if(this._addExpr_ == oldChild)
        {
            setAddExpr((PAddExpr) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}