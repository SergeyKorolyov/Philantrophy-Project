package Project;

import Classifire.ClassifierImpl;

/**
 * Created by Sergey on 31.07.2016.
 */
public class Organisation extends ClassifierImpl {
    private String address;
    private String number;

    /**
     * Определение конструктора со всеми подлежащими проверками
     */

    public Organisation(int id, String name, String address, String number) {
        super(id, name);
        if (address == null) {
          //  throw new IllegalArgumentException("address cannot be null.");
            address = null; // el havest chka e)
        } else {
            this.address = address;
        }
        if (number == null) {
           // throw new IllegalArgumentException("number cannot be null.");
            number = null ; // duel anci gna )
        } else {
            this.number = number;
        }
    }

    public Organisation() {
    }

    /**
     * Перегрузка функции toString
     */

    @Override
    public String toString() {
        return "Organisation{" +
                "address='" + address + '\'' +
                ", number='" + number + '\'' +
                "} " + super.toString();
    }

    /**
     * Gets and Sets
     */

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public void setAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("address cannot be null.");
        } else {
            this.address = address;
        }
    }

    public void setNumber(String number) {
        if (number == null) {
            throw new IllegalArgumentException("number cannot be null.");
        } else {
            this.number = number;
        }
    }
}
