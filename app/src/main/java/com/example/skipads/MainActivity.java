package com.example.skipads;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private Switch switchAccessibility;
    private TextView textAccessibilityDescription;
    private AccessibilityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        switchAccessibility = findViewById(R.id.switchAccessibility);
        textAccessibilityDescription = findViewById(R.id.textAccessibilityDescription);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(AccessibilityViewModel.class);

        // Observe changes in the accessibility state
        viewModel.getAccessibilityEnabled().observe(this, this::updateAccessibilityStatus);

        // Set a listener for the switch
        switchAccessibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });

    }

    private void updateAccessibilityStatus(boolean isEnabled) {
        if (isEnabled) {
            textAccessibilityDescription.setText("Accessibility service is enabled");
        } else {
            textAccessibilityDescription.setText("Turn on to enable accessibility service");
        }
        switchAccessibility.setChecked(isEnabled);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check the accessibility service state when the activity is resumed
        viewModel.checkAccessibilityServiceState(this, SkipAdsService.class);
    }
}
