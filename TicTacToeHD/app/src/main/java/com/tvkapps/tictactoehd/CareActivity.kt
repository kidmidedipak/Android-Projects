package com.tvkapps.tictactoehd

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.tvkapps.tictactoehd.databinding.ActivityCareBinding

class CareActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCareBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.message.setOnClickListener {
            openUrl("https://tictactoe-hd.web.app/")
        }






        val careActivity = findViewById<ImageView>(R.id.careback)
        careActivity.setOnClickListener {
            val Intent = Intent(this,MainActivity::class.java)
            startActivity(Intent)
        }

    }

    private fun openUrl(link: String) {

        val uri = Uri.parse(link)
        val inte = Intent(Intent.ACTION_VIEW, uri)

        startActivity(inte)
    }


}