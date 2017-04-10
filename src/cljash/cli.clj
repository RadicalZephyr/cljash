(ns cljash.cli
  (:require [boot.core :as boot]
            [boot.util :as util]
            [clojure.string :as str])
  (:import
   (java.io BufferedReader
            InputStreamReader
            IOException)
   (jnr.posix POSIX
              POSIXFactory)))

(defonce ^POSIX posix (POSIXFactory/getNativePOSIX))

(defn prompt-and-read! [reader]
  (.print System/err "%% ")
  (.readLine reader))

(defn arg-parse [line]
  (-> line
      (str/replace #" +" " ")
      (str/split #" ")))

(defn process-line [line]
  (let [envp (mapv #(format "%s=%s" (.getKey %) (.getValue %)) (System/getenv))
        [command :as args] (arg-parse line)]
    (.posix_spawnp posix command [] args envp)
    (.wait posix (int-array 1))))

(defn cli []
  (with-open [reader (-> System/in
                         InputStreamReader.
                         BufferedReader.)]
    (loop []
      (when-let [line (prompt-and-read! reader)]
        (process-line line)
       (recur)))))
