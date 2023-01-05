package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


//const val SETTINGS_PACKAGE = "com.android.settings"
const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"

const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class ChangeTextTest {

    private lateinit var device: UiDevice
    private val textToSet = "Netology"


    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

    @Before
    fun beforeEachTest() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)

    }

    @Test
    fun testChangeTextNew() {
        waitForPackage(MODEL_PACKAGE)

        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = textToSet
        device.findObject(By.res(MODEL_PACKAGE, "buttonChange")).click()
        val result = device.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")).text

        assertEquals(textToSet, result)
    }

    @Test
    fun testInstallEmptyString() {
        val textToSetEmpty = ""
        waitForPackage(MODEL_PACKAGE)

        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = textToSet
        device.findObject(By.res(MODEL_PACKAGE, "buttonChange")).click()
        val result = device.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")).text

        assertEquals(textToSet, result)

        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = textToSetEmpty
        device.findObject(By.res(MODEL_PACKAGE, "buttonChange")).click()
        val resultNew = device.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")).text

        assertEquals(textToSet, resultNew)
    }

    @Test
    fun testInstallSpace() {
        val textToSetSpace = "    "
        waitForPackage(MODEL_PACKAGE)

        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = textToSet
        device.findObject(By.res(MODEL_PACKAGE, "buttonChange")).click()
        val result = device.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")).text

        assertEquals(textToSet, result)

        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = textToSetSpace
        device.findObject(By.res(MODEL_PACKAGE, "buttonChange")).click()
        val resultNew = device.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")).text

        assertEquals(textToSet, resultNew)
    }

    @Test
    fun testOpenTextInNewActivity() {
        waitForPackage(MODEL_PACKAGE)

        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = textToSet
        device.findObject(By.res(MODEL_PACKAGE, "buttonActivity")).click()
        device.wait(Until.hasObject(By.res(MODEL_PACKAGE, "text")), TIMEOUT)
        val result = device.findObject(By.res(MODEL_PACKAGE, "text")).text

        assertEquals(textToSet, result)
    }
}



