/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.node;

import P4.Sable.analysis.*;
import P4.symbolTable.TypeException;

@SuppressWarnings("nls")
public final class AFloatLiteral extends PLiteral
{
    private TFloatLiteral _value_;

    public AFloatLiteral()
    {
        // Constructor
    }

    public AFloatLiteral(
        @SuppressWarnings("hiding") TFloatLiteral _value_)
    {
        // Constructor
        setValue(_value_);

    }

    @Override
    public Object clone()
    {
        return new AFloatLiteral(
            cloneNode(this._value_));
    }

    @Override
    public void apply(Switch sw) throws TypeException {
        ((Analysis) sw).caseAFloatLiteral(this);
    }

    public TFloatLiteral getValue()
    {
        return this._value_;
    }

    public void setValue(TFloatLiteral node)
    {
        if(this._value_ != null)
        {
            this._value_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._value_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._value_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._value_ == child)
        {
            this._value_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._value_ == oldChild)
        {
            setValue((TFloatLiteral) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
