package com.privdnstoggle.app

import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Regression tests for the Settings.Global and SharedPreferences paths of [DnsManager].
 * Runs on the JVM under Robolectric; no device or emulator needed.
 * Run with: ./gradlew :app:testDebugUnitTest
 * or Run Tests on this class in Android Studio (green play icon next to class or method).
 */
@RunWith(RobolectricTestRunner::class)
class DnsManagerSettingsTest {

    private val context: Context
        get() = RuntimeEnvironment.getApplication()

    private val resolver: ContentResolver
        get() = context.contentResolver

    // --- Reading system state (must never throw; falls back to safe defaults) ---

    @Test
    fun getCurrentMode_unset_returnsOff() {
        assertEquals(DnsManager.MODE_OFF, DnsManager.getCurrentMode(resolver))
    }

    @Test
    fun getCurrentHostname_unset_returnsEmpty() {
        assertEquals("", DnsManager.getCurrentHostname(resolver))
    }

    @Test
    fun isActive_opportunisticMode_returnsFalse() {
        Settings.Global.putString(resolver, "private_dns_mode", DnsManager.MODE_OPPORTUNISTIC)
        assertFalse(DnsManager.isActive(resolver))
    }

    // --- Writing system state ---

    @Test
    fun enableDns_writesHostnameModeAndSpecifier() {
        assertTrue(DnsManager.enableDns(resolver, "dns.example.com"))
        // Assert through the raw keys so a renamed settings key is caught
        assertEquals(DnsManager.MODE_HOSTNAME, Settings.Global.getString(resolver, "private_dns_mode"))
        assertEquals("dns.example.com", Settings.Global.getString(resolver, "private_dns_specifier"))
        assertTrue(DnsManager.isActive(resolver))
        assertEquals("dns.example.com", DnsManager.getCurrentHostname(resolver))
    }

    @Test
    fun disableDns_setsModeOff_keepsSpecifier() {
        DnsManager.enableDns(resolver, "dns.example.com")
        assertTrue(DnsManager.disableDns(resolver))
        assertEquals(DnsManager.MODE_OFF, DnsManager.getCurrentMode(resolver))
        assertFalse(DnsManager.isActive(resolver))
        // Current behavior: disabling leaves the specifier in place
        assertEquals("dns.example.com", DnsManager.getCurrentHostname(resolver))
    }

    // --- Toggle (Quick Settings tile entry point) ---

    @Test
    fun toggle_whenOff_enablesSavedHostname() {
        DnsManager.saveHostname(context, "dns.nextdns.io")
        assertTrue(DnsManager.toggle(context))
        assertTrue(DnsManager.isActive(resolver))
        assertEquals("dns.nextdns.io", DnsManager.getCurrentHostname(resolver))
    }

    @Test
    fun toggle_whenOff_noSavedHostname_usesDefault() {
        assertTrue(DnsManager.toggle(context))
        assertTrue(DnsManager.isActive(resolver))
        assertEquals("dns.adguard.com", DnsManager.getCurrentHostname(resolver))
    }

    @Test
    fun toggle_whenActive_disables() {
        DnsManager.enableDns(resolver, "dns.example.com")
        assertTrue(DnsManager.toggle(context))
        assertFalse(DnsManager.isActive(resolver))
        assertEquals(DnsManager.MODE_OFF, DnsManager.getCurrentMode(resolver))
    }

    @Test
    fun toggle_blankSavedHostname_returnsFalseAndStaysOff() {
        DnsManager.saveHostname(context, "   ")
        assertFalse(DnsManager.toggle(context))
        assertEquals(DnsManager.MODE_OFF, DnsManager.getCurrentMode(resolver))
        assertNull(Settings.Global.getString(resolver, "private_dns_specifier"))
    }

    // --- SharedPreferences for saved hostname ---

    @Test
    fun getSavedHostname_default_isAdguard() {
        assertEquals("dns.adguard.com", DnsManager.getSavedHostname(context))
    }

    @Test
    fun saveHostname_trimsWhitespace() {
        DnsManager.saveHostname(context, "  dns.example.com  ")
        assertEquals("dns.example.com", DnsManager.getSavedHostname(context))
    }

    // --- Permission probe ---

    @Test
    fun hasPermission_grantedPath_preservesCurrentMode() {
        DnsManager.enableDns(resolver, "dns.example.com")
        assertTrue(DnsManager.hasPermission(context))
        // The probe writes the current mode back; it must not change it
        assertEquals(DnsManager.MODE_HOSTNAME, DnsManager.getCurrentMode(resolver))
        assertEquals("dns.example.com", DnsManager.getCurrentHostname(resolver))
    }
}
