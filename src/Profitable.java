/**
 * Project 5 - Profitable
 *
 * Is an interface that will be implemented by Vehicle
 *
 * @author Akshat Poddutoori, Linnea Lindstrom, CS180 Black
 *
 * @version December 9, 2018
 *
 * This interface represents something that can be used to make a profit. Along
 * with returning total profits it must also be able to provide a report.
 *
 */

public interface Profitable {

    double getProfit();
    String report();
}