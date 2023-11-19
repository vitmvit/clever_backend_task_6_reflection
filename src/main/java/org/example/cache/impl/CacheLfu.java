package org.example.cache.impl;


import org.example.cache.Cache;

import java.util.HashMap;
import java.util.Map;

import static org.example.constant.Constant.CAPACITY_FACTOR;
import static org.example.constant.Constant.LOAD_FACTOR;

/**
 * Реализация LFU-кэша.
 * Этот класс представляет собой реализацию LFU-кэша (Least Frequently Used),
 * который позволяет хранить пары ключ-значение в кэше с определенной вместимостью.
 * Механизм работы кэша основан на принципе вытеснения наименее используемых элементов.
 * Кэш имеет фиксированную вместимость, которая задается при создании объекта класса.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 * @author Витикова Мария
 */
public class CacheLfu<K, V> implements Cache<K, V> {

    /**
     * Вместимость кэша
     */
    private final int capacity;

    /**
     * Карта для хранения элементов кэша
     */
    private final Map<K, Node<K, V>> cache;

    /**
     * Карта для хранения элементов по их частоте использования
     */
    private final Map<Integer, Node<K, V>> frequencyTails;

    /**
     * Первый элемент в списке элементов кэша. Последний элемент в списке содержит наибольшую частоту использования
     */
    private Node<K, V> head;

    /**
     * Счетчик изменений, используется для итерации по элементам кэша
     */
    private int modCount;

    /**
     * Создает объект CacheLfu с указанной вместимостью.
     *
     * @param capacity вместимость кэша
     */
    public CacheLfu(int capacity) {
        this.capacity = capacity;
        int hashMapCapacity = (int) (capacity * CAPACITY_FACTOR);
        cache = new HashMap<>(hashMapCapacity, LOAD_FACTOR);
        frequencyTails = new HashMap<>(hashMapCapacity, LOAD_FACTOR);
    }

    /**
     * Получает значение из кэша по ключу.
     * Если элемент отсутствует, возвращает null.
     * Увеличивает частоту использования элемента.
     *
     * @param key ключ
     * @return значение, соответствующее ключу, или null, если элемент отсутствует
     */
    @Override
    public V get(Object key) {
        Node<K, V> node;
        if ((node = cache.get(key)) == null) {
            return null;
        }
        incrementNodeFrequency(node);
        modCount++;
        return node.value;
    }

    /**
     * Добавляет элемент в кэш или обновляет его, если он уже существует.
     * Возвращает старое значение элемента, или null, если элемент добавлен впервые.
     * Если вместимость кэша превышена, вытесняет элемент с наичастотнее использованием.
     *
     * @param key   ключ
     * @param value значение
     * @return старое значение элемента, или null, если элемент добавлен впервые
     */
    @Override
    public V put(K key, V value) {
        V oldValue;
        Node<K, V> node = cache.get(key);
        if (node == null) {
            oldValue = null;
            if (cache.size() >= capacity) {
                Node<K, V> oldHead = head;
                head = head.next;
                if (isFrequencyTail(oldHead)) {
                    frequencyTails.remove(oldHead.frequency);
                }
                oldHead.unlink();
                cache.remove(oldHead.key);
            }
            Node<K, V> newNode = new Node<>(key, value, 0);
            cache.put(key, newNode);
            Node<K, V> targetTail = frequencyTails.put(0, newNode);
            if (targetTail == null) {
                if (head != null) {
                    head.insertPrevious(newNode);
                }
                head = newNode;
            } else {
                targetTail.insertNext(newNode);
            }
        } else {
            oldValue = node.value;
            node.value = value;
            incrementNodeFrequency(node);
        }
        modCount++;
        return oldValue;
    }

    /**
     * Удаляет элемент из кэша по ключу.
     * Возвращает значение удаленного элемента, или null, если элемент отсутствует.
     *
     * @param key ключ
     * @return значение удаленного элемента, или null, если элемент отсутствует
     */
    @Override
    public V remove(Object key) {
        Node<K, V> node = cache.remove(key);
        if (node == null) {
            return null;
        }
        if (isFrequencyTail(node)) {
            if (isFrequencyHead(node)) {
                frequencyTails.remove(key);
            } else {
                frequencyTails.put(node.frequency, node.prev);
            }
        }
        if (node == head) {
            head = node.next;
        }
        node.unlink();
        modCount++;
        return node.value;
    }

    /**
     * Очищает кэш, удаляя все элементы
     */
    @Override
    public void clear() {
        cache.clear();
        frequencyTails.clear();
        head = null;
        modCount++;
    }

    /**
     * Проверяет, является ли указанный элемент головным по частоте использования.
     *
     * @param node элемент
     * @return true, если элемент является головным по частоте использования, false в противном случае
     */
    private boolean isFrequencyHead(Node<K, V> node) {
        return node.prev == null || node.prev.frequency != node.frequency;
    }

    /**
     * Проверяет, является ли указанный элемент последним по частоте использования.
     *
     * @param node элемент
     * @return true, если элемент является последним по частоте использования, false в противном случае
     */
    private boolean isFrequencyTail(Node<K, V> node) {
        return node.next == null || node.next.frequency != node.frequency;
    }

    /**
     * Увеличивает частоту использования указанного узла.
     * Если узел является головным по частоте использования, увеличивает его частоту и перемещает его в список элементов
     * с повышенной частотой. Если узел является последним по частоте использования, увеличивает его частоту, перемещает
     * его в список элементов с повышенной частотой и обновляет ссылку на последний узел в списке.
     * Если узел находится где-то посередине списка частот использования, увеличивает его частоту, перемещает его в
     * список элементов с повышенной частотой и оставляет ссылку на последний узел без изменений.
     *
     * @param node узел
     */
    private void incrementNodeFrequency(Node<K, V> node) {
        int oldFrequency = node.frequency;
        int newFrequency = node.frequency + 1;
        Node<K, V> targetTail = frequencyTails.put(newFrequency, node);
        if (isFrequencyTail(node)) {
            if (isFrequencyHead(node)) {
                frequencyTails.remove(oldFrequency);
            } else {
                frequencyTails.put(oldFrequency, node.prev);
            }
            if (targetTail != null) {
                if (node == head) {
                    head = node.next;
                }
                node.unlink();
                targetTail.insertNext(node);
            }
        } else {
            if (node == head) {
                head = node.next;
            }
            node.unlink();
            if (targetTail == null) {
                frequencyTails.get(oldFrequency).insertNext(node);
            } else {
                targetTail.insertNext(node);
            }
        }
        node.frequency = newFrequency;
    }

    /**
     * Вспомогательный класс Node, представляющий узел в двусвязном списке элементов кэша.
     * <p>
     * Класс Node содержит ключ, значение, частоту использования элемента,
     * ссылки на предыдущий и следующий узлы в списке.
     *
     * @param <K> тип ключа
     * @param <V> тип значения
     */
    private static class Node<K, V> {

        /**
         * Ключ элемента
         */
        final K key;

        /**
         * Значение элемента
         */
        V value;

        /**
         * Частота использования элемента
         */
        int frequency;

        /**
         * Ссылка на предыдущий узел в списке
         */
        Node<K, V> prev;

        /**
         * Ссылка на следующий узел в списке
         */
        Node<K, V> next;

        /**
         * Создает новый узел с указанными ключом, значением и частотой использования.
         *
         * @param key       ключ
         * @param value     значение
         * @param frequency частота использования
         */
        public Node(K key, V value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }

        /**
         * Вставляет новый узел перед данным узлом.
         *
         * @param node новый узел для вставки
         */
        public void insertPrevious(Node<K, V> node) {
            node.prev = prev;
            node.next = this;
            if (prev != null) prev.next = node;
            prev = node;
        }

        /**
         * Вставляет новый узел после данного узла.
         *
         * @param node новый узел для вставки
         */
        public void insertNext(Node<K, V> node) {
            node.next = next;
            node.prev = this;
            if (next != null) next.prev = node;
            next = node;
        }

        /**
         * Удаляет данный узел из списка
         */
        public void unlink() {
            Node<K, V> p = prev;
            Node<K, V> n = next;
            if (p != null) {
                p.next = n;
                prev = null;
            }
            if (n != null) {
                n.prev = p;
                next = null;
            }
        }
    }
}
