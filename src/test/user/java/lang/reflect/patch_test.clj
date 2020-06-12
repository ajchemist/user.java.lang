(ns user.java.lang.reflect.patch-test
  (:require
   [clojure.test :as test :refer [deftest is are]]
   [user.java.lang.reflect.patch :refer :all]
   ))


(deftest main
  (is
    (= #{0 1 2 3} (set (arg-counts (fn ([x]) ([x y]) ([x y & more]))))))
  (is
    (= [1 2 0] (arg-counts (fn ([x y]) ([x]) ([])))))
  )
