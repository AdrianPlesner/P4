/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class TMoves extends Token
{
    public TMoves()
    {
        super.setText("Moves");
    }

    public TMoves(int line, int pos)
    {
        super.setText("Moves");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TMoves(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTMoves(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TMoves text.");
    }
}