package Model.Statement;

import Model.ADT.IMyDictionary;
import Model.State.PrgState;
import Exception.ADTException;
import Exception.ExprException;
import Exception.StmtException;
import Model.Expression.IExp;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.IValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{
    private IExp expression;
    private String varName;
    private String fileName;

    public ReadFileStmt(IExp expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }
    public ReadFileStmt(IExp expression, String varName, String fileName) {
        this.expression = expression;
        this.varName = varName;
        this.fileName = fileName;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IMyDictionary<String, IValue> symTable = state.getSymbolTable();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (symTable.isDefined(varName)) {
            if (symTable.lookup(varName).getType().equals(new IntType())) {
                IValue value = expression.evaluate(symTable);
                if (value.getType().equals(new StringType())) {
                    StringValue stringVal = (StringValue) value;
                    if (fileTable.isDefined(stringVal)) {
                        BufferedReader bufferedReader = fileTable.lookup(stringVal);
                        try {
                            String line = bufferedReader.readLine();
                            IValue intVal;
                            IntType type = new IntType();
                            if (line == null) {
                                intVal = type.getDefaultValue();
                            } else {
                                intVal = new IntValue(Integer.parseInt(line));
                            }
                            symTable.update(varName, intVal);
                        } catch (IOException e) {
                            throw new StmtException(e.getMessage());
                        }
                    }
                    else {
                        throw new StmtException("The file " + stringVal.getValue() + " is not in the File Table!");
                    }
                }
                else {
                    throw new StmtException("The value couldn't be evaluated to a string value!");
                }
            }
            else {
                throw new StmtException(varName + " is not of type int!");
            }
        }
        else {
            throw new StmtException(varName + " is not defined in Sym Table");
        }

        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ", " + varName + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(expression.deepCopy(), varName, fileName);
    }
}
