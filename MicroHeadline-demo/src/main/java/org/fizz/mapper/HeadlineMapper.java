package org.fizz.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.fizz.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.fizz.pojo.vo.PortalVo;

import java.util.Map;

/**
* @author fez0618
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-10-28 15:19:23
* @Entity org.fizz.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {
    // 加了@Param("portalVo")注解，后面就可以通过key去取里面的属性了
    IPage<Map> selectMyPage(IPage page, @Param("portalVo") PortalVo portalVo);

    Map queryDetailMap(Integer hid);
}




