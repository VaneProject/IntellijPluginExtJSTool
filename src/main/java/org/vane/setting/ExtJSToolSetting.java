package org.vane.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(
        name = "org.vane.setting.ExtJSToolSetting",
        storages = @Storage("ExtJSToolSettingsPlugin.xml")
)
public final class ExtJSToolSetting implements PersistentStateComponent<ExtJSToolSetting> {
    // view
    public String defaultLayout = "border";
    public String height = "720", width = "1080";
    // model
    public String type = "json";
    // controller
    public String controllerName = "ViewController";

    public static ExtJSToolSetting getInstance() {
        return ApplicationManager.getApplication().getService(ExtJSToolSetting.class);
    }

    @Override
    public ExtJSToolSetting getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ExtJSToolSetting setting) {
        XmlSerializerUtil.copyBean(setting, this);
    }
}
