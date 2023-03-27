package com.example.ustudy.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ustudy.databinding.FragmentStatisticsBinding
import com.example.ustudy.ui.viewmodels.StatisticsViewModel
import com.example.ustudy.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatisticsFragment : Fragment() {
    private var _statisticsBinding: FragmentStatisticsBinding? = null
    private val statisticsBinding: FragmentStatisticsBinding
        get() = checkNotNull(_statisticsBinding) {
            Util.bindingErrorMessage("statistics")
        }
    private val statisticsViewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _statisticsBinding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return statisticsBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            statisticsViewModel.currentStreak.collect {
                statisticsBinding.tvLearningStreak.text = "Learning streak : $it"
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            statisticsViewModel.daysLearned.collect {
                statisticsBinding.tvLearnedDays.text = "Day(s) of learning : $it"
            }
        }
    }
}