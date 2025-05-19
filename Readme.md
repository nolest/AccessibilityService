# AccessibilityService Demo

本项目演示了如何实现一个简单的 Android 无障碍服务（AccessibilityService），用于抓取屏幕控件信息并自动跳转到微信主页面。

## 目录结构

```
AndroidManifest.xml
MyAccessibilityService.java
res/
  values/
    strings.xml
  xml/
    accessibility_service_config.xml
```

## 运行步骤

1. **导入项目**

   使用 Android Studio 打开本项目文件夹。

2. **配置包名**

   确认 `AndroidManifest.xml` 和 `MyAccessibilityService.java` 中的包名一致（默认为 `com.example.accessibilityservice`）。

3. **连接设备**

   使用 USB 数据线连接 Android 设备，并确保已开启开发者选项和 USB 调试。

4. **编译并安装 APK**

   在 Android Studio 中点击“运行”按钮，或使用命令行：

   ```sh
   ./gradlew installDebug
   ```

5. **开启无障碍服务**

   - 在设备上打开“设置” > “无障碍” > 找到“AccessibilityDemo”服务。
   - 点击进入并开启该服务。

6. **测试功能**

   - 开启服务后，切换到任意界面，服务会自动抓取当前屏幕控件信息并输出到 Logcat（标签为 `MyAccessibilityService`）。
   - 当窗口状态发生变化时，服务会自动跳转到微信主页面（需已安装微信）。

## 注意事项

- 需确保设备已安装微信（包名为 `com.tencent.mm`），否则跳转无效。
- 若未在“无障碍”设置中看到服务，请确认 APK 已正确安装且权限配置无误。
- 日志信息可通过 Android Studio 的 Logcat 查看。

## 相关文件说明

- [`MyAccessibilityService.java`](MyAccessibilityService.java)：无障碍服务主逻辑。
- [`AndroidManifest.xml`](AndroidManifest.xml)：应用及服务声明与权限配置。
- [`res/xml/accessibility_service_config.xml`](res/xml/accessibility_service_config.xml)：无障碍服务配置。
- [`res/values/strings.xml`](res/values/strings.xml)：字符串资源。
