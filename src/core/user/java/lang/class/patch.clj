(ns user.java.lang.class.patch
  (:refer-clojure :exclude [name])
  (:require
   [clojure.main]
   ))


(defprotocol IClass
  (name [this])
  (simple-name [this]))


;;


(defn demunge
  [fn]
  (clojure.main/demunge (name fn)))


;;


(extend-type Class
  IClass
  (name
    [this]
    (. this getName))
  (simple-name
    [this]
    (. this getSimpleName)))


(extend-type Object
  IClass
  (name
    [this]
    (.. this getClass getName))
  (simple-name
    [this]
    (.. this getClass getSimpleName)))
