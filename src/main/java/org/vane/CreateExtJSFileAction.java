package org.vane;

import com.intellij.icons.AllIcons;
import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.vane.setting.ExtJSToolSetting;

import java.util.Properties;

public class CreateExtJSFileAction extends CreateFileFromTemplateAction implements CreateExtJSFileActionData {

    protected CreateExtJSFileAction() {
        super("Create ExtJS Files", "Create extJS files", null);
    }

    @Override
    protected void buildDialog(@NotNull Project project,
                               @NotNull PsiDirectory psiDirectory,
                               @NotNull CreateFileFromTemplateDialog.Builder builder) {
        builder
                .setTitle("Create ExtJS Tool")
                .addKind("ExtJS", AllIcons.FileTypes.JavaScript, MAIN)
                .addKind("Controller", AllIcons.FileTypes.JavaScript, CONTROLLER)
                .addKind("ViewModel", AllIcons.FileTypes.JavaScript, VIEW_MODEL);
    }

    @Override
    protected @NlsContexts.Command String getActionName(PsiDirectory psiDirectory,
                                                        @NonNls @NotNull String s,
                                                        @NonNls String s1) {
        return "Create ExtJS: " + s;
    }

    @Override
    protected PsiFile createFileFromTemplate(String name, FileTemplate template, PsiDirectory dir) {
        return switch (template.getName()) {
            case MAIN -> {
                Project project = dir.getProject();
                FileTemplate controller = FileTemplateManager.getInstance(project).getInternalTemplate(CONTROLLER);
                FileTemplate viewModel = FileTemplateManager.getInstance(project).getInternalTemplate(VIEW_MODEL);
                // properties
                Properties properties = getProperties(project, name);
                ExtJSToolSetting setting = ExtJSToolSetting.getInstance();
                try {
                    // create template
                    FileTemplateUtil.createFromTemplate(controller, name + setting.controllerName, properties, dir);
                    FileTemplateUtil.createFromTemplate(viewModel, name + FILE_MODEL, properties, dir);
                    yield FileTemplateUtil.createFromTemplate(template, name, properties, dir).getContainingFile();
                } catch (Exception e) {
                    LOG.error(e);
                }
                yield null;
            }
            case VIEW_MODEL, CONTROLLER -> {
                Project project = dir.getProject();
                // properties
                Properties properties = getProperties(project, name);
                try {
                    yield FileTemplateUtil.createFromTemplate(template, name, properties, dir).getContainingFile();
                } catch (Exception e) {
                    LOG.error(e);
                }
                yield null;
            }
            default -> super.createFileFromTemplate(name, template, dir);
        };
    }

    private Properties getProperties(Project project, String name) {
        Properties properties = FileTemplateManager.getInstance(project).getDefaultProperties();
        properties.setProperty(ALIAS, toAlias(name));
        // setting value
        ExtJSToolSetting setting = ExtJSToolSetting.getInstance();
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
