(def project 'cljash)
(def +version+ "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"src"}
          :source-paths #{"java"}
          :dependencies   (template
                           [[org.clojure/clojure ~(clojure-version)]
                            [com.github.jnr/jnr-posix "3.0.37"]
                            [boot/core             "2.7.1" :scope "provided"]
                            [metosin/boot-alt-test "0.3.1" :scope "test"]]))

(task-options!
 pom {:project     project
      :version     +version+
      :description "A modern shell running on the JVM, written and extensible in Clojure."
      :url         "https://github.com/RadicalZephyr/cljash"
      :scm         {:url "https://github.com/RadicalZephyr/cljash"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))

(require '[metosin.boot-alt-test :refer [alt-test]])

(deftask run-shell []
  (with-pass-thru [_]
    (require 'cljash.task)
    (when-let [main (resolve 'cljash.task/main)]
      (main))))
