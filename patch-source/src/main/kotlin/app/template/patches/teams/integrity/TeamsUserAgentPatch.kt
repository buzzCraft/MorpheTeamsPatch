package app.template.patches.teams.integrity

import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.shared.Constants.TEAMS_COMPATIBILITY
import app.template.patches.teams.integrity.webViewAuthSetUpWebViewFingerprint
import app.morphe.patcher.util.registersUsed

// Desktop Chrome user agent string to spoof as desktop browser
private const val DESKTOP_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"

@Suppress("unused")
val teamsUserAgentPatch = bytecodePatch(
    name = "Desktop User-Agent for Login",
    description = "Spoofs the WebView user agent to a desktop Chrome user agent during Microsoft login, " +
        "allowing login as a desktop client instead of mobile. Works with conditional access policies that block mobile user agents.",
    default = true,
) {
    compatibleWith(TEAMS_COMPATIBILITY)

    execute {
        // Find the setUserAgentString call and inject our desktop user agent before it
        webViewAuthSetUpWebViewFingerprint.matchAllMethodIndicesForEach { index ->
            // The fingerprint filter "setUserAgentString" matches the method call
            // The instruction at this index is invoke-virtual {v0, v1}, Landroid/webkit/WebSettings;->setUserAgentString(Ljava/lang/String;)V
            // v0 = WebSettings instance, v1 = user agent string parameter
            // Use registersUsed to get the registers regardless of invoke format (regular vs range)
            val registers = registersUsed(index)
            
            // For instance method calls, registersUsed returns [instanceRegister, param1, param2, ...]
            // So the user agent string is at index 1 (the first parameter after the instance)
            val userAgentRegister = registers.getOrElse(1) { 1 }
            
            // Insert const-string before the invoke to set our desktop user agent
            addInstructions(index, """
                const-string v$userAgentRegister, "$DESKTOP_USER_AGENT"
            """)
        }
    }
}

@JvmSynthetic
internal fun teamsUserAgentDependency() = teamsUserAgentPatch