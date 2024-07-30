(ns routing.main
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [clojure.pprint :as pp]))

(defn handler
  [request]
  ;; (pp/pprint request)
  (let [route {(:request-method request) (:uri request)}]
    (println route))
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

(defn -main
  [& args]
  (jetty/run-jetty (wrap-reload handler) {:port 3000}))
