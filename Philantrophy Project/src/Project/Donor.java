package Project;

import Classifire.ClassifierImpl;

/**
 * Created by Sergey on 02.08.2016.
 */
public class Donor extends ClassifierImpl{
    public Donor() {
    }

    public Donor(int id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Donor{} " + super.toString();
    }
}
