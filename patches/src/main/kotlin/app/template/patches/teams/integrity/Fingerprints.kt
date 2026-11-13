package app.template.patches.teams.integrity

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags

// =============================================================================
// Microsoft Teams — WebView User-Agent Fingerprints
// =============================================================================

// ---------------------------------------------------------------------------
// WebViewAuthorizationFragment.setUpWebView (MSAL)
// ---------------------------------------------------------------------------
// This method configures the WebView used for authentication.
// It calls WebSettings.setUserAgentString() to set the user agent.
// We inject a const-string with a desktop user agent before that call.
internal val webViewAuthSetUpWebViewFingerprint = Fingerprint(
    definingClass = "Lcom/microsoft/identity/common/internal/providers/oauth2/WebViewAuthorizationFragment;",
    name = "setUpWebView",
    returnType = "V",
    accessFlags = listOf(AccessFlags.PRIVATE),
    parameters = listOf(
        "Landroid/webkit/WebView;",
    ),
    filters = listOf(
        methodCall(
            definingClass = "Landroid/webkit/WebSettings;",
            name = "setUserAgentString",
            parameters = listOf("Ljava/lang/String;"),
            returnType = "V",
        ),
    ),
)