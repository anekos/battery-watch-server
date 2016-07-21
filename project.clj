(defproject battery-watch-server"0.1.0-SNAPSHOT"
  :description "Battery Watcher"
  :url "http://snca.net/"
  :license {:name "Eclipse Public License" :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main battery-watch-server.core
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler battery-watch-server.core/app}
  :aot [battery-watch-server.core]
  :repl-options {:init-ns user}
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [criterium "0.4.4"]]
                   :source-paths ["dev"]
                   :env {:redis {:host "localhost"
                                 :port 6379}}}}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.5.0"]
                 [compojure "1.5.1"]
                 [clj-redis "0.0.12"]
                 [environ "1.0.3"]
                 [clj-time "0.12.0"]
                 [meowdule "0.3.0"]])
