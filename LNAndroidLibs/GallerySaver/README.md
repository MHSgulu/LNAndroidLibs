# GallerySaver 保存图片和视频到相册  兼容Android 10+以及鸿蒙系统
### 依赖Library

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
    implementation 'com.lnkj.libs.LNAndroidLibs:GallerySaver:0.0.4.2'
}
```

### 使用
Kotlin代码中的使用
```
// 保存Bitmap到相册
GalleryUtils.saveImageToGallery(context,bmp,quality,name,result)

// 保存路径图片到相册
GalleryUtils.saveImageToGalleryWithUrl(context, path, result)

// 保存视频到相册  传递下载路径
GalleryUtils.saveVideoToGallery(context,filePath, result)
```