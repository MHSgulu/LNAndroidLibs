# 更新日志

## V 0.0.4
> 为 Bundle 添加 Bundle.putExtras  扩展方法, 实现给 Bundle 批量添加各种类型的数据
> 为 Date 添加 Date.age 扩展属性, 实现根据Date获取年龄  
> 为 ArrayList 添加 ArrayList.deleteIf 扩展方法, 实现根据给定的条件删除列表的数据
> 为 TextView 添加 TextView.isBold 动态设置字体加粗
> 为 TextView 添加 TextView.textColor 动态设置字体颜色, 可直接设置颜色值资源
> 为 EditText 添加 EditText.txt 扩展属性, 可以快速获取和设置输入框的内容
> 添加 HttpUtils 实现网络请求工具类, 支持设置请求方法(get/post/put/delete),支持Kotlin Flow方式以及RxJava方式的请求, 以及文件下载的请求, 支持进度监听
> 重写 BaseViewModelExt.kt 里面的请求扩展, 使其不依赖于RxHttp, 采用HttpUtils类进行请求
## V 0.0.3.5
> 修复请求失败时, code和msg顺序颠倒的问题
## V 0.0.3.4
> 修改onLoading回调字段错误的问题
> 修改网络请求打包运行闪退问题
## V 0.0.3.3
> 升级 SmartRefreshLayout 的依赖库版本到2.0.5, 修复类找不到的问题