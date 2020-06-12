(ns user.java.lang.reflect.patch
  (:import
   clojure.lang.AFunction
   java.lang.reflect.Method
   ))


(defn arg-counts
  [f]
  {:pre [(fn? f)]}
  (into []
    (comp
     (map #(. ^Method % getParameterTypes))
     (map alength))
    (. (class f) getDeclaredMethods)))
