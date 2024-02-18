package org.vane.setting;

import com.intellij.openapi.Disposable;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

class CreateView implements ExtJSToolSettingsComponentValid {
    private final JBTextField defaultLayout = new JBTextField();
    private final JBTextField widthLayout = new JBTextField();
    private final JBTextField heightLayout = new JBTextField();
    private final JPanel panel;

    CreateView(Disposable disposable) {
        setValidBlank(disposable, defaultLayout);
        setValidNumber(disposable, widthLayout);
        setValidNumber(disposable, heightLayout);

        // size panel
        JPanel sizePanel = new JPanel(new GridLayoutManager(1, 4, JBUI.emptyInsets(), -1, -1)) {{
            add(new Label("Width: "), new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED, null, null, null));
            add(widthLayout, new GridConstraints(0, 1, 1, 1,
                    GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_FIXED, null, null, null));
            add(new JLabel("Height:"), new GridConstraints(0, 2, 1, 1,
                    GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED, null, null, null));
            add(heightLayout, new GridConstraints(0, 3, 1, 1,
                    GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        }};

        panel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Default layout: "), defaultLayout, 1, false)
                .addComponent(sizePanel, 1)
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
