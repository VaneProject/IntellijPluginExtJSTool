package org.vane.setting;

import com.intellij.openapi.Disposable;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

class CreateModel implements ExtJSToolSettingsComponentValid {
    private final JBTextField typeLayout = new JBTextField();
    private final JPanel panel;

    CreateModel(Disposable disposable) {
        setValidBlank(disposable, typeLayout);
        panel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Render type: "), typeLayout, 1, false)
                .getPanel();
        panel.setBorder(IdeBorderFactory.createTitledBorder("Model"));
    }

    @Override
    public void resetData(ExtJSToolSetting setting) {
        typeLayout.setText(setting.type);
    }

    @Override
    public void applyData(ExtJSToolSetting setting) {
        setting.type = typeLayout.getText();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    public String getType() {
        return typeLayout.getText();
    }
}
