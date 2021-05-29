package intellij_awk;

import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;

public class AwkCodeStyleSettingsProviderLanguage extends LanguageCodeStyleSettingsProvider {

  @NotNull
  @Override
  public Language getLanguage() {
    return AwkLanguage.INSTANCE;
  }

  @Override
  public void customizeSettings(
      @NotNull CodeStyleSettingsCustomizable consumer, @NotNull SettingsType settingsType) {
    if (settingsType == SettingsType.SPACING_SETTINGS) {
      consumer.showStandardOptions("SPACE_AROUND_ASSIGNMENT_OPERATORS");
      consumer.renameStandardOption("SPACE_AROUND_ASSIGNMENT_OPERATORS", "Separator");
    } else if (settingsType == SettingsType.BLANK_LINES_SETTINGS) {
      consumer.showStandardOptions("KEEP_BLANK_LINES_IN_CODE");
    }
  }

  @Override
  public String getCodeSample(@NotNull SettingsType settingsType) {
    return "BEGIN {\n"
        + "    if (5>2) {\n"
        + "        print \"Hello world\"\n"
        + "    }\n"
        + "}\n"
        + "\n"
        + "function inc(i) {\n"
        + "    return i+1\n"
        + "}\n";
  }
}
