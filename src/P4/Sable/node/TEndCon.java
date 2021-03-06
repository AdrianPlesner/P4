/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class TEndCon extends Token
{
    public TEndCon()
    {
        super.setText("EndCondition");
    }

    public TEndCon(int line, int pos)
    {
        super.setText("EndCondition");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TEndCon(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTEndCon(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TEndCon text.");
    }
}
