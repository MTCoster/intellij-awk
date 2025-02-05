package intellij_awk;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;
import java.util.Optional;

public class AwkInspectionTests extends BasePlatformTestCase {

  private final Inspection unusedFunctionParam =
      new Inspection(
          new AwkInspectionUnusedFunctionParam(), AwkInspectionUnusedFunctionParam.QUICK_FIX_NAME);

  private final Inspection unusedFunction =
      new Inspection(new AwkInspectionUnusedFunction(), AwkInspectionUnusedFunction.QUICK_FIX_NAME);

  private final Inspection unusedGlobalVariable =
      new Inspection(
          new AwkInspectionUnusedGlobalVariable(),
          AwkInspectionUnusedGlobalVariable.QUICK_FIX_NAME);

  private final Inspection unresolvedFunctionCall =
      new Inspection(
          new AwkInspectionUnresolvedFunction(), AwkInspectionUnresolvedFunction.QUICK_FIX_NAME);

  private final Inspection duplicateFunction =
      new Inspection(new AwkInspectionDuplicateFunction(), null);

  private final Inspection declareLocal =
      new Inspection(
          new AwkInspectionDeclareLocalVariable(),
          AwkInspectionDeclareLocalVariable.QUICK_FIX_NAME);

  private final Inspection enforceGlobalVarNaming =
      new Inspection(
          new AwkInspectionEnforceGlobalVariableNaming(),
          AwkInspectionEnforceGlobalVariableNaming.QUICK_FIX_NAME);

  public void testUnusedFunctionParam1() {
    checkByFile(unusedFunctionParam);
  }

  public void testUnusedFunctionParam2_0() {
    checkByFile(unusedFunctionParam);
  }

  public void testUnusedFunctionParam2_1() {
    checkByFile(unusedFunctionParam);
  }

  public void testUnusedFunctionParam3_0() {
    checkByFile(unusedFunctionParam);
  }

  public void testUnusedFunctionParam3_1() {
    checkByFile(unusedFunctionParam);
  }

  public void testUnusedFunctionParam4() {
    checkByFile(unusedFunctionParam);
  }

  public void testUnusedFunction1() {
    checkByFile(unusedFunction);
  }

  public void testUnusedGlobalVar1_0() {
    checkByFile(unusedGlobalVariable);
  }

  public void testUnusedGlobalVar1_1() {
    checkByFile(unusedGlobalVariable);
  }

  public void testUnusedGlobalVar2_0() {
    checkByFile(unusedGlobalVariable);
  }

  public void testUnusedGlobalVar2_1() {
    checkByFile(unusedGlobalVariable);
  }

  public void testUnusedGlobalVar2_2() {
    checkByFile(unusedGlobalVariable);
  }

  public void testUnusedGlobalVar3() {
    checkByFile(unusedGlobalVariable);
  }

  public void testUnusedGlobalVar4() {
    checkByFile(unusedGlobalVariable);
  }

  public void testUnusedGlobalVarNoProblem1() {
    checkByFileNoProblemAtCaret(unusedGlobalVariable);
  }

  public void testUnusedGlobalVarNoProblem2() {
    checkByFileNoProblemAtCaret(unusedGlobalVariable);
  }

  public void testUnusedGlobalVarNoProblem3() {
    checkByFileNoProblemAtCaret(unusedGlobalVariable);
  }

  public void testUnusedGlobalVarNoProblem4() {
    checkByFileNoProblemAtCaret(unusedGlobalVariable);
  }

  public void testUnusedGlobalVarNoProblem5() {
    checkByFileNoProblemAtCaret(unusedGlobalVariable);
  }

  public void testUnusedGlobalVarNoProblem6() {
    checkByFileNoProblemAtCaret(unusedGlobalVariable);
  }

  public void testDeclareLocal1_0() {
    checkByFile(declareLocal);
  }

  public void testDeclareLocal1_1() {
    // checks that we only report on first same variable occurrence
    checkByFileNoProblemAtCaret(declareLocal);
  }

  public void testDeclareLocal2_0() {
    checkByFile(declareLocal);
  }

  public void testDeclareLocal2_1() {
    checkByFile(declareLocal);
  }

  public void testDeclareLocal2_2() {
    checkByFile(declareLocal);
  }

  public void testDeclareLocal2_3() {
    checkByFile(declareLocal);
  }

  public void testDeclareLocal2_4() {
    checkByFile(declareLocal);
  }

  public void testDeclareLocalNoProblem1() {
    checkByFileNoProblemAtCaret(declareLocal);
  }

  public void testDeclareLocalNoProblem2() {
    checkByFileNoProblemAtCaret(declareLocal);
  }

  public void testDupFunctionsNotReportedUnused() {
    // dup functions should not imply unused
    checkByFileNoProblemAtCaret(unusedFunction);
  }

  public void testDupFunctions1() {
    checkByFile(duplicateFunction);
  }

  public void testDupFunctions2() {
    checkByFile(duplicateFunction);
  }

  public void testUnresolvedFunctionCall1() {
    checkByFile(unresolvedFunctionCall);
  }

  public void testUnresolvedFunctionCall2() {
    checkByFileNoProblemAtCaret(unresolvedFunctionCall);
  }

  public void testUnresolvedFunctionCall3() {
    checkByFileNoProblemAtCaret(unresolvedFunctionCall);
  }

  public void testEnforceGlobalVarNaming1() {
    checkByFile(enforceGlobalVarNaming);
  }

  public void testEnforceGlobalVarNaming2() {
    checkByFile(enforceGlobalVarNaming);
  }

  public void testEnforceGlobalVarNaming3() {
    checkByFile(enforceGlobalVarNaming);
  }

  @Override
  protected String getTestDataPath() {
    return "src/test/testData/inspection";
  }

  private void checkByFileNoProblemAtCaret(Inspection inspection) {
    String before = getTestName(true) + ".awk";
    myFixture.configureByFile(before);

    myFixture.enableInspections(inspection.inspection);

    List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

    int caretOffset = myFixture.getCaretOffset();

    Optional<HighlightInfo> highlightInfoAtCaretOpt =
        highlightInfos.stream()
            .filter(
                highlightInfo ->
                    highlightInfo.startOffset <= caretOffset
                        && highlightInfo.endOffset >= caretOffset)
            .findAny();

    assertTrue(highlightInfoAtCaretOpt.isEmpty());
  }

  private void checkByFile(Inspection inspection) {
    String before = getTestName(true) + ".awk";
    String after = before.replace(".awk", "After.awk");
    myFixture.configureByFile(before);

    myFixture.enableInspections(inspection.inspection);
    List<HighlightInfo> highlightInfos = myFixture.doHighlighting();
    assertFalse(highlightInfos.isEmpty());

    if (inspection.quickFixName != null) {
      // Get the quick fix action for comparing references inspection and apply it to the file
      final IntentionAction action = myFixture.findSingleIntention(inspection.quickFixName);
      assertNotNull(action);
      myFixture.launchAction(action);

      myFixture.checkResultByFile(after, true);
    }
  }

  private static class Inspection {
    final LocalInspectionTool inspection;
    final String quickFixName;

    Inspection(LocalInspectionTool inspection, String quickFixName) {
      this.inspection = inspection;
      this.quickFixName = quickFixName;
    }
  }
}
