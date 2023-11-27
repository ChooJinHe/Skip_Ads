package com.example.skipads;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.view.accessibility.AccessibilityManager;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AccessibilityViewModel extends ViewModel {

    private MutableLiveData<Boolean> accessibilityEnabled = new MutableLiveData<>();

    public void checkAccessibilityServiceState(Context context, Class<? extends AccessibilityService> service) {
        boolean isEnabled = isAccessibilityServiceEnabled(context, service);
        accessibilityEnabled.setValue(isEnabled);
    }

    public MutableLiveData<Boolean> getAccessibilityEnabled() {
        return accessibilityEnabled;
    }

    // Check if an Accessibility Service is enabled
    public boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        for (AccessibilityServiceInfo enabledService : enabledServices) {
            ServiceInfo enabledServiceInfo = enabledService.getResolveInfo().serviceInfo;
            if (enabledServiceInfo.packageName.equals(context.getPackageName()) && enabledServiceInfo.name.equals(service.getName()))
                return true;
        }
        return false;
    }
}
