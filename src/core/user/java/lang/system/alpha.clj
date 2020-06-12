(ns user.java.lang.system.alpha
  (:require
   [clojure.string :as str]
   [user.clojure.import.alpha :as import]
   [user.java.lang.system.patch]
   ))


(import/import-namespace 'user.java.lang.system.patch)


(defn java-version
  ([]
   (java-version (System/getProperty "java.version")))
  ([java-version-str]
   (let [[major minor incremental build] (map #(Integer/parseInt %) (str/split java-version-str #"[^0-9]"))]
     {:major       major
      :minor       minor
      :incremental incremental
      :build       build})))


(defn get-profile
  []
  (let [ns   (System/getProperty "user.profile.namespace")
        name (System/getProperty "user.profile.name")]
    (when (string? name)
      (keyword ns name))))


(defmacro __PROFILE__ "Statically captured jvm property"
  []
  (get-profile))


(defmacro __DATE__ "Statically captured jvm property"
  []
  (str (java.util.Date.)))


(defmacro with-profile
  {:style/indent [1]}
  [profile & body]
  (when (= profile (get-profile))
    (when (seq body)
      (if (== (count body) 1)
        (first body)
        `(do ~@body)))))


(defmacro fn-profile
  {:style/indent [:defn]}
  [profile & fn-body]
  (if (= profile (get-profile))
    `(fn ~@fn-body)
    `(constantly nil)))


(defn os-name
  []
  (let [^String osname (str/upper-case (System/getProperty "os.name"))]
    (case osname
      "MAC OS X" "macos"
      "LINUX"    "linux"
      "WINDOWS"  "windows"
      (condp #(. ^String %2 startsWith ^String %) osname
        "windows" "windows"
        (throw (ex-info "Unknown osname" {:osname osname}))))))


(defn os-keyword
  []
  (keyword (os-name)))
