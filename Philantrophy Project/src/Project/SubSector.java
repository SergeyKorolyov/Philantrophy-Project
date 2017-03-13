package Project;

/**
 * Created by Sergey on 29.07.2016.
 */

import Classifire.ChildClassifireImpl;
import Classifire.ClassifierImpl;


public class SubSector extends ChildClassifireImpl {

    /**
     * Определение конструкторов
     */
    public SubSector(int id, String name, int parentId) {
        super(id, name, parentId);
    }

    public SubSector() {

    }

    /**
     * Перегрузка функции toString
     */

    @Override
    public String toString() {
        return "SubSector{} " + super.toString();
    }
}
