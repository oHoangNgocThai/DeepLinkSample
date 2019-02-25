package android.thaihn.deeplinksample

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.deeplinksample.databinding.ActivityMainBinding
import android.thaihn.deeplinksample.deeplink.DeepLinkActivity

class MainActivity : AppCompatActivity() {

    companion object {
        enum class Extras {
            EXTRAS_NAME,
            EXTRAS_AGE
        }
    }

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainBinding.btnSend.setOnClickListener {
            startActivity(Intent(this, DeepLinkActivity::class.java).apply {
                putExtra(Extras.EXTRAS_NAME.name, "Hoang Ngoc Thai")
                putExtra(Extras.EXTRAS_AGE.name, 20)
            })
        }
    }
}
