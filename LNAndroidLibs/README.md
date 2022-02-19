#  LNAndroidLibs

## 必备知识点

### 1. [Kotlin基础与进阶](https://book.kotlincn.net/)

### 2. [Kotlin协程与Flow](https://book.kotlincn.net/text/coroutines-overview.html)

### 3. ViewModel与LiveData

[LiveData 概览  | Android 开发者  | Android Developers (google.cn)](https://developer.android.google.cn/topic/libraries/architecture/livedata)

[ViewModel 概览  | Android 开发者  | Android Developers (google.cn)](https://developer.android.google.cn/topic/libraries/architecture/viewmodel)

### 4. ViewBinding

[视图绑定  | Android 开发者  | Android Developers (google.cn)](https://developer.android.google.cn/topic/libraries/view-binding)

### 5. RxHttp网络请求库的基本使用

[rxhttp/README_zh.md at master · liujingxing/rxhttp (github.com)](https://github.com/liujingxing/rxhttp/blob/master/README_zh.md)

[RxHttp + Flow 三步搞定任意请求 - 掘金 (juejin.cn)](https://juejin.cn/post/7017604875764629540)

[RxHttp 让你眼前一亮的Http请求框架 - 掘金 (juejin.cn)](https://juejin.cn/post/6844904016380428302)

[RxHttp ，比Retrofit 更优雅的协程体验 - 掘金 (juejin.cn)](https://juejin.cn/post/6844904100090347528)

[RxHttp 完美适配Android 10/11 上传/下载/进度监听 - 掘金 (juejin.cn)](https://juejin.cn/post/6884986439587594247)

[RxHttp 全网Http缓存最优解 - 掘金 (juejin.cn)](https://juejin.cn/post/6844904029219192845)

[Android 史上最优雅的实现文件上传、下载及进度的监听 - 掘金 (juejin.cn)](https://juejin.cn/post/6844903839036882957)

[RxHttp 一条链发送请求之强大的数据解析功能（二） - 掘金 (juejin.cn)](https://juejin.cn/post/6844903830136553485)

[RxHttp 一条链发送请求之强大的Param类（三） - 掘金 (juejin.cn)](https://juejin.cn/post/6844903831931715597)

[RxHttp 一条链发送请求之注解处理器 Generated API - 掘金 (juejin.cn)](https://juejin.cn/post/6844903831935926280)

[Android OkHttp 史上最优雅的设置baseUrl - 掘金 (juejin.cn)](https://juejin.cn/post/6844903847698104333)

[RxLife 史上最优雅的管理RxJava生命周期 - 掘金 (juejin.cn)](https://juejin.cn/post/6844903858796249096)

[30秒上手新一代Http请求神器RxHttp - 掘金 (juejin.cn)](https://juejin.cn/post/6844903862344613901)

[RxHttp 优雅的实现请求串行与并行 - 掘金 (juejin.cn)](https://juejin.cn/post/6844903870074732551)

[可怕！RxHttp2.0重大更新！协程发请求，原来如此简单 - 掘金 (juejin.cn)](https://juejin.cn/post/6844904135293141000)

[RxHttp 2000+star，协程请求，仅需三步 - 掘金 (juejin.cn)](https://juejin.cn/post/6856550856796897287)

[实战：5分钟搞懂OkHttp断点上传 - 掘金 (juejin.cn)](https://juejin.cn/post/6986413030032539684)

### 6. RxJava3的基本用法(了解)

[Rxjava3文档级教程一： 介绍和基本使用 - 掘金 (juejin.cn)](https://juejin.cn/post/7020665574682263560)

## 1. 准备工作

### 1.1 启用viewBinding

在主工程app的build.gradle的android{}中加入:

```
viewBinding {
    enabled = true
}
```

### 1.2 依赖Library

从远程依赖:

在根目录的settings.gradle中加入

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url "https://jitpack.io" }
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        maven {
            url 'https://maven.aliyun.com/repository/public'
        }
        maven {
        		credentials {
                username '606eaa398e4139b4d75018a8'
                password 'm3DOt91WxhP_'
            }
            url 'https://packages.aliyun.com/maven/repository/2092914-release-ZCzD1E/'
        }
}
```

在主项目app的build.gradle中依赖

```
dependencies {
	....
    implementation 'com.lnkj.libs:LNAndroidLibs:0.0.2'
}
```

或者

从git地址: http://118.190.21.60:3000/Android/LNAndroidLibs.git下载例子程序, 在主项目app的build.gradle中依赖例子程序中的LNAndroidLibs:

```
dependencies {	
    ...
    implementation project(':LNAndroidLibs')
}
```

### 1.3 开启kapt

```
plugins {
    ....
    id 'kotlin-kapt'
}
```

### 1.4 添加常用三方库

配置方法超限:

```
android{
	defaultConfig {
		....
		multiDexEnabled true
	}
}
```

添加常用三方库

```
implementation 'androidx.core:core-ktx:1.6.0'
implementation 'androidx.appcompat:appcompat:1.3.1'
implementation 'com.google.android.material:material:1.4.0'
implementation 'androidx.constraintlayout:constraintlayout:2.0.4'

// 方法超限  分包
implementation 'androidx.multidex:multidex:2.0.1'

//滚动选择器 https://github.com/Bigkoo/Android-PickerView
implementation 'com.contrarywind:Android-PickerView:4.1.9'

//多图选择 https://github.com/LuckSiege/PictureSelector
implementation 'io.github.lucksiege:pictureselector:v2.7.3-rc06'

//常用的TabLayout替换的三方库 https://github.com/li-xiaojun/FlycoTabLayout
implementation 'com.github.li-xiaojun:FlycoTabLayout:2.0.6'

implementation 'com.google.android.flexbox:flexbox:3.0.0'
```

以上是常用的三方库, 视实际情况进行增删

### 1.5 配置AndroidManifest

添加权限:

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

其他权限视实际情况添加

### 1.6 配置文件

创建config包, 创建请求配置类:  HttpConfigImpl

```
class HttpConfigImpl: HttpConfig {
    
    // 网络请求的baseUrl地址
    override val baseUrl: String
        get() = ""
    
    // 网络请求返回的code
    override val code: String
        get() = "code"
    // 网络请求返回的data
    override val data: String
        get() = "data"
    //网络请求返回的msg
    override val msg: String
        get() = "msg"
    // 默认的错误code
    override val errorCode: Int
        get() = 0
    // 默认成功code
    override val successCode: Int
        get() = 1

    override fun handlerNetworkError(code: Int) {
        // 根据特殊的错误code, 做对应的动作, 例如token过期, 进行登录操作
    }

    // 传递公共请求参数
    override fun httpCommonParams(): Map<String, Any> {
        return emptyMap()
    }

    // 传递公共请求头
    override fun httpRequestHeader(): Map<String, String> {
        return emptyMap()
    }
}
```

配置Application:

```
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        MVVMLibs.init(this, httpConfig = HttpConfigImpl())
        // 网络初始化
        RxHttpManager.init(this)
    }

}
```

```
<application
    android:name=".MyApp"
    // 配置访问http
    android:networkSecurityConfig="@xml/network_security_config"
    ....
    >
</application>
```

### 1.7 配置屏幕适配

在AndroidManifest.xml中添加:

```
<application>
			<meta-data
    android:name="android.max_aspect"
    android:value="2.4" />
<!--适配华为（huawei）刘海屏-->
<meta-data
    android:name="android.notch_support"
    android:value="true" />
<!--适配小米（xiaomi）刘海屏-->
<meta-data
    android:name="notch.config"
    android:value="portrait|landscape" />
    <!--效果图的宽度-->
<meta-data
    android:name="design_width_in_dp"
    android:value="375" />
    <!--效果图的高度-->
<meta-data
    android:name="design_height_in_dp"
    android:value="812" />
</application>
```

### 1.8 创建定制化基类

#### 1.8.1 创建Activity的基类, BaseVMActivity.kt

```
abstract class BaseVMActivity<VM: BaseViewModel, VB: ViewBinding>: BaseActivity<VM, VB>() {

    companion object {
        fun isLogin(work: ()->Unit){
            if(AccountUtils.isLogin()){
                work.invoke()
            }else{
                context.startPage<LoginActivity>()
            }
        }
    }

    var page = 1
    var isShowing = true

    override fun initImmersionBar() {
        immersionBar {
            statusBarDarkFont(true)
            statusBarColor(R.color.white)
            titleBarMarginTop(binding.root)
        }
    }

    fun isLogin(work: ()->Unit){
        if(AccountUtils.isLogin()){
            work.invoke()
        }else{
            startPage<LoginActivity>()
        }
    }

    override fun showLoading(msg: String) {
        if(isShowing) {
            super.showLoading(msg)
        }
    }

    override fun dismissLoading() {
        if (isShowing) {
            super.dismissLoading()
        }
    }

}
```

#### 1.8.2 创建Fragment的基类, BaseVMFragment.kt

```
abstract class BaseVMFragment<VM: BaseViewModel, VB: ViewBinding>: BaseFragment<VM, VB>() {

    var page = 1
    var isShowing = true

    fun isLogin(work: ()->Unit){
        if(AccountUtils.isLogin()){
            work.invoke()
        }else{
            startPage<LoginActivity>()
        }
    }

    override fun showLoading(msg: String) {
        if(isShowing) {
            super.showLoading(msg)
        }
    }

    override fun dismissLoading() {
        if (isShowing) {
            super.dismissLoading()
        }
    }

}
```

## 2.快速上手

### 2.1 创建一个基础头部View布局view_topbar.xml

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:background="@color/white"
    >

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="44dp">

        <ImageView
            android:id="@+id/ivBack"
            android:layout_width="24dp"
            android:layout_height="24dp"
            android:layout_marginStart="15dp"
            android:src="@mipmap/com_icon_back_b"
            android:layout_gravity="center_vertical"/>

        <TextView
            android:id="@+id/tvTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:gravity="center"
            android:singleLine="true"
            android:layout_marginHorizontal="54dp"
            android:ellipsize="marquee"
            android:textColor="@color/c_33"
            android:textSize="18sp"
            android:textStyle="bold"
            tools:text="标题" />

    </FrameLayout>
</LinearLayout>
```

### 2.2 创建第一个Activity, 我们以登录界面为例

LoginViewModel:

```
class LoginViewModel : BaseViewModel() {

    /**
     * 发送短信验证码
     */
    val sendSmsData = StatefulMutableLiveData<Boolean>()
    fun sendSms(vararg params: Pair<String, Any?>) {
        rxLifeScope.launch {
            okRequest("api/Sms/send", *params,
                onStart = {
                    sendSmsData.value = StateData.Loading
                },
                onError = { msg, code ->
                    sendSmsData.value = StateData.Error(code, msg)
                }, onSuccess = {
                    sendSmsData.value = StateData.Success(true)
                })
        }
    }

    /**
     * 手机号登录
     */
    val loginData = StatefulMutableLiveData<UserBean>()
    fun login(vararg params: Pair<String, Any?>) {
        rxLifeScope.launch {
            request<UserBean>("api/User/mobilelogin", *params,
                onStart = {
                    loginData.value = StateData.Loading
                },
                onError = { msg, code ->
                    loginData.value = StateData.Error(code, msg)
                }, onSuccess = {
                    loginData.value = StateData.Success(it)
                })
        }
    }

}
```

activity_login.xml:

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical">

   	<include
        android:id="@+id/appBar"
        layout="@layout/view_topbar" />

    <androidx.core.widget.NestedScrollView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        >

        <androidx.appcompat.widget.LinearLayoutCompat
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            >

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:layout_marginTop="70dp"
                android:layout_marginHorizontal="43dp"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                >

                <ImageView
                    android:layout_width="22dp"
                    android:layout_height="22dp"
                    android:src="@mipmap/login_icon_phone"
                    />

                <EditText
                    android:id="@+id/etMobile"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight="1"
                    android:background="@null"
                    android:paddingHorizontal="17dp"
                    android:hint="请输入手机号"
                    android:textColor="@color/c_33"
                    android:textColorHint="@color/c_99"
                    android:textSize="15sp"
                    android:inputType="phone"
                    android:imeOptions="actionNext"
                    />

            </LinearLayout>

            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="@color/c_f6f8fc"
                android:layout_marginHorizontal="43dp"
                />

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:layout_marginTop="27dp"
                android:layout_marginHorizontal="43dp"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                >

                <ImageView
                    android:layout_width="22dp"
                    android:layout_height="22dp"
                    android:src="@mipmap/login_icon_yzm"
                    />

                <EditText
                    android:id="@+id/etCode"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight="1"
                    android:background="@null"
                    android:paddingHorizontal="17dp"
                    android:hint="请输入验证码"
                    android:textColor="@color/c_33"
                    android:textColorHint="@color/c_99"
                    android:textSize="15sp"
                    android:inputType="number"
                    android:imeOptions="actionDone"
                    />

                <TextView
                    android:id="@+id/tvSendCode"
                    android:layout_width="wrap_content"
                    android:layout_height="match_parent"
                    android:text="获取验证码"
                    android:gravity="center"
                    android:textColor="@color/c_134b95"
                    android:textSize="15sp"
                    />

            </LinearLayout>

            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="@color/c_f6f8fc"
                android:layout_marginHorizontal="43dp"
                />

            <com.noober.background.view.BLButton
                android:id="@+id/btnLogin"
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:layout_marginTop="56dp"
                android:layout_marginHorizontal="43dp"
                app:bl_corners_radius="43dp"
                app:bl_solid_color="@color/c_134b95"
                android:text="登录"
                android:textColor="@color/white"
                android:textSize="16sp"
                />

        </androidx.appcompat.widget.LinearLayoutCompat>

    </androidx.core.widget.NestedScrollView>

</LinearLayout>
```



LoginActivity:

```
class LoginActivity : BaseVMActivity<LoginViewModel, ActivityLoginBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

				binding.appBar.ivBack.clickWithTrigger { finish() }
        binding.appBar.tvTitle.text = "登录"

        binding.tvSendCode.clickWithTrigger {
            val phone = binding.etMobile.text.toString().trim()
            if(phone.isEmpty()){
                toast("请输入手机号")
                return@clickWithTrigger
            }

            if(!RegexUtils.isMobileSimple(phone)){
                toast("请输入有效手机号")
                return@clickWithTrigger
            }
            binding.tvSendCode.isEnabled = false
            vm.sendSms("mobile" to phone, "event" to "mobilelogin")
        }

        binding.btnLogin.clickWithTrigger {
            val phone = binding.etMobile.text.toString().trim()
            val code = binding.etCode.text.toString().trim()
            if(phone.isEmpty()){
                toast("请输入手机号")
                return@clickWithTrigger
            }

            if(!RegexUtils.isMobileSimple(phone)){
                toast("请输入有效手机号")
                return@clickWithTrigger
            }

            if(code.isEmpty()){
                toast("请输入验证码")
                return@clickWithTrigger
            }
            vm.login("mobile" to phone, "captcha" to code)
        }

        binding.llWechatLogin.clickWithTrigger {
            startPage<BindPhoneActivity>()
        }

    }

    override fun initData() {
    }

    override fun startObserve() {

        vm.sendSmsData.observeState(this){
            onLoading = {
                showLoading()
            }
            onError = {code, msg ->
                dismissLoading()
                toast(msg)
            }
            onSuccess = {
                dismissLoading()
                CountDownWorker(this@PhoneLoginActivity, onChange = {
                    binding.tvSendCode.text = "${it}S"
                }){
                    binding.tvSendCode.text = "重新获取"
                    binding.tvSendCode.isEnabled = true
                }.start()
            }
        }

        vm.loginData.observeState(this){
            onLoading = {
                showLoading()
            }
            onError = {code, msg ->
                dismissLoading()
                toast(msg)
            }
            onSuccess = {
                dismissLoading()
                AccountUtils.saveUser(it!!)
                AccountUtils.saveToken(it.token)
                if(it.nickname.isEmpty() || it.avatar.isEmpty()){
                    startPage<BasicUserInfoActivity>()
                }
                finish()
            }
        }

    }

}
```

### 2.3 第一个列表界面

ViewModel:

```
class BookListViewModel : BaseViewModel() {

    /**
     * 获取往期书单列表
     */
    val getAllBookListData = StatefulMutableLiveData<MutableList<HomeBean.Books>>()
    fun getAllBookList(vararg params: Pair<String, Any?>) {
        rxLifeScope.launch {
            request<MutableList<HomeBean.Books>>("api/BookList/getAllBookList", *params,
                onStart = {
                    getAllBookListData.value = StateData.Loading
                },
                onError = { msg, code ->
                    getAllBookListData.value = StateData.Error(code, msg)
                }, onSuccess = {
                    getAllBookListData.value = StateData.Success(it)
                })
        }
    }

}
```

layout:

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <include 
        layout="@layout/view_topbar"
        android:id="@+id/appBar"/>

    <View
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="@color/c_ee"
        />

    <com.scwang.smart.refresh.layout.SmartRefreshLayout
        android:id="@+id/refreshLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            />

    </com.scwang.smart.refresh.layout.SmartRefreshLayout>

</LinearLayout>
```

Adapter布局:

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_marginTop="16dp">

    <androidx.appcompat.widget.LinearLayoutCompat
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        >

        <androidx.appcompat.widget.LinearLayoutCompat
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:layout_marginStart="16dp"
            android:orientation="vertical"
            >

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:id="@+id/tvTitle"
                android:text="触摸屏使用技术与工程应用-自从化技术丛书"
                android:textColor="@color/c_33"
                android:textStyle="bold"
                android:textSize="15sp"
                android:maxLines="2"
                android:ellipsize="end"
                />

            <TextView
                android:id="@+id/tvContent"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="《电工实战：操作技能必备…"
                android:maxLines="1"
                android:ellipsize="end"
                android:textColor="@color/c_a9"
                android:textSize="12sp"
                android:layout_marginTop="4dp"
                />

            <TextView
                android:id="@+id/tvBookNum"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="共12本"
                android:layout_marginTop="4dp"
                android:textColor="@color/c_7d"
                android:textSize="12sp"
                />

        </androidx.appcompat.widget.LinearLayoutCompat>

        <com.makeramen.roundedimageview.RoundedImageView
            android:id="@+id/rivImage"
            android:layout_width="160dp"
            android:layout_height="90dp"
            app:riv_corner_radius="4dp"
            android:layout_marginStart="10dp"
            android:layout_marginEnd="16dp"
            android:scaleType="centerCrop"
            />

    </androidx.appcompat.widget.LinearLayoutCompat>

    <View
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="@color/c_eff2f3"
        android:layout_marginHorizontal="16dp"
        />

</LinearLayout>
```

Adapter:

```
class BookListAdapter(data: MutableList<Any>) : BaseBindingQuickAdapter<Any, AdapterBookListBinding>(-1,data) {
    init {
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
    }
    override fun convert(holder: BaseBindingHolder, item: Any) {
       holder.getViewBinding<AdapterBookListBinding>().apply {
           
       }
    }
}
```



Activity:

```
class BookListActivity : BaseVMActivity<BookListViewModel, ActivityBookListBinding>() {

    private val data = arrayListOf<HomeBean.Books>()
    private val adapter = BookListAdapter(data)

    override fun initView(savedInstanceState: Bundle?) {
        binding.appBar.ivBack.clickWithTrigger { finish() }
        binding.appBar.tvTitle.text = "往期书单"

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClick { view, position ->
            val item = adapter.getItem(position)
            BookListDetailsActivity.launch(this, item.id)
        }

        binding.refreshLayout.apply {
            setOnRefreshListener {
                page = 1
                isShowing = false
                vm.getAllBookList("page" to page)
            }

            setOnLoadMoreListener {
                page ++
                isShowing = false
                vm.getAllBookList("page" to page)
            }
        }

    }

    override fun initData() {
        isShowing = true
        page = 1
        vm.getAllBookList("page" to page)
    }

    override fun startObserve() {
        vm.getAllBookListData.observeState(this){
            onLoading = {showLoading()}
            onError = {code, msg ->
                dismissLoading()
                toast(msg)
                binding.refreshLayout.setupData(emptyList(), data, adapter, page)
            }
            onSuccess = {
                dismissLoading()
                binding.refreshLayout.setupData(it, data, adapter, page)
            }
        }
    }

}
```

## 3. 网络请求 模板

### 3.1 状态请求

快捷键: rho

模板代码:

```
/**
 * $RETURN$
 */
val $NAME$Data = StatefulMutableLiveData<Boolean>()
fun $NAME$(vararg params: Pair<String, Any?>) {
    rxLifeScope.launch {
        okRequest("", *params,
            onStart = {
                $NAME$Data.value = StateData.Loading
            },
            onError = { msg, code ->
                $NAME$Data.value = StateData.Error(code, msg)
            }, onSuccess = {
                $NAME$Data.value = StateData.Success(true)
            })
    }
}
```

处理相应:

```
        vm.sendSmsData.observeState(this){
            onLoading = {
                showLoading()
            }
            onError = {code, msg ->
                dismissLoading()
                toast(msg)
            }
            onSuccess = {
                dismissLoading()
                // 成功的处理
            }
        }
```

### 3.2 单数据源请求

快捷键: rh

模板代码:

```
/**
 * $RETURN$
 */
val $NAME$Data = StatefulMutableLiveData<$CLASS$>()
fun $NAME$(vararg params: Pair<String, Any?>) {
    rxLifeScope.launch {
        request<$CLASS$>("", *params,
            onStart = {
                $NAME$Data.value = StateData.Loading
            },
            onError = { msg, code ->
                $NAME$Data.value = StateData.Error(code, msg)
            }, onSuccess = {
                $NAME$Data.value = StateData.Success(it)
            })
    }
}
```



处理响应:

```
vm.loginData.observeState(this){
            onLoading = {
                showLoading()
            }
            onError = {code, msg ->
                dismissLoading()
                toast(msg)
            }
            onSuccess = { user->
                dismissLoading()
                // 成功的处理
            }
        }
```



### 3.3 列表数据源请求

快捷键: rh

模板代码:

```
/**
 * $RETURN$
 */
val $NAME$Data = StatefulMutableLiveData<$CLASS$>()
fun $NAME$(vararg params: Pair<String, Any?>) {
    rxLifeScope.launch {
        request<$CLASS$>("", *params,
            onStart = {
                $NAME$Data.value = StateData.Loading
            },
            onError = { msg, code ->
                $NAME$Data.value = StateData.Error(code, msg)
            }, onSuccess = {
                $NAME$Data.value = StateData.Success(it)
            })
    }
}
```



处理响应:

```
vm.listData.observeState(this){
            onLoading = {
                showLoading()
            }
            onError = {code, msg ->
                dismissLoading()
                toast(msg)
            }
            onSuccess = { list->
                dismissLoading()
                // 成功的处理
            }
        }
```

## 4 其他方式的网络请求

### 4.1 Kotlin

```
/**
 *	无参返回的请求
 * @param url String
 * @param tag String
 * @param params Array<out Pair<String, Any?>>
 * @param onStart Function0<Unit>
 * @param onSuccess Function0<Unit>
 * @param onError Function2<[@kotlin.ParameterName] String, [@kotlin.ParameterName] String, Unit>
 * @return Any
 */
fun okRequest(
    url: String,
    tag: String,
    vararg params: Pair<String, Any?>,
    onStart: () -> Unit = {},
    onSuccess: () -> Unit = {},
    onError: (msg: String, code: String) -> Unit = { msg, code -> }
): Any {
    return RxHttp.postForm(url)
        .tag(tag)
        .addAll(params.toMap())
        .asResponse<Any>()
        .doOnSubscribe {
            onStart()
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            onSuccess()
        }, {
            val msg = it.msg
            val code = it.code
            onError(msg, code)
        })
}

// 使用示例
val params = arrayOf<Pair<String, Any>>()
okRequest(
    "url",
    "tag",
    *params,
    onStart = {},
    onError = { msg, code -> },
    onSuccess = {})

/**
 * 有参数返回的请求
 * @param url String
 * @param params Array<out Pair<String, Any?>>
 * @param onStart Function0<Unit>
 * @param onSuccess Function1<[@kotlin.ParameterName] T, Unit>
 * @param onError Function2<[@kotlin.ParameterName] String, [@kotlin.ParameterName] String, Unit>
 */
inline fun <reified T : Any> request(
    url: String,
    tag: String,
    vararg params: Pair<String, Any?>,
    crossinline onStart: () -> Unit,
    crossinline onSuccess: (data: T) -> Unit,
    crossinline onError: (msg: String, code: String) -> Unit
) {
    RxHttp.postForm(url)
        .tag(tag)
        .addAll(params.toMap())
        .asResponse<T>()
        .doOnSubscribe {
            onStart()
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            onSuccess(it)
        }, {
            val msg = it.msg
            val code = it.code
            onError(msg, code)
        })
}

val params = arrayOf<Pair<String, Any>>()
request<String>("url",
	"tag",
	*params,
	onStart = {},
	onError = { msg, code -> },
	onSuccess = {})
	
	// 取消指定tag的请求
	cancelRequest("tag")
	// 取消所有请求
	cancelAllRequest()
```

### 4.2 Java

```
// 无参返回请求
Pair<String, Object>[] params = new Pair[]{
        new Pair<String, Object>("key", "value")
};
BaseViewModelExtKt.okRequest("url", "tag", params,
        () -> {
            // 加载中
            return null;
        }, () -> {
            // 请求成功
            return null;
        }, (msg, code) -> {
            // 请求失败
            return null;
        });
        
// 有参返回的请求
Map<String, Object> params1 = new HashMap<>();
RxHttp.postForm("")
        .tag("tag")
        .addAll(params1)
        .asResponse(String.class)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(disposable -> {
            // 加载中
        })
        .subscribe(result -> {
            // 请求成功 result返回结果
        }, throwable -> {
            // 请求失败
            String msg = ThrowableExtKt.getMsg(throwable);
            String code = ThrowableExtKt.getCode(throwable);
            
        });

// 列表参数的返回请求
Map<String, Object> params1 = new HashMap<>();
RxHttp.postForm("")
        .tag("tag")
        .addAll(params1)
        .asResponseList(String.class)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(disposable -> {
            // 加载中
        })
        .subscribe(result -> {
            // 请求成功 result返回结果
        }, throwable -> {
            // 请求失败
            String msg = ThrowableExtKt.getMsg(throwable);
            String code = ThrowableExtKt.getCode(throwable);
            
        });
```



## 5. 事件总线 

[JeremyLiao/LiveEventBus: EventBus for Android，消息总线，基于LiveData，具有生命周期感知能力，支持Sticky，支持AndroidX，支持跨进程，支持跨APP (github.com)](https://github.com/JeremyLiao/LiveEventBus)



## 6. 路由跳转

```
// 无参跳转
context.startPage<MainActivity>()
// 有参数的跳转
context.startPage<MainActivity>("id" to 1)
// 无参跳转并等待返回参数
context.startPageForResult<MainActivity>{code, data -> }
// 有参数跳转并等待返回
context.startPageForResult<MainActivity>("id" to 1){code, data -> }
// 结束界面并返回参数给上一个界面
context.setResult(code, intent)
context.finish()
// 前往APP详情页面, 传递包名
context.goToAppInfoPage(packageName)
// 跳转到日期和时间页
Context.goToDateAndTimePage()
// 跳转到语言页面
Context.goToLanguagePage()
// 跳转到辅助功能页
Context.goToAccessibilitySetting()
Context.installApk(apkFile: File)
// 使用浏览器访问特定的url
Context.openBrowser(url: String) 
Context.browse(url: String, newTask: Boolean = false)
// 访问应用商店中的应用
Context.openInAppStore(packageName: String = this.packageName)
// 发送邮件
Context.sendEmail(email: String, subject: String?, text: String?)
```

## 7. 公共Widget

### 7.1 字母索引

```
com.lnkj.libs.widget.LetterIndexBar
```

### 7.2 根据设置的图片大小，自动按比例限制在最大宽高范围内的ImageView

```
com.lnkj.libs.widget.LimitImageView
```

### 7.3 支持一段布局进行滚动的布局，

```
com.lnkj.libs.widget.MarqueeLayout    // 继承自FrameLayout
```

### 7.4 支持一段文字进行滚动的View

```
com.lnkj.libs.widget.MarqueeTextView
```

### 7.5 ViewPager2嵌套时内部的将无法滑动，使用这个类包裹一下即可

```
NestedViewPager2Wrapper
```

### 7.6 按指定宽高比自动设置高度的ImageView，默认情况下根据图片的内容来自适应

```
RatioImageView
```

### 7.7 通用标题栏

```
TitleBar
```

## 8.辅助工具

### 8.1 应用商店 AppStoreUtils

```
// 获取跳转到应用商店的 Intent
getAppStoreIntent()
// 获取跳转到应用商店的 Intent isIncludeGooglePlayStore 是否包括 Google Play 商店
getAppStoreIntent(boolean isIncludeGooglePlayStore)
// 获取跳转到应用商店的 Intent
getAppStoreIntent(final String packageName)
// 获取跳转到应用商店的 Intent
// 优先跳转到手机自带的应用市场
Intent getAppStoreIntent(final String packageName, boolean isIncludeGooglePlayStore)
```

### 8.2 缓存工具类CacheUtil

```
// 获取缓存大小
getTotalCacheSize(context: Context)
// 清除缓存
clearAllCache(context: Context)
// 格式化存储大小
getFormatSize(size: Double)
```

### 8.4 CountDownWorker: 精准倒计时实现

```
/**
 * 精准倒计时实现，如果传入了LifecycleOwner，将自动cancel，无需调用cancel
 * 系统的CountDownTimer以时间戳作为任务值，容易有误差
 * @param total 总步数
 * @param step 步长
 * @param countDownInterval 递减时间间隔
 * @param immediately 是否立即执行onChange，false的话会间隔一个countDownInterval再执行onChange
 * @param onChange 递减回调
 * @param onFinish 倒计时结束回调
 */
CountDownWorker(owner, total = 60, step = 1, countDownInterval = 1000, immediately = true, onChange = {s -> }, onFinish = {})
```

### 8.5 DirManager 自动创建常用目录

```
// 初始化
DirManager.init()
// 根目录
DirManager.rootDir
// 临时目录
DirManager.tempDir
// 下载目录
DirManager.downloadDir
// 缓存目录
DirManager.cacheDir
// 可以共享给三方的文件目录
DirManager.shareDir
```

### 8.6 NetworkUtil  网络工具类

```
// 网络检测
NetworkUtil.isNetworkAvailable(Context context)
// 获取本地IP
getLocalIpAddress()
// 获取当前网络状态
getNetState(Context context)
is3G(Context context)
isWifi(Context context)
is2G(Context context)

```

### 8.7 权限检测: PermissionsCheckUtils

```
// 判断是否开启定位服务
locationEnable()
// 判断是否开启通知
areNotificationsEnabled()
// 跳转到通知设置界面
jumpNotificationsSettings(context: Context, cancel: ()->Unit, confirm: ()->Unit)
```

### 8.8 拼音相关工具类: PinyinUtils

```
// 汉字转拼音
ccs2Pinyin(final CharSequence ccs)
// 汉字转拼音
ccs2Pinyin(final CharSequence ccs, final CharSequence split)
// 获取第一个汉字首字母
getPinyinFirstLetter(final CharSequence ccs)
// 获取所有汉字的首字母
getPinyinFirstLetters(final CharSequence ccs)
// 根据名字获取姓氏的拼音
getSurnamePinyin(final CharSequence name)
// 根据名字获取姓氏的首字母
getSurnameFirstLetter(final CharSequence name)
```

### 8.9  SpannableStringUtils

## 9. 常用扩展

### 9.1 通用扩展

```
// 全局context
context
application

// 获取屏幕宽度
Context.screenWidth

// 获取屏幕高度
Context.screenHeight

// 判断是否为空 并传入相关操作
Any?.notNull({t->
	// 不为空时的操作
}, {
	// 为空时的操作
})

// dp值转换为px
Context.dp2px(dp: Int)
View.dp2px(dp: Int)

// px值转换成dp
Context.px2dp(px: Int)
View.px2dp(px: Int)

复制文本到粘贴板
Context.copyToClipboard(text: String, label: String = "JetpackMvvm") {

检查是否启用无障碍服务
Context.checkAccessibilityServiceEnabled(serviceName: String)
```

### 9.2 Json相关扩展

```
T.toJson()
String.fromJson(): T
String.fromJsonToList(): List<T>
Any.fromJson(): T
Any.fromJsonToList(): List<T>
```

### 9.3 软键盘相关扩展

```
Activity.hideSoftInput()
Fragment.hideSoftInput()
```

### 9.4 String相关扩展

```
// 是否为手机号  0开头 12开头的不支持
String?.isPhone(): Boolean
// 是否为座机号
String?.isTel(): Boolean
// 是否为邮箱号
String?.isEmail(): Boolean
```

### 9.5 系统常用服务的扩展

```
Context.windowManager get() = getSystemService<WindowManager>()
Context.clipboardManager get() = getSystemService<ClipboardManager>()
Context.layoutInflater get() = getSystemService<LayoutInflater>()
Context.activityManager get() = getSystemService<ActivityManager>()
Context.powerManager get() = getSystemService<PowerManager>()
Context.alarmManager get() = getSystemService<AlarmManager>()
Context.notificationManager get() = getSystemService<NotificationManager>()
Context.keyguardManager get() = getSystemService<KeyguardManager>()
Context.locationManager get() = getSystemService<LocationManager>()
Context.searchManager get() = getSystemService<SearchManager>()
Context.storageManager get() = getSystemService<StorageManager>()
Context.vibrator get() = getSystemService<Vibrator>()
Context.connectivityManager get() = getSystemService<ConnectivityManager>()
Context.wifiManager get() = getSystemService<WifiManager>()
Context.audioManager get() = getSystemService<AudioManager>()
Context.mediaRouter get() = getSystemService<MediaRouter>()
Context.telephonyManager get() = getSystemService<TelephonyManager>()
Context.sensorManager get() = getSystemService<SensorManager>()
Context.subscriptionManager get() = getSystemService<SubscriptionManager>()
Context.carrierConfigManager get() = getSystemService<CarrierConfigManager>()
Context.inputMethodManager get() = getSystemService<InputMethodManager>()
Context.uiModeManager get() = getSystemService<UiModeManager>()
Context.downloadManager get() = getSystemService<DownloadManager>()
Context.batteryManager get() = getSystemService<BatteryManager>()
Context.jobScheduler get() = getSystemService<JobScheduler>()
Context.accessibilityManager get() = getSystemService<AccessibilityManager>()
```

### 9.6 Intent常用扩展

```
// 返回安装APK的意图
Context.getInstallIntent(apkFile: File): Intent?
// 获取String类型参数  返回非空
Intent.getString(key: String, default: String = ""): String
// 为Intent添加参数
Intent.fillIntentArguments(vararg params: Pair<String, Any?>): Intent
// Bundle中读取参数, 附带默认值
Bundle.read(key: String, defaultValue: T): T

// Add the [Intent.FLAG_ACTIVITY_CLEAR_TASK] flag to the [Intent].
Intent.clearTask()
// Add the [Intent.FLAG_ACTIVITY_CLEAR_TOP] flag to the [Intent].
Intent.clearTop()
// Add the [Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS] flag to the [Intent].
Intent.excludeFromRecents()
// Add the [Intent.FLAG_ACTIVITY_MULTIPLE_TASK] flag to the [Intent].
Intent.multipleTask()
// Add the [Intent.FLAG_ACTIVITY_NEW_TASK] flag to the [Intent].
Intent.newTask()
// Intent.FLAG_ACTIVITY_NO_ANIMATION
Intent.noAnimation()
// Intent.FLAG_ACTIVITY_SINGLE_TOP
Intent.singleTop()
```



