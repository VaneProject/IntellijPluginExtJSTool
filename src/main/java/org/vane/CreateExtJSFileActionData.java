package org.vane;

import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.project.Project;
import org.vane.setting.ExtJSToolSetting;

import java.util.Properties;

interface CreateExtJSFileActionData {
    // templates file name
    String MAIN = "Main";
    String CONTROLLER = "Controller";
    String VIEW_MODEL = "ViewModel";
    // file name
    String FILE_MODEL = "ViewModel";

    // properties
    String PROJECT_NAME = "PROJECT_NAME";
    // view
    String ALIAS = "ALIAS";
    String DEFAULT_LAYOUT = "DEFAULT_LAYOUT";
    String HEIGHT = "HEIGHT";
    String WIDTH = "WIDTH";
    // model
    String TYPE = "TYPE";
    // controller
    String CONTROLLER_NAME = "CONTROLLER_NAME";

    default Properties getProperties(Project project, String name) {
        Properties properties = FileTemplateManager.getInstance(project).getDefaultProperties();
        ExtJSToolSetting setting = ExtJSToolSetting.getInstance();
        properties.setProperty(ALIAS, toAlias(name));
        properties.setProperty(PROJECT_NAME, setting.getProjectName());
        // setting value
        properties.setProperty(DEFAULT_LAYOUT, setting.defaultLayout);
        properties.setProperty(HEIGHT, setting.height);
        properties.setProperty(WIDTH, setting.width);
        // model
        properties.setProperty(TYPE, setting.type);
        // controller
        properties.setProperty(CONTROLLER_NAME, setting.controllerName);
        return properties;
    }

    private String toAlias(String name) {
        StringBuilder builder = new StringBuilder();
        char[] words = name.toCharArray();
        char word = words[0];
        if ('A' <= word && word <= 'Z')
            builder.append((char) (word + 32));
        else
            builder.append(word);
        for (int i = 1; i < words.length; i++) {
            word = words[i];
            if ('A' <= word && word <= 'Z')
                builder.append("_").append((char) (word + 32));
            else builder.append(word);
        }
        return builder.toString();
    }
}
