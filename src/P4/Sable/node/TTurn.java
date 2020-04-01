/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class TTurn extends Token
{
    public TTurn()
    {
        super.setText("Turn");
    }

    public TTurn(int line, int pos)
    {
        super.setText("Turn");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTurn(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTurn(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTurn text.");
    }
}