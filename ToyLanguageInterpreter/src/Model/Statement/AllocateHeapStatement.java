package Model.Statement;

import Model.ADT.IMyDictionary;
import Model.ADT.IMyHeap;
import Model.ADT.IMyStack;
import Model.Expression.IExp;
import Model.State.ProgramState;
import Model.Value.IValue;
import Exception.ExprException;
import Exception.StmtException;
import Model.Type.ReferenceType;
import Model.Value.ReferenceValue;

public class AllocateHeapStatement implements IStatement {
    private final String variableName;
    private final IExp expression;

    public AllocateHeapStatement(String variableName, IExp expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtException, ExprException {
        IMyStack<IStatement> stack = state.getExecutionStack();
        IMyDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IMyHeap<IValue> heap = state.getHeap();
        if(symbolTable.isDefined(variableName)){
            if(symbolTable.lookup(variableName).getType() instanceof ReferenceType){
                IValue value = expression.evaluate(symbolTable, heap);
                IValue tableValue = symbolTable.lookup(variableName);
                if(value.getType().equals(((ReferenceType)(tableValue.getType())).getInner())){
                    int address = heap.allocate(value);
                    symbolTable.update(variableName, new ReferenceValue(address, value.getType()));
                }
                else{
                    throw new StmtException("Value's type is not correct!");
                }
            }
            else{
                throw new StmtException("Value's type is not reference!");
            }
        }
        else{
            throw new StmtException("Value is not declared!");
        }
        state.setSymbolTable(symbolTable);
        state.setHeap(heap);
        state.setExecutionStack(stack);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AllocateHeapStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + variableName + ", " + expression.toString() + ")";
    }

}
