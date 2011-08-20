package de.peeeq.wurstscript.ast;

import java.io.IOException;

import katja.common.KatjaTuple;
import katja.common.KatjaTupleImpl;

public interface OpLessEq extends de.peeeq.wurstscript.ast.OpBinary, KatjaTuple {

    //----- methods of OpLessEq -----

    public Object get(int i);
    public int size();
    public de.peeeq.wurstscript.ast.OpLessEq replace(int pos, Object term);
    public <CT, E extends Throwable> CT Switch(de.peeeq.wurstscript.ast.OpBinary.Switch<CT, E> switchClass) throws E;

    //----- nested classes of OpLessEq -----

    static interface VisitorType<E extends Throwable> {

        //----- methods of VisitorType<E extends Throwable> -----

        public void visit(de.peeeq.wurstscript.ast.OpLessEq term) throws E;
    }

    public static abstract class Visitor<E extends Throwable> implements de.peeeq.wurstscript.ast.OpLessEq.VisitorType<E> {
    }

    static class Impl extends KatjaTupleImpl implements de.peeeq.wurstscript.ast.OpLessEq {

        //----- methods of Impl -----

        Impl() {

        }

        public Object get(int i) {
            int ith = i;

            if(ith < 0) ith += 0;

            switch(ith) {
                default:
                    if(ith < 0) {
                        throw new IllegalArgumentException("get on sort OpLessEq invoked with negative parameter "+i);
                    } else {
                        throw new IllegalArgumentException("get on sort OpLessEq invoked with too large parameter "+i+", sort has only 0 components");
                    }
            }
        }

        public int size() {
            return 0;
        }

        public de.peeeq.wurstscript.ast.OpLessEq replace(int pos, Object term) {
            if(pos < 0)
                throw new IllegalArgumentException("replace on sort OpLessEq invoked with negative parameter "+pos);
            if(pos >= 0)
                throw new IllegalArgumentException("replace on sort OpLessEq invoked with too large parameter "+pos+", sort has only 0 components");


            return (de.peeeq.wurstscript.ast.OpLessEq) AST.unique(new de.peeeq.wurstscript.ast.OpLessEq.Impl(
            ));
        }

        public <CT, E extends Throwable> CT Switch(de.peeeq.wurstscript.ast.OpBinary.Switch<CT, E> switchClass) throws E {
            return switchClass.CaseOpLessEq(this);
        }

        public Appendable toJavaCode(Appendable builder) throws IOException {
            builder.append("AST.OpLessEq");
            builder.append("( ");
            builder.append(" )");

            return builder;
        }

        public Appendable toString(Appendable builder) throws IOException {
            builder.append("OpLessEq");
            builder.append("( ");
            builder.append(" )");

            return builder;
        }

        public final String sortName() {
            return "OpLessEq";
        }
    }
}
