package org.vane.setting;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.io.File;
import java.util.Optional;

public final class ExtJSToolSettingsComponent implements ExtJSToolSettingsComponentValid {
    private final JLabel projectNameLabel = new JBLabel("Project name: ");
    private final JLabel appJsonPathLabel = new JBLabel("File path app.json: ");

    private final JBTextField projectName = new JBTextField();
    private final JBCheckBox appJsonPathCheck = new JBCheckBox("Use app.json option: ");
    private final TextFieldWithBrowseButton appJsonPath = new TextFieldWithBrowseButton();
    private final JPanel mainPanel;
    private final CreateView view;
    private final CreateModel model;
    private final CreateController controller;

    public ExtJSToolSettingsComponent(Disposable disposable) {
        setValidBlank(disposable, projectName);
        // app.json setting
        validList.add(appJsonPath);
        new ComponentValidator(disposable).withValidator(() -> {
            if (!appJsonPath.isEnabled())
                return null;
            String text = appJsonPath.getText();
            if (StringUtil.isEmptyOrSpaces(text))
                return new ValidationInfo("Please enter a text", appJsonPath);
            if (!new File(text).isFile())
                return new ValidationInfo("Do not find file", appJsonPath);
            return null;
        }).installOn(appJsonPath);
        appJsonPath.getTextField().getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent documentEvent) {
                ComponentValidator.getInstance(appJsonPath).ifPresent(ComponentValidator::revalidate);
            }
        });

        appJsonPath.addBrowseFolderListener("Choose File", null, null,
                new FileChooserDescriptor(true, false, false, false, false, false)
        );
        appJsonPathCheck.addChangeListener(e -> setAppJsonInit(((AbstractButton) e.getSource()).getModel().isSelected()));

        view = new CreateView(disposable);
        model = new CreateModel(disposable);
        controller = new CreateController(disposable);

        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(appJsonPathCheck, 1)
                .addLabeledComponent(projectNameLabel, projectName, 1)
                .addLabeledComponent(appJsonPathLabel, appJsonPath, 1)
                .addComponent(view.getPanel())
                .addComponent(controller.getPanel())
                .addComponent(model.getPanel())
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public void resetData(ExtJSToolSetting setting) {
        projectName.setText(setting.projectName);
        appJsonPath.setText(setting.appJsonPath);
        appJsonPathCheck.setSelected(setting.appJsonPathCheck);
        view.resetData(setting);
        model.resetData(setting);
        controller.resetData(setting);
        setAppJsonInit(setting.appJsonPathCheck);
    }

    public void applyData(ExtJSToolSetting setting) {
        setting.projectName = projectName.getText();
        setting.appJsonPath = appJsonPath.getText();
        setting.appJsonPathCheck = appJsonPathCheck.isSelected();
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

    private void setAppJsonInit(boolean isCheck) {
        projectName.setEnabled(!isCheck);
        projectNameLabel.setEnabled(!isCheck);
        appJsonPath.setEnabled(isCheck);
        appJsonPathLabel.setEnabled(isCheck);
    }

    // property
    public JPanel getPanel() {
        return mainPanel;
    }

    public String getProjectName() {
        return projectName.getText();
    }

    public String getAppJsonPath() {
        return appJsonPath.getText();
    }

    public boolean isAppJsonPathCheck() {
        return appJsonPathCheck.isSelected();
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
