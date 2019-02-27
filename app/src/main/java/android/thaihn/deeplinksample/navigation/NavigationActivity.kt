package android.thaihn.deeplinksample.navigation

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.deeplinksample.R
import android.thaihn.deeplinksample.databinding.ActivityNavigationBinding
import androidx.navigation.findNavController

class NavigationActivity : AppCompatActivity() {

    private lateinit var navigationBinding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationBinding = DataBindingUtil.setContentView(this, R.layout.activity_navigation)

    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.nav_host_fragment).navigateUp()
}
