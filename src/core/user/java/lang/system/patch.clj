(ns user.java.lang.system.patch)


;;


(defprotocol Property
  (^String to-property-name [this])
  (^String property [key] [key def])
  (property! [key val])
  (clear-property! [key]))


;;


(defn set-property!
  [key val]
  (property! key val))


(defn -to-property-name
  [x]
  (let [ns-name (namespace x)
        name    (name x)]
    (if (seq ns-name)
      (str ns-name "." name)
      name)))


(defn properties
  []
  (System/getProperties))


(defn keywordized-properties
  {:deprecated "0.3.4"}
  []
  (into
    (hash-map)
    (map
     #(let [k (key %) v (val %)
            m (re-matches #"(.*)\.([^.]*)$" k)
            a (nth m 1)
            b (nth m 2)]
        (clojure.lang.MapEntry.
         (or
          (and b (keyword a b))
          (and a (keyword a))
          (keyword k))
         v)))
    (properties)))


(defmacro aot-property
  ([key    ] `(quote ~(property key)))
  ([key def] `(quote ~(property key def))))


(defn now [] (System/currentTimeMillis))


(defn getenv
  ([]
   (System/getenv))
  ([env]
   (System/getenv env)))


;;


(extend-type clojure.lang.Symbol
  Property
  (to-property-name [this]
    (-to-property-name this))
  (property
    ([x  ] (property (-to-property-name x)))
    ([x y] (property (-to-property-name x) y)))
  (property! [x y]
    (property! (to-property-name x) y))
  (clear-property! [x]
    (clear-property! (to-property-name x))))


(extend-type clojure.lang.Keyword
  Property
  (to-property-name [this]
    (-to-property-name this))
  (property
    ([x  ] (property (-to-property-name x)))
    ([x y] (property (-to-property-name x) y)))
  (property! [x y]
    (property! (to-property-name x) y))
  (clear-property! [x]
    (clear-property! (to-property-name x))))


(extend-type String
  Property
  (to-property-name [this] this)
  (property
    ([x  ] (System/getProperty x))
    ([x y] (System/getProperty x y)))
  (property! [x y]
    (System/setProperty x y))
  (clear-property! [x]
    (System/clearProperty x)))
