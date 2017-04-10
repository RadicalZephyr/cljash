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
  (let [envp (mapv #(format "%s=%s" (.getKey %) (.getValue %)) (System/getenv))]
    (.posix_spawnp posix line [] [line] envp)
    (.wait posix (int-array 1))))

(defn cli []
  (with-open [reader (-> System/in
                         InputStreamReader.
                         BufferedReader.)]
    (loop []
      (when-let [line (prompt-and-read! reader)]
        (process-line line)
       (recur)))))
