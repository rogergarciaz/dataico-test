(ns invoice-item-test
  (:require [clojure.test :refer :all]
            [invoice-item :refer :all]))

(deftest test-subtotal-with-discount
  (is (= 4500.0 (subtotal {:precise-quantity 10
                           :precise-price 500.0
                           :discount-rate 10}))))

(deftest test-subtotal-without-discount
  (is (= 5000.0 (subtotal {:precise-quantity 10
                           :precise-price 500.0}))))

(deftest test-subtotal-with-zero-quantity
  (is (= 0.0 (subtotal {:precise-quantity 0
                        :precise-price 500.0
                        :discount-rate 10}))))

(deftest test-subtotal-with-zero-price
  (is (= 0.0 (subtotal {:precise-quantity 10
                        :precise-price 0.0
                        :discount-rate 10}))))

(deftest test-subtotal-with-zero-discount
  (is (= 5000.0 (subtotal {:precise-quantity 10
                           :precise-price 500.0
                           :discount-rate 0}))))
