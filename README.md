# Android开发项目仿淘宝客户端
######注：项目仍处于开发阶段，本项目仅供学习使用，未经本人授权，请勿进行商业用途
***
     一款基于MVP(Model-View-Presenter)设计模式开发的Adroid APP项目
   
***

###功能测试

   
***  
###已经实现的功能
   
- 2016/10/25 添加加入购物车功能、移除购物车中的商品和下单之后更新购物车等功能  
   
- 2016/10/24 添加评论功能的显示、登陆用户的收藏和取消收藏功能、下单页面的显示、以及下单功能的实现

- 2016/10/23 商品的类别显示、分类查询展示、固定数据的搜索(暂时数据固定)

- 2016/10/22 17:49:35 添加用户注销功能、实现头像的上传个更新功能

- 2016/10/20 注册、登陆、加载默认头像、解决ViewPager +Fragment 中Fragment被预加载问题

- 2016/10/19 进行主页、微淘、问大家、购物车、我的淘宝等页面的设计、添加轮播图、资讯滚动条功能

- 2016// 构建基本MVP框架

***
###需要实现的功能

搜索查询显示、和商品详情显示呢
- 个人中心
     添加头像、和收货地址的修改和新增
- 商城功能  
  
***
###已知Bug
-  当用户未登录时,点击购物车,登陆之后,程序闪退
***
###开发过程中遇到的问题(可能导致程序无法运行的bug)及解决方案

- 
  
- 拍照时无法进行图片的裁剪（不断加载）
 
       解决步骤如下：<br>
    1. Activity跳转时图库时的Intent如下 
           `Intent takePhotoIntent = new Intent( "android.media.action.IMAGE_CAPTURE");
            takePhotoIntent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    imageUri);`
    2. 在onActivityResult()方法中调用P层进行处理，相关代码如下

        `//获取图片的路径
       String uri = Environment.getExternalStorageDirectory() + "/icoImage.jpg";
        if(!allSelectedPicture.contains(uri)){
            allSelectedPicture.add(uri);
        }`
        在
     3. ViewPager和Fragment结合使用,Fragment出现被预计载的情况
       解决:
        
     
***   
###项目中使用的主要技术及框架
- 框架
      1. ButterKnife  注解绑定获取控件功能

      2. Picasso   网络和本地图片的加载功能
       
      3. okhttp   网络连接功能
      
- 技术：

##License

Copyright 2016 [mmengchen](https://github.com/mmengchen "mmengchen")

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.