package org.vane.setting;

import com.intellij.openapi.Disposable;
import com.intellij.ui.IdeBorderFactory;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

class CreateController implements ExtJSToolSettingsComponentValid {
    private final JBTextField nameLayout = new JBTextField();
    private final JPanel panel;

    CreateController(Disposable disposable) {
        setValidBlank(disposable, nameLayout);
        panel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Controller name: "), nameLayout, 1, false)
                .getPanel();
        panel.setBorder(IdeBorderFactory.createTitledBorder("Controller"));
    }

    @Override
    public void resetData(ExtJSToolSetting setting) {
        nameLayout.setText(setting.controllerName);
    }

    @Override
    public void applyData(ExtJSToolSetting setting) {
        setting.controllerName = nameLayout.getText();
    }

    // property
    @Override
    public JPanel getPanel() {
        return panel;
    }

    public String getControllerName() {
        return nameLayout.getText();
    }
}
