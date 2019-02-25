package android.thaihn.deeplinksample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.deeplinksample.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val name = intent?.getStringExtra(MainActivity.Companion.Extras.EXTRAS_NAME.name)
                ?: "HAHAHA"
        val age = intent?.getIntExtra(MainActivity.Companion.Extras.EXTRAS_AGE.name, 0)

        detailBinding.tvAge.setText("$age +")
        detailBinding.tvName.text = name
    }
}
