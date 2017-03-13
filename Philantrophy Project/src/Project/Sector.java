package Project;

/**
 * Created by Sergey on 29.07.2016.
 */


import Classifire.ClassifierImpl;

import java.util.ArrayList;

public class Sector extends ClassifierImpl {
    private SubSector subSector;


    /**
     * Определение конструктора со всеми подлежащими проверками
     */

    public Sector(int id, String name,SubSector subSector) {
        super(id, name);
        if (subSector == null) {
            this.subSector = new SubSector();
        } else {
            this.subSector = subSector;
        }
    }

    public Sector() {
    }

    /**
     * Перегрузка функции toString
     */

    @Override
    public String toString() {
        return "Sector{" +
                "subSectorArray=" + subSector +
                "} " + super.toString();
    }

    /**
     * Определение функций получения знаячений и присваивания занчений со всеми подлежащими проверками.
     */

    public SubSector getSubSector() {
        return subSector;
    }

    public void setSubSector(SubSector subSector) {
        if (subSector == null) {
            this.subSector = new SubSector() ;
        } else {
            this.subSector = subSector;
        }
    }
}
