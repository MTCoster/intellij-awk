package intellij_awk.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import intellij_awk.AwkReferenceVariable;
import intellij_awk.psi.AwkElementFactory;
import intellij_awk.psi.AwkUserVarName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class AwkUserVarNameMixin extends AwkNamedElementImpl implements AwkUserVarName {
  public AwkUserVarNameMixin(@NotNull ASTNode node) {
    super(node);
  }

  public PsiElement setName(String newName) {
    replace(AwkElementFactory.createUserVarName(getProject(), newName));
    return this;
  }

  @Override
  public PsiReference getReference() {
    return new AwkReferenceVariable(
        (AwkUserVarNameImpl) this, TextRange.from(0, getName().length()));
  }

  @Override
  public @Nullable Icon getIcon(int flags) {
    return null;
  }
}
