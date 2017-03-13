package Project;
/**
 * Created by Sergey on 27.07.2016.
 */

import Classifire.ChildClassifireImpl;

import java.math.BigDecimal;
import java.util.*;

public class Province extends ChildClassifireImpl {
    private ArrayList<District> districts;
    private BigDecimal districtsPercentSum;

    public Province(ArrayList<District> districts) {
        this.districts = districts;
    }

    public Province(int id, String name, int parentId, ArrayList<District> districts) {
        super(id, name, parentId);
        districtsPercentSum = new BigDecimal(0);
        if (districts == null) {
            //throw new IllegalArgumentException("districts cannot be null.");
            this.districts = new ArrayList<District>();
        } else {
            this.districts = districts;
            for (int i = 0; i < districts.size(); ++i) {
                if (districtsPercentSum.add(districts.get(i).getPercent()).compareTo(new BigDecimal(100)) > 0) {
                    throw new IllegalArgumentException("percent cannot be greater than 100.");
                } else {
                    this.districtsPercentSum = districtsPercentSum.add(districts.get(i).getPercent());
                }
            }
        }
    }

    /**
     * Перегрузка функции toString
     */

    @Override
    public String toString() {
        return "Project.Province{" +
                "districts=" + districts +
                "} " + super.toString();
    }

    /**
     * Добавление и удаление Дистрикта в массив
     */

    public void addProvince(District pr) {
        districts.add(pr);
    }

    public boolean removeProvince(District pr) {
        for (int i = 0; i < districts.size(); ++i) {
            if (districts.get(i).equals(pr)) {
                districts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * gets and sets
     */


    public ArrayList<District> getDistricts() {
        return districts;
    }

    public void setDistrictsArray(ArrayList<District> districts) {
        if (districts == null) {
            throw new IllegalArgumentException("districts cannot be null.");
        } else {
            this.districts = districts;
        }
    }

    public BigDecimal getDistrictsPercentSum() {
        return districtsPercentSum;
    }
}
