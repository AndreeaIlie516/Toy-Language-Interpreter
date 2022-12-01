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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class OpenRFileStmt implements IStmt{
    private final IExp expression;

    public OpenRFileStmt(IExp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IMyDictionary<String, IValue> symTable = state.getSymbolTable();
        IValue value = expression.evaluate(symTable);
        if (value.getType().equals(new StringType())) {
            IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
            StringValue stringVal = (StringValue) value;
            if (!fileTable.isDefined(stringVal)) {
                try {
                    Reader reader = new FileReader(stringVal.getValue());
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    fileTable.update(stringVal, bufferedReader);
                }
                catch (FileNotFoundException e) {
                    throw new StmtException(e.getMessage());
                }
            }
            else {
                throw new StmtException("The file is already in use!");
            }
        }
        else {
            throw new StmtException("Expression couldn't be evaluated to a string value in File Open!");
        }
        return null;
    }

    @Override
    public String toString() {
        return "Open(" + expression + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(expression.deepCopy());
    }
}
