(ns user.java.lang.system.patch-test
  (:require
   [clojure.test :as test :refer [deftest is are]]
   [user.java.lang.system.patch :refer :all]
   ))


(deftest main
  (is (= (to-property-name :x.y)
         (to-property-name :x/y))
      ":x.y and :x/y have the same property name")
  (is (= (to-property-name 'x.y)
         (to-property-name 'x/y))
      "x.y and x/y have the same property name"))
