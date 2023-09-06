package com.emami.lebenindeutschland.screen.viewModel

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emami.lebenindeutschland.domain.model.QuizModel
import com.emami.lebenindeutschland.domain.repo.Baden_Wurttemberg
import com.emami.lebenindeutschland.domain.repo.Bayern
import com.emami.lebenindeutschland.domain.repo.Berlin
import com.emami.lebenindeutschland.domain.repo.Brandenburg
import com.emami.lebenindeutschland.domain.repo.Bremen
import com.emami.lebenindeutschland.domain.repo.Hamburg
import com.emami.lebenindeutschland.domain.repo.Hessen
import com.emami.lebenindeutschland.domain.repo.Mecklenburg_Vorpommern
import com.emami.lebenindeutschland.domain.repo.Niedersachsen
import com.emami.lebenindeutschland.domain.repo.Nordrhein_Westfalen
import com.emami.lebenindeutschland.domain.repo.QuizModelList
import com.emami.lebenindeutschland.domain.repo.QuizModelList2
import com.emami.lebenindeutschland.domain.repo.QuizModelList3
import com.emami.lebenindeutschland.domain.repo.QuizRepository
import com.emami.lebenindeutschland.domain.repo.Rheinland_Pfalz
import com.emami.lebenindeutschland.domain.repo.Saarland
import com.emami.lebenindeutschland.domain.repo.Sachsen
import com.emami.lebenindeutschland.domain.repo.Sachsen_Anhalt
import com.emami.lebenindeutschland.domain.repo.Schleswig_Holstein
import com.emami.lebenindeutschland.domain.repo.Thüringen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val KEY_SELECTED_BUND = "selected_bund"

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repo: QuizRepository,
    private val app: Application
) : ViewModel() {

    val quizList =
        QuizModelList + QuizModelList2 + QuizModelList3 + Baden_Wurttemberg + Bayern + Berlin + Brandenburg + Bremen + Hamburg + Hessen + Mecklenburg_Vorpommern + Niedersachsen + Nordrhein_Westfalen + Rheinland_Pfalz + Saarland +
                Sachsen + Sachsen_Anhalt + Schleswig_Holstein + Thüringen

    var allQuizFromDB = mutableStateListOf<QuizModel>()
        private set


    var testQuizList = mutableStateOf<List<QuizModel>>(listOf())
        private set
    var testQuizList2 = mutableStateOf<List<QuizModel>>(listOf())
        private set


    var scoreResult = mutableIntStateOf(0)

    var selectedBund = mutableStateOf(getSelectedBund(app) ?: "")


    var quizIndex = mutableStateOf(0)

    val loading = mutableStateOf(false)



    init {
        saveSelectedBund(app, selectedBund.value)
        insertAllQuiz(quizList)
        getAllQuiz()
        bundQuiz(selectedBund.value)
    }


    private fun insertAllQuiz(list: List<QuizModel>) = viewModelScope.launch {
        repo.insert(list)
    }

    fun getAllQuiz() = viewModelScope.launch {
        allQuizFromDB.addAll(repo.getAll(1, 300))
//        testQuizList.value = repo.getAll(1, 300)
    }

    private fun getBundQuiz(startId: Int, endId: Int) = viewModelScope.launch {

        allQuizFromDB.addAll(repo.getAll(startId = startId, endId = endId))


    }


    fun updateIsCoreorNot(id: String, isCororNot: Boolean) = viewModelScope.launch {
        repo.updateIsCorOrNot(id, isCororNot)
    }

    fun updateIsFavorite(id: String, isFavorite: Boolean) = viewModelScope.launch {
        repo.updateIsFavorite(id, isFavorite)
    }





    fun bundQuiz(bund: String) {
        when (bund) {
            "Baden-Württemberg" -> getBundQuiz(301, 310)
            "Bayern" -> getBundQuiz(311, 320)
            "Berlin" -> getBundQuiz(321, 330)
            "Brandenburg" -> getBundQuiz(331, 340)
            "Bremen" -> getBundQuiz(341, 350)
            "Hamburg" -> getBundQuiz(351, 360)
            "Hessen" -> getBundQuiz(361, 370)
            "Mecklenburg-Vorpommern" -> getBundQuiz(371, 380)
            "Niedersachsen" -> getBundQuiz(381, 390)
            "Nordrhein-Westfalen" -> getBundQuiz(391, 400)
            "Rheinland-Pfalz" -> getBundQuiz(401, 410)
            "Saarland" -> getBundQuiz(411, 420)
            "Sachsen" -> getBundQuiz(421, 430)
            "Sachsen-Anhalt" -> getBundQuiz(431, 440)
            "Schleswig-Holstein" -> getBundQuiz(441, 450)
            "Thüringen" -> getBundQuiz(451, 460)
        }
    }


    fun saveSelectedBund(context: Context, bund: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit {
            putString(KEY_SELECTED_BUND, bund)
        }
    }

    fun getSelectedBund(context: Context): String? {
        val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }
        return sharedPreferences.getString(KEY_SELECTED_BUND, null)
    }


}