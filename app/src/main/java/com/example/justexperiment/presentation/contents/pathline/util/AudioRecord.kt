package com.example.justexperiment.presentation.contents.pathline.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SampleAudioViewModel : ViewModel() {

    val smplRateHz = 44100
    val chnlConfig = AudioFormat.CHANNEL_IN_MONO
    val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    val bufferSize = AudioRecord.getMinBufferSize(smplRateHz, chnlConfig, audioFormat)


    // Sample Audio Buffer
    private val _sampleAudioBuffer = MutableStateFlow(ShortArray(bufferSize))
    val sampleBufferState = _sampleAudioBuffer.asStateFlow()

    // RMS
    val rms = MutableStateFlow(null as Double?)
    val shouldStop = MutableStateFlow(false)

    fun startRecording(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                smplRateHz,
                chnlConfig,
                audioFormat,
                bufferSize
            )


            audioRecord.startRecording()

            val buffer = ShortArray(bufferSize)

            while (shouldStop.value) {
                val readCount = audioRecord.read(buffer, 0, bufferSize)
                if (readCount > 0) {
                    _sampleAudioBuffer.value = buffer.copyOf(readCount)
                    rms.value = computeRms(buffer, readCount)
                }
            }

            audioRecord.stop()
            audioRecord.release()
            return@launch
        }
    }


     fun computeRms(buffer: ShortArray, readCount: Int): Double {
        if (readCount == 0) return 0.0
        var sum = 0.0
        for (i in 0 until readCount) {
            val sample = buffer[i].toDouble()
            sum += sample * sample
        }
        return kotlin.math.sqrt(sum / readCount)
    }


}
