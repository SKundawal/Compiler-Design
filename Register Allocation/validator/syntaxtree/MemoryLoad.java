//
// Generated by JTB 1.3.2
//

package syntaxtree;

/**
 * Grammar production:
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
public class MemoryLoad implements Node {
   public NodeToken f0;
   public NodeToken f1;
   public Type f2;
   public NodeToken f3;
   public NodeToken f4;
   public NodeToken f5;
   public NodeToken f6;
   public NodeToken f7;
   public NodeToken f8;

   public MemoryLoad(NodeToken n0, NodeToken n1, Type n2, NodeToken n3, NodeToken n4, NodeToken n5, NodeToken n6, NodeToken n7, NodeToken n8) {
      f0 = n0;
      f1 = n1;
      f2 = n2;
      f3 = n3;
      f4 = n4;
      f5 = n5;
      f6 = n6;
      f7 = n7;
      f8 = n8;
   }

   public MemoryLoad(Type n0, NodeToken n1) {
      f0 = new NodeToken("(");
      f1 = new NodeToken("(");
      f2 = n0;
      f3 = new NodeToken(")");
      f4 = new NodeToken("load");
      f5 = new NodeToken("(");
      f6 = n1;
      f7 = new NodeToken(")");
      f8 = new NodeToken(")");
   }

   public void accept(visitor.Visitor v) {
      v.visit(this);
   }
   public <R,A> R accept(visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
}

