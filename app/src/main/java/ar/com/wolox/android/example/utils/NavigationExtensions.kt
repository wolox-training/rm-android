package ar.com.wolox.android.example.utils

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

fun Fragment.requireView() = view!!

fun Fragment.requireNavController() = Navigation.findNavController(requireView())