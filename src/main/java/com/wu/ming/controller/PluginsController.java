package com.wu.ming.controller;

import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ResultUtils;
import com.wu.ming.dto.PluginInfoDTO;
import com.wu.ming.plugins.PluginsHandler;
import com.wu.ming.vo.OpenPluginVO;
import com.wu.ming.vo.PluginInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    /**
     * 获取所有插件列表
     */
    @GetMapping("/list/plugins")
    public BaseResponse<List<PluginInfoVO>> getPlugins(){
        List<PluginInfoVO> reList = PluginsHandler.allPluginList.stream().map(o -> {
            return PluginInfoVO.builder()
                    .pluginName(o.getPluginName())
                    .isOpen(o.getIsOpen())
                    .pluginType(o.getDataConvertService().getConvertType())
                    .build();
        }).collect(Collectors.toList());

        return ResultUtils.success(reList);
    }

    /**
     * 获取所有开启的插件的列表
     */
    @GetMapping("/list/openPlugins")
    public BaseResponse<List<OpenPluginVO>> openPlugins(){
        List<OpenPluginVO> reList = PluginsHandler.openPluginsMap.values().stream().map(o -> {
            return OpenPluginVO.builder()
                    .pluginName(o.getPluginName())
                    .pluginType(o.getDataConvertService().getConvertType())
                    .build();
        }).collect(Collectors.toList());

        return ResultUtils.success(reList);
    }


    /**
     * 插件实现数据字符串转换
     * @param input   传入数据类型
     * @param output  传出数据类型
     * @param dataStr 要转换的数据
     * @return        转换结果
     */
    @PostMapping("/plugins/{input}2{output}")
    public BaseResponse<String> pluginsConvert(@PathVariable("input")String input, @PathVariable("output") String output,@RequestBody String dataStr){
        // 通过输入和输出对获取数据
        return ResultUtils.success(pluginsHandler.convert(input, output, dataStr));
    }

    /**
     * 插件实现文件转换
     */
    @PostMapping(value = "/plugins/file/{input}2{output}")
    public ResponseEntity<byte[]> convertXmlToCsv(@PathVariable("input")String input, @PathVariable("output") String output,@RequestPart("file") MultipartFile file) throws IOException {
        return pluginsHandler.fileConvert(input, output, file);
    }

    /**
     * 切换插件转换类型
     */
    @PostMapping("/plugins/open")
    public BaseResponse<String> changePluginInfo(@RequestBody PluginInfoVO pluginInfoVO){
        pluginsHandler.changePluginType(pluginInfoVO);
        return ResultUtils.success("切换成功");
    }

}
