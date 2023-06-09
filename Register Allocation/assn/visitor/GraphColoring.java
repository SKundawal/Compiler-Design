//
// Generated by JTB 1.3.2
//

package visitor;

import syntaxtree.*;

import java.util.*;
import static visitor.Constants.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GraphColoring implements GJNoArguVisitor<String> {
   /**
    * Live Variable Analysis result set to generate Interference Graph using which Graph Coloring Algorithm
    * for performing Register Allocation is done
    */
   HashMap<Node, Set<String>> _ResultMap;
   public GraphColoring(HashMap<Node, Set<String>> resultMap) {
      _ResultMap = resultMap;
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

   String _CurrentClass;
   String _CurrentMethod;
   Integer _RegisterLimit = null;
   SymbolTable _SymbolTable = new SymbolTable();
   InterferenceGraph _InterferenceGraph = new InterferenceGraph();
   RegisterAllocation _RegisterAllocation = new RegisterAllocation();
   /**
    * @return -> Object of SymbolTable
    */
   public SymbolTable getSymbolTable(){
      return _SymbolTable;
   }


   /**
    * f0 -> ( <REGLIMIT> )?
    * f1 -> MainClass()
    * f2 -> ( TypeDeclaration() )*
    * f3 -> <EOF>
    */
   public String visit(Goal n) {
      String _ret=null;
      /**
       * Available number of registers
       */
      _RegisterLimit = Integer.parseInt((n.f0.tokenImage).replace(ASTERISK, BLANK).replace(FORWARDSLASH, BLANK));
      n.f1.accept(this);
      n.f2.accept(this);
      _RegisterAllocation.init(_RegisterLimit, _InterferenceGraph, _SymbolTable);
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
      String _ret=null;
      n.f0.accept(this);

      /**
       * Class Declaration
       */
      _CurrentClass = n.f1.accept(this);
      _SymbolTable.ClassDeclaration(_CurrentClass, null);
      _InterferenceGraph.GraphClassDeclaration(_CurrentClass);

      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);

      /**
       * Method Declaration
       */
      String _methodType = n.f5.tokenImage;
      _CurrentMethod = n.f6.tokenImage;
      _SymbolTable.MethodDeclaration(_CurrentClass, _CurrentMethod, _methodType);
      _InterferenceGraph.GraphMethodDeclaration(_CurrentClass, _CurrentMethod);

      n.f7.accept(this);

      /**
       * Argument Declaration
      */
      n.f8.accept(this);
      n.f9.accept(this);
      n.f10.accept(this);
      _SymbolTable.AddMethodArgs(_CurrentClass, _CurrentMethod, "String[]", n.f11.accept(this), true);

      n.f12.accept(this);
      n.f13.accept(this);

      /**
       * Variable Declaration
       */
      Vector<Node> _VarDeclarations = n.f14.nodes;
      for(Node _vars : _VarDeclarations){
         String[] variables = _vars.accept(this).split(HYPHEN);
         _InterferenceGraph.AddVertices(_CurrentClass, _CurrentMethod, variables[1]);
         _SymbolTable.AddMethodVariables(_CurrentClass, _CurrentMethod, variables[0], variables[1], false);
      }

      /**
       * Statement Declaration
       */
      Vector<Node> _StatementDeclarations = n.f15.nodes;
      for(Node _statement : _StatementDeclarations){
         _statement.accept(this);
      }

      n.f16.accept(this);
      n.f17.accept(this);
      return _ret;
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
      String _ret=null;
      n.f0.accept(this);

      /**
       * Class Declaration
      */
      _CurrentClass = n.f1.accept(this);
      _SymbolTable.ClassDeclaration(_CurrentClass, null);
      _InterferenceGraph.GraphClassDeclaration(_CurrentClass);

      n.f2.accept(this);

      /**
       * Class Field Declaration
       */
      Vector<Node> _FieldDeclaration = n.f3.nodes;
      for(Node _field : _FieldDeclaration){
         String[] fields = _field.accept(this).split(HYPHEN);
         _SymbolTable.AddClassFields(_CurrentClass, fields[0], fields[1]);
      }

      /**
       * Method Declaration
       */
      Vector<Node> _MethodDeclaration = n.f4.nodes;
      for(Node _methods : _MethodDeclaration){
         _methods.accept(this);
      }

      n.f5.accept(this);
      return _ret;
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
      String _ret=null;
      n.f0.accept(this);

      /**
       * Class Declaration
       */
      _CurrentClass = n.f1.accept(this);
      n.f2.accept(this);
      String _parentClass = n.f3.accept(this);
      _SymbolTable.ClassDeclaration(_CurrentClass, _parentClass);
      _InterferenceGraph.GraphClassDeclaration(_CurrentClass);

      /**
       * Class Field Declaration
      */
      Vector<Node> _FieldDeclaration = n.f5.nodes;
      for(Node _field : _FieldDeclaration){
         String[] fields = _field.accept(this).split(HYPHEN);
         _SymbolTable.AddClassFields(_CurrentClass, fields[0], fields[1]);
      }

      /**
       * Method Declaration
      */
      Vector<Node> _MethodDeclaration = n.f6.nodes;
      for(Node _methods : _MethodDeclaration){
         _methods.accept(this);
      }

      n.f7.accept(this);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public String visit(VarDeclaration n) {
      String varType = n.f0.accept(this);
      String varName = n.f1.accept(this);
      n.f2.accept(this);
      return varType + "-" + varName;
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
      String _ret=null;
      n.f0.accept(this);

      /**
       * Method Info
       */
      String methodType = n.f1.accept(this);
      _CurrentMethod = n.f2.accept(this);
      _SymbolTable.MethodDeclaration(_CurrentClass, _CurrentMethod, methodType);
      _InterferenceGraph.GraphMethodDeclaration(_CurrentClass, _CurrentMethod);

      n.f3.accept(this);

      /**
       * Formal Parameters
       */
      if(n.f4.present()){
         n.f4.accept(this);
      }

      n.f5.accept(this);
      n.f6.accept(this);

      /**
       * Traversing the Variable Declaration node and inserting them in -->
       *    1.Symbol Table :- To use them in the next pass for generating the code
       *    2.Interference Graph :- For constructing the graph for Register Allocation Coloring Algorithm
      */
      Vector<Node> _VarDeclarations = n.f7.nodes;
      for(Node _vars : _VarDeclarations){
         String[] variables = _vars.accept(this).split(HYPHEN);
         _InterferenceGraph.AddVertices(_CurrentClass, _CurrentMethod, variables[1]);
         _SymbolTable.AddMethodVariables(_CurrentClass, _CurrentMethod, variables[0], variables[1], false);
      }

      /**
       * Statement Declaration
      */
      Vector<Node> _StatementDeclarations = n.f8.nodes;
      for(Node _statement : _StatementDeclarations){
         _statement.accept(this);
      }

      /**
       * Method return statement
       * THINK if it can have more than one live variables --> Can't as per grammar, it can only have an identifier
       */
      n.f9.accept(this);

      n.f11.accept(this);
      n.f12.accept(this);
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public String visit(FormalParameterList n) {
      String _ret=null;

      /**
       * Traversing formal parameters node of methods
       */
      n.f0.accept(this);
      Vector<Node> _FormalParameters = n.f1.nodes;
      for(Node _formalParameter : _FormalParameters){
         _formalParameter.accept(this);
      }

      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public String visit(FormalParameter n) {
      String _ret=null;

      /**
       * Inserting formal parameters of method in Symbol Table
       */
      String formalParameterType = n.f0.accept(this);
      String formalParameterName = n.f1.accept(this);
      _SymbolTable.AddMethodArgs(_CurrentClass, _CurrentMethod, formalParameterType, formalParameterName, true);
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
      return n.f0.accept(this);
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public String visit(ArrayType n) {
      return "int[]";
   }

   /**
    * f0 -> "float"
    */
   public String visit(FloatType n) {
      return "float";
   }

   /**
    * f0 -> "boolean"
    */
   public String visit(BooleanType n) {
      return "boolean";
   }

   /**
    * f0 -> "int"
    */
   public String visit(IntegerType n) {
      return "int";
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
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public String visit(AssignmentStatement n) {
      String _ret=null;
      n.f0.accept(this);
      String identifier = n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);

      /**
       * Adding edges to the Interference Graph
       * Cases to check:
       *    1.If the edge is already present in the Interference Graph
       *    2.If the variable is not local variable, then we need not draw any edge between them
       */
      if(_ResultMap.containsKey(n)){
         /**
          * Converting the Live-Variable Set into an Array for easy traversal
          */
         String[] _drawEdges = _ResultMap.get(n).toArray(new String[0]);
         for(int i = 0; i < _drawEdges.length; i++){
            /**
             * Source vertex
             */
            String src = _drawEdges[i];
            for(int j = i + 1; j < _drawEdges.length; j++){
               /**
                * Destination vertex
                */
               String des = _drawEdges[j];
               /**
                * If both the vertices are local variables, then we proceed forward and draw an edge between them;
                * otherwise, we don't
                */
               if(_InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, src) && _InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, des)){
                  /**
                   * If an edge between both the vertices exists, then no need to draw the edge
                   */
                  if(!_InterferenceGraph.EdgeExists(_CurrentClass, _CurrentMethod, src, des)){
                     _InterferenceGraph.AddEdge(_CurrentClass, _CurrentMethod, src, des);
                  }
               }
            }
         }
      }

      return _ret;
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
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);
      n.f6.accept(this);

      /**
       * Adding edges to the Interference Graph
       * Cases to check:
       *    1.If the edge is already present in the Interference Graph
       *    2.If the variable is not local variable, then we need not draw any edge between them
       */
      if(_ResultMap.containsKey(n)){
         /**
          * Converting the Live-Variable Set into an Array for easy traversal
          */
         String[] _drawEdges = _ResultMap.get(n).toArray(new String[0]);
         for(int i = 0; i < _drawEdges.length; i++){
            /**
             * Source vertex
             */
            String src = _drawEdges[i];
            for(int j = i + 1; j < _drawEdges.length; j++){
               /**
                * Destination vertex
                */
               String des = _drawEdges[j];
               /**
                * If both the vertices are local variables, then we proceed forward and draw an edge between them;
                * otherwise, we don't
                */
               if(_InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, src) && _InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, des)){
                  /**
                   * If an edge between both the vertices exists, then no need to draw the edge
                   */
                  if(!_InterferenceGraph.EdgeExists(_CurrentClass, _CurrentMethod, src, des)){
                     _InterferenceGraph.AddEdge(_CurrentClass, _CurrentMethod, src, des);
                  }
               }
            }
         }
      }

      return _ret;
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
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);

      /**
       * Adding edges to the Interference Graph
       * Cases to check:
       *    1.If the edge is already present in the Interference Graph
       *    2.If the variable is not local variable, then we need not draw any edge between them
       */
      if(_ResultMap.containsKey(n)){
         /**
          * Converting the Live-Variable Set into an Array for easy traversal
          */
         String[] _drawEdges = _ResultMap.get(n).toArray(new String[0]);
         for(int i = 0; i < _drawEdges.length; i++){
            /**
             * Source vertex
             */
            String src = _drawEdges[i];
            for(int j = i + 1; j < _drawEdges.length; j++){
               /**
                * Destination vertex
                */
               String des = _drawEdges[j];
               /**
                * If both the vertices are local variables, then we proceed forward and draw an edge between them;
                * otherwise, we don't
                */
               if(_InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, src) && _InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, des)){
                  /**
                   * If an edge between both the vertices exists, then no need to draw the edge
                   */
                  if(!_InterferenceGraph.EdgeExists(_CurrentClass, _CurrentMethod, src, des)){
                     _InterferenceGraph.AddEdge(_CurrentClass, _CurrentMethod, src, des);
                  }
               }
            }
         }
      }
      return _ret;
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
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);

      /**
       * Adding edges to the Interference Graph
       * Cases to check:
       *    1.If the edge is already present in the Interference Graph
       *    2.If the variable is not local variable, then we need not draw any edge between them
       */
      if(_ResultMap.containsKey(n)){
         /**
          * Converting the Live-Variable Set into an Array for easy traversal
          */
         String[] _drawEdges = _ResultMap.get(n).toArray(new String[0]);
         for(int i = 0; i < _drawEdges.length; i++){
            /**
             * Source vertex
             */
            String src = _drawEdges[i];
            for(int j = i + 1; j < _drawEdges.length; j++){
               /**
                * Destination vertex
                */
               String des = _drawEdges[j];
               /**
                * If both the vertices are local variables, then we proceed forward and draw an edge between them;
                * otherwise, we don't
                */
               if(_InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, src) && _InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, des)){
                  /**
                   * If an edge between both the vertices exists, then no need to draw the edge
                   */
                  if(!_InterferenceGraph.EdgeExists(_CurrentClass, _CurrentMethod, src, des)){
                     _InterferenceGraph.AddEdge(_CurrentClass, _CurrentMethod, src, des);
                  }
               }
            }
         }
      }
      return _ret;
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
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);
      n.f6.accept(this);

      /**
       * Adding edges to the Interference Graph
       * Cases to check:
       *    1.If the edge is already present in the Interference Graph
       *    2.If the variable is not local variable, then we need not draw any edge between them
       */
      if(_ResultMap.containsKey(n)){
         /**
          * Converting the Live-Variable Set into an Array for easy traversal
          */
         String[] _drawEdges = _ResultMap.get(n).toArray(new String[0]);
         for(int i = 0; i < _drawEdges.length; i++){
            /**
             * Source vertex
             */
            String src = _drawEdges[i];
            for(int j = i + 1; j < _drawEdges.length; j++){
               /**
                * Destination vertex
                */
               String des = _drawEdges[j];
               /**
                * If both the vertices are local variables, then we proceed forward and draw an edge between them;
                * otherwise, we don't
                */
               if(_InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, src) && _InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, des)){
                  /**
                   * If an edge between both the vertices exists, then no need to draw the edge
                   */
                  if(!_InterferenceGraph.EdgeExists(_CurrentClass, _CurrentMethod, src, des)){
                     _InterferenceGraph.AddEdge(_CurrentClass, _CurrentMethod, src, des);
                  }
               }
            }
         }
      }

      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public String visit(WhileStatement n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);

      /**
       * Adding edges to the Interference Graph
       * Cases to check:
       *    1.If the edge is already present in the Interference Graph
       *    2.If the variable is not local variable, then we need not draw any edge between them
       */
      if(_ResultMap.containsKey(n)){
         /**
          * Converting the Live-Variable Set into an Array for easy traversal
          */
         String[] _drawEdges = _ResultMap.get(n).toArray(new String[0]);
         for(int i = 0; i < _drawEdges.length; i++){
            /**
             * Source vertex
             */
            String src = _drawEdges[i];
            for(int j = i + 1; j < _drawEdges.length; j++){
               /**
                * Destination vertex
                */
               String des = _drawEdges[j];
               /**
                * If both the vertices are local variables, then we proceed forward and draw an edge between them;
                * otherwise, we don't
                */
               if(_InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, src) && _InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, des)){
                  /**
                   * If an edge between both the vertices exists, then no need to draw the edge
                   */
                  if(!_InterferenceGraph.EdgeExists(_CurrentClass, _CurrentMethod, src, des)){
                     _InterferenceGraph.AddEdge(_CurrentClass, _CurrentMethod, src, des);
                  }
               }
            }
         }
      }

      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> ";"
    */
   public String visit(PrintStatement n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);

      /**
       * Adding edges to the Interference Graph
       * Cases to check:
       *    1.If the edge is already present in the Interference Graph
       *    2.If the variable is not local variable, then we need not draw any edge between them
      */
      if(_ResultMap.containsKey(n)){
         /**
          * Converting the Live-Variable Set into an Array for easy traversal
          */
         String[] _drawEdges = _ResultMap.get(n).toArray(new String[0]);
         for(int i = 0; i < _drawEdges.length; i++){
            /**
             * Source vertex
             */
            String src = _drawEdges[i];
            for(int j = i + 1; j < _drawEdges.length; j++){
               /**
                * Destination vertex
                */
               String des = _drawEdges[j];
               /**
                * If both the vertices are local variables, then we proceed forward and draw an edge between them;
                * otherwise, we don't
                */
               if(_InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, src) && _InterferenceGraph.vertexExists(_CurrentClass, _CurrentMethod, des)){
                  /**
                   * If an edge between both the vertices exists, then no need to draw the edge
                   */
                  if(!_InterferenceGraph.EdgeExists(_CurrentClass, _CurrentMethod, src, des)){
                     _InterferenceGraph.AddEdge(_CurrentClass, _CurrentMethod, src, des);
                  }
               }
            }
         }
      }
      return _ret;
   }

   /**
    * f0 -> <SCOMMENT1>
    * f1 -> <LIVENESSQUERY>
    * f2 -> <SCOMMENT2>
    */
   public String visit(LivenessQueryStatement n) {
      String _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
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
      return n.f0.accept(this);
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public String visit(IntegerLiteral n) {
      return n.f0.tokenImage;
   }

   /**
    * f0 -> <FLOAT_LITERAL>
    */
   public String visit(FloatLiteral n) {
      return n.f0.tokenImage;
   }

   /**
    * f0 -> "true"
    */
   public String visit(TrueLiteral n) {
      return n.f0.tokenImage;
   }

   /**
    * f0 -> "false"
    */
   public String visit(FalseLiteral n) {
      return n.f0.tokenImage;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public String visit(Identifier n) {
      return n.f0.tokenImage;
   }

   /**
    * f0 -> "this"
    */
   public String visit(ThisExpression n) {
      return n.f0.accept(this);
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
