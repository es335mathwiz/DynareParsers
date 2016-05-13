package gov.frb.ma.msu;

import junit.framework.TestCase;
import java.util.*;
//import java.io.*;
import org.antlr.runtime.*;
import java.io.FileWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import gov.frb.ma.msu.EconXML.*;
import org.antlr.runtime.tree.*;
import java.util.List;
import java.util.ArrayList;

public class DynareModelTest extends TestCase {
	dynare412Parser gg;
	public DynareModelTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	       String osStr=System.getProperty("os.name");
	       String bStr=System.getProperty("user.dir");
	       System.out.println("os.name="+osStr+"<"+"user.dir="+bStr+"<");
String homeDir;
homeDir=bStr;
System.out.println("homeDir="+homeDir);
 //       dynare412Lexer lex = new dynare412Lexer(new ANTLRFileStream("./example1.mod"));
	       dynare412Lexer lex = new dynare412Lexer(new ANTLRFileStream("./AltEx1.mod"));

	       CommonTokenStream tokens = new CommonTokenStream(lex);

        gg = new dynare412Parser(tokens);
		/*
whttp://java.sun.com/webservices/docs/1.6/tutorial/doc/JAXBUsing3.html
		 */
//		ANTLRFileStream theInput = new ANTLRFileStream("./example1.mod");
 //   	ANTLRFileStream theInput = new ANTLRFileStream("./example1.mod");
        ANTLRFileStream theInput = new ANTLRFileStream("./AltEx1.mod");   	
		dynare412Lexer theLexer = new dynare412Lexer(theInput);
        CommonTokenStream theTokens = new CommonTokenStream(theLexer);
        dynare412Parser theParser  = new dynare412Parser(theTokens);
		dynare412Parser.statement_list_return parsedModel =theParser.statement_list();
CommonTree tree = (CommonTree)parsedModel.getTree();
CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
	nodes.setTokenStream(theTokens);

	SymbolTable symtab= new SymbolTable();
	List<String> equations = new ArrayList<String>();
	SingletonWrapper anSWrapper = new SingletonWrapper();
	anSWrapper.setFactory(new ParameterTypeFactoryFunctor());
	anSWrapper.setInstance(null);
	ParameterType paramType = (ParameterType)anSWrapper.instance();
	anSWrapper.setFactory(new LagLeadTypeFactoryFunctor());
	anSWrapper.setInstance(null);
	LagLeadVarType llVarType = (LagLeadVarType)anSWrapper.instance();
	anSWrapper.setFactory(new InnovationTypeFactoryFunctor());
	anSWrapper.setInstance(null);
	InnovationVarType innovType = (InnovationVarType)anSWrapper.instance();
	dynare412Tree walker = new dynare412Tree(nodes,equations,symtab,paramType,llVarType,innovType);
	
	//walker.downup(tree);//walker.topdown();
	/*
	
	dynare412Tree.statement_list_return statement_listTree=walker.statement_list(equations,symtab,paramType,llVarType,innovType);
//	 theInput = new ANTLRFileStream("./example1.mod");
	 theInput = new ANTLRFileStream("./example1.mod");
	 theLexer = new dynare412Lexer(theInput);
     theTokens = new CommonTokenStream(theLexer);
     theParser  = new dynare412Parser(theTokens);
	 parsedModel =theParser.statement_list();
tree = (CommonTree)parsedModel.getTree();
nodes = new CommonTreeNodeStream(tree);
nodes.setTokenStream(theTokens);
List<String> newEquations = new ArrayList();
walker = new dynare412Tree(nodes,newEquations,symtab,paramType,llVarType,innovType);

*/
	//List<String> newEquations = new ArrayList();
	dynare412Tree.statement_list_return statement_listTree=walker.statement_list(equations,symtab,paramType,llVarType,innovType);
//statement_listTree=walker.statement_list(newEquations,symtab,paramType,llVarType,innovType);	
	
	
	
//	nodes.setTokenStream(theTokens);
//	walker = new dynare412Tree(nodes,equations,symtab,paramType,llVarType,innovType);
//	statement_listTree=walker.statement_list(equations,symtab,paramType,llVarType,innovType);
	
	
	
		JAXBContext jaxContext = JAXBContext.newInstance( "gov.frb.ma.msu.EconXML" );
Marshaller mrshllr = jaxContext.createMarshaller();
mrshllr.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
// creating the ObjectFactory
ObjectFactory objFactory = new ObjectFactory();
AMAModel statement_list = objFactory.createAMAModel();
String modName = statement_list.getModelName();
String newModName = "aNewName";
statement_list.setModelName(newModName);
EndogenousVariable endV =objFactory.createEndogenousVariable();
gov.frb.ma.msu.EconXML.Parameter paramV = objFactory.createParameter();
endV.setDescription("aDescription");
endV.setName("atestName");
HashMap<String,Symbol> aSet=symtab.symbols;
//System.out.println("Retrieving all keys from the HashMap");

Iterator<String> iterator = aSet.keySet().iterator();
List<Object> modAccessor=statement_list.getEndogenousVariableOrExogenousVariableOrParameter();
while( iterator.hasNext() ){
String theKey=iterator.next();
if(aSet.get(theKey).getType()==llVarType)
{
endV =objFactory.createEndogenousVariable();
endV.setName(theKey);
statement_list.getEndogenousVariableOrExogenousVariableOrParameter().add(endV);
} else{
	paramV =objFactory.createParameter();
	paramV.setName(theKey);
	modAccessor.add(paramV);
	}
}
Iterator<String> eqiterator = equations.iterator();

while( eqiterator.hasNext() ){
Equation anEq =objFactory.createEquation();
anEq.setValue((String)eqiterator.next());
modAccessor.add(anEq);
} 

//System.out.println("modelName="+modName);

//mrshllr.marshal( statement_list, System.out );

mrshllr.marshal( statement_list, new FileWriter(homeDir+"mod.xml") );
	}
public void testParseDynareModel() {
    try {
        gg.statement_list();
    } catch (RecognitionException ee) {
        ee.printStackTrace();
    }
}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
