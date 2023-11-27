package com.example.skipads;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class SkipAdsService extends AccessibilityService {
    private AccessibilityNodeInfo accessibilityNodeInfo;
    private AccessibilityNodeInfo node = null;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        accessibilityNodeInfo = getRootInActiveWindow();

        if (accessibilityNodeInfo == null) {
            return;
        }

        CharSequence packageName = accessibilityNodeInfo.getPackageName();

        switch (packageName.toString()) {
            case "com.google.android.youtube":
                node = getNodeById(accessibilityNodeInfo, "com.google.android.youtube:id/skip_ad_button");
                break;
            case "com.google.android.apps.youtube.music":
                node = getNodeById(accessibilityNodeInfo, "com.google.android.apps.youtube.music:id/skip_ad_button");
                break;
            // Add more cases as needed
            default:
                return;
        }

        if (node != null) {
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            node.recycle(); // Ensure proper recycling
            node = null;
        }
    }

    @Override
    public void onInterrupt() {

    }

    private AccessibilityNodeInfo getNodeById(AccessibilityNodeInfo node, String id) {
        if (node == null) {
            return null;
        }

        CharSequence widgetId = node.getViewIdResourceName();

        if (widgetId != null && widgetId.toString().equals(id)) {
            return node;
        }

        int childCount = node.getChildCount();

        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo childNode = node.getChild(i);
            AccessibilityNodeInfo foundNode = getNodeById(childNode, id);
            if (foundNode != null) {
                return foundNode;
            }
        }

        return null;
    }


}
