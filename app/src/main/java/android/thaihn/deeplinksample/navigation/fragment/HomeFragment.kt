package android.thaihn.deeplinksample.navigation.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.thaihn.deeplinksample.R
import android.thaihn.deeplinksample.databinding.FragmentHomeBinding
import androidx.navigation.fragment.NavHostFragment

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return homeBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeBinding.btnOpenContact.setOnClickListener {
            val action = HomeFragmentDirections.openContact(1, "Hoang Ngoc Thai")
            NavHostFragment.findNavController(this).navigate(action)
        }
    }
}
