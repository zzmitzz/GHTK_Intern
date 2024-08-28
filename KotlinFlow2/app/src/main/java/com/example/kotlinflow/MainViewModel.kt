package com.example.kotlinflow

import androidx.lifecycle.ViewModel
import com.example.kotlinflow.model.Staff
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class MainViewModel : ViewModel() {
    private var listStaff = initSampleData().toMutableList()
    var data: MutableStateFlow<List<Staff>> = MutableStateFlow(listStaff)
    private val _nameFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val _birthYearFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val _birthPlaceFlow: MutableStateFlow<String> = MutableStateFlow("")

    val searchFlow: Flow<Staff> = combine(
        _nameFlow,
        _birthYearFlow,
        _birthPlaceFlow
    ) { mName, mYear, mPlace ->
        Staff(
            name = mName,
            year = mYear,
            birthPlace = mPlace
        )
    }


    fun initSampleData(): MutableList<Staff> {
        val list: MutableList<Staff> = mutableListOf()
        repeat(15) {
            list.add(
                Staff(
                    name = arrayOf("John", "Mit", "Senf", "Nguyen Van A", "Danthd").random(),
                    year = arrayOf("2000", "2001", "2002", "2003").random(),
                    birthPlace = arrayOf("Hanoi", "HCM", "Thanh Hoa", "Da Nang").random()
                )
            )
        }
        return list
    }

    fun updateData(staff: Staff) {
        val result: MutableList<Staff> = mutableListOf()
        for (i in listStaff) {
            if (
                (staff.name == "" || i.name.startsWith(staff.name, true))
                && (staff.year == "" || i.year.equals(staff.year, ignoreCase = true))
                && (staff.birthPlace == "" || i.birthPlace.equals(
                    staff.birthPlace,
                    ignoreCase = true
                ))
            ) {
                result.add(i)
            }
        }
        data.value = result
    }

    // Function to update the name flow
    fun updateName(name: String) {
        _nameFlow.value = name
    }

    // Function to update the birth year flow
    fun updateBirthYear(birthYear: String) {
        _birthYearFlow.value = birthYear
    }

    // Function to update the birth place flow
    fun updateBirthPlace(birthPlace: String) {
        _birthPlaceFlow.value = birthPlace
    }
    fun deleteStaff(staff: Staff){
        listStaff.remove(staff)
        val newList = listStaff.toMutableList()
        data.value = newList
    }
    fun addStaff(staff: Staff) {
        listStaff.add(staff)
        val newList = listStaff.toMutableList()
        data.value = newList
    }
}