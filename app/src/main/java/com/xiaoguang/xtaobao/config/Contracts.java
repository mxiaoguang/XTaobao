package com.xiaoguang.xtaobao.config;

import android.content.Intent;

/**
 * 默认配置的属性值
 * Created by 11655 on 2016/10/18.
 */

public interface Contracts {
    //设置一个标记,true 为debug状态
    boolean DEBUG = true;

    //天猫URL
    String TIANMAO_URL = "https://www.tmall.com/?locate=icon-1&" +
            "spm=a215s.7406091.icons.1&scm=2027.1.2.1000&force=m&wh_from=tbc\n";

    //聚划算URL
    String JUHUASUAN_URL = "https://jhs.m.taobao.com/m/index.htm?" +
            "locate=icon-2&spm=a215s.7406091.icons.2&scm=2027.1.2.1001";

    //天猫国际
    String TIANMAO_GUOJI_URL = "https://www.tmall.com/wh/tmall/import/act/mp-pc-2015?" +
            "locate=icon-3&spm=a215s.7406091.icons.3&scm=2027.4.1.16&wh_isTBHP=true";

    //外卖
    String WAIMAI = "https://h5.m.taobao.com/app/waimai/index.html?" +
            "locate=icon-4&spm=a215s.7406091.icons.4&scm=2027.1.2.10021#/?_k=9egjwj";

    //天猫超市
    String TIANMAO_SUPERMARKET = "https://chaoshi.m.tmall.com/?" +
            "locate=icon-5&spm=a215s.7406091.icons.5&scm=2027.1.2.1007&_ig=shoutao";

    //充值中心
    String CHONGZHI_CENTER = "https://h5.m.taobao.com/app/cz/cost.html?" +
            "locate=icon-6&spm=a215s.7406091.icons.6&scm=2027.1.2.1003&clientSource=stcz_1";

    //阿里旅行
    String ALI_TRAVEL = "https://h5.m.taobao.com/app/triphome/pages/home/index.html?" +
            "locate=icon-7&spm=a215s.7406091.icons.7&scm=2027.1.2.1008&ttid=12mtb0000155&" +
            "_projVer=0.1.43&alitripCalloutFail=true";

    //领金币
    String LING_MONEY = "https://h5.m.taobao.com/app/tjb/www/index3.html?" +
            "locate=icon-8&spm=a215s.7406091.icons.8&scm=2027.1.2.1004";

    //到家
    String DAO_JIA = "https://h5.m.taobao.com/app/daojia/www/index/index.html?" +
            "locate=icon-9&spm=a215s.7406091.icons.9&scm=2027.1.2.1006";

    //分类
    String TYPE = "https://h5.m.taobao.com/s-nx-catmap/index/index.html?" +
            "locate=icon-10&spm=a215s.7406091.icons.10&scm=2027.1.2.1005&_s_nx_orange_key=catmap";

    //Bmob云APP_Key
    String BMOB_APP_KEY = "4704a65c576098ab3ab4392300f60e11";

    //微信分享key
    String WX_APP_ID = "wxb9b8271eea5609b0";

    //默认头像的地址
    String DEFALT_HEAD_URL = "http://bmob-cdn-6590.b0.upaiyun.com/2016/10/16/22901ee0406f7af280b56a1b5d555f58.png";

    //照相
    int TAKE_PHOTO = 1000;

    //从图库中选择照片
    int CHOOSE_PIC = 2000;

    //裁剪
    int CROP_PHOTO  = 3000;

    //照相的action
    String TAKE_PHOTO_ACTION  = "android.media.action.IMAGE_CAPTURE";

    //图库选择图片的action
    String CHOOSER_PIC_ACTION = Intent.ACTION_PICK;

    /**
     * 此为支付插件的官方最新版本号,请在更新时留意更新说明
     */
    int PLUGINVERSION = 7;
    /**
     * 响应失败
     */
    int RESULT_FAIL = 404 ;

    /**
     * 响应成功
     */
    int RESULT_SUCCESS = 500 ;

    /**
     * 响应的其他情况
     */
    int RESULT_UN = 600;
}
