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
//
// The fingerprint is intentionally loose (no definingClass, name, accessFlags,
// parameters, returnType) because the exact signature varies across builds.
// The methodCall filter for setUserAgentString uniquely identifies it.
internal val webViewAuthSetUpWebViewFingerprint = Fingerprint(
    filters = listOf(
        methodCall(
            name = "setUserAgentString",
        ),
    ),
)