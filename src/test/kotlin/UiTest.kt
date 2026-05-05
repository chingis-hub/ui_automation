import com.intellij.driver.sdk.ui.components.UiComponent.Companion.waitFound
import com.intellij.driver.sdk.ui.components.common.ideFrame
import com.intellij.driver.sdk.ui.components.elements.checkBoxWithName
import com.intellij.driver.sdk.ui.components.settings.settingsDialog
import com.intellij.ide.starter.config.ConfigurationStorage
import com.intellij.ide.starter.config.splitMode
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.ide.IdeProductProvider
import com.intellij.ide.starter.junit5.hyphenateWithClass
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.project.GitHubProject
import com.intellij.ide.starter.runner.CurrentTestMethod
import com.intellij.ide.starter.runner.Starter
import com.intellij.ide.starter.sdk.JdkDownloaderFacade.jdk21
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.time.Duration.Companion.minutes

class UiTest {

    @ParameterizedTest(name = "split-mode={0}")
    @ValueSource(booleans = [false, true])
    fun createChangelistsAutomaticallyCheckboxCanBeEnabled(splitMode: Boolean) {
        ConfigurationStorage.splitMode(splitMode)

        val testContext = Starter
            .newContext(CurrentTestMethod.hyphenateWithClass(), TestCase(
                IdeProductProvider.IU, GitHubProject.fromGithub(
                branchName = "master",
                repoRelativeUrl = "project-mirai/mirai-hello-world.git",
                commitHash = "c9b3bfe53ed71e346328ca447a21ec00b10e7793"
            )))
            .setupSdk(jdk21.toSdk())
            .setLicense(System.getenv("LICENSE_KEY"))
            .prepareProjectCleanImport()

        testContext.runIdeWithDriver().useDriverAndCloseIde {
            ideFrame {
                waitForIndicators(5.minutes)
                openSettingsDialog()
                settingsDialog {
                    waitFound()

                    openTreeSettingsSection("Version Control", "Changelists")

                    val checkbox = checkBoxWithName("Create changelists automatically")
                    checkbox.waitFound()

                    checkbox.uncheck()
                    checkbox.check()

                    assertTrue(checkbox.isSelected()) { "Expected 'Create changelists automatically' to be selected" }

                    okButton.click()
                }
            }
        }
    }
}