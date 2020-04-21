/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import java.util.*;
import P4.Sable.analysis.*;
import P4.symbolTable.TypeException;

@SuppressWarnings("nls")
public final class ASetup extends PSetup
{
    private PClassBody _card_;
    private final LinkedList<PStmt> _public_ = new LinkedList<PStmt>();
    private PClassBody _private_;
    private final LinkedList<PStmt> _dcls_ = new LinkedList<PStmt>();

    public ASetup()
    {
        // Constructor
    }

    public ASetup(
        @SuppressWarnings("hiding") PClassBody _card_,
        @SuppressWarnings("hiding") List<?> _public_,
        @SuppressWarnings("hiding") PClassBody _private_,
        @SuppressWarnings("hiding") List<?> _dcls_)
    {
        // Constructor
        setCard(_card_);

        setPublic(_public_);

        setPrivate(_private_);

        setDcls(_dcls_);

    }

    @Override
    public Object clone()
    {
        return new ASetup(
            cloneNode(this._card_),
            cloneList(this._public_),
            cloneNode(this._private_),
            cloneList(this._dcls_));
    }

    @Override
    public void apply(Switch sw) throws TypeException {
        ((Analysis) sw).caseASetup(this);
    }

    public PClassBody getCard()
    {
        return this._card_;
    }

    public void setCard(PClassBody node)
    {
        if(this._card_ != null)
        {
            this._card_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._card_ = node;
    }

    public LinkedList<PStmt> getPublic()
    {
        return this._public_;
    }

    public void setPublic(List<?> list)
    {
        for(PStmt e : this._public_)
        {
            e.parent(null);
        }
        this._public_.clear();

        for(Object obj_e : list)
        {
            PStmt e = (PStmt) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._public_.add(e);
        }
    }

    public PClassBody getPrivate()
    {
        return this._private_;
    }

    public void setPrivate(PClassBody node)
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

    public LinkedList<PStmt> getDcls()
    {
        return this._dcls_;
    }

    public void setDcls(List<?> list)
    {
        for(PStmt e : this._dcls_)
        {
            e.parent(null);
        }
        this._dcls_.clear();

        for(Object obj_e : list)
        {
            PStmt e = (PStmt) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._dcls_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._card_)
            + toString(this._public_)
            + toString(this._private_)
            + toString(this._dcls_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._card_ == child)
        {
            this._card_ = null;
            return;
        }

        if(this._public_.remove(child))
        {
            return;
        }

        if(this._private_ == child)
        {
            this._private_ = null;
            return;
        }

        if(this._dcls_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._card_ == oldChild)
        {
            setCard((PClassBody) newChild);
            return;
        }

        for(ListIterator<PStmt> i = this._public_.listIterator(); i.hasNext();)
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

        if(this._private_ == oldChild)
        {
            setPrivate((PClassBody) newChild);
            return;
        }

        for(ListIterator<PStmt> i = this._dcls_.listIterator(); i.hasNext();)
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
