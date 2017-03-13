package Project;

import Classifire.ClassifierImpl;

import java.math.BigDecimal;

/**
 * Created by Sergey on 27.07.2016.
 */

public class Curency extends ClassifierImpl {
    private BigDecimal totalAmount;
    private BigDecimal courseToDram;
    private BigDecimal dram;

    public Curency(BigDecimal courseToDram) {
        this.courseToDram = courseToDram;
    }

    public Curency(int id, String name, BigDecimal currencyToDram, BigDecimal totalAmount) {

        super(id, name);

        if (currencyToDram == null) {
            throw new IllegalArgumentException("currencyToDram cannot be null.");
        } else if (currencyToDram.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("currencyToDram cannot be negative number.");
        } else {
            this.courseToDram = currencyToDram;
        }

        if (totalAmount == null) {
            this.totalAmount = new BigDecimal(0);
        } else if (totalAmount.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("totalAmount cannot be negative number.");
        } else {
            this.totalAmount = totalAmount;
        }

        dram = this.courseToDram.multiply(this.totalAmount);
    }


    /**
     * Перегрузка функции toString
     */

    @Override
    public String toString() {
        return "Curency{" +
                "totalAmount=" + totalAmount +
                ", courseToDram=" + courseToDram +
                ", dram=" + dram +
                "} " + super.toString();
    }


    /**
     * gets and sets
     */

    public BigDecimal getCourseToDram() {
        return courseToDram;
    }

    public void setCourseToDram(BigDecimal courseToDram) {
        this.courseToDram = courseToDram;
        dram = this.courseToDram.multiply(this.totalAmount);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getDram() {
        return dram;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        dram = this.courseToDram.multiply(this.totalAmount);
    }
}
