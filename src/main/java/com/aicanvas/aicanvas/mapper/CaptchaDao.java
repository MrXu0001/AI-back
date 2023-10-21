package com.aicanvas.aicanvas.mapper;

import com.aicanvas.aicanvas.domain.CaptchaEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface CaptchaDao extends BaseMapper<CaptchaEntity> {

}