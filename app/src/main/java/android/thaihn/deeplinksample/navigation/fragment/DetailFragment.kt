package android.thaihn.deeplinksample.navigation.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.thaihn.deeplinksample.databinding.FragmentDetailBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.thaihn.deeplinksample.R

class DetailFragment : Fragment() {

    private lateinit var detailBinding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        detailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return detailBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData(arguments)
    }

    private fun getData(args: Bundle?) {
        args?.let {
            val title = DetailFragmentArgs.fromBundle(it).title
            detailBinding.tvDetail.text = title
        }
    }
}