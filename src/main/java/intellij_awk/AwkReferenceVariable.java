package intellij_awk;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import intellij_awk.psi.*;
import intellij_awk.psi.impl.AwkItemImpl;
import intellij_awk.psi.impl.AwkUserVarNameImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AwkReferenceVariable extends PsiReferenceBase<AwkUserVarNameImpl>
    implements PsiPolyVariantReference {


  public AwkReferenceVariable(@NotNull AwkUserVarNameImpl element, TextRange rangeInElement) {
    super(element, rangeInElement);
  }

  @Override
  public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
    List<ResolveResult> res = new ArrayList<>();

    PsiElement ref = resolveFunctionArgument(myElement);
    if (ref == null) {
      ref = resolveGlobalVariable(myElement);
    }
    if (ref != null) {
      res.add(new PsiElementResolveResult(ref));
    }
    return res.toArray(new ResolveResult[0]);
  }

  private PsiElement resolveFunctionArgument(AwkUserVarNameImpl userVarName) {
    PsiElement parent = userVarName;
    while (true) {
      parent = parent.getParent();
      if (parent == null || parent instanceof AwkFile) {
        break;
      }
      if (parent instanceof AwkItemImpl) {
        AwkItemImpl awkItem = (AwkItemImpl) parent;
        if (awkItem.getFunctionName() != null) {
          AwkParamList paramList = awkItem.getParamList();
          if (paramList != null) {
            List<AwkUserVarName> userVarNameList = paramList.getUserVarNameList();
            for (AwkUserVarName awkUserVarName : userVarNameList) {
              PsiElement varName = awkUserVarName.getVarName();
              if (varName.getText().equals(userVarName.getName())) {
                return varName;
              }
            }
          }
        }
      }
    }
    return null;
  }

  private PsiElement resolveGlobalVariable(AwkUserVarNameImpl userVarName) {
    AwkFile awkFile = (AwkFile) userVarName.getContainingFile();

    for (PsiElement child : awkFile.getChildren()) {
      if (child instanceof AwkItem) {
        AwkItem awkItem = (AwkItem) child;
        AwkPattern pattern = awkItem.getPattern();
        if (pattern != null) {
          AwkBeginOrEnd beginOrEnd = pattern.getBeginOrEnd();
          if (beginOrEnd != null) {
             if (AwkTypes.BEGIN.equals(beginOrEnd.getFirstChild().getNode().getElementType())) {
               AwkAction action = awkItem.getAction();


             }
          }
        }
      }
    }

    return null;
  }

  /** Resolves references to a single result, or fails. */
  @Override
  public @Nullable PsiElement resolve() {
    ResolveResult[] resolveResults = multiResolve(false);
    return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
  }
}
