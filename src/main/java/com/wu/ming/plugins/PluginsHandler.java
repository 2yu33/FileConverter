package com.wu.ming.plugins;

import com.wu.ming.DataConvertService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 插件处理器
 */
@Component
public class PluginsHandler {

    @Value("${plugins.path}")
    private String pluginsPath;

    /**
     * 服务转换插件
     * a2b:对应的接口
     */
    public static HashMap<String, DataConvertService> dataConvertServiceHashMap = new HashMap<>();

    /**
     * 启动项目自动加载注解
     */
    // @PostConstruct
    public void loadPlugins() throws Exception {
        URL url = new URL("file:F:\\Java_IDEA练习\\FileConverter\\lib\\plugins-yaml2properties-0.0.1-SNAPSHOT.jar");
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});

        // 获取类中的数据
        Class<?> clazz = classLoader.loadClass("com.wu.ming.Yaml2PropertiesService");

        // 获取类的字段
        DataConvertService instance = (DataConvertService) clazz.newInstance();

        System.out.println("============================");
        System.out.println(instance.dataConvert("测试"));
        System.out.println("============================");

    }

    @PostConstruct
    public void loadPlugins2() throws Exception {
        String pluginsDirectoryPath = "F:\\Java_IDEA练习\\FileConverter\\lib";
        File pluginsDirectory = new File(pluginsDirectoryPath);

        // 获取子目录的所有jar包
        File[] jarFiles = pluginsDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".jar"));
        if (jarFiles != null) {
            List<URL> urls = new ArrayList<>();
            for (File jarFile : jarFiles) {
                URL jarUrl = jarFile.toURI().toURL();
                urls.add(jarUrl);
            }

            URL[] urlArray = urls.toArray(new URL[0]);
            URLClassLoader classLoader = new URLClassLoader(urlArray);

            for (File jarFile : jarFiles) {
                String jarName = jarFile.getName();
                String className = getClassName(jarName); // 根据JAR文件名获取类名

                Class<?> clazz = classLoader.loadClass(className);
                DataConvertService dataConvertService = (DataConvertService) clazz.newInstance();

                System.out.println("============================");
                System.out.println(dataConvertService.dataConvert("测试"));
                System.out.println("============================");
            }
        }
    }

    private String getClassName(String jarName) {
        // 根据JAR文件名获取类名的逻辑，根据实际情况自行实现
        // 示例：假设JAR文件名为plugins-yaml2properties-0.0.1-SNAPSHOT.jar，则类名为com.wu.ming.Yaml2PropertiesService
        return "com.wu.ming.Yaml2PropertiesService";
    }


}
