(ns cljash.cli
  (:require [boot.core :as boot]
            [boot.util :as util])
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

(defn process-line [line]
  (let [cpid (.fork posix)]
    (if (= cpid 0)
      (.execv posix line (into-array String []))
      (.wait posix (int-array 1)))))

(defn cli []
  (with-open [reader (-> System/in
                         InputStreamReader.
                         BufferedReader.)]
    (loop []
      (when-let [line (prompt-and-read! reader)]
        (process-line line)
       (recur)))))
