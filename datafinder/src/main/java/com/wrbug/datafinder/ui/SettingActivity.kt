package com.wrbug.datafinder.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.wrbug.datafinder.R
import com.wrbug.datafinder.data.ConfigDataManager
import com.wrbug.datafinder.data.GlobalEnv
import com.wrbug.datafinder.server.ServerManager
import com.wrbug.datafinder.server.ServerStatus
import com.wrbug.datafinder.server.ServerStatusListener
import com.wrbug.datafinder.startup.DataFinderService
import com.wrbug.datafinder.util.showToast
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), ServerStatusListener {
    private var saveMenu: MenuItem? = null
    private var startMenu: MenuItem? = null
    private var stopMenu: MenuItem? = null
    private var originServerPort: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        originServerPort = ConfigDataManager.getServerPort()
        portSettingEt.setText(originServerPort.toString())
        ServerManager.instance.addListener(this)
        portSettingEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    val port = s.toString().toInt()
                    saveMenu?.isVisible = port != originServerPort
                } finally {

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }


    override fun onDestroy() {
        ServerManager.instance.removeListener(this)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        saveMenu = menu?.findItem(R.id.saveMenu)
        startMenu = menu?.findItem(R.id.startServerMenu)
        stopMenu = menu?.findItem(R.id.stopServerMenu)
        saveMenu?.isVisible = false
        resetMenu(GlobalEnv.serverStatus)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) {
            return super.onOptionsItemSelected(item)
        }
        when (item.itemId) {
            R.id.saveMenu -> {
                saveSetting()
            }
            R.id.startServerMenu -> {
                if (GlobalEnv.serverStatus != ServerStatus.Running) {
                    DataFinderService.startServer(this)
                }
            }
            R.id.stopServerMenu -> {
                if (GlobalEnv.serverStatus == ServerStatus.Running) {
                    DataFinderService.stopServer(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveSetting() {
        val port = portSettingEt.text.toString().toInt()
        ConfigDataManager.saveServerPort(port)
        originServerPort = port
        saveMenu?.isVisible = false
        showToast("保存成功")
        DataFinderService.restartServer(this)
    }

    private fun resetMenu(serverStatus: ServerStatus) {
        when (serverStatus) {
            ServerStatus.StartFailed, ServerStatus.Stop -> {
                startMenu?.isEnabled = true
                stopMenu?.isEnabled = false
            }
            ServerStatus.Running -> {
                startMenu?.isEnabled = false
                stopMenu?.isEnabled = true
            }
        }
    }

    override fun onStarted() {
        showToast("服务已开启")
        resetMenu(ServerStatus.Running)
    }

    override fun onStopped() {
        showToast("服务已关闭")
        resetMenu(ServerStatus.Stop)
    }

    override fun onException(e: Exception?) {
        resetMenu(ServerStatus.StartFailed)
    }
}
