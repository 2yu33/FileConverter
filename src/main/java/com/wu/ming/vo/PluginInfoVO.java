package com.wu.ming.vo;

import com.wu.ming.DataConvertService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    /**
     * 插件类型
     */
    private String pluginType;

}
