package org.preownedkittens

object Logic {
    def matchLikelihood(kitten: Kitten, buyer: BuyerPreferences): Double = {
        val matches = buyer.attributes map { attribute =>
            kitten.attributes contains attribute
        }
        val nums = matches map (if (_) 1.0 else 0.0)
        nums.sum / nums.length
    }
}
