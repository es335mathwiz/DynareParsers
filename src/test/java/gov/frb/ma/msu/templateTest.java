package gov.frb.ma.msu;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import org.antlr.stringtemplate.*;
import java.io.*;
public class templateTest {
    public static void mainTest(String [] args) throws Exception {
//	FileReader groupFileR = new FileReader("/msu/scratch/m1gsa00/collectMaven/treeTop/dynare/dynareAntlr/src/main/antlr/aGroup.stg");
	FileReader groupFileR = new FileReader("s:/collectMaven/treeTop/dynare/dynareAntlr/src/main/antlr/aGroup.stg");
	StringTemplateGroup templates = new StringTemplateGroup(groupFileR);
	groupFileR.close();

FileInputStream inputFile = new
//FileInputStream(
//"/msu/scratch/m1gsa00/learnAntlr/my-app/src/test/java/gov/frb/ma/infile.txt");
FileInputStream(
"s:/learnAntlr/my-app/src/test/java/gov/frb/ma/infile.txt");
	ANTLRInputStream input = new ANTLRInputStream(inputFile);

dynare412Lexer lexer = new dynare412Lexer(input);
CommonTokenStream tokens = new CommonTokenStream(lexer);
dynare412Parser parser = new dynare412Parser(tokens);
//parser.setTemplateLib(templates);
//dynare412Parser.statement_list_return sl = parser.statement_list();
//parser.statement_list();
//Tree tt= (Tree) sl.getTree();
//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//System.out.println("anything>"+tt.toStringTree()+"<anyting");
//Object output = sl.getTemplate();

//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//System.out.println(output.toString());
//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


    }
}
