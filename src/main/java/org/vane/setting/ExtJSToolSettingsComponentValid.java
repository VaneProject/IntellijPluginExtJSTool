package org.vane.setting;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.util.ArrayList;
import java.util.List;

interface ExtJSToolSettingsComponentValid {
    List<JComponent> validList = new ArrayList<>();

    void resetData(ExtJSToolSetting setting);
    void applyData(ExtJSToolSetting setting);
    JPanel getPanel();

    private void setValidListener(JBTextField textField) {
        validList.add(textField);
        textField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent documentEvent) {
                ComponentValidator.getInstance(textField).ifPresent(ComponentValidator::revalidate);
            }
        });
    }

    default void setValidBlank(Disposable disposable, JBTextField textField) {
        String message = "Please enter a text";
        new ComponentValidator(disposable).withValidator(() -> {
            if (textField.isEnabled() && StringUtil.isEmptyOrSpaces(textField.getText()))
                return new ValidationInfo(message, textField);
            return null;
        }).installOn(textField);
        setValidListener(textField);
    }

    default void setValidNumber(Disposable disposable, JBTextField textField) {
        String message = "Please enter a number";
        new ComponentValidator(disposable).withValidator(() -> {
            if (!textField.isEnabled())
                return null;
            String text = textField.getText();
            if (StringUtil.isEmptyOrSpaces(text))
                return new ValidationInfo(message, textField);
            try {
                Integer.parseInt(text);
                return null;
            } catch (NumberFormatException e) {
                return new ValidationInfo(message, textField);
            }
        }).installOn(textField);
        setValidListener(textField);
    }
}
