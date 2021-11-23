package com.twelvet;

import com.twelvet.framework.utils.image.QrCodeUtils;
import org.apache.commons.codec.Charsets;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 工具测试
 */
public class UtilsTest {

    @Test
    public void util() {
        // 生成 二维码
        QrCodeUtils.form("twelvet")
                .size(512) // 默认 512，可以不设置
                .backGroundColor(Color.WHITE) // 默认白色，可以不设置
                .foreGroundColor(Color.BLACK) // 默认黑色，可以不设置
                .imageFormat("png") // 默认 png，可以不设置
                .deleteMargin(true) // 删除白边，默认为 true，可以不设置
                .toFile("F:\\github\\twelvet\\1.png"); // 写出，同类方法有 toImage、toStream、toBytes

        // 二维码读取
        String text = QrCodeUtils.read("F:\\github\\twelvet\\1.png");
        System.out.println(text);
    }

}
