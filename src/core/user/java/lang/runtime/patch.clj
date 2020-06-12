(ns user.java.lang.runtime.patch)


(defn available-processors
  []
  (. (Runtime/getRuntime) availableProcessors))
