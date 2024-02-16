package org.vane.setting;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.util.NlsContexts;

import javax.swing.*;
import java.util.Objects;

public class ExtJSToolSettingsConfigurable implements Configurable {
    private static final Disposable EMPTY_DISPOSABLE = () -> {};
    private ExtJSToolSettingsComponent component;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "ExtJS Tool Setting";
    }

    @Override
    public JComponent createComponent() {
        return (component = new ExtJSToolSettingsComponent(EMPTY_DISPOSABLE)).getPanel();
    }

    @Override
    public boolean isModified() {
        ExtJSToolSetting setting = ExtJSToolSetting.getInstance();
        if (component.isNotValid())
            return false;
        // view
        boolean modified = !component.getDefaultLayout().equals(setting.defaultLayout);
        modified |= !Objects.equals(component.getHeight(), setting.height);
        modified |= !Objects.equals(component.getWidth(), setting.width);
        // model
        modified |= !Objects.equals(component.getType(), setting.type);
        // controller
        modified |= !Objects.equals(component.getControllerName(), setting.controllerName);
        return modified;
    }

    @Override
    public void reset() {
        component.resetData(ExtJSToolSetting.getInstance());
    }

    @Override
    public void apply() {
        component.applyData(ExtJSToolSetting.getInstance());
    }

    @Override
    public void disposeUIResources() {
        component = null;
    }
}
