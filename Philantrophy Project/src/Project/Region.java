package Project; /**
 * Created by Sergey on 27.07.2016.
 */

import Classifire.ClassifierImpl;

import java.math.BigDecimal;
import java.util.*;

public class Region extends ClassifierImpl {
    private ArrayList<Province> provinces;
    private BigDecimal percentSum;


    public Region() {
    }

    public Region(int id, String name, ArrayList<Province> provinces) {
        super(id, name);
        percentSum = new BigDecimal(0);
        if (provinces == null) {
            //throw new IllegalArgumentException("provinces cannot be null.");
            this.provinces = new ArrayList<Province>();
        } else {
            this.provinces = provinces;
            for (int i = 0; i < provinces.size(); ++i) {
                if (percentSum.add(provinces.get(i).getDistrictsPercentSum()).compareTo(new BigDecimal(100)) > 0) {
                    throw new IllegalArgumentException("percent cannot be greater than 100.");
                } else {
                    this.percentSum = percentSum.add(provinces.get(i).getDistrictsPercentSum());

                }
            }
        }
    }

    /**
     * Перегрузка функции toString
     */
    @Override
    public String toString() {
        return "Region{" +
                "provinces=" + provinces +
                "} " + super.toString();
    }

    /**
     * Добавление и удаление Провинции в массив
     */

    public void addProvince(Province pr) {
        provinces.add(pr);
    }

    public boolean removeProvince(Province pr) {
        for (int i = 0; i < provinces.size(); ++i) {
            if (provinces.get(i).equals(pr)) {
                provinces.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * gets and sets
     */

    public ArrayList<Province> getProvinces() {
        return provinces;
    }

    public void setProvincesArray(ArrayList<Province> provinces) {
        if (provinces == null) {
            this.provinces = new ArrayList<Province>();
        } else {
            this.provinces = provinces;
        }
    }

    public BigDecimal getPercent() {
        return this.percentSum;
    }

    public void setProvinces(ArrayList<Province> provinces) {
        this.provinces = provinces;
    }
}
