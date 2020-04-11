package com.azhara.asyncawait_coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener {
            setText("Cliked!! Start Process")

            fakeApiRequest()
        }
    }

    private fun fakeApiRequest() {

//        val startTime = System.currentTimeMillis()
//
//        val proccessTime = CoroutineScope(Dispatchers.IO).launch {
//            val job1 = launch {
//                val time1 = measureTimeMillis {
//                    println("debug: launching job1 in thread: ${Thread.currentThread().name} ")
//                    val result1 = getFakeApi1()
//                    setTextOnMainThread(result1)
//                }
//                println("debug: process job1 complete in $time1 ms")
//            }
//
//            val job2 = launch {
//                val time2 = measureTimeMillis {
//                    println("debug: launching job2 in thread: ${Thread.currentThread().name} ")
//                    val result2 = getFakeApi2()
//                    setTextOnMainThread(result2)
//                }
//                println("debug: process job2 complete in $time2 ms")
//            }
//
//
//        }
//        proccessTime.invokeOnCompletion {
//            println("debug: Total time process in job 1 and job 2 ${System.currentTimeMillis()- startTime}")
//        }


        // Proses menggunakan async await
        CoroutineScope(Dispatchers.IO).launch {
            val timeProcess = measureTimeMillis {

                //Penggunaan async selalu mengembalikan nilai Deferred
                val result1 = async {
                    println("debug: job 1 run in the ${Thread.currentThread().name}")
                    getFakeApi1()
                }

                val result2 = async {
                    println("debug: job 2 run in the ${Thread.currentThread().name}")
                    getFakeApi2()
                }

                // Untuk mengambil nilai yang dikembalikan async (Deferred) anda harus menangkapnya dengan await()
                setTextOnMainThread("Get data job1 : ${result1.await()}")
                setTextOnMainThread("Get data job2 : ${result2.await()}")
            }

            println("debug: Total time process job1 and job 2 : $timeProcess")

        }


    }

    private fun setText(inputText: String){
        val newText = text_process.text.toString() + "\n$inputText"
        text_process.text = newText
    }

    private suspend fun setTextOnMainThread(input: String){
        withContext(Dispatchers.Main){
            setText(input)
        }
    }

    private suspend fun getFakeApi1(): String{
        delay(1000)
        return "This is Api 1"
    }

    private suspend fun getFakeApi2(): String{
        delay(1600)
        return "this is Api 2"
    }
}
