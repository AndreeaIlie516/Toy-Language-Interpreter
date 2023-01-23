package Model.Statement;

import Model.ADT.IMyDictionary;
import Model.ADT.IMyHeap;
import Model.State.ProgramState;
import Exception.ADTException;
import Exception.ExprException;
import Exception.StmtException;
import Exception.TypeException;
import Model.Expression.IExp;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.StringValue;
import Model.Value.IValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement {
    private IExp expression;

    public CloseRFileStatement(IExp expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtException, ExprException, ADTException {
        IMyDictionary<String, IValue> symTable = state.getSymbolTable();
        IMyHeap<IValue> heap = state.getHeap();
        IValue val = expression.evaluate(symTable, heap);
        if (val.getType().equals(new StringType())) {
            IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
            StringValue stringVal = (StringValue) val;
            if (fileTable.isDefined(stringVal)) {
                BufferedReader bufferedReader = fileTable.lookup(stringVal);
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new StmtException(e.getMessage());
                }
                fileTable.remove(stringVal);
            } else {
                throw new StmtException("The file doesn't exist in the File Table!");
            }
        }
        else {
            throw new StmtException("Expression could not be evaluated to a string in File Close!");
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFileStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "close(" + expression + ")";
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> table) throws TypeException {
        if (expression.typeCheck(table).equals(new StringType()))
            return table;
        else
            throw new TypeException("Expression not of type string.");
    }
}
