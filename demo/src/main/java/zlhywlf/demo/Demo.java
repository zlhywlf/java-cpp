package zlhywlf.demo;

import zlhywlf.jc.NativeMethod;

@NativeMethod("@org.springframework.stereotype.Component")
public interface Demo {
    String helloWorld(String param);
}
