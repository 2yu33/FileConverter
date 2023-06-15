package com.wu.ming.controller;

import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ResultUtils;
import com.wu.ming.dto.PluginInfoDTO;
import com.wu.ming.plugins.PluginsHandler;
import com.wu.ming.vo.PluginInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 插件处理器
 */
@RestController
public class PluginsController {

    @Autowired
    private PluginsHandler pluginsHandler;

    @GetMapping("/list/plugins")
    public BaseResponse<List<PluginInfoVO>> getPlugins(){
        List<PluginInfoVO> reList = PluginsHandler.allPluginMap.values().stream().map(o -> {
            return PluginInfoVO.builder()
                    .pluginName(o.getPluginName())
                    .isOpen(o.getIsOpen())
                    .build();
        }).collect(Collectors.toList());

        return ResultUtils.success(reList);
    }

    @PostMapping("/plugins/{input}2{output}")
    public BaseResponse<String> pluginsConvert(@PathVariable("input")String input, @PathVariable("output") String output, String dataStr){
        // 通过输入和输出对获取数据
        return ResultUtils.success(pluginsHandler.convert(input, output, dataStr));
    }

}
