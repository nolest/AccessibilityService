package com.example.accessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 抓取当前屏幕控件信息
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            printAllNodes(rootNode, 0);
        }

        // 自动跳转到其他APP指定页面（以微信为例，跳转到主页面）
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    private void printAllNodes(AccessibilityNodeInfo node, int depth) {
        if (node == null) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) sb.append("--");
        sb.append("[").append(node.getClassName()).append("] ")
          .append(node.getText());
        Log.d("MyAccessibilityService", sb.toString());
        for (int i = 0; i < node.getChildCount(); i++) {
            printAllNodes(node.getChild(i), depth + 1);
        }
    }

    @Override
    public void onInterrupt() {
        // 无障碍服务被打断时的处理
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;
        setServiceInfo(info);
    }
}