package br.com.arodevsistemas.bebergualembrete.model

import br.com.arodevsistemas.bebergualembrete.core.formatCurrencyBr

class CalcIngestionWater {

    private val ML_0_17 = 40.0
    private val ML_18_55 = 35.0
    private val ML_56_65 = 30.0
    private val ML_66 = 25.0
    private var total_ml = 0.0

    fun CalcIngestionWater(weight: Double, age: Int): String {
        if (age <= 17){
            total_ml = weight * ML_0_17
        }else if (age <= 55){
            total_ml = weight * ML_18_55
        }else if(age <= 65){
            total_ml = weight * ML_56_65
        }else{
            total_ml = weight * ML_66
        }

        return "${total_ml.formatCurrencyBr()} ml"
    }

}