package android.thaihn.deeplinksample

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.deeplinksample.databinding.ActivityMainBinding
import android.thaihn.deeplinksample.deeplink.DeepLinkActivity
import android.thaihn.deeplinksample.R
import android.thaihn.deeplinksample.navigation.NavigationActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainBinding.btnDeepLink.setOnClickListener {
            startActivity(Intent(this, DeepLinkActivity::class.java))
        }

        mainBinding.btnNavigation.setOnClickListener {
            startActivity(Intent(this, NavigationActivity::class.java))
        }

        mainBinding.btnDynamicLink.setOnClickListener {
        }
    }
}
