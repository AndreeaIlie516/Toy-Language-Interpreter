package Test;

import Model.ADT.*;
import Model.State.ProgramState;
import Exception.ADTException;
import Exception.ExprException;
import Exception.MyException;
import Exception.StmtException;
import Model.Expression.IExp;
import Model.Expression.ValueExp;
import Model.Statement.*;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.IValue;

import java.io.BufferedReader;

public class testFile {
    /*
    public void testFileOperation() {
        IMyStack<IStatement> stack=new MyStack<>();
        IMyDictionary<String, IValue> table=new MyDictionary<>();
        IMyList<IValue> out=new MyList<>();
        IMyDictionary<StringValue, BufferedReader>files=new MyDictionary<>();

        IStatement prog=new VarDeclStatement("v",new StringType());

        ProgramState ps=new ProgramState(stack,table,out,files,prog);

        IExp exfile=new ValueExp(new StringValue("test.in"));
        IStatement of=new OpenRFileStatement(exfile);
        try {
            of.execute(ps);
        }catch (StmtException | ExprException | ADTException | MyException e){
            assert false;
        }

        IStatement vardec=new VarDeclStatement("varc",new IntType());
        try{
            vardec.execute(ps);
        }catch (StmtException | ExprException | ADTException e){
            assert false;
        } catch (MyException e) {
            throw new RuntimeException(e);
        }

        IStatement rf=new ReadFileStatement(exfile,"varc");
        try{
            rf.execute(ps);
        }catch (StmtException | ExprException | ADTException | MyException e){
            assert false;
        }

        IValue val=(IntValue)table.lookup("varc");
        assert(val.equals(new IntValue(15)));

        IStatement rf2=new ReadFileStatement(exfile,"varc");
        try{
            rf2.execute(ps);
        }catch (StmtException | ExprException | ADTException e){
            assert false;
        } catch (MyException e) {
            throw new RuntimeException(e);
        }

        val=(IntValue)table.lookup("varc");
        assert(val.equals(new IntValue(50)));

        IStatement cf=new CloseRFileStatement(exfile);
        try{
            cf.execute(ps);
        }catch (StmtException | ExprException | ADTException | MyException e){
            assert false;
        }

    }


     */
}
