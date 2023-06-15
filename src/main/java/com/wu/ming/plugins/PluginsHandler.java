package com.wu.ming.plugins;

import com.wu.ming.DataConvertService;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.dto.PluginInfoDTO;
import com.wu.ming.exception.BusinessException;
import com.wu.ming.vo.PluginInfoVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 插件处理器
 */
@Component
public class PluginsHandler {

    @Value("${plugins.path}")
    private String pluginsPath;

    /**
     * 服务转换所有插件
     * a2b:对应的接口
     */
    public static List<PluginInfoDTO> allPluginList = new ArrayList<>();

    /**
     * 已经开启的插件
     */
    public static HashMap<String, PluginInfoDTO> openPluginsMap = new HashMap<>();

    /**
     * 项目启动的时候加载所有插件
     */
    @PostConstruct
    public void loadPlugins() throws Exception {
        // String pluginsDirectoryPath = "F:\\Java_IDEA练习\\FileConverter\\lib";
        File pluginsDirectory = new File(pluginsPath);

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
                String className = null;
                try {
                    className = getClassName();

                    Class<?> clazz = classLoader.loadClass(className);
                    DataConvertService dataConvertService = (DataConvertService) clazz.newInstance();

                    // 将读取到的插件添加到allPluginList中
                    allPluginList.add(PluginInfoDTO.builder()
                            .pluginName(jarName)
                            .dataConvertService(dataConvertService)
                            .isOpen(Boolean.FALSE)
                            .build());
                } catch (Exception e) {
                    System.out.println("获取插件"+jarName+"失败,可能是插件不符合规范");
                    e.printStackTrace();
                }
            }
        }
        System.out.println("----------加载插件完成-----------");
        allPluginList.forEach(System.out::println);
    }

    /**
     * 通过对应的转换类型进行获取
     */
    public String convert(String input, String output, String dataStr){
        String key = getKey(input,output);
        if (openPluginsMap.containsKey(key)){
            return openPluginsMap.get(key).getDataConvertService().dataConvert(dataStr);
        }else {
            return "没有开启此插件";
        }
    }

    /**
     * 对插件装填进行修改
     */
    public void changePluginType(PluginInfoVO pluginInfoVO){
        // 判断是否已经开启了相同的插件
        if (pluginInfoVO.getIsOpen() && openPluginsMap.containsKey(pluginInfoVO.getPluginType().toLowerCase())){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插件启动重复");
        }
        // 对插件状态进行修改
        for (PluginInfoDTO pluginInfoDTO : allPluginList) {
            if (pluginInfoVO.getPluginType().equals(pluginInfoDTO.getDataConvertService().getConvertType())){
                Boolean isOpen = pluginInfoVO.getIsOpen();
                // 修改当前插件的状态
                pluginInfoDTO.setIsOpen(isOpen);
                if (isOpen){
                    // 将插件信息存储到全局map中
                    openPluginsMap.put(pluginInfoDTO.getDataConvertService().getConvertType().toLowerCase(), pluginInfoDTO);
                }else {
                    openPluginsMap.remove(pluginInfoDTO.getDataConvertService().getConvertType().toLowerCase());
                }
                return;
            }
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插件不存在");
    }

    // TODO 修改成扫描jar包中所有实现了DataConvertService接口的类
    public void loadPlugins2() throws Exception {
        // String pluginsDirectoryPath = "F:\\Java_IDEA练习\\FileConverter\\lib";
        File pluginsDirectory = new File(pluginsPath);

        String interfaceName = "com.wu.ming.DataConvertService";
        Class<?> interfaceClass = Class.forName(interfaceName);

        // 获取子目录的所有jar包
        File[] jarFiles = pluginsDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".jar"));
        if (jarFiles != null) {
            List<URL> urls = new ArrayList<>();
            for (File jarFile : jarFiles) {
                URL jarUrl = jarFile.toURI().toURL();
                urls.add(jarUrl);
            }

            URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[0]));



            JarFile jarFile = new JarFile("/path/to/your.jar");
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                    Class<?> loadedClass = classLoader.loadClass(className);

                    // 检查类是否实现了目标接口
                    if (interfaceClass.isAssignableFrom(loadedClass)) {
                        // 类实现了目标接口，进行相应操作...
                    }
                }
            }

            jarFile.close();


        }

    }

    private String getKey(String input, String output){
        String reS = input + "2" + output;
        return reS.toLowerCase();
    }


    private String getClassName() {
        // 根据JAR文件名获取类名的逻辑，根据实际情况自行实现
        // 示例：假设JAR文件名为plugins-yaml2properties-0.0.1-SNAPSHOT.jar，则类名为com.wu.ming.Yaml2PropertiesService
        return "com.wu.ming.DataConvertServiceImpl";
    }



}
