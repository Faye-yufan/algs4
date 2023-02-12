/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
        size = 0;
        // capacity = 1;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int targetCapacity) {
        Item[] copy = (Item[]) new Object[targetCapacity];
        for (int i = 0; i < size; i++) {
            copy[i] = array[i];
        }
        // System.out.println(Arrays.toString(copy));
        array = copy;
    }

    // add the item
    public void enqueue(Item item) {
        // Throw an IllegalArgumentException if the client calls enqueue() with a null argument.
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == array.length) {
            resize(2 * array.length);
        }
        array[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        // Throw a java.util.NoSuchElementException if the client calls either sample() or dequeue() when the randomized queue is empty.
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        if (size > 0 && size == array.length / 4) {
            resize(array.length / 2);
        }
        return array[--size];
    }

    // return a random item (but do not remove it)
    public Item sample() {
        // Throw a java.util.NoSuchElementException if the client calls either sample() or dequeue() when the randomized queue is empty.
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int randIdx = StdRandom.uniformInt(size);
        return array[randIdx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    // Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
    // Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
    private class RandomArrayIterator implements Iterator<Item> {
        private int current;
        private int[] shuffledIdx = new int[size];

        public RandomArrayIterator() {
            current = 0;
            for (int i = 0; i < size; i++) {
                shuffledIdx[i] = i;
            }
            StdRandom.shuffle(shuffledIdx);
        }

        public boolean hasNext() {
            return current < size || size == 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array[shuffledIdx[current++]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(7);
        for (int i : randomizedQueue) {
            System.out.println(i);
        }
    }

}
