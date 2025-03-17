package com.nbug.module.infra.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.utils.RestTemplateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 后台管理员表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("infra/wechat")
@Tag(name = "管理后台 - 企业微信消息推送")
public class WeChatWxWorkPushController {

    private static String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=";

    @Resource
    private RestTemplateUtil restTemplateUtil;


    /**
     * 新增后台管理员表
     * @param message string message
     * @author Mr.Zhang
     * @since 2020-04-13
     */
    @Operation(summary = "gitlab钩子")
    @RequestMapping(value = "/gitlab", method = RequestMethod.POST)
    @Parameters({
            @Parameter(name="message", description="推送消息内容"),
            @Parameter(name="token", description="企业微信群token"),
    })
    public CommonResult<Object> gitlab(@RequestBody String message,
                                       @RequestParam(name = "token", required = true) String token){

        Map<String, Object> map = new HashMap<>();
        map.put("msgtype", "text");

        Map<String, Object> text = new HashMap<>();

        //需要@的人
        ArrayList<Object> people = new ArrayList<>();
        people.add("@all");
        text.put("mentioned_list", people);

        //gitlab 动作标签
        JSONObject jsonObject = JSONObject.parseObject(message);
        String action = jsonObject.getString("object_kind");
        String content;
        switch(action){
            case "push":
                content = jsonObject.getJSONArray("commits").getJSONObject(0).getJSONObject("author").getString("name") + " " +
                        action + " " +
                        jsonObject.getString("ref").replace("refs/heads/", "") +
                        "\n 备注：\n" +
                        jsonObject.getJSONArray("commits").getJSONObject(0).getString("message");
                break;
            case "tag_push":
                content = jsonObject.getString("user_name") + " " +
                        action + " " +
                        jsonObject.getString("ref").replace("refs/heads/", "") +
                        "\n 备注：\n" +
                        jsonObject.getJSONArray("commits").getJSONObject(0).getString("message");
                break;
            case "note":
                String author = "未知用户";
                if(jsonObject.containsKey("commit")){
                    author = jsonObject.getJSONObject("commit").getJSONObject("author").getString("name");
                }

                if(jsonObject.containsKey("last_commit")){
                    author = jsonObject.getJSONObject("last_commit").getJSONObject("author").getString("name");
                }

                content = author +
                        " 提交代码到 " +
                        jsonObject.getJSONObject("project").getString("default_branch") +
                        "\n 备注：\n" +
                        jsonObject.getJSONObject("object_attributes").getString("note");
                break;
            case "merge_request":
                content = jsonObject.getJSONObject("object_attributes").getJSONObject("assignee").getString("name") + " " +
                        "合并代码， 从 " +
                        jsonObject.getJSONObject("object_attributes").getString("source_branch") + " ---> " +
                        jsonObject.getJSONObject("object_attributes").getString("target_branch") +
                        "\n 备注：\n" +
                        jsonObject.getJSONObject("object_attributes").getJSONObject("last_commit").getString("message");
                break;
            default:
                content = "gitlab 项目有更新";
        }

        text.put("content", content);
        map.put("text", text);
        String result = restTemplateUtil.postMapData(url + token, map);
        return CommonResult.success(JSONObject.parseObject(result));
    }

    /**
     * 新增后台管理员表
     * @param message string message
     * @author Mr.Zhang
     * @since 2020-04-13
     */
    @Operation(summary = "消息推送")
    @RequestMapping(value = "/push", method = RequestMethod.GET)
    @Parameters({
            @Parameter(name="message", description="推送消息内容"),
            @Parameter(name="token", description="企业微信群token"),
    })
    public CommonResult<Object> push(@RequestParam(name = "message") String message,
                                    @RequestParam(name = "token") String token){

        Map<String, Object> map = new HashMap<>();
        map.put("msgtype", "text");

        Map<String, Object> text = new HashMap<>();

        //需要@的人
        ArrayList<Object> people = new ArrayList<>();
        people.add("@all");
        text.put("mentioned_list", people);
        text.put("content", message);
        map.put("text", text);
        String result = restTemplateUtil.postMapData(url + token, map);
        return CommonResult.success(JSONObject.parseObject(result));
    }
}



