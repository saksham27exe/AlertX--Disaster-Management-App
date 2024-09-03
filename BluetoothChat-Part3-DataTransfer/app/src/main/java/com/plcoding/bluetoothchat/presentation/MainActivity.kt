package com.plcoding.bluetoothchat.presentation

import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.bluetoothchat.presentation.components.ChatScreen
import com.plcoding.bluetoothchat.presentation.components.DeviceScreen
import com.plcoding.bluetoothchat.ui.theme.BluetoothChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val bluetoothManager by lazy {
        applicationContext.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val isBluetoothEnabled: Boolean
        get() = bluetoothAdapter?.isEnabled == true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BluetoothChatTheme {
                val viewModel = hiltViewModel<BluetoothViewModel>()
                val state by viewModel.state.collectAsState()

                LaunchedEffect(key1 = state.errorMessage) {
                    state.errorMessage?.let { message ->
                        Toast.makeText(
                            applicationContext,
                            message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                LaunchedEffect(key1 = state.isConnected) {
                    if (state.isConnected) {
                        Toast.makeText(
                            applicationContext,
                            "You're connected!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        when {
                            state.isConnecting -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    CircularProgressIndicator()
                                    Text(text = "Connecting...")
                                }
                            }
                            state.isConnected -> {
                                ChatScreen(
                                    state = state,
                                    onDisconnect = viewModel::disconnectFromDevice,
                                    onSendMessage = viewModel::sendMessage
                                )
                            }
                            else -> {
                                DeviceScreen(
                                    state = state,
                                    onStartScan = viewModel::startScan,
                                    onStopScan = viewModel::stopScan,
                                    onDeviceClick = viewModel::connectToDevice,
                                    onStartServer = viewModel::waitForIncomingConnections
                                )
                            }
                        }

                        // Add the button with the microphone icon at the top right
                        IconButton(
                            onClick = {
                                val intent = Intent()
                                intent.setClassName(
                                    "com.imptt.proptt",
                                    "com.imptt.proptt.ui.IntroActivity"
                                )
                                startActivity(intent)
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Mic,
                                contentDescription = "Open Another App"
                            )
                        }
                    }
                }
            }
        }
    }
}
