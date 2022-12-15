package Model.Expression;

import Exception.ExprException;
import Model.ADT.IMyDictionary;
import Model.ADT.IMyHeap;
import Model.Value.IValue;

public interface IExp {
    IValue evaluate(IMyDictionary<String, IValue> symbolTable, IMyHeap<IValue> heap) throws ExprException;

    String toString();

    IExp deepCopy();

}