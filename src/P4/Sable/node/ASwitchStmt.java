/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import java.util.*;
import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class ASwitchStmt extends PSwitchStmt
{
    private TSwitch _switch_;
    private PVal _val_;
    private TLBrack _lBrack_;
    private final LinkedList<PCase> _case_ = new LinkedList<PCase>();
    private TRBrack _rBrack_;

    public ASwitchStmt()
    {
        // Constructor
    }

    public ASwitchStmt(
        @SuppressWarnings("hiding") TSwitch _switch_,
        @SuppressWarnings("hiding") PVal _val_,
        @SuppressWarnings("hiding") TLBrack _lBrack_,
        @SuppressWarnings("hiding") List<?> _case_,
        @SuppressWarnings("hiding") TRBrack _rBrack_)
    {
        // Constructor
        setSwitch(_switch_);

        setVal(_val_);

        setLBrack(_lBrack_);

        setCase(_case_);

        setRBrack(_rBrack_);

    }

    @Override
    public Object clone()
    {
        return new ASwitchStmt(
            cloneNode(this._switch_),
            cloneNode(this._val_),
            cloneNode(this._lBrack_),
            cloneList(this._case_),
            cloneNode(this._rBrack_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASwitchStmt(this);
    }

    public TSwitch getSwitch()
    {
        return this._switch_;
    }

    public void setSwitch(TSwitch node)
    {
        if(this._switch_ != null)
        {
            this._switch_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._switch_ = node;
    }

    public PVal getVal()
    {
        return this._val_;
    }

    public void setVal(PVal node)
    {
        if(this._val_ != null)
        {
            this._val_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._val_ = node;
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

    public LinkedList<PCase> getCase()
    {
        return this._case_;
    }

    public void setCase(List<?> list)
    {
        for(PCase e : this._case_)
        {
            e.parent(null);
        }
        this._case_.clear();

        for(Object obj_e : list)
        {
            PCase e = (PCase) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._case_.add(e);
        }
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
            + toString(this._switch_)
            + toString(this._val_)
            + toString(this._lBrack_)
            + toString(this._case_)
            + toString(this._rBrack_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._switch_ == child)
        {
            this._switch_ = null;
            return;
        }

        if(this._val_ == child)
        {
            this._val_ = null;
            return;
        }

        if(this._lBrack_ == child)
        {
            this._lBrack_ = null;
            return;
        }

        if(this._case_.remove(child))
        {
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
        if(this._switch_ == oldChild)
        {
            setSwitch((TSwitch) newChild);
            return;
        }

        if(this._val_ == oldChild)
        {
            setVal((PVal) newChild);
            return;
        }

        if(this._lBrack_ == oldChild)
        {
            setLBrack((TLBrack) newChild);
            return;
        }

        for(ListIterator<PCase> i = this._case_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PCase) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._rBrack_ == oldChild)
        {
            setRBrack((TRBrack) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
