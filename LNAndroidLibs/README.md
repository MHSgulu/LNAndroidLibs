#  LNAndroidLibs

## 必备知识点

### 1. Kotlin基础与进阶

### 2. Kotlin协程与Flow

### 3. ViewModel与LiveData

### 4. RxHttp网络请求库的基本使用

### 5. RxJava3的基本用法(了解)



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

## 4 辅助功能

### 4.1 事件总线

