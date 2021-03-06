/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class TSetup extends Token
{
    public TSetup()
    {
        super.setText("Setup");
    }

    public TSetup(int line, int pos)
    {
        super.setText("Setup");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TSetup(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTSetup(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TSetup text.");
    }
}
