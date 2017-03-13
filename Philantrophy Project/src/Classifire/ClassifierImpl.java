package Classifire;

/**
 * Created by Sergey on 27.07.2016.
 */
public abstract class ClassifierImpl implements Classifire {
    private int id;
    private String name;

    /**
     * Перегрузка функций toString , equals , hashMap
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassifierImpl that = (ClassifierImpl) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public ClassifierImpl() {
    }

    public ClassifierImpl(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
