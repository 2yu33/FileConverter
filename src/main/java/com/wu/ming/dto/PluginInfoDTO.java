package com.wu.ming.dto;

import com.wu.ming.DataConvertService;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PluginInfoDTO {

    /**
     * 插件状态
     */
    private Boolean isOpen;

    /**
     * 插件名称
     */
    private String pluginName;

    /**
     * 插件主体
     */
    private DataConvertService dataConvertService;

}
