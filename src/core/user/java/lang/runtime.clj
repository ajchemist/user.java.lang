(ns user.java.lang.runtime
  (:require
   [user.clojure.import.alpha :refer [import-namespace]]
   [user.java.lang.runtime.patch]
   )
  (:import
   java.lang.Runtime
   ))


(import-namespace 'user.java.lang.runtime.patch)


(defn max-memory
  []
  (.maxMemory (Runtime/getRuntime)))


(defn total-memory
  []
  (.totalMemory (Runtime/getRuntime)))


(defn free-memory
  []
  (.freeMemory (Runtime/getRuntime)))


(defn add-shutdown-hook!
  {:style/indent [0]}
  [f]
  {:pre [(fn? f)]}
  (. (Runtime/getRuntime) addShutdownHook (Thread. f)))
