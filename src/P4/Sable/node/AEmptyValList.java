/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class AEmptyValList extends PValList
{

    public AEmptyValList()
    {
        // Constructor
    }

    @Override
    public Object clone()
    {
        return new AEmptyValList();
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAEmptyValList(this);
    }

    @Override
    public String toString()
    {
        return "";
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        throw new RuntimeException("Not a child.");
    }
}
