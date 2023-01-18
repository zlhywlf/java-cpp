package zlhywlf.jc;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class Main {
    public static void main(String[] args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, new String[] {
                "-d", "./jc/target", "jc/src/test/java/zlhywlf/jc/Demo.java", "jc/src/test/java/zlhywlf/jc/Demo2.java"
        });
    }

}
