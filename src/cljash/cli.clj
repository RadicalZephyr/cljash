(ns cljash.cli
  (:require [boot.core :as boot]
            [boot.util :as util])
  (:import
   (java.io BufferedReader
            InputStreamReader
            IOException)
   (jnr.posix POSIX
              POSIXFactory)
   (jnr.posix.util ProcessMaker)))

(defonce ^POSIX posix (POSIXFactory/getNativePOSIX))

(defn prompt-and-read! [reader]
  (.print System/err "%% ")
  (.readLine reader))

(defn process-line [line]
  (let [process-maker (.newProcessMaker posix (into-array String [line]))]
    (try
      (.start process-maker)
      (catch IOException e
        (.printf System/err "error: %s\n" (into-array Object (.getMessage e)))))))

(defn cli []
  (with-open [reader (-> System/in
                         InputStreamReader.
                         BufferedReader.)]
    (loop []
      (when-let [line (prompt-and-read! reader)]
        (process-line line)
       (recur)))))
