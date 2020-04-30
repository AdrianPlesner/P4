/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import java.util.*;
import P4.Sable.analysis.*;
import P4.contextualAnalysis.TypeException;

@SuppressWarnings("nls")
public final class AListExpr extends PExpr
{
    private final LinkedList<PExpr> _elements_ = new LinkedList<PExpr>();

    public AListExpr()
    {
        // Constructor
    }

    public AListExpr(
        @SuppressWarnings("hiding") List<?> _elements_)
    {
        // Constructor
        setElements(_elements_);

    }

    @Override
    public Object clone()
    {
        return new AListExpr(
            cloneList(this._elements_));
    }

    @Override
    public void apply(Switch sw) throws TypeException {
        ((Analysis) sw).caseAListExpr(this);
    }

    public LinkedList<PExpr> getElements()
    {
        return this._elements_;
    }

    public void setElements(List<?> list)
    {
        for(PExpr e : this._elements_)
        {
            e.parent(null);
        }
        this._elements_.clear();

        for(Object obj_e : list)
        {
            PExpr e = (PExpr) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._elements_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._elements_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._elements_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        for(ListIterator<PExpr> i = this._elements_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PExpr) newChild);
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
