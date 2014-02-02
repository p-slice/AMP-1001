package net.pslice.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class is borrowed from another of my projects.
 * Although it doesn't have much use here, I foresee
 *  a few times when it may be needed.
 * Its only current usage is in the CommandTwerk class.
 */

public final class Probability<P> {

    /*
     * =============================================
     * Variables, Objects, Sets, Lists and Maps:
     * =============================================
     */

    private final HashMap<P, Integer>

            // Map of all items and their probabilities
            itemProbabilities = new HashMap<>();

    private final Random

            // Random object used in generation
            random = new Random();





    /**
     * =============================================
     * Method to add an item to the probability:
     *
     * @param item: The item being added
     * @param probability: The number of times the item will
     *                       be added to the list
     * =============================================
     */

    public void add(P item, int probability)
    {
        itemProbabilities.put(item, probability);
    }





    /**
     * =============================================
     * Method to remove an item from the probability:
     *
     * @param item: The item being removed
     * =============================================
     */

    public void remove(P item)
    {
        if (itemProbabilities.containsKey(item))
            itemProbabilities.remove(item);
    }





    /**
     * =============================================
     * Method to check if the probability contains an item:
     *
     * @param item: The item being checked
     * @return Whether or not the item exists
     * =============================================
     */

    public boolean containsItem(P item)
    {
        return itemProbabilities.containsKey(item);
    }





    /**
     * =============================================
     * Getter for the probability of an item:
     *
     * @param item: The item being queried
     * @return The probability of the item
     *         0 if no probability is found
     * =============================================
     */

    public int getProbability(P item)
    {
        return itemProbabilities.containsKey(item) ? itemProbabilities.get(item) : 0;
    }





    /**
     * =============================================
     * Getter for the final result from the probability:
     *
     * @return The result, with a default erasing of the
     *              items
     * =============================================
     */

    // Method without parameters - defaults with true
    public P getResult()
    {
        return getResult(true);
    }





    /**
     * =============================================
     * Getter for the final result from the probability:
     *
     * @param eraseValues: Whether or not the items should be
     *                          erased after getting the result
     * @return A randomly chosen result
     * In order to try simulate probability as accurately as possible,
     *      each item is added to a list as many times as its
     *      probability
     * =============================================
     */

    // Method with parameters
    public P getResult(boolean eraseValues)
    {
        List<P> items = new ArrayList<>();
        for (P item: itemProbabilities.keySet())
        {
            int added = 0;
            while (added < itemProbabilities.get(item))
            {
                items.add(item);
                added++;
            }
        }
        P result = items.get(random.nextInt(items.size()));
        if (eraseValues)
            clear();
        return result;
    }





    /*
     * =============================================
     * Method to erase all values from the probability:
     * =============================================
     */

    public void clear()
    {
        itemProbabilities.clear();
    }
}
