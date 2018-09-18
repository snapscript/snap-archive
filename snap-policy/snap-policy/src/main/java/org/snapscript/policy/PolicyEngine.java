package org.snapscript.policy;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.snapscript.common.store.ClassPathStore;
import org.snapscript.common.store.Store;
import org.snapscript.compile.StoreContext;
import org.snapscript.compile.assemble.Assembler;
import org.snapscript.compile.assemble.ModelScopeBuilder;
import org.snapscript.compile.assemble.OperationAssembler;
import org.snapscript.core.Context;
import org.snapscript.core.scope.EmptyModel;
import org.snapscript.core.scope.Model;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.extend.FileExtension;
import org.snapscript.parse.SyntaxCompiler;
import org.snapscript.parse.SyntaxNode;
import org.snapscript.parse.SyntaxParser;

public interface PolicyEngine {

   public static <T> T execute(File file) throws Exception {
      FileExtension extension = new FileExtension();
      String source = extension.readText(file);
      Model model = new EmptyModel();
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      Executor executor = new ScheduledThreadPoolExecutor(5);
      Assembler builder = new OperationAssembler(context, executor, "policy.instruction");
      SyntaxCompiler compiler = new SyntaxCompiler("policy.grammar");
      ModelScopeBuilder merger = new ModelScopeBuilder(context);
      Scope scope = merger.create(model, "default");
      SyntaxParser analyzer = compiler.compile();

      analyzer.parse("/policy.txt", "90", "attribute");
      analyzer.parse("/policy.txt", "true", "attribute");
      analyzer.parse("/policy.txt", "null", "attribute");
      analyzer.parse("/policy.txt", "age < 90", "condition");
      analyzer.parse("/policy.txt", "(age < 90)", "condition-group");
      analyzer.parse("/policy.txt", "(age < 90)", "condition-list");
      analyzer.parse("/policy.txt", "(age > 18 and age < 90)", "condition-list");
      analyzer.parse("/policy.txt", "x != y",  "condition");
      analyzer.parse("/policy.txt", "(x != y)",  "condition-list");
      analyzer.parse("/policy.txt", "(x != y)",  "condition-group");
      analyzer.parse("/policy.txt", "(x != y)",  "condition-operand");
      analyzer.parse("/policy.txt", "x != y",  "condition-operand");
      analyzer.parse("/policy.txt", "(x != y and y != x)",  "condition-list");
      analyzer.parse("/policy.txt", "x",  "attribute");
      analyzer.parse("/policy.txt", "x.y",  "attribute");
      analyzer.parse("/policy.txt", "Action.READ",  "attribute");
      analyzer.parse("/policy.txt", "Action.READ on Resource.CONTENT",  "permission");      
      analyzer.parse("/policy.txt", "allow Action.READ on Resource.CONTENT where discipline in ['x'] and country !in ['north korea'];", "allow-statement");
      analyzer.parse("/policy.txt", "where x in ['y']",  "where-clause");
      
      SyntaxNode token = analyzer.parse(file.getCanonicalPath(), source, "policy");
      System.err.println(SyntaxPrinter.print(analyzer, source, "policy")); // Evaluating the
                                                      // following
      return null;
   }
   
   public static void main(String[] args) throws Exception {
      PolicyEngine.execute(new File("C:\\Work\\development\\snapscript\\snap-develop\\snap-studio\\work\\demo\\misc\\assets\\test.policy"));
   }
}
