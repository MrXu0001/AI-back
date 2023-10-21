package com.aicanvas.aicanvas.service.impl;

import com.aicanvas.aicanvas.domain.CaptchaEntity;
import com.aicanvas.aicanvas.mapper.CaptchaDao;
import com.aicanvas.aicanvas.service.CaptchaService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * 验证码
 */
@Service("CaptchaService")
public class CaptchaServiceImpl extends ServiceImpl<CaptchaDao, CaptchaEntity> implements CaptchaService {
    @Autowired
    private Producer producer;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            throw new RuntimeException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();

        CaptchaEntity captchaEntity = new CaptchaEntity();
        captchaEntity.setUuid(uuid);
        captchaEntity.setCode(code);
        //5分钟后过期
        DateTime dateTime = new DateTime(new Date());
        captchaEntity.setExpireTime(dateTime.plusMinutes(5).toDate());
        this.save(captchaEntity);

        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        CaptchaEntity captchaEntity = this.getOne(new QueryWrapper<CaptchaEntity>().eq("uuid", uuid));
        if(captchaEntity == null){
            return false;
        }

        //删除验证码
        this.removeById(uuid);

        if(captchaEntity.getCode().equalsIgnoreCase(code) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis()){
            return true;
        }

        return false;
    }
}