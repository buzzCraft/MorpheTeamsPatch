package app.template.patches.teams.integrity

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
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
        // Match on method name only. Constraining definingClass/parameters/returnType
        // too tightly made this fail to match on a real device (reported: Teams
        // 1416/1.0.0.2026152302) — the call may go through a WebSettings subtype,
        // a different overload, or otherwise not look exactly like the SDK signature.
        methodCall(
            name = "setUserAgentString",
        ),
    ),
)