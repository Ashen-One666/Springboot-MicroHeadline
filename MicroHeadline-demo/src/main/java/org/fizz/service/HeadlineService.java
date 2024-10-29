package org.fizz.service;

import org.fizz.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import org.fizz.pojo.vo.PortalVo;
import org.fizz.utils.Result;

/**
* @author fez0618
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-10-28 15:19:23
*/
public interface HeadlineService extends IService<Headline> {

    /**
     * 首页数据查询
     * @param portalVo
     * @return
     */
    Result findNewsPage(PortalVo portalVo);

    /**
     * 根据id查询头条详情
     * @param hid
     * @return
     */
    Result showHeadlineDetail(Integer hid);

    /**
     * 发布头条
     * @param headline
     * @return
     */
    Result publish(Headline headline, String token);

    /**
     * 修改头条数据
     * @param headline
     * @return
     */
    Result updateData(Headline headline);
}
