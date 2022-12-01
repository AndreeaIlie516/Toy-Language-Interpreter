package Model.Expression;

import Exception.ExprException;
import Model.ADT.IMyDictionary;
import Model.Value.IValue;

public interface IExp {
    IValue evaluate(IMyDictionary<String, IValue> symbolTable) throws ExprException;

    String toString();

    IExp deepCopy();

}