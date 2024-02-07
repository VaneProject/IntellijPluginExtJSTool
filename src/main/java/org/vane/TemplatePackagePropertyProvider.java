package org.vane;

import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TemplatePackagePropertyProvider implements DefaultTemplatePropertiesProvider {
    @Override
    public void fillProperties(@NotNull PsiDirectory psiDirectory, @NotNull Properties properties) {
        List<String> root = new ArrayList<>();
        if (findApplication(root, psiDirectory)) {
            Collections.reverse(root);
            properties.setProperty("MAP", psiDirectory.getName());
            properties.setProperty("PATH", String.join(".", root));
        }
    }

    private boolean findApplication(List<String> root, @NotNull PsiDirectory psiDirectory) {
        PsiFile psiFile = psiDirectory.findFile("Application.js");
        if (psiFile == null) {
            PsiDirectory parent = psiDirectory.getParent();
            if (parent == null)
                return false;
            root.add(psiDirectory.getName());
            return findApplication(root, parent);
        } else
            return true;
    }
}
