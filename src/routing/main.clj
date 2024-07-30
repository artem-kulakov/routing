(ns routing.main
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [clojure.pprint :as pp]))

;; (defn lists
;;   []
;;   {:status 200
;;    :headers {"Content-Type" "text/html"}
;;    :body "Lists page"})

;; (defn info
;;   []
;;   {:status 200
;;    :headers {"Content-Type" "text/html"}
;;    :body "Info page"})

(defn handler
  [request]
  ;; (pp/pprint request)
  (let [route {(:request-method request) (:uri request)}]
    (case route
      {:get "/lists"} {:status 200
                       :headers {"Content-Type" "text/html"}
                       :body "Lists page"}
      {:get "/info"} {:status 200
                      :headers {"Content-Type" "text/html"}
                      :body "Info page"})))

(defn -main
  [& args]
  (jetty/run-jetty (wrap-reload handler) {:port 3000}))
