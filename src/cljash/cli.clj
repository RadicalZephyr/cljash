(ns cljash.cli
  (:require [boot.core :as boot]
            [boot.util :as util]))

(defn cli []
  (util/dosh "ls" "-lah" "--color=always"))
