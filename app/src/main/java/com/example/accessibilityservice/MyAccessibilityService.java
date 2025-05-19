package com.example.accessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";
    private static final String TARGET_PACKAGE = "com.tencent.mm"; // 微信包名
    private boolean jumpedToWechat = false; // 跟踪是否已跳转到微信

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 抓取当前屏幕控件信息
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            printAllNodes(rootNode, 0);
        }

        // 自动跳转到微信，但避免循环跳转
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            String packageName = event.getPackageName() != null ? event.getPackageName().toString() : "";
            
            // 如果当前不在微信且尚未执行过跳转，则跳转到微信
            if (!packageName.equals(TARGET_PACKAGE) && !jumpedToWechat) {
                Intent intent = getPackageManager().getLaunchIntentForPackage(TARGET_PACKAGE);
                if (intent != null) {
                    Log.d(TAG, "正在跳转到微信...");
                    jumpedToWechat = true; // 标记已尝试跳转
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            } else if (packageName.equals(TARGET_PACKAGE)) {
                Log.d(TAG, "已在微信中，无需跳转");
            }
        }
    }

    private void printAllNodes(AccessibilityNodeInfo node, int depth) {
        if (node == null) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) sb.append("--");
        sb.append("[").append(node.getClassName()).append("] ")
          .append(node.getText());
        Log.d(TAG, sb.toString());
        for (int i = 0; i < node.getChildCount(); i++) {
            printAllNodes(node.getChild(i), depth + 1);
        }
    }

    @Override
    public void onInterrupt() {
        // 无障碍服务被打断时的处理
        Log.d(TAG, "无障碍服务被中断");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "无障碍服务已连接");
        
        // 重置跳转状态
        jumpedToWechat = false;
        
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;
        info.packageNames = new String[]{TARGET_PACKAGE}; // 只监听微信应用
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        setServiceInfo(info);
    }
} 