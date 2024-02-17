package org.vane.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import java.io.*;

@State(
        name = "org.vane.setting.ExtJSToolSetting",
        storages = @Storage("ExtJSToolSettingsPlugin.xml")
)
public final class ExtJSToolSetting implements PersistentStateComponent<ExtJSToolSetting> {
    public String projectName = "Ext";
    public String appJsonPath = "";
    public boolean appJsonPathCheck = false;
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

    public String getProjectName() {
        if (appJsonPathCheck) {
            File file = new File(appJsonPath);
            try (Reader reader = new FileReader(file)) {
                JsonElement json = JsonParser.parseReader(reader);
                return json.getAsJsonObject().get("name").getAsString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else
            return projectName;
    }
}
