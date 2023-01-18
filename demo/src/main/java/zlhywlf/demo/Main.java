package zlhywlf.demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("zlhywlf.demo")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        Demo bean = context.getBean(Demo.class);
        System.out.println(bean.helloWorld("java message!"));
        context.close();
    }
}
