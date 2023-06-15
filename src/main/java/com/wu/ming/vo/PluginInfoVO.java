package com.wu.ming.vo;

import com.wu.ming.DataConvertService;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PluginInfoVO {

    /**
     * 插件状态
     */
    private Boolean isOpen;

    /**
     * 插件名称
     */
    private String pluginName;


}
