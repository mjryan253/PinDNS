package com.privdnstoggle.app

import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast

class DnsTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        syncTileState()
    }

    override fun onClick() {
        super.onClick()

        val success = DnsManager.toggle(this)
        if (!success) {
            Toast.makeText(
                this,
                "Permission denied. Grant WRITE_SECURE_SETTINGS via ADB.",
                Toast.LENGTH_LONG
            ).show()
        }
        syncTileState()
    }

    private fun syncTileState() {
        val tile = qsTile ?: return
        val active = DnsManager.isActive(contentResolver)

        tile.state = if (active) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        // Tile.setSubtitle exists from API 29; minSdk is 28
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tile.subtitle = if (active) {
                DnsManager.getCurrentHostname(contentResolver)
            } else {
                "Off"
            }
        }
        tile.updateTile()
    }
}
