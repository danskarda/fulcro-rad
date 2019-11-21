(ns com.fulcrologic.rad.rendering.semantic-ui.text-field
  (:require
    #?(:cljs
       [com.fulcrologic.fulcro.dom :as dom :refer [div label input]]
       :clj
       [com.fulcrologic.fulcro.dom-server :as dom :refer [div label input]])
    [com.fulcrologic.rad.attributes :as attr]
    [clojure.string :as str]
    [com.fulcrologic.rad.form :as form]
    [com.fulcrologic.fulcro.components :as comp]
    [com.fulcrologic.fulcro.dom.events :as evt]
    [com.fulcrologic.fulcro.ui-state-machines :as uism]
    [com.fulcrologic.rad.form :refer [render-field]]))

(defmethod render-field :text [this k props]
  (let [attribute  (attr/key->attribute k)
        {::form/keys [field-label]} attribute
        asm-id     (comp/get-ident this)
        read-only? (form/read-only? this attribute)
        value      (or (and attribute (get props k)) "")]
    (div :.ui.field {:key (str k)}
      (label (or field-label (some-> k name str/capitalize)))
      (if read-only?
        (div value)
        (input {:value    value
                :onBlur   (fn [evt]
                            (form/input-blur! this k (evt/target-value evt)))
                :onChange (fn [evt]
                            (form/input-changed! this k (evt/target-value evt)))})))))

