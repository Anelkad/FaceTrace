package com.example.facetrace.ui.search_result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.domain.model.SearchResult
import com.example.facetrace.R
import com.example.facetrace.base.ResultIntentData
import com.example.facetrace.base.constants.IntentConstants
import com.example.facetrace.base.extensions.hide
import com.example.facetrace.base.extensions.show
import com.example.facetrace.databinding.FragmentSearchResultBinding

class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private var resultIntentData: ResultIntentData? = null
    lateinit var binding: FragmentSearchResultBinding

    companion object {
        @JvmStatic
        fun newInstance(result: List<SearchResult>) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(IntentConstants.SEARCH_RESULT,
                        ResultIntentData(result = result)
                    )
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resultIntentData = it.getSerializable(IntentConstants.SEARCH_RESULT) as? ResultIntentData?
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchResultBinding.bind(view)

        val dataList = resultIntentData?.result ?: emptyList()

        binding.apply {
            rvSearchResult.itemAnimator = null
            rvSearchResult.adapter = SearchResultAdapter(dataList)
            if (dataList.isEmpty()) tvDataEmpty.show() else tvDataEmpty.hide()
        }
    }

}