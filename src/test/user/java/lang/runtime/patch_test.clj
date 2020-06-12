(ns user.java.lang.runtime.patch-test
  (:require
   [clojure.test :as test :refer [deftest is are testing]]
   [user.java.lang.runtime.patch :refer :all]
   ))


(deftest main
  (is (number? (available-processors)))
  )
