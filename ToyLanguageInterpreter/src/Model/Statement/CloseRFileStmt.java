package Model.Statement;

import Model.ADT.IMyDictionary;
import Model.State.PrgState;
import Exception.ADTException;
import Exception.ExprException;
import Exception.StmtException;
import Model.Expression.IExp;
import Model.Type.StringType;
import Model.Value.StringValue;
import Model.Value.IValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{
    private IExp expression;

    public CloseRFileStmt(IExp expression){
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IMyDictionary<String, IValue> symTable = state.getSymbolTable();
        IValue val = expression.evaluate(symTable);
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
    public IStmt deepCopy() {
        return new CloseRFileStmt(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "close(" + expression + ")";
    }
}
