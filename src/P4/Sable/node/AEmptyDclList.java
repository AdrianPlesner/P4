/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;

@SuppressWarnings("nls")
public final class AEmptyDclList extends PDclList
{

    public AEmptyDclList()
    {
        // Constructor
    }

    @Override
    public Object clone()
    {
        return new AEmptyDclList();
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAEmptyDclList(this);
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