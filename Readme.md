# AccessibilityService Demo

本项目演示了如何实现一个简单的 Android 无障碍服务（AccessibilityService），用于抓取屏幕控件信息并自动跳转到微信主页面。

## 项目特点

1. **抓取屏幕控件信息**：自动遍历并打印当前屏幕上的所有UI元素及其属性
2. **智能跳转**：可以自动检测并跳转到微信（可根据需要修改目标应用）
3. **避免循环跳转**：通过状态跟踪避免无限循环跳转的问题

## 项目结构

```
app/
  src/
    main/
      java/com/example/accessibilityservice/
        MainActivity.java            - 应用主界面
        MyAccessibilityService.java  - 无障碍服务实现
      res/
        layout/
          activity_main.xml          - 主界面布局
        values/
          strings.xml                - 字符串资源
        xml/
          accessibility_service_config.xml - 无障碍服务配置
      AndroidManifest.xml            - 应用清单
build.gradle                         - 项目构建配置
```

## 开发环境要求

- Android Studio Chipmunk 2021.2.1 或更高版本
- JDK 8 或更高版本
- Android SDK:
  - 编译SDK版本 (compileSdk): 33 (Android 13)
  - 最低SDK版本 (minSdk): 24 (Android 7.0)
  - 目标SDK版本 (targetSdk): 33 (Android 13)
- Gradle 版本: 7.3.3
- 依赖库:
  - androidx.appcompat:appcompat:1.5.1
  - com.google.android.material:material:1.7.0
  - androidx.constraintlayout:constraintlayout:2.1.4

## 运行步骤

1. **导入项目**

   使用 Android Studio 打开本项目文件夹。

2. **编译并安装 APK**

   在 Android Studio 中点击"运行"按钮，或使用命令行：

   ```sh
   ./gradlew installDebug
   ```

3. **开启无障碍服务**

   - 打开应用，点击"打开无障碍设置"按钮
   - 在系统设置中找到"AccessibilityService"服务
   - 点击进入并开启该服务

4. **测试功能**

   - 开启服务后，服务会自动尝试跳转到微信主页面（需已安装微信）
   - 所有屏幕控件信息会输出到 Logcat（过滤标签: `MyAccessibilityService`）

## 注意事项

- 需确保设备已安装微信（包名为 `com.tencent.mm`），否则跳转无效
- 如需修改目标应用，请在 `MyAccessibilityService.java` 文件中修改 `TARGET_PACKAGE` 常量
- 为避免无限循环跳转，服务只会在首次启动时尝试跳转一次
- 若有特殊需求，可通过修改 `onAccessibilityEvent()` 方法中的逻辑来实现

## 常用热门应用包名

如果想要修改跳转目标，可以将 `MyAccessibilityService.java` 文件中的 `TARGET_PACKAGE` 常量更改为以下热门应用之一：

1. 微信：`com.tencent.mm`
2. 抖音：`com.ss.android.ugc.aweme`
3. 淘宝：`com.taobao.taobao`
4. 支付宝：`com.eg.android.AlipayGphone`
5. 哔哩哔哩：`tv.danmaku.bili`
6. QQ：`com.tencent.mobileqq`

使用方法：将 `private static final String TARGET_PACKAGE = "com.tencent.mm";` 中的包名替换为上述列表中的任一包名即可。

## 相关文件说明

- [`MyAccessibilityService.java`](MyAccessibilityService.java)：无障碍服务主逻辑。
- [`AndroidManifest.xml`](AndroidManifest.xml)：应用及服务声明与权限配置。
- [`res/xml/accessibility_service_config.xml`](res/xml/accessibility_service_config.xml)：无障碍服务配置。
- [`res/values/strings.xml`](res/values/strings.xml)：字符串资源。
