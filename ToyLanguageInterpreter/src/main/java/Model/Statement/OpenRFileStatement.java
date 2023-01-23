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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class OpenRFileStatement implements IStatement {
    private final IExp expression;

    public OpenRFileStatement(IExp expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtException, ExprException, ADTException {
        IMyDictionary<String, IValue> symTable = state.getSymbolTable();
        IMyHeap<IValue> heap = state.getHeap();
        IValue value = expression.evaluate(symTable, heap);
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
    public IStatement deepCopy() {
        return new OpenRFileStatement(expression.deepCopy());
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> table) throws TypeException {
        IType type = expression.typeCheck(table);
        if (type.equals(new StringType())) {
            return table;
        } else {
            throw new TypeException("Expression not of type string");
        }
    }
}
