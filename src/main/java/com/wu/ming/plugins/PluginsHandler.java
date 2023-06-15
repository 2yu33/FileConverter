package com.wu.ming.plugins;

import com.wu.ming.DataConvertService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

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
    @PostConstruct
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

}
