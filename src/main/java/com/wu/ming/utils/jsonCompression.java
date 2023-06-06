package com.wu.ming.utils;

import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

public class jsonCompression {
    public static String compress(String yamlString) {
        Yaml yaml = new Yaml();
        List<Map<String, Object>> yamlData = yaml.load(yamlString);
        return yaml.dump(yamlData).replaceAll("[\n\t ]", "");
    }
}
