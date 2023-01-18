package zlhywlf.jc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface NativeMethod {
    /**
     * 其他注解
     * 
     * @return String
     */
    String value() default "";
}
