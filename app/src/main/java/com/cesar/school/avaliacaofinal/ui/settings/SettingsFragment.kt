package com.cesar.school.avaliacaofinal.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cesar.school.avaliacaofinal.R
import com.cesar.school.avaliacaofinal.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null
    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProvider(requireActivity()).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        prefs = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        editor = prefs?.edit()

        val saveButton = binding.btnSave

        setLists()

        val temp = binding.rgTempUnit
        val lang = binding.rgLang

        temp.check(settingsViewModel.temp.value!!)
        lang.check(settingsViewModel.lang.value!!)

        saveButton.setOnClickListener {
            savePreferences(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun savePreferences(view: View) {
        val temp = binding.rgTempUnit
        val lang = binding.rgLang

        when (temp!!.checkedRadioButtonId) {
            R.id.rbCelsius -> {
                settingsViewModel.changeTemp(R.id.rbCelsius)
                editor.apply {
                    putString("temperature unit", "metric")
                    apply()
                }
            }
            R.id.rbFahr -> {
                settingsViewModel.changeTemp(R.id.rbFahr)
                editor.apply {
                    putString("temperature unit", "imperial")
                    apply()
                }
            }
        }

        when (lang!!.checkedRadioButtonId) {
            R.id.rbEn -> {
                settingsViewModel.changeLang(R.id.rbEn)
                editor.apply {
                    putString("description language", "en")
                    apply()
                }
            }
            R.id.rbPt -> {
                settingsViewModel.changeLang(R.id.rbPt)
                editor.apply {
                    putString("description language", "pt_br")
                    apply()
                }
            }
        }
    }

    fun setLists(){
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", 0)
        val temperature = prefs.getString("temperature unit", null)
        val language = prefs.getString("description language", null)

        var temp_id = 0
        var lang_id = 0

        if(temperature.toString() == "metric") temp_id = binding.rbCelsius.id
        else if(temperature.toString() == "imperial") temp_id = binding.rbFahr.id

        if(language.toString() == "en") lang_id = binding.rbEn.id
        else if(language.toString() == "pt_br") lang_id = binding.rbPt.id

        settingsViewModel.setLists(temp_id, lang_id)
    }
}