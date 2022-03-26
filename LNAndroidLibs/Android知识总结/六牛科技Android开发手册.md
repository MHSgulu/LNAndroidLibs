# 六牛科技Android开发手册

## 前言

《六牛科技Android开发手册》作为六牛开发规约重要的一环, 我们的目标是

- 防患未然, 提升质量意识, 降低故障率和维护成本
- 标准统一, 提升协作效率
- 追求卓越的工匠精神, 打磨精品代码

本手册以开发者为中心视角分为Java语言规范（遵循《阿里巴巴Java开发手册》）,Kotlin语言规范, Android 项目结构,Android资源文件命名与使用,Android基本组件,UI与布局,进程/线程/消息通信,文件与数据库,Bitmap/Drawable/动画,安全,其他等十一大部分, 根据约束力强弱, 规约依次分为强制、推荐、参考三大类:

- **[强制]** 必须遵守, 违反本约定或将会引起严重的后果
- **[推荐]** 尽量遵守, 长期遵守有助于系统稳定性和合作效率的提升
- **[参考]** 充分理解, 技术意识的引导, 是个人学习, 团队沟通, 项目合作的方向



## 一. Java语言规范

遵循《阿里巴巴 Java 开发手册》

手册下载地址：https://yq.aliyun.com/articles/69327

## 二. Kotlin语言规范

### 一. 命名格式

#### 包名

包的命名规则：采用小驼峰，当遇到多个词连接的场景，不要使用下划线(_)和连字号(-)。

**推荐**

```
package vn.asiantech.android
```

**不推荐**

```
package Vn.Asiantech.Android
```

#### 类和接口

命名遵守大驼峰规则（首字母大写）。

**推荐**

```undefined
HomeActivity
MainFragment
```

#### 方法

命名遵守小驼峰规则（首字母小写）。

**推荐**

````
setData
getApiNews
````

#### 字段（Fields）

总体来说，字段命名遵守小驼峰规则

**推荐**

```
class MyClass {
  var publicField: Int = 0
  val person = Person()
  private var privateField: Int?
}
```

而伴生对象中的常量定义要遵守规则：**全大写**，单词连接使用下划线。

```
companion object {
  const val THE_ANSWER = 42
}
```

### 二、变量和参数

一句话概括： 小驼峰命名法。
不建议使用单个字符的变量，除非是循环中的临时变量。

#### 其他

首字母缩略词作为完整单词看待，适时选择命名规则。

**推荐**

```
XMLHTTPRequest
URL: String? 
findPostByID
```

### 三. 间距

#### 缩进

缩进使用4个空格，不建议使用tabs - 制表符。

#### 闭包

闭包的缩进使用2个空格

#### 自动换行

新的换行缩进使用4个空格，而不是默认的8个。

#### 换行

- 每行不应超过130个字符，这在Android Studio内可以配置。
- 如果单行代码超过限制需要换行显示，使用, / : / { , = 作为首行的结尾。

#### 垂直间距

方法体之间保留一个空行，这样可以让结构清晰。
方法体内的空行用来分割不同功能，这样如果一个方法内分多个部分，也许需要重构代码了。

#### 冒号(:)

- 类型和父类型之间的冒号前面要保留空格，而变量和类型之间的冒号前没有空格。

**推荐**

```
interface Foo<out T : Any> : Bar {
    fun foo(a: Int): T
}

// 申明变量类型

val firstName: String

```

**不推荐**

```
interface Foo<out T:Any>: Bar {
    fun foo(a : Int):T
}

// 申明变量类型

val firstName:String

```

- 在定义类的时候，在结构体的右括号和类主题的左括号之间保留空格，结构体的每个参数单行显示。



## 三. Android 项目结构

![image-20220312133937927](https://gitee.com/andlin/blog-image/raw/master/image/20220312133946.png)

- [强制] 包名命名: 各级采用小驼峰格式, 第三级命名单词要用英文, 严禁使用中文, 以及首字母的缩写

Java/Kotlin  类

- base: 基类存放的包名
- bean: 
  - 接口返回的映射实体类
  - EventBus使用的Event类
- config:
  - 常用配置文件
  - 基础库的配置文件类
- ui: 存放UI相关的类
  - adapter: 公用的Adapter适配器
  - dialog: 所有的对话框都放在此目录
  - easyUI: 环信相关的类, 存放在此目录
  - viewModel: 一些公用的ViewModel放在此目录
  - page: 所有界面都放在此目录
    - 子文件按照业务模块去创建, 把Activity/Fragment, ViewModel, Adapter写在一起
- utils: 常用的工具类, 放在此目录下
  - ext: 扩展的kt文件放在此目录下
- widget: 自定义的View放在此目录下
- MyApp.kt: Application类放在最外层

res资源目录:

- anim: 存放动画资源
- drawable: 存放shape/selector/.9等位图资源
- layout: 存放布局文件
- Layout-land: 存放平板/横屏布局
- mipmap: 存放切图资源
  - xhdpi
  - xxhdpi
  - xxxhdpi
- values
- values-night: 夜间模式资源
- xml

## 四. Android资源文件命名与使用

1. 【推荐】资源文件需带模块前缀。
2. 【推荐】layout 文件的命名方式。

```
Activity 的 layout 以 module_activity 开头
Fragment 的 layout 以 module_fragment 开头
Dialog 的 layout 以 module_dialog 开头
include 的 layout 以 module_include 开头
Adapter 的 layout 以 module_item 开头
```

3. 【推荐】 drawable/minmap 资源名称以小写单词+下划线的方式命名，根据分辨率不同存放

   在不同的 drawable/minmap 目录下，建议只使用一套,例如 drawable-xhdpi/minmap-xhdpi。采用规则如下:

   ```
   模块名_shape_圆角_背景色_边框颜色_边框粗细.xml
   模块名_shape_圆角_渐变色_边框颜色_边框粗细.xml
   ```

4. 【推荐】anim 资源名称以小写单词+下划线的方式命名，采用以下规则：

   ```
   模块名_逻辑名称_[方向|序号]
   ```

   tween 动画资源 ： 尽可能以通用的动画名称命名，如 module_fade_in ,

   module_fade_out , module_push_down_in (动画+方向)；

   frame 动画资源：尽可能以模 块+功能命名+序号。如：module_loading_grey_001

5. 【推荐】 color 资源使用#AARRGGBB 格式，写入 module_colors.xml 文件中，命

   名格式采用以下规则：

   ```
   module_c_颜色值
   <color name="module_c_33">#333333</color>
   ```

6. 【推荐】dimen 资源以小写单词+下划线方式命名，写入 module_dimens.xml 文件中，

   采用以下规则：

   ```
   模块名_描述信息
   <dimen name="module_horizontal_line_height">1dp</dimen>
   ```

7. 【推荐】style 资源采用小写单词+下划线方式命名，写入 module_styles.xml 文件中，

   采用以下规则：

   

## 五. Android基本组件

## 六. UI与布局

## 七. 进程/线程/消息通信

## 八. 文件与数据库

## 九. Bitmap/Drawable/动画

## 十. 安全

## 十一. 其他



