package com.example.apicalling

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
   lateinit  var progress :ProgressBar;
    lateinit var imageView :ImageView
    var img : String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progress=findViewById<ProgressBar>(R.id.progressBar)
        imageView = findViewById<ImageView>(R.id.imageView)
        getMems()

    }

    fun getMems() {
        progress.visibility=View.VISIBLE
        var jsonQ = Volley.newRequestQueue(this)
       lateinit var url :String
        val jsonObj =
            JsonObjectRequest(Request.Method.GET, "https://meme-api.herokuapp.com/gimme/2", null,
                { response ->
                    Log.d("sorabh", "success")
                     url = response.getJSONArray("memes").getJSONObject(0).getString("url")
                    Log.d("sorabh", url)
                    img=url
                    Glide.with(this).load(url).into(imageView)
                    progress.visibility=View.GONE

                },
                { error ->
                    Log.d("sorabh", error.toString())

                }
            );
        jsonQ.add(jsonObj)
    }



    fun nextMem(view: View) {
      getMems()
    }

    fun shareMem(view: View) {
        var intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
intent.putExtra(Intent.EXTRA_TEXT,img)
        startActivity(Intent.createChooser(intent,"Share Using :"))
    }

}


