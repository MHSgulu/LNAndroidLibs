#  LNAndroidLibs

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
            url 'https://github.com/psiegman/mvn-repo/raw/master/releases'
        }
        maven {
            url 'https://maven.aliyun.com/repository/public'
        }
        maven {
            url 'https://packages.aliyun.com/maven/repository/2153597-release-WSo6aw/'
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

## 2.快速上手

