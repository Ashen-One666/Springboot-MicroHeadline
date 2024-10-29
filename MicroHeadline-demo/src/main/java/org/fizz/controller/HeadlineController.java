package org.fizz.controller;

import org.fizz.pojo.Headline;
import org.fizz.service.HeadlineService;
import org.fizz.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("headline")
@CrossOrigin
public class HeadlineController {

    @Autowired
    private HeadlineService headlineService;

    // 发布头条（必须登录后才能发布，且除了这个请求，其余的所有"headline"下的请求都需要验证登录： 增加拦截器验证登录）
    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline, @RequestHeader String token) {
        Result result = headlineService.publish(headline, token);
        return result;
    }

    // 头条回显
    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid) {
        Headline headline = headlineService.getById(hid); // service层也有crud，可以直接调用
        Map data = new HashMap();
        data.put("headline", headline);
        return Result.ok(data);
    }

    @PostMapping("update")
    public Result update(@RequestBody Headline headline) {
        Result result = headlineService.updateData(headline);
        return result;
    }

    @PostMapping("removeByHid")
    public Result removeByHid(Integer hid) {
        headlineService.removeById(hid); // 逻辑删除
        Result result = Result.ok(null);
        return result;
    }
}
