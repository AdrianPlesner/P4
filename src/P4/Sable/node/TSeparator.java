/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class TSeparator extends Token
{
    public TSeparator()
    {
        super.setText(",");
    }

    public TSeparator(int line, int pos)
    {
        super.setText(",");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TSeparator(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTSeparator(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TSeparator text.");
    }
}
