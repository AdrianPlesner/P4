/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import java.util.*;

import P4.CodeGenarator.SemanticException;
import P4.Sable.analysis.*;
import P4.contextualAnalysis.TypeException;

@SuppressWarnings("nls")
public final class AClassBody extends PClassBody
{
    private final LinkedList<PStmt> _dcls_ = new LinkedList<PStmt>();
    private PConstruct _construct_;
    private final LinkedList<PMethodDcl> _methods_ = new LinkedList<PMethodDcl>();
    private final LinkedList<PSubclass> _subclasses_ = new LinkedList<PSubclass>();

    public AClassBody()
    {
        // Constructor
    }

    public AClassBody(
        @SuppressWarnings("hiding") List<?> _dcls_,
        @SuppressWarnings("hiding") PConstruct _construct_,
        @SuppressWarnings("hiding") List<?> _methods_,
        @SuppressWarnings("hiding") List<?> _subclasses_)
    {
        // Constructor
        setDcls(_dcls_);

        setConstruct(_construct_);

        setMethods(_methods_);

        setSubclasses(_subclasses_);

    }

    @Override
    public Object clone()
    {
        return new AClassBody(
            cloneList(this._dcls_),
            cloneNode(this._construct_),
            cloneList(this._methods_),
            cloneList(this._subclasses_));
    }

    @Override
    public void apply(Switch sw) throws TypeException, SemanticException {
        ((Analysis) sw).caseAClassBody(this);
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

    public PConstruct getConstruct()
    {
        return this._construct_;
    }

    public void setConstruct(PConstruct node)
    {
        if(this._construct_ != null)
        {
            this._construct_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._construct_ = node;
    }

    public LinkedList<PMethodDcl> getMethods()
    {
        return this._methods_;
    }

    public void setMethods(List<?> list)
    {
        for(PMethodDcl e : this._methods_)
        {
            e.parent(null);
        }
        this._methods_.clear();

        for(Object obj_e : list)
        {
            PMethodDcl e = (PMethodDcl) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._methods_.add(e);
        }
    }

    public LinkedList<PSubclass> getSubclasses()
    {
        return this._subclasses_;
    }

    public void setSubclasses(List<?> list)
    {
        for(PSubclass e : this._subclasses_)
        {
            e.parent(null);
        }
        this._subclasses_.clear();

        for(Object obj_e : list)
        {
            PSubclass e = (PSubclass) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._subclasses_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._dcls_)
            + toString(this._construct_)
            + toString(this._methods_)
            + toString(this._subclasses_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._dcls_.remove(child))
        {
            return;
        }

        if(this._construct_ == child)
        {
            this._construct_ = null;
            return;
        }

        if(this._methods_.remove(child))
        {
            return;
        }

        if(this._subclasses_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
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

        if(this._construct_ == oldChild)
        {
            setConstruct((PConstruct) newChild);
            return;
        }

        for(ListIterator<PMethodDcl> i = this._methods_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PMethodDcl) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        for(ListIterator<PSubclass> i = this._subclasses_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PSubclass) newChild);
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
