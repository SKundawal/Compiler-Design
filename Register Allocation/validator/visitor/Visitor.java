//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * All void visitors must implement this interface.
 */

public interface Visitor {

   //
   // void Auto class visitors
   //

   public void visit(NodeList n);
   public void visit(NodeListOptional n);
   public void visit(NodeOptional n);
   public void visit(NodeSequence n);
   public void visit(NodeToken n);

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> <REGLIMIT>
    * f1 -> <TOP_IMPORT>
    * f2 -> MainClass()
    * f3 -> ( TypeDeclaration() )*
    * f4 -> <EOF>
    */
   public void visit(Goal n);

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
    * f15 -> <ALLOCA>
    * f16 -> ( Statement() )*
    * f17 -> "}"
    * f18 -> "}"
    */
   public void visit(MainClass n);

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public void visit(TypeDeclaration n);

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public void visit(ClassDeclaration n);

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
   public void visit(ClassExtendsDeclaration n);

   /**
    * f0 -> <REGISTER_OBJECT>
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public void visit(VarDeclaration n);

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> <ALLOCA>
    * f9 -> ( Statement() )*
    * f10 -> "return"
    * f11 -> ( Identifier() | RegisterLoad() | MemoryLoad() )
    * f12 -> ";"
    * f13 -> "}"
    */
   public void visit(MethodDeclaration n);

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public void visit(FormalParameterList n);

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public void visit(FormalParameter n);

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public void visit(FormalParameterRest n);

   /**
    * f0 -> "("
    * f1 -> "("
    * f2 -> Type()
    * f3 -> ")"
    * f4 -> Identifier()
    * f5 -> ")"
    */
   public void visit(RegisterLoad n);

   /**
    * f0 -> "("
    * f1 -> "("
    * f2 -> Type()
    * f3 -> ")"
    * f4 -> <MEM_LOAD>
    * f5 -> "("
    * f6 -> <INTEGER_LITERAL>
    * f7 -> ")"
    * f8 -> ")"
    */
   public void visit(MemoryLoad n);

   /**
    * f0 -> "store"
    * f1 -> "("
    * f2 -> <INTEGER_LITERAL>
    * f3 -> ","
    * f4 -> Expression()
    * f5 -> ")"
    * f6 -> ";"
    */
   public void visit(MemoryStoreStatement n);

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | FloatType()
    *       | Identifier()
    */
   public void visit(Type n);

   /**
    * f0 -> <INT_TYPE>
    * f1 -> "["
    * f2 -> "]"
    */
   public void visit(ArrayType n);

   /**
    * f0 -> <FLOAT_TYPE>
    */
   public void visit(FloatType n);

   /**
    * f0 -> <BOOLEAN_TYPE>
    */
   public void visit(BooleanType n);

   /**
    * f0 -> <INT_TYPE>
    */
   public void visit(IntegerType n);

   /**
    * f0 -> Block()
    *       | MemoryStoreStatement()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | FieldAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    *       | LivenessQueryStatement()
    */
   public void visit(Statement n);

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public void visit(Block n);

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public void visit(AssignmentStatement n);

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Identifier()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Identifier()
    * f6 -> ";"
    */
   public void visit(ArrayAssignmentStatement n);

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "="
    * f4 -> Identifier()
    * f5 -> ";"
    */
   public void visit(FieldAssignmentStatement n);

   /**
    * f0 -> IfthenElseStatement()
    *       | IfthenStatement()
    */
   public void visit(IfStatement n);

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> ( Identifier() | RegisterLoad() | MemoryLoad() )
    * f3 -> ")"
    * f4 -> Statement()
    */
   public void visit(IfthenStatement n);

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> ( Identifier() | RegisterLoad() | MemoryLoad() )
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public void visit(IfthenElseStatement n);

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> ( Identifier() | RegisterLoad() | MemoryLoad() )
    * f3 -> ")"
    * f4 -> Statement()
    */
   public void visit(WhileStatement n);

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> ( Identifier() | RegisterLoad() | MemoryLoad() )
    * f3 -> ")"
    * f4 -> ";"
    */
   public void visit(PrintStatement n);

   /**
    * f0 -> <SCOMMENT1>
    * f1 -> <LIVENESSQUERY>
    * f2 -> <SCOMMENT2>
    */
   public void visit(LivenessQueryStatement n);

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
   public void visit(Expression n);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public void visit(AndExpression n);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "||"
    * f2 -> PrimaryExpression()
    */
   public void visit(OrExpression n);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<="
    * f2 -> PrimaryExpression()
    */
   public void visit(CompareExpression n);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "!="
    * f2 -> PrimaryExpression()
    */
   public void visit(neqExpression n);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public void visit(PlusExpression n);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public void visit(MinusExpression n);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public void visit(TimesExpression n);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "/"
    * f2 -> PrimaryExpression()
    */
   public void visit(DivExpression n);

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Identifier()
    * f3 -> "]"
    */
   public void visit(ArrayLookup n);

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "length"
    */
   public void visit(ArrayLength n);

   /**
    * f0 -> ( Identifier() | RegisterLoad() | MemoryLoad() )
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ArgList() )?
    * f5 -> ")"
    */
   public void visit(MessageSend n);

   /**
    * f0 -> ( Identifier() | RegisterLoad() | MemoryLoad() )
    * f1 -> ( ArgRest() )*
    */
   public void visit(ArgList n);

   /**
    * f0 -> ","
    * f1 -> ( Identifier() | RegisterLoad() | MemoryLoad() )
    */
   public void visit(ArgRest n);

   /**
    * f0 -> RegisterLoad()
    *       | MemoryLoad()
    *       | IntegerLiteral()
    *       | FloatLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    */
   public void visit(PrimaryExpression n);

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public void visit(IntegerLiteral n);

   /**
    * f0 -> <FLOAT_LITERAL>
    */
   public void visit(FloatLiteral n);

   /**
    * f0 -> "true"
    */
   public void visit(TrueLiteral n);

   /**
    * f0 -> "false"
    */
   public void visit(FalseLiteral n);

   /**
    * f0 -> <IDENTIFIER>
    */
   public void visit(Identifier n);

   /**
    * f0 -> "this"
    */
   public void visit(ThisExpression n);

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Identifier()
    * f4 -> "]"
    */
   public void visit(ArrayAllocationExpression n);

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public void visit(AllocationExpression n);

   /**
    * f0 -> "!"
    * f1 -> ( Identifier() | RegisterLoad() | MemoryLoad() )
    */
   public void visit(NotExpression n);

}

