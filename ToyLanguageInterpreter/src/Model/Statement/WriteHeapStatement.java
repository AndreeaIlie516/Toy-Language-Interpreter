package Model.Statement;

import Model.ADT.IMyDictionary;
import Model.ADT.IMyHeap;
import Model.Expression.IExp;
import Model.State.ProgramState;
import Model.Value.IValue;
import Exception.ADTException;
import Exception.MyException;
import Exception.ExprException;
import Model.Value.ReferenceValue;

public class WriteHeapStatement implements IStatement {
    String variableName;
    IExp expression;

    public WriteHeapStatement(String variableName, IExp expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    public ProgramState execute(ProgramState state) throws ADTException, MyException, ExprException {
        IMyDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IMyHeap<IValue> heap = state.getHeap();

        if (symbolTable.isDefined(variableName)) {
            IValue value = symbolTable.lookup(variableName);
            if(value != null) {
                int referenceAddress = ((ReferenceValue)value).getAddress();
                if(heap.contains(referenceAddress)) {
                    IValue value1 = expression.evaluate(symbolTable,heap);
                    if(value1.getType().equals(heap.get(referenceAddress).getType())) {
                        heap.update(referenceAddress, value1);
                    }
                    else {
                        throw new MyException("Expression not of variable type");
                    }
                }
                else {
                    throw new MyException("Value not in the heap");
                }
            }
            else{
                throw new MyException("Value is not a reference");
            }
        }
        else {
            throw new MyException("Variable not declared");
        }
        state.setSymbolTable(symbolTable);
        state.setHeap(heap);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(new String(variableName), expression.deepCopy());
    }


    @Override
    public String toString() {
        return "writeHeap(" + variableName + ',' + expression.toString() + ")";
    }
}
