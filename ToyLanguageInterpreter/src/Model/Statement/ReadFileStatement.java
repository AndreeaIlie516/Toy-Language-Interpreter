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
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.IValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private IExp expression;
    private String varName;
    private String fileName;

    public ReadFileStatement(IExp expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }
    public ReadFileStatement(IExp expression, String varName, String fileName) {
        this.expression = expression;
        this.varName = varName;
        this.fileName = fileName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtException, ExprException, ADTException {
        IMyDictionary<String, IValue> symTable = state.getSymbolTable();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IMyHeap<IValue> heap = state.getHeap();
        if (symTable.isDefined(varName)) {
            if (symTable.lookup(varName).getType().equals(new IntType())) {
                IValue value = expression.evaluate(symTable, heap);
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
    public IStatement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), varName, fileName);
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> table) throws TypeException {
        IType expressionType = expression.typeCheck(table);
        IType variableType = table.lookup(varName);
        if (expressionType.equals(new StringType())) {
            if (variableType.equals(new IntType())) {
                return table;
            } else {
                throw new TypeException("Variable not of type int");
            }
        } else {
            throw new TypeException("Expression not of type string");
        }
    }
}
