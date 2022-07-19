package com.starwarsmasterdetails.ui.main.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.starwarsmasterdetails.databinding.DetailsFragmentBinding
import com.starwarsmasterdetails.models.People
import com.starwarsmasterdetails.ui.main.MainViewModel
import com.starwarsmasterdetails.util.Constants.Companion.TAG
import com.starwarsmasterdetails.util.PDFConverter


class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        binding.shareFb.setOnClickListener {
            shareDetails()
        }
    }

    private fun subscribeObservers() {
        viewModel.selectedPeople.observe(viewLifecycleOwner, { people ->
            Log.d(TAG, "subscribeObservers: new people = $people")
            setPeopleDetails(people)
        })
//        viewModel.filmsList.observe(viewLifecycleOwner, { filmsStr ->
//            Log.d(TAG, "subscribeObservers: new films list = $filmsStr")
//            setFilmsToPeople(filmsStr)
//        })
    }

    private fun setPeopleDetails(people : People) {
        binding.tvName.text = people.name
        binding.tvGender.text = people.gender
        binding.tvBirthYear.text = people.birth_year
    }

//    private fun setFilmsToPeople(filmsStr: String) {
//        binding.tvFilms.text = filmsStr
//    }

    private fun shareDetails() {
        val pdfConverter= PDFConverter()
        view?.let { view ->
            activity?.let { activity ->
            pdfConverter.createPdf(activity.applicationContext, activity, view) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}