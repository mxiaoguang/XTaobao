package com.xiaoguang.xtaobao.config;

/**
 * Created by 11655 on 2016/10/22.
 */

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * @author Jack Tony
 * @brief
 * @date 2015/4/26
 */
public class SimplePhoto {

    /**
     * 图片的uri，其实就是地址。eg:/storage/sdcard0/Tencent/QQ_Images/-1935240a504f548c.jpg
     */
    public Uri uri;

    /**
     * 图像需要旋转的角度。方向不正确的图像可以根据这个进行旋转操作
     */
    public int degree;

    /**
     * 图像的bitmap
     */
    public Bitmap bitmap;
}