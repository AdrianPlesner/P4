/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import java.util.*;
import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class AConstruct extends PConstruct
{
    private TId _name_;
    private final LinkedList<PParamDcl> _params_ = new LinkedList<PParamDcl>();
    private final LinkedList<PStmt> _body_ = new LinkedList<PStmt>();

    public AConstruct()
    {
        // Constructor
    }

    public AConstruct(
        @SuppressWarnings("hiding") TId _name_,
        @SuppressWarnings("hiding") List<?> _params_,
        @SuppressWarnings("hiding") List<?> _body_)
    {
        // Constructor
        setName(_name_);

        setParams(_params_);

        setBody(_body_);

    }

    @Override
    public Object clone()
    {
        return new AConstruct(
            cloneNode(this._name_),
            cloneList(this._params_),
            cloneList(this._body_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAConstruct(this);
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

    public LinkedList<PParamDcl> getParams()
    {
        return this._params_;
    }

    public void setParams(List<?> list)
    {
        for(PParamDcl e : this._params_)
        {
            e.parent(null);
        }
        this._params_.clear();

        for(Object obj_e : list)
        {
            PParamDcl e = (PParamDcl) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._params_.add(e);
        }
    }

    public LinkedList<PStmt> getBody()
    {
        return this._body_;
    }

    public void setBody(List<?> list)
    {
        for(PStmt e : this._body_)
        {
            e.parent(null);
        }
        this._body_.clear();

        for(Object obj_e : list)
        {
            PStmt e = (PStmt) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._body_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._name_)
            + toString(this._params_)
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

        if(this._params_.remove(child))
        {
            return;
        }

        if(this._body_.remove(child))
        {
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

        for(ListIterator<PParamDcl> i = this._params_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PParamDcl) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        for(ListIterator<PStmt> i = this._body_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PStmt) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        throw new RuntimeException("Not a child.");
    }
}
