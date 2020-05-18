/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import java.util.*;

import P4.CodeGenarator.SemanticException;
import P4.Sable.analysis.*;
import P4.contextualAnalysis.TypeException;

@SuppressWarnings("nls")
public final class AWhileStmt extends PStmt
{
    private PExpr _predicate_;
    private final LinkedList<PStmt> _then_ = new LinkedList<PStmt>();

    public AWhileStmt()
    {
        // Constructor
    }

    public AWhileStmt(
        @SuppressWarnings("hiding") PExpr _predicate_,
        @SuppressWarnings("hiding") List<?> _then_)
    {
        // Constructor
        setPredicate(_predicate_);

        setThen(_then_);

    }

    @Override
    public Object clone()
    {
        return new AWhileStmt(
            cloneNode(this._predicate_),
            cloneList(this._then_));
    }

    @Override
    public void apply(Switch sw) throws TypeException, SemanticException {
        ((Analysis) sw).caseAWhileStmt(this);
    }

    public PExpr getPredicate()
    {
        return this._predicate_;
    }

    public void setPredicate(PExpr node)
    {
        if(this._predicate_ != null)
        {
            this._predicate_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._predicate_ = node;
    }

    public LinkedList<PStmt> getThen()
    {
        return this._then_;
    }

    public void setThen(List<?> list)
    {
        for(PStmt e : this._then_)
        {
            e.parent(null);
        }
        this._then_.clear();

        for(Object obj_e : list)
        {
            PStmt e = (PStmt) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._then_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._predicate_)
            + toString(this._then_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._predicate_ == child)
        {
            this._predicate_ = null;
            return;
        }

        if(this._then_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._predicate_ == oldChild)
        {
            setPredicate((PExpr) newChild);
            return;
        }

        for(ListIterator<PStmt> i = this._then_.listIterator(); i.hasNext();)
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
