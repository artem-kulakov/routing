(ns routing.main
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [clojure.pprint :as pp]))

(def not-found
  "Not found")

(def routes
  {"get /lists" "Lists page"
   "get /lists/:id" "Single list page"
   "get /info" "Info page"})

(defn f
  [route rt]
  (re-matches (re-pattern (clojure.string/replace
                           rt
                           #"/:\w+"
                           "/\\\\d+")) route))

(defn foo
  [route]
  (second (first (filter #(f route (first %)) routes))))

(defn handler
  [request]
  (let [route (str (name (:request-method request)) " " (:uri request))
        body (foo route);;(get routes route)
        status (if body 200 404)]
      {:status status
       :headers {"Content-Type" "text/html"}
       :body (or body not-found)}))

(def app
  (wrap-reload handler))

(defn -main
  [& args]
  (jetty/run-jetty app {:port 3000}))

(comment
  (re-pattern (clojure.string/replace
               "get /lists/:id"
               #"/:\w+"
               "/\\\\d+"))
  (re-matches (re-pattern (clojure.string/replace
                           "get /lists/:id"
                           #"/:\w+"
                           "/\\\\d+")) "get /lists/10")
  ;; "get /lists/:id" -> "get /lists/\d+", i.e. ":smth" -> "\d+"
  "\fds"
  "\\d+"
  (foo "get /lists/1"))
