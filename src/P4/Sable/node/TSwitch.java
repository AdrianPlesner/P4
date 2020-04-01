/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class TSwitch extends Token
{
    public TSwitch()
    {
        super.setText("switch");
    }

    public TSwitch(int line, int pos)
    {
        super.setText("switch");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TSwitch(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTSwitch(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TSwitch text.");
    }
}