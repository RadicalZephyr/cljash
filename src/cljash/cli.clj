(ns cljash.cli
  (:require [boot.core :as boot]
            [boot.util :as util])
  (:import
   (java.io BufferedReader
            InputStreamReader
            IOException)))

(defn prompt-and-read! [reader]
  (.print System/err "%% ")
  (.readLine reader))

(defn process-line [line]
  (let [process-builder (-> (ProcessBuilder. (into-array String [line]))
                            )]
    (try
      (.start process-builder)
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
