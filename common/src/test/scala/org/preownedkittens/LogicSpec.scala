package org.preownedkittens

import org.specs2.mutable.Specification

object LogicSpec extends Specification {
  "The 'matchLikelihood' method" should {
    "be 100% when all attributes match" in {
      val tom = Kitten(1, Seq("male", "Tom"))
      val prefs = BuyerPreferences(List("male", "Tom"))
      val result = Logic.matchLikelihood(tom, prefs)
      result must beGreaterThan(.999)
    }
    "be 0% when no attributes match" in {
      val tom = Kitten(1, Seq("male", "Tom"))
      val prefs = BuyerPreferences(List("female", "tabby"))
      val result = Logic.matchLikelihood(tom, prefs)
      result must beLessThan(.001)
    }
  }
}
