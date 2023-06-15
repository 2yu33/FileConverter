package com.wu.ming.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenPluginVO {

    /**
     * 插件名称
     */
    private String pluginName;

    /**
     * 插件类型
     */
    private String pluginType;

}
