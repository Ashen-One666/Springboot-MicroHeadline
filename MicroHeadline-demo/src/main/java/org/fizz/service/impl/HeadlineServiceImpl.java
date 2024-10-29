package org.fizz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fizz.pojo.Headline;
import org.fizz.pojo.vo.PortalVo;
import org.fizz.service.HeadlineService;
import org.fizz.mapper.HeadlineMapper;
import org.fizz.utils.JwtHelper;
import org.fizz.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @author fez0618
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-10-28 15:19:23
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private HeadlineMapper headlineMapper;

    @Autowired
    private JwtHelper jwtHelper;


    /*
     接口文档要求：
     {
       "code":"200",
       "message":"success"
       "data":{
          "pageInfo":{
            "pageData":[
              {
                "hid":"1",                     // 新闻id
                "title":"title1 ... ...",   // 新闻标题
                "type":"1",                    // 新闻所属类别编号
                "pageViews":"40",              // 新闻浏览量
                "pastHours":"3" ,              // 发布时间已过小时数
                "publisher":"1"                // 发布用户ID
              },
              {
                "hid":"2",                     // 新闻id
                "title":"title2 ... ...",   // 新闻标题
                "type":"2",                    // 新闻所属类别编号
                "pageViews":"60",              // 新闻浏览量
                "pastHours":"5",               // 发布时间已过小时数
                "publisher":"2"                // 发布用户ID
              }
            ],
            "pageNum":1,    //页码数
            "pageSize":10,  // 页大小
            "totalPage":20, // 总页数
            "totalSize":200 // 总记录数
            }
          }
        }
     */
    /**
     * 首页数据查询
     *  1. 进行分页数据查询
     *  2. 将分页数据拼接到result中
     * 注意：
     *  1. 查询需要自定义语句 自定义mapper方法 携带分页
     *  2. 返回的结果是 List<Map>
     * @param portalVo
     * @return
     */
    @Override
    public Result findNewsPage(PortalVo portalVo) {

        // IPage中的泛型写需要查询的实体类（如果用map接值就写map）
        IPage<Map> page = new Page<>(portalVo.getPageNum(), portalVo.getPageSize());
        headlineMapper.selectMyPage(page, portalVo);

        // 按接口文档要求，套两层map
        List<Map> records = page.getRecords();
        Map info = new HashMap();
        info.put("pageData", records);
        info.put("pageNum", page.getCurrent());
        info.put("pageSize", page.getSize());
        info.put("totalPage", page.getPages());
        info.put("totalSize", page.getTotal());

        Map pageInfo = new HashMap();
        pageInfo.put("pageInfo", info);

        return Result.ok(pageInfo);
    }

    /**
     * 根据id查询头条详情
     *  1. 查询对应的数据（多表查询 headline & user 表，方法需要自定义）
     *  2. 修改阅读量 + 1 [version乐观锁，需要当前数据对应的版本]
     *  3. 使用map装返回结果
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {

        Map data = headlineMapper.queryDetailMap(hid);
        Map headlineMap = new HashMap();
        headlineMap.put("headline", data);

        // 修改阅读量 + 1
        Headline headline = new Headline();
        headline.setHid((Integer) data.get("hid"));
        headline.setVersion((Integer) data.get("version"));
        // 阅读量 + 1
        headline.setPageViews((Integer) data.get("pageViews") + 1);
        headlineMapper.updateById(headline);

        return Result.ok(headlineMap);
    }

    /**
     * 发布头条（数据插入）
     *  1. 补全数据
     * @param headline
     * @return
     */
    @Override
    public Result publish(Headline headline, String token) {

        // 根据token查询用户id
        int userId = jwtHelper.getUserId(token).intValue();
        // 数据装配
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        headlineMapper.insert(headline);

        return Result.ok(null);
    }

    /**
     * 修改头条数据
     *  1. 根据hid查询数据最新version
     *  2. 修改数据时间为当前时间
     * @param headline
     * @return
     */
    @Override
    public Result updateData(Headline headline) {
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion(); // 乐观锁
        headline.setUpdateTime(new Date());
        headlineMapper.updateById(headline);
        return Result.ok(null);
    }
}




