/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;
import P4.symbolTable.TypeException;

@SuppressWarnings("nls")
public final class ASubclass extends PSubclass
{
    private TId _name_;
    private PClassBody _body_;

    public ASubclass()
    {
        // Constructor
    }

    public ASubclass(
        @SuppressWarnings("hiding") TId _name_,
        @SuppressWarnings("hiding") PClassBody _body_)
    {
        // Constructor
        setName(_name_);

        setBody(_body_);

    }

    @Override
    public Object clone()
    {
        return new ASubclass(
            cloneNode(this._name_),
            cloneNode(this._body_));
    }

    @Override
    public void apply(Switch sw) throws TypeException {
        ((Analysis) sw).caseASubclass(this);
    }

    public TId getName()
    {
        return this._name_;
    }

    public void setName(TId node)
    {
        if(this._name_ != null)
        {
            this._name_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._name_ = node;
    }

    public PClassBody getBody()
    {
        return this._body_;
    }

    public void setBody(PClassBody node)
    {
        if(this._body_ != null)
        {
            this._body_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._body_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._name_)
            + toString(this._body_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._name_ == child)
        {
            this._name_ = null;
            return;
        }

        if(this._body_ == child)
        {
            this._body_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._name_ == oldChild)
        {
            setName((TId) newChild);
            return;
        }

        if(this._body_ == oldChild)
        {
            setBody((PClassBody) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
