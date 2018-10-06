package io.orthrus.common.collections;

public interface LazyBuilder<K, V> {
   V create(K key);
}
