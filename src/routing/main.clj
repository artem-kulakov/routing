(ns routing.main
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn handler
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

(defn -main
  [& args]
  (jetty/run-jetty (wrap-reload handler) {:port 3000}))
