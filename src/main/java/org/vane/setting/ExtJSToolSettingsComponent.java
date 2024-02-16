package org.vane.setting;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import java.util.Optional;

public class ExtJSToolSettingsComponent {
    private final JPanel mainPanel;
    private final CreateView view;
    private final CreateModel model;
    private final CreateController controller;

    public ExtJSToolSettingsComponent(Disposable disposable) {
        view = new CreateView(disposable);
        model = new CreateModel(disposable);
        controller = new CreateController(disposable);

        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(view.getPanel())
                .addComponent(controller.getPanel())
                .addComponent(model.getPanel())
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    // init method
    void resetData(ExtJSToolSetting setting) {
        view.resetData(setting);
        model.resetData(setting);
        controller.resetData(setting);
    }

    void applyData(ExtJSToolSetting setting) {
        view.applyData(setting);
        model.applyData(setting);
        controller.applyData(setting);
    }

    // valid check method
    boolean isNotValid() {
        for (JComponent view : ExtJSToolSettingsComponentValid.validList) {
            Optional<ComponentValidator> instance = ComponentValidator.getInstance(view);
            if (instance.isPresent() && instance.get().getValidationInfo() != null)
                return true;
        }
        return false;
    }

    // property
    public JPanel getPanel() {
        return mainPanel;
    }

    // property view
    public String getDefaultLayout() {
        return view.getDefaultLayout();
    }

    public String getWidth() {
        return view.getWidth();
    }

    public String getHeight() {
        return view.getHeight();
    }

    // property model
    public String getType() {
        return model.getType();
    }

    // property controller
    public String getControllerName() {
        return controller.getControllerName();
    }
}
