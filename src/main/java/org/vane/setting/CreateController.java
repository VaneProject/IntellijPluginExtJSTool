package org.vane.setting;

import com.intellij.openapi.Disposable;
import com.intellij.ui.IdeBorderFactory;

import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

class CreateController implements ExtJSToolSettingsComponentValid {
    private final JPanel panel;

    CreateController(Disposable disposable) {
        panel = FormBuilder.createFormBuilder()
                .getPanel();
        panel.setBorder(IdeBorderFactory.createTitledBorder("Controller"));
    }

    @Override
    public void resetData(ExtJSToolSetting setting) {

    }

    @Override
    public void applyData(ExtJSToolSetting setting) {

    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
