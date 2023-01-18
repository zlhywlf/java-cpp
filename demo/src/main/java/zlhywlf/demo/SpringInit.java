package zlhywlf.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class SpringInit implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
        if (System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0) {
            try {
                SpringInit.load("libdemo.so");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void load(String path) throws IOException {
        File inf = new File(path);
        if (inf.exists() && inf.isFile()) {
            System.load(path);
            return;
        }
        System.load(saveLib(path));
    }

    public static String saveLib(String path) throws IOException {
        String fileName = path.substring(path.lastIndexOf('/') + 1);
        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("The fileName should not be empty!");
        }
        String property = System.getProperty("java.library.path").split(":")[0];
        File tmp = new File(property);
        tmp.deleteOnExit();
        if (!tmp.exists()) {
            tmp.mkdirs();
        }
        File so = new File(property + "/" + fileName);
        byte[] buffer = new byte[1024];
        int len;
        FileOutputStream out = new FileOutputStream(so);
        InputStream in = SpringInit.class.getResourceAsStream("/" + path);
        if (in != null) {
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } else {
            out.close();
            throw new NullPointerException("Wrong path:" + path);
        }
        return so.getAbsolutePath();
    }

}
