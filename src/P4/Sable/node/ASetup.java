/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import java.util.*;

import P4.CodeGenarator.SemanticException;
import P4.Sable.analysis.*;
import P4.contextualAnalysis.TypeException;

@SuppressWarnings("nls")
public final class ASetup extends PSetup
{
    private PClassBody _card_;
    private final LinkedList<PStmt> _game_ = new LinkedList<PStmt>();
    private PClassBody _player_;

    public ASetup()
    {
        // Constructor
    }

    public ASetup(
        @SuppressWarnings("hiding") PClassBody _card_,
        @SuppressWarnings("hiding") List<?> _game_,
        @SuppressWarnings("hiding") PClassBody _player_)
    {
        // Constructor
        setCard(_card_);

        setGame(_game_);

        setPlayer(_player_);

    }

    @Override
    public Object clone()
    {
        return new ASetup(
            cloneNode(this._card_),
            cloneList(this._game_),
            cloneNode(this._player_));
    }

    @Override
    public void apply(Switch sw) throws TypeException, SemanticException {
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

    public LinkedList<PStmt> getGame()
    {
        return this._game_;
    }

    public void setGame(List<?> list)
    {
        for(PStmt e : this._game_)
        {
            e.parent(null);
        }
        this._game_.clear();

        for(Object obj_e : list)
        {
            PStmt e = (PStmt) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._game_.add(e);
        }
    }

    public PClassBody getPlayer()
    {
        return this._player_;
    }

    public void setPlayer(PClassBody node)
    {
        if(this._player_ != null)
        {
            this._player_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._player_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._card_)
            + toString(this._game_)
            + toString(this._player_);
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

        if(this._game_.remove(child))
        {
            return;
        }

        if(this._player_ == child)
        {
            this._player_ = null;
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

        for(ListIterator<PStmt> i = this._game_.listIterator(); i.hasNext();)
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

        if(this._player_ == oldChild)
        {
            setPlayer((PClassBody) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
