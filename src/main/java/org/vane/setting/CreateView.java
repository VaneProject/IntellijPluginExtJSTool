package org.vane.setting;

import com.intellij.openapi.Disposable;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

class CreateView implements ExtJSToolSettingsComponentValid {
    private final JBTextField defaultLayout = new JBTextField();
    private final JBTextField widthLayout = new JBTextField();
    private final JBTextField heightLayout = new JBTextField();
    private final JPanel panel;

    CreateView(Disposable disposable) {
        setValidBlank(disposable, defaultLayout);
        setValidNumber(disposable, widthLayout);
        setValidNumber(disposable, heightLayout);
        panel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Default layout: "), defaultLayout, 1, false)
                .addLabeledComponent(new JBLabel("Height: "), heightLayout, 1, false)
                .addLabeledComponent(new JBLabel("Width: "), widthLayout,1, false)
                .getPanel();
        panel.setBorder(IdeBorderFactory.createTitledBorder("View"));
    }

    // init
    @Override
    public void resetData(ExtJSToolSetting setting) {
        defaultLayout.setText(setting.defaultLayout);
        widthLayout.setText(setting.width);
        heightLayout.setText(setting.height);
    }

    @Override
    public void applyData(ExtJSToolSetting setting) {
        setting.defaultLayout = defaultLayout.getText();
        setting.width = getWidth();
        setting.height = getHeight();
    }

    // property
    @Override
    public JPanel getPanel() {
        return panel;
    }

    public String getDefaultLayout() {
        return defaultLayout.getText();
    }

    public String getWidth() {
        return widthLayout.getText();
    }

    public String getHeight() {
        return heightLayout.getText();
    }
}
