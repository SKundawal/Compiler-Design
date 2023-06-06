//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class CFGGen implements GJNoArguVisitor<String> {

   ProgramCFG cfg;

   public CFGGen() {
      cfg = new ProgramCFG();
   }

   public ProgramCFG getCFG() {
      BB mainEndBB = BB.getEndBB(cfg.methodBBSet.get(cfg.mainMethod));
      cfg.methodBBSetLast.put(cfg.mainMethod, mainEndBB);

      for (String method : cfg.methodBBSet.keySet()) {
         BB endBB = BB.getEndBB(cfg.methodBBSet.get(method));
//         BB.printBB(endBB);
         cfg.methodBBSetLast.put(method, endBB);
      }
      return cfg;
   }

   //
   // Auto class visitors--probably don't need to be overridden.
   //
   public String visit(NodeList n) {
      String _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeListOptional n) {
      if ( n.present() ) {
         String _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public String visit(NodeOptional n) {
      if ( n.present() )
         return n.node.accept(this);
      else
         return null;
   }

   public String visit(NodeSequence n) {
      String _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeToken n) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> ( <REGLIMIT> )?
    * f1 -> MainClass()
    * f2 -> ( TypeDeclaration() )*
    * f3 -> <EOF>
    */
   public String visit(Goal n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> ( VarDeclaration() )*
    * f15 -> ( Statement() )*
    * f16 -> "}"
    * f17 -> "}"
    */
   public String visit(MainClass n) {
      String mainClassName = n.f1.f0.tokenImage;
      String mainMethodName = n.f6.tokenImage;

      // Initialize main class
      cfg.setMainClass(mainClassName);

      // Initialize main method
      cfg.setMainMethod(mainMethodName);

      // Declare Start BB
      BB varDecBB = new BB();
      cfg.setStartBB(varDecBB);
      cfg.setCurrentBB(varDecBB);

      // Populate start BB
      n.f14.accept(this);

      // Statements are free to create new BB's
      n.f15.accept(this);

      return null;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public String visit(TypeDeclaration n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public String visit(ClassDeclaration n) {
      String className = n.f1.f0.tokenImage;

      // Initialize class
      cfg.setActiveClass(className);

//      n.f3.accept(this);
      n.f4.accept(this);
      return null;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   public String visit(ClassExtendsDeclaration n) {
      String className = n.f1.f0.tokenImage + "_" + n.f3.f0.tokenImage;

      // Initialize class
      cfg.setActiveClass(className);

//      n.f5.accept(this);
      n.f6.accept(this);
      return null;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public String visit(VarDeclaration n) {
      cfg.addInstruction(new Instruction(n));
      return null;
   }

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Identifier()
    * f11 -> ";"
    * f12 -> "}"
    */
   public String visit(MethodDeclaration n) {
      String methodName = n.f2.f0.tokenImage;

      // Initialize main method
      cfg.setCurrentMethod(methodName);

      BB startBB = new BB();
      cfg.setStartBB(startBB);
      cfg.setCurrentBB(startBB);

      n.f7.accept(this);
      n.f8.accept(this);

      cfg.addInstruction(new Instruction(n));
      return null;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public String visit(FormalParameterList n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public String visit(FormalParameter n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public String visit(FormalParameterRest n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | FloatType()
    *       | Identifier()
    */
   public String visit(Type n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public String visit(ArrayType n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> "float"
    */
   public String visit(FloatType n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public String visit(BooleanType n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public String visit(IntegerType n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | FieldAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    *       | LivenessQueryStatement()
    */
   public String visit(Statement n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public String visit(Block n) {
      // Declare all BB's
      BB blockBB = new BB();
      BB nextBlock = new BB();

      // Add edges from incoming BB to block BB
      cfg.addEdgeFromTo(cfg.currentBB, blockBB);

      // Populate blockBB
      cfg.setCurrentBB(blockBB);
      n.f1.accept(this);

      // Add edges from blockBB's last block to next block
      cfg.addEdgeFromTo(cfg.currentBB, nextBlock);

      // Set nextBlock as active BB
      cfg.setCurrentBB(nextBlock);

      return null;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public String visit(AssignmentStatement n) {
      cfg.addInstruction(new Instruction(n));
      return null;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Identifier()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Identifier()
    * f6 -> ";"
    */
   public String visit(ArrayAssignmentStatement n) {
      cfg.addInstruction(new Instruction(n));
      return null;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "="
    * f4 -> Identifier()
    * f5 -> ";"
    */
   public String visit(FieldAssignmentStatement n) {
      cfg.addInstruction(new Instruction(n));
      return null;
   }

   /**
    * f0 -> IfthenElseStatement()
    *       | IfthenStatement()
    */
   public String visit(IfStatement n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public String visit(IfthenStatement n) {
      // Declare all BB's
      BB condition = new BB();
      BB trueConditionBlock = new BB();
      BB nextBlock = new BB();

      // Add edges
      cfg.addEdgeFromTo(cfg.currentBB, condition);
      cfg.addEdgeFromTo(condition, nextBlock);

      // Populate Condition block
      condition.pushInstruction(new Instruction(n));

      // Populate trueConditionBlock
      cfg.addEdgeFromTo(condition, trueConditionBlock);
      cfg.setCurrentBB(trueConditionBlock);
      n.f4.accept(this);

      // Add edges from trueCondition's last block to next block
      cfg.addEdgeFromTo(cfg.currentBB, nextBlock);

      // Set nextBlock as active BB
      cfg.setCurrentBB(nextBlock);

      return null;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public String visit(IfthenElseStatement n) {
      // Declare all BB's
      BB condition = new BB();
      BB trueConditionBlock = new BB();
      BB falseConditionBlock = new BB();
      BB nextBlock = new BB();

      // Add edges
      cfg.addEdgeFromTo(cfg.currentBB, condition);
      cfg.addEdgeFromTo(condition, trueConditionBlock);
      cfg.addEdgeFromTo(condition, falseConditionBlock);

      // Populate Condition block
      condition.pushInstruction(new Instruction(n));

      // Populate trueConditionBlock
      cfg.setCurrentBB(trueConditionBlock);
      n.f4.accept(this);

      // Add edges from trueCondition's last block to next block
      cfg.addEdgeFromTo(cfg.currentBB, nextBlock);

      // Populate falseConditionBlock
      cfg.setCurrentBB(falseConditionBlock);
      n.f6.accept(this);

      // Add edges from falseCondition's last block to next block
      cfg.addEdgeFromTo(cfg.currentBB, nextBlock);

      // Set nextBlock as active BB
      cfg.setCurrentBB(nextBlock);

      return null;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public String visit(WhileStatement n) {
      // Declare all BB's
      BB condition = new BB();
      BB whileBodyBlock = new BB();
      BB nextBlock = new BB();

      // Add edges
      cfg.addEdgeFromTo(cfg.currentBB, condition);
      cfg.addEdgeFromTo(condition, whileBodyBlock);
      cfg.addEdgeFromTo(condition, nextBlock);

      // Populate Condition block
      condition.pushInstruction(new Instruction(n));

      // Populate whileBodyBlock
      cfg.setCurrentBB(whileBodyBlock);
      n.f4.accept(this);

      // Add edges from whileBody's last block to next block
      cfg.addEdgeFromTo(cfg.currentBB, condition);

      // Set nextBlock as active BB
      cfg.setCurrentBB(nextBlock);

      return null;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> ";"
    */
   public String visit(PrintStatement n) {
      cfg.addInstruction(new Instruction(n));
      return null;
   }

   /**
    * f0 -> <SCOMMENT1>
    * f1 -> <LIVENESSQUERY>
    * f2 -> <SCOMMENT2>
    */
   public String visit(LivenessQueryStatement n) {
      cfg.addInstruction(new Instruction(n));
      return null;
   }

   /**
    * f0 -> OrExpression()
    *       | AndExpression()
    *       | CompareExpression()
    *       | neqExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | DivExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
   public String visit(Expression n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "&&"
    * f2 -> Identifier()
    */
   public String visit(AndExpression n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "||"
    * f2 -> Identifier()
    */
   public String visit(OrExpression n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "<="
    * f2 -> Identifier()
    */
   public String visit(CompareExpression n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "!="
    * f2 -> Identifier()
    */
   public String visit(neqExpression n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "+"
    * f2 -> Identifier()
    */
   public String visit(PlusExpression n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "-"
    * f2 -> Identifier()
    */
   public String visit(MinusExpression n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "*"
    * f2 -> Identifier()
    */
   public String visit(TimesExpression n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "/"
    * f2 -> Identifier()
    */
   public String visit(DivExpression n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Identifier()
    * f3 -> "]"
    */
   public String visit(ArrayLookup n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "length"
    */
   public String visit(ArrayLength n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ArgList() )?
    * f5 -> ")"
    */
   public String visit(MessageSend n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> ( ArgRest() )*
    */
   public String visit(ArgList n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Identifier()
    */
   public String visit(ArgRest n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | FloatLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    */
   public String visit(PrimaryExpression n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public String visit(IntegerLiteral n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> <FLOAT_LITERAL>
    */
   public String visit(FloatLiteral n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public String visit(TrueLiteral n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public String visit(FalseLiteral n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public String visit(Identifier n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "this"
    */
   public String visit(ThisExpression n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Identifier()
    * f4 -> "]"
    */
   public String visit(ArrayAllocationExpression n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public String visit(AllocationExpression n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Identifier()
    */
   public String visit(NotExpression n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

}