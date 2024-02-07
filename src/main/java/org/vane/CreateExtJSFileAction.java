package org.vane;

import com.intellij.codeInsight.navigation.BackgroundUpdaterTask;
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

import java.util.Properties;

public class CreateExtJSFileAction extends CreateFileFromTemplateAction {
    protected CreateExtJSFileAction() {
        super("Create ExtJS Files", "Create extJS files", null);
    }

    @Override
    protected void buildDialog(@NotNull Project project,
                               @NotNull PsiDirectory psiDirectory,
                               @NotNull CreateFileFromTemplateDialog.Builder builder) {
        builder
                .setTitle("Create ExtJS Tool")
                .addKind("ExtJS", AllIcons.FileTypes.JavaScript, "Main.js");
    }

    @Override
    protected @NlsContexts.Command String getActionName(PsiDirectory psiDirectory,
                                                        @NonNls @NotNull String s,
                                                        @NonNls String s1) {
        return "Create ExtJS: " + s;
    }

    @Override
    protected PsiFile createFileFromTemplate(String name, FileTemplate template, PsiDirectory dir) {
        Project project = dir.getProject();
        FileTemplate controller = FileTemplateManager.getInstance(project).getInternalTemplate("Controller.js");
        FileTemplate viewModel = FileTemplateManager.getInstance(project).getInternalTemplate("ViewModel.js");
        try {
            Properties properties = FileTemplateManager.getInstance(project).getDefaultProperties();
            properties.setProperty("ALIAS", toAlias(name));

            FileTemplateUtil.createFromTemplate(controller, name + "Controller", properties, dir);
            FileTemplateUtil.createFromTemplate(viewModel, name + "ViewModel", properties, dir);

            return FileTemplateUtil.createFromTemplate(template, name, properties, dir).getContainingFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
