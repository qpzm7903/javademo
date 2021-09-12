package com.qpzm.anno;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Locale;
import java.util.Set;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-09-12-21:32
 */
@SupportedAnnotationTypes("com.qpzm.anno.Data")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DataAnnotationProcessor extends AbstractProcessor {
    private JavacTrees javacTrees;
    private TreeMaker treeMaker;
    private Names names;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        javacTrees = JavacTrees.instance(context);
        treeMaker = TreeMaker.instance(context);
        names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(Data.class);
        for (Element element : set) {
            JCTree tree = javacTrees.getTree(element);
            tree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    jcClassDecl.defs.stream()
                            .filter(it -> it.getKind().equals(Tree.Kind.VARIABLE))
                            .map(it -> (JCTree.JCVariableDecl) it)
                            .forEach(it -> {
                                jcClassDecl.defs = jcClassDecl.defs.prepend(getGetterMethod(it));
                                jcClassDecl.defs = jcClassDecl.defs.prepend(getSetterMethod(it));
                            });
                    super.visitClassDef(jcClassDecl);
                }
            });
        }

        return true;
    }

    private JCTree getSetterMethod(JCTree.JCVariableDecl jcVariableDecl) {
        JCTree.JCExpressionStatement statement = treeMaker.Exec(
                treeMaker.Assign(
                        treeMaker.Select(
                                treeMaker.Ident(names.fromString("this")),
                                jcVariableDecl.getName()
                        ),
                        treeMaker.Ident(jcVariableDecl.getName())
                )
        );
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        statements.add(statement);

        JCTree.JCVariableDecl param = treeMaker.VarDef(
                treeMaker.Modifiers(Flags.PARAMETER, List.nil()),
                jcVariableDecl.name,
                jcVariableDecl.vartype,
                null
        );
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC);
        Name setMethodName = setMethodName(jcVariableDecl.getName());
        JCTree.JCExpression returnType = treeMaker.Type(new Type.JCVoidType());

        JCTree.JCBlock body = treeMaker.Block(0, statements.toList());
        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();
        List<JCTree.JCVariableDecl> parameters = List.of(param);

        List<JCTree.JCExpression> throwCauses = List.nil();


        return treeMaker.MethodDef(
                modifiers,
                setMethodName,
                returnType,
                methodGenericParams,
                parameters,
                throwCauses,
                body,
                null
        );
    }

    private JCTree getGetterMethod(JCTree.JCVariableDecl jcVariableDecl) {
        JCTree.JCReturn returnStatement = treeMaker.Return(
                treeMaker.Select(
                        treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName()
                )
        );
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        statements.add(returnStatement);
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC);
        JCTree.JCExpression returnType = jcVariableDecl.vartype;
        Name getMethodName = getMethodName(jcVariableDecl.getName());
        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();
        List<JCTree.JCVariableDecl> parameters = List.nil();
        List<JCTree.JCExpression> throwCauses = List.nil();
        JCTree.JCBlock body = treeMaker.Block(0, statements.toList());

        return treeMaker.MethodDef(
                modifiers,
                getMethodName,
                returnType,
                methodGenericParams,
                parameters,
                throwCauses,
                body,
                null
        );
    }


    private Name getMethodName(Name name) {
        String filedName = name.toString();
        return names.fromString("get" + filedName.substring(0, 1).toUpperCase(Locale.ROOT) + filedName.substring(1, name.length()));
    }

    private Name setMethodName(Name name) {
        String filedName = name.toString();
        return names.fromString("set" + filedName.substring(0, 1).toUpperCase(Locale.ROOT) + filedName.substring(1, name.length()));
    }


}
