package Test;

import Model.ADT.*;
import Model.State.PrgState;
import Exception.ADTException;
import Exception.ExprException;
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
    public void testFileOperation() {
        IMyStack<IStmt> stack=new MyStack<>();
        IMyDictionary<String, IValue> table=new MyDictionary<>();
        IMyList<IValue> out=new MyList<>();
        IMyDictionary<StringValue, BufferedReader>files=new MyDictionary<>();

        IStmt prog=new VarDeclStmt("v",new StringType());

        PrgState ps=new PrgState(stack,table,out,files,prog);

        IExp exfile=new ValueExp(new StringValue("test.in"));
        IStmt of=new OpenRFileStmt(exfile);
        try {
            of.execute(ps);
        }catch (StmtException | ExprException | ADTException e){
            assert false;
        }

        IStmt vardec=new VarDeclStmt("varc",new IntType());
        try{
            vardec.execute(ps);
        }catch (StmtException | ExprException | ADTException e){
            assert false;
        }

        IStmt rf=new ReadFileStmt(exfile,"varc");
        try{
            rf.execute(ps);
        }catch (StmtException | ExprException | ADTException e){
            assert false;
        }

        IValue val=(IntValue)table.lookup("varc");
        assert(val.equals(new IntValue(15)));

        IStmt rf2=new ReadFileStmt(exfile,"varc");
        try{
            rf2.execute(ps);
        }catch (StmtException | ExprException | ADTException e){
            assert false;
        }

        val=(IntValue)table.lookup("varc");
        assert(val.equals(new IntValue(50)));

        IStmt cf=new CloseRFileStmt(exfile);
        try{
            cf.execute(ps);
        }catch (StmtException | ExprException | ADTException e){
            assert false;
        }

    }

}
