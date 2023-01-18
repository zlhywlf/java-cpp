package zlhywlf.jc;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("zlhywlf.jc.NativeMethod")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class NativeMethodProcessor extends AbstractProcessor {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 获取所有使用了 @NativeMethod 的元素
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(NativeMethod.class);
        elements.forEach(ele -> {
            // @NativeMethod 必须在接口上
            if (ele.getKind() != ElementKind.INTERFACE) {
                throw new RuntimeException(String.format("@NativeMethod 只用于接口! %s 类型是 %s", ele, ele.getKind()));
            }
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("package %s;\n", ele.getEnclosingElement()))
                    .append(ele.getAnnotation(NativeMethod.class).value())
                    .append(String.format("\npublic class %sImpl implements %s{\n", ele.getSimpleName(),
                            ele.getSimpleName()));
            // 生成所有方法
            List<? extends Element> methodElements = ele.getEnclosedElements();
            methodElements.forEach(method -> {
                System.out.println("========================");
                // 只实现返回类型且存在唯一参数类型为 java.lang.String 的成员方法
                if (method.getKind() == ElementKind.METHOD
                        && "(java.lang.String)java.lang.String".equals(method.asType().toString())) {
                    builder.append(String.format("\tpublic native String %s(String param);\n", method.getSimpleName()));
                } else {
                    throw new RuntimeException(String.format(
                            "@NativeMethod 只实现返回类型且存在唯一参数类型为 java.lang.String 的成员方法，%s 不支持的成员定义", method.asType()));
                }
            });
            builder.append("}");
            System.out.println(builder);
            try {
                JavaFileObject source = this.processingEnv.getFiler()
                        .createSourceFile(String.format("%s.%sImpl", ele.getEnclosingElement(), ele.getSimpleName()));
                Writer writer = source.openWriter();
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return true;
    }

}
